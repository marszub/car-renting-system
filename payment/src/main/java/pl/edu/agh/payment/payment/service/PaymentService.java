package pl.edu.agh.payment.payment.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import pl.edu.agh.payment.payment.dto.Message;
import pl.edu.agh.payment.payment.dto.PaymentData;
import pl.edu.agh.payment.payment.dto.PaymentInputData;
import pl.edu.agh.payment.payment.dto.PaymentList;
import pl.edu.agh.payment.payment.error.NotificationNotValidatedError;
import pl.edu.agh.payment.payment.persistence.Payment;
import pl.edu.agh.payment.payment.persistence.PaymentRepository;
import pl.edu.agh.payment.payment.persistence.PaymentStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {
    private final PayUCommunicator payUCommunicator;
    private final PaymentRepository paymentRepository;

    public PaymentService(final PayUCommunicator payUCommunicator, final PaymentRepository paymentRepository) {
        this.payUCommunicator = payUCommunicator;
        this.paymentRepository = paymentRepository;
    }

    public Message getToken(final PaymentInputData data) {
        try {
            JSONObject body = prepareBody(data);
            HttpResponse<String> response = payUCommunicator.newPaymentRequest(body);
            JSONObject paymentData = new JSONObject(response.body());
            if(!Objects.equals(paymentData.getJSONObject("status").getString("statusCode"), "SUCCESS")) {
                System.out.println(response.body());
                throw new UnknownError();
            }

            paymentRepository.save(new Payment(paymentData.getString("orderId"), data.rentalId(), PaymentStatus.NEW));
            return new Message(paymentData.getString("redirectUri"));
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
        paymentRepository.updatePaymentStatus(payUId, status);
    }

    public PaymentList getPayments() {
        List<Payment> paymentList = paymentRepository.getAllPayments();
        return new PaymentList(paymentList.stream().map(PaymentData::new).toList());
    }

    private JSONObject prepareBody(PaymentInputData data) {
        JSONObject body = new JSONObject();
        body.put("description", "car");
        body.put("currencyCode", "PLN");
        body.put("totalAmount", "2137");
        JSONObject buyer = new JSONObject();
        buyer.put("email", data.email());
        buyer.put("phone", data.phone());
        buyer.put("language", "en");
        body.put("buyer", buyer);
        JSONObject product1 = new JSONObject();
        product1.put("name", "Car rental");
        product1.put("unitPrice", "2137");
        product1.put("quantity", "1");
        List<JSONObject> list = new LinkedList<>();
        list.add(product1);
        body.put("products", list);
        return body;
    }
}

