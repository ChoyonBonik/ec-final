package com.ecfingerprint.ecfingerprint.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    private static String access_token;

    public static String getAccessToken(String payload) {

        Gson loginGson = new Gson();
        JsonElement loginJsonElement = loginGson.fromJson(payload.toString(), JsonElement.class);

        if (loginJsonElement.isJsonObject()) {
            JsonObject loginJsonObject = loginJsonElement.getAsJsonObject();

            if (loginJsonObject.has("success") && loginJsonObject.getAsJsonObject("success").has("data")) {
                JsonObject loginDataObject = loginJsonObject.getAsJsonObject("success").getAsJsonObject("data");

                if (loginDataObject.has("access_token")) {
                    access_token = loginDataObject.get("access_token").getAsString();
                    System.out.println("Access Token: " + access_token);

                } else {
                    System.out.println("Access Token not found in the response.");
                }
            } else {
                System.out.println("Invalid response structure.");
            }
        }
        return access_token;
    }
}
