package com.example.tender.json;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            // Prepare the JSON data
            String data = "{"
                    + "\"send\":\"\","
                    + "\"number\":\"932481337\","
                    + "\"text\":\"matn\","
                    + "\"token\":\"tDeMaUxHQEnOKfqoyAVPvXZNFpidJkhgTYGuILbRBsjmSrl\","
                    + "\"id\":267,"
                    + "\"user_id\":\"666771575\""
                    + "}";

            // Prepare the URL
            String baseUrl = "https://api.xssh.uz/smsv1/";
            String encodedData = URLEncoder.encode(data, String.valueOf(StandardCharsets.UTF_8));
            String url = baseUrl + "?data=" + encodedData;

            // Prepare the connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            // Send the request
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
