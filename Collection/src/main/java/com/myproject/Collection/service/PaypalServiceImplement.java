package com.myproject.Collection.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

@Service
public class PaypalServiceImplement implements PaypalService{

    private static final String CLIENT_ID = "AZHRMTtJAPnKOhUz_n-PjlkI7XUr9Egr7NQLMN4n4VzGVYr_luSXhw7_FB432MXM7y2Hnt363UEpwQIj";
    private static final String CLIENT_SECRET = "EJh4r1rxvTnkN3_k35PuCTINw8q64EiAg_rmtWb3JaIXWb3HLjx1ibWBW4MkpbPPkeF2pWuYhaaLUsYm";
    private static final String PAYPAL_API_BASE = "https://api-m.sandbox.paypal.com";

    private static String productId = null;

    public String createOrder() {
        String response;
        try {
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return "Error occurred while obtaining access token";
            }

            URL url = new URL(PAYPAL_API_BASE + "/v2/checkout/orders");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);

            httpConn.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
                writer.write("{ \"intent\": \"CAPTURE\", \"purchase_units\": [ { \"amount\": { \"currency_code\": \"USD\", \"value\": \"100.00\" } } ] }");
                writer.flush();
            }
            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
                response = s.hasNext() ? s.next() : "";
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            productId = jsonNode.path("id").asText();


        } catch (IOException ex) {
            ex.printStackTrace();
            response = "Error occurred while creating order: " + ex.getMessage();
        }
        return response;
    }

    public String showOrderDetail() {
        String response;
        try {
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return "Error occurred while obtaining access token";
            }

            if(productId == null) return "You have to send an order first ";

            URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + productId);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            httpConn.setRequestProperty("Authorization", "Bearer "  + accessToken);

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            response = s.hasNext() ? s.next() : "";
            System.out.println(response);

        }catch (IOException ex){
            ex.printStackTrace();
            response = "Error occurred while creating order: " + ex.getMessage();
        }
        return response;
    }

    private String getAccessToken() {
        String accessToken = null;
        try {
            URL url = new URL(PAYPAL_API_BASE + "/v1/oauth2/token");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            String auth = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            httpConn.setRequestProperty("Authorization", "Basic " + encodedAuth);
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
                writer.write("grant_type=client_credentials");
                writer.flush();
            }

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
                String response = s.hasNext() ? s.next() : "";
                accessToken = parseAccessToken(response);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return accessToken;
    }

    private String parseAccessToken(String response) {
        String tokenPattern = "\"access_token\":\"(.*?)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(tokenPattern);
        java.util.regex.Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
