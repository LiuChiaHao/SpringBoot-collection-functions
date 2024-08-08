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
    // this CLIENT_ID is get from the Paypal test client
    private static final String CLIENT_ID = "AZHRMTtJAPnKOhUz_n-PjlkI7XUr9Egr7NQLMN4n4VzGVYr_luSXhw7_FB432MXM7y2Hnt363UEpwQIj";
    // this CLIENT_SECRET is get from the Paypal test client secret
    private static final String CLIENT_SECRET = "EJh4r1rxvTnkN3_k35PuCTINw8q64EiAg_rmtWb3JaIXWb3HLjx1ibWBW4MkpbPPkeF2pWuYhaaLUsYm";
    // A sandbox is a testing environment that allows developers to simulate interactions with a service without affecting live data or operations
    private static final String PAYPAL_API_BASE = "https://api-m.sandbox.paypal.com";

    private static String productId = null;

    // to create a Paypal order
    public String createOrder() {
        String response;
        try {
            //retrieve an access token
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return "Error occurred while obtaining access token";
            }

            URL url = new URL(PAYPAL_API_BASE + "/v2/checkout/orders");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            //
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
            //created with the PayPal API endpoint for obtaining an OAuth 2.0 token
            URL url = new URL(PAYPAL_API_BASE + "/v1/oauth2/token");
            //An HttpURLConnection object is opened to this URL, and the request method is set to POST.
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            //client ID and client secret are concatenated with a colon (:) and then encoded in Base64.
            String auth = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            httpConn.setRequestProperty("Authorization", "Basic " + encodedAuth);
            //The Content-Type header is set to application/x-www-form-urlencoded.
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);

            //OutputStreamWriter use to write the request body to the connection output stream
            try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
                writer.write("grant_type=client_credentials");
                writer.flush();
            }

            //based on the HTTP response code, if success getInputString otherwise getErrorString
            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            //read the entire response from the response stream.
            //response string passed to the parseAccessToken method to extract the access token.
            try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
                String response = s.hasNext() ? s.next() : "";
                accessToken = parseAccessToken(response);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return accessToken;
    }

    //extracts an access token from a JSON response string
    private String parseAccessToken(String response) {
        // This is a regular expression pattern designed to match the value of the access_token field in a JSON string.
        String tokenPattern = "\"access_token\":\"(.*?)\"";
        //Compiles the regular expression into a Pattern object
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(tokenPattern);
        //Creates a Matcher object that will match the compiled pattern against the provided response string.
        java.util.regex.Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            //Returns the first (and only) capturing group from the match, which is the access token.
            return matcher.group(1);
        }
        return null;
    }
}
