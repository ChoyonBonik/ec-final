package com.ecfingerprint.ecfingerprint.service;

import com.ecfingerprint.ecfingerprint.util.httpUtil.HttpGet;
import com.ecfingerprint.ecfingerprint.util.httpUtil.HttpPost;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ResultCheckService {

    public static String resultCheck(String url, String accessToken) throws IOException {

        String result="";

        HttpGet resultCheckHttpPost = new HttpGet(url);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Bearer " + accessToken);
        resultCheckHttpPost.setHeaders(headerMap);
        int response = resultCheckHttpPost.execute();
        String responseBody = "";
        if (response == 200) {
            responseBody = resultCheckHttpPost.getResponseBody().toString();
            JSONObject jsonResponse = new JSONObject(resultCheckHttpPost.getResponseBody().toString());
            JSONObject successData = jsonResponse.getJSONObject("success").getJSONObject("data");
            result = successData.getString("result");
            System.out.println("result "+jsonResponse);

        }
        return result;
    }
}
