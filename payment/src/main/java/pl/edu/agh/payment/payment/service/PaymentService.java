package pl.edu.agh.payment.payment.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.edu.agh.payment.payment.dto.Message;
import pl.edu.agh.payment.payment.dto.PaymentInputData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;

@Service
public class PaymentService {
    public Message getToken(final PaymentInputData data) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("https://secure.snd.payu.com/pl/standard/user/oauth/authorize"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=451870&client_secret=0778ae48ed2a8f0c54b0e2c433e2f7bd")).build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(response.body());
            String token = obj.getString("access_token");

            JSONObject body = new JSONObject();
            body.put("customerIp", "127.0.0.1");
            body.put("notifyUrl", "http://165.227.245.183:5070/api/payment");
            body.put("merchantPosId", "451870");
            body.put("description", "car");
            body.put("currencyCode", "PLN");
            body.put("totalAmount", "2100");
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

            request = HttpRequest.newBuilder(new URI("https://secure.snd.payu.com/api/v2_1/orders"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", " application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            return new Message(response.body());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Message("ERROR");
    }
}
