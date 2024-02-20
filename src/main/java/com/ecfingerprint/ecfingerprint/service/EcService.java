package com.ecfingerprint.ecfingerprint.service;
import com.ecfingerprint.ecfingerprint.entity.Info;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class EcService {

    public String callEcSdk() {

        try {

            URL url = new URL("http://localhost:20000/api/info/fingerdata");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String raw = "{\"MinQ\":30,\"Retry\":3,\"TokenId\":\"g86v5s4g5se84g5sfd4g5werx25sdf4f\"}";
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            byte[] input = raw.getBytes("utf-8");
            os.write(input, 0, input.length);


            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);



            String rightThumb = jsonObject.get("0").getAsJsonObject().get("fingerData").getAsString();
            String rightIndex = jsonObject.get("1").getAsJsonObject().get("fingerData").getAsString();
            String leftThumb = jsonObject.get("2").getAsJsonObject().get("fingerData").getAsString();
            String leftIndex = jsonObject.get("3").getAsJsonObject().get("fingerData").getAsString();
            //System.out.println("RT" + rightThumb);



            return response.toString();

        } catch (Exception e) {
            System.out.println("Error calling ecSdk: " + e.getMessage());
            return null;
        }
    }

}