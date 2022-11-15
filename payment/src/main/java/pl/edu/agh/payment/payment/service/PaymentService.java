package pl.edu.agh.payment.payment.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import pl.edu.agh.payment.payment.auth.User;
import pl.edu.agh.payment.payment.dto.InputNotification;
import pl.edu.agh.payment.payment.dto.Redirect;
import pl.edu.agh.payment.payment.dto.PaymentData;
import pl.edu.agh.payment.payment.dto.PaymentInputData;
import pl.edu.agh.payment.payment.dto.PaymentList;
import pl.edu.agh.payment.payment.error.NotificationNotValidatedError;
import pl.edu.agh.payment.payment.persistence.Payment;
import pl.edu.agh.payment.payment.persistence.PaymentRepository;
import pl.edu.agh.payment.payment.persistence.PaymentStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import Rental.UnauthorizedRequestException;
import Rental.RentalDoesNotExistException;
import Rental.RentalSummaryData;

@Service
public class PaymentService {
    private final PayUCommunicator payUCommunicator;
    private final PaymentRepository paymentRepository;
    private final Rental rental;

    public PaymentService(final PayUCommunicator payUCommunicator, final PaymentRepository paymentRepository, final Rental rental) {
        this.payUCommunicator = payUCommunicator;
        this.paymentRepository = paymentRepository;
        this.rental = rental;
    }

    public Redirect createPayment(final PaymentInputData data, final User user) throws RentalDoesNotExistException,
                                                                                       UnauthorizedRequestException {
        try {
            RentalSummaryData rentalData = null;
            try {
                rentalData = rental.endRental(data.rentalId(), user.id());
            } catch (UnauthorizedRequestException e) {
                System.out.println("UNAUTHORIZED REQUEST EXCEPTION");
                throw new UnauthorizedRequestException("Unauthorized ice call");
            } catch (RentalDoesNotExistException e) {
                System.out.println("RENTAL DOES NOT EXIST EXCEPTION");
                throw new RentalDoesNotExistException("Rental with id: " + data.rentalId() + " does not exist");
            }

            JSONObject body = prepareBody(data, rentalData);
            HttpResponse<String> response = payUCommunicator.newPaymentRequest(body);
            JSONObject paymentData = new JSONObject(response.body());
            if(!Objects.equals(paymentData.getJSONObject("status").getString("statusCode"), "SUCCESS")) {
                System.out.println(response.body());
                throw new UnknownError();
            }

            paymentRepository.save(new Payment(paymentData.getString("orderId"), data.rentalId(),
                                               PaymentStatus.NEW, rentalData.rentalCost));
            return new Redirect(paymentData.getString("redirectUri"));
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        throw new UnknownError();
    }

    public void receiveNotification(final HttpEntity<String> httpEntity) throws NotificationNotValidatedError {
        boolean validated = payUCommunicator.validateNotification(httpEntity);
        if(!validated) {
            throw new NotificationNotValidatedError();
        }
        JSONObject notificationData = new JSONObject(httpEntity.getBody());
        JSONObject order = notificationData.getJSONObject("order");
        String payUId = order.getString("orderId");
        PaymentStatus status = PaymentStatus.valueOf(order.getString("status"));
        Optional<Payment> payment = paymentRepository.getPaymentByPayUId(payUId);
        if(payment.isPresent()) {
            if(payment.get().getPaymentStatus() != PaymentStatus.COMPLETED) {
                paymentRepository.updatePaymentStatus(payUId, status);
            }
        }
    }

    public PaymentList getPayments() {
        List<Payment> paymentList = paymentRepository.getAllPayments();
        return new PaymentList(paymentList.stream().map(PaymentData::new).toList());
    }

    private JSONObject prepareBody(final PaymentInputData data, final RentalSummaryData rentalSummaryData) {
        long cost = rentalSummaryData.rentalCost;
        JSONObject body = new JSONObject();
        body.put("description", "Car rental summary");
        body.put("currencyCode", "PLN");
        body.put("totalAmount", Long.toString(cost));
        JSONObject buyer = new JSONObject();
        buyer.put("email", data.email());
        buyer.put("language", "en");
        body.put("buyer", buyer);
        JSONObject product1 = new JSONObject();
        product1.put("name", "Renting a car");
        product1.put("unitPrice", Long.toString(cost));
        product1.put("quantity", "1");
        List<JSONObject> list = new LinkedList<>();
        list.add(product1);
        body.put("products", list);
        return body;
    }

    //to be deleted, only while payment is not on server
    public void sendNotification(InputNotification inputNotification) {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject body = new JSONObject();
        PaymentStatus status = PaymentStatus.valueOf(inputNotification.changeValueTo());
        JSONObject order = new JSONObject();
        order.put("orderId", inputNotification.paymentPayuId());
        order.put("status", status.toString());
        body.put("order", order);
        String signature = countSignature(body.toString());
        try {
            HttpRequest request =
                    HttpRequest.newBuilder(new URI("http://localhost:5070/api/payment/notification"))
                            .setHeader("OpenPayu-Signature", "signature=" + signature)
                            .setHeader("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //to be deleted, only while payment is not on server
    private String countSignature(final String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return "";
        }
        md.update((string + "75604486e320ee3b789fb6a0daef968a").getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5PayU-------" + sb + "\n\n");

        return sb.toString();

    }
}

