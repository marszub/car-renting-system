package pl.edu.agh.payment.payment.service;

import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Component
public class PayUCommunicator {
    private String currentOauthToken = "";
    private Long tokenValidUntil = 0L;
    private final String clientSecret;
    private final String clientId;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String secondToken;
    private final String notificationUrl;
    private final String continueUrl;

    public PayUCommunicator(Environment environment) {
        this.clientSecret = environment.getProperty("payu.client.secret");
        this.clientId = environment.getProperty("payu.client.id");
        this.secondToken = environment.getProperty("payu.client.second-token");
        this.notificationUrl = environment.getProperty("payu.client.notificationUrl");
        this.continueUrl = environment.getProperty("payu.client.continueUrl");
    }

    public HttpResponse<String> newPaymentRequest(JSONObject body) throws URISyntaxException,
                                                                          IOException,
                                                                          InterruptedException {
        if(System.currentTimeMillis() > tokenValidUntil) {
            renewToken();
        }
        //Adding other necessary data to request
        body.put("continueUrl", this.continueUrl);
        body.put("customerIp", "127.0.0.1");
        body.put("notifyUrl", this.notificationUrl);
        body.put("merchantPosId", this.clientId);

        System.out.println("-----------------------------------------------------------------------------\n");
        HttpResponse<String> response = sendPaymentRequest(body);
        System.out.println(response);
        System.out.println("-----------------------------------------------------------------------------\n\n\n");
        if (response.statusCode() == 401) {
            renewToken();
            return sendPaymentRequest(body);
        }
        return response;
    }

    public boolean validateNotification(final HttpEntity<String> data) {
        String signature = getSignature(data.getHeaders());
        if(signature == null) {
            return false;
        }
        System.out.println("-------SIGNATURE-------" + signature + "\n\n");
        Object body = data.getBody();
        if(body == null) {
            return false;
        }
        String stringBody = body.toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return false;
        }
        md.update((stringBody + this.secondToken).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5PayU-------" + sb + "\n\n");

        return sb.toString().equalsIgnoreCase(signature);
    }

    private HttpResponse<String> sendPaymentRequest(JSONObject body) throws IOException,
                                                                            InterruptedException,
                                                                            URISyntaxException {
        HttpRequest request;
        request = HttpRequest.newBuilder(new URI("https://secure.snd.payu.com/api/v2_1/orders"))
                .header("Authorization", "Bearer " + this.currentOauthToken)
                .header("Content-Type", " application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        System.out.println(request);
        System.out.println("=============================================================================\n");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void renewToken() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request =
                HttpRequest.newBuilder(new URI("https://secure.snd.payu.com/pl/standard/user/oauth/authorize"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=client_credentials&client_id=" +
                        this.clientId + "&client_secret=" + this.clientSecret)).build();
        HttpResponse<String> response = this.client.send(request,
                HttpResponse.BodyHandlers.ofString());
        JSONObject obj = new JSONObject(response.body());
        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.println(request);
        System.out.println("=============================================================================\n");
        System.out.println(response);
        System.out.println("-----------------------------------------------------------------------------\n\n\n");
        String token = obj.getString("access_token");
        int expiresIn = obj.getInt("expires_in");
        this.tokenValidUntil = System.currentTimeMillis() + expiresIn * 950L;
        this.currentOauthToken = token;
    }

    private String getSignature(final HttpHeaders headers) {
        List<String> signaturesList = headers.get("OpenPayu-Signature");
        if(signaturesList == null || signaturesList.size() != 1) {
            return null;
        }
        String signatures = signaturesList.get(0);
        List<String> splitList = List.of(signatures.split(";"));
        for (String s : splitList) {
            String[] element = s.split("=");
            if (Objects.equals(element[0], "signature")) {
                return element[1];
            }
        }
        return null;
    }
}
