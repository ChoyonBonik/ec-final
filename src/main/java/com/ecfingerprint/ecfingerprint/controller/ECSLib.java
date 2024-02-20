package com.ecfingerprint.ecfingerprint.controller;

import com.ecfingerprint.ecfingerprint.enums.Fingers;
import com.ecfingerprint.ecfingerprint.model.FingerprintModel;
import com.ecfingerprint.ecfingerprint.service.AccessTokenService;
import com.ecfingerprint.ecfingerprint.service.FingerUploadService;
import com.ecfingerprint.ecfingerprint.service.ResultCheckService;
import com.ecfingerprint.ecfingerprint.util.httpUtil.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;

public class ECSLib {

    //first call

    String requestForToken(String username, String password) throws IOException {
        String accessToken = "";

        String loginUrl = "https://prportal.nidw.gov.bd/partner-service/rest/auth/login";
        String body = "{\"password\":\"" + password + "\",\"username\":\"" + username + "\"}";

        HttpPost loginHttpPost = new HttpPost(loginUrl);
        loginHttpPost.setHeader("Content-Type", "application/json");
        loginHttpPost.setBody(body);
        int response = loginHttpPost.execute();
        String responseBody = "";
        if (response == 200) {
            responseBody = loginHttpPost.getResponseBody().toString();
            accessToken = AccessTokenService.getAccessToken(responseBody);
            System.out.println(responseBody);
        }

        return accessToken;

    }

    // second call
    FingerprintModel requestForVerificationPath(String nid, String dob, String accessToken) throws IOException {
//          List<String> fingerArrays
        FingerprintModel fingerprintModel = new FingerprintModel();


        String verificationUrl = "https://prportal.nidw.gov.bd/partner-service/rest/afis/verification-secured";
        HttpPost verificationHttpPost = new HttpPost(verificationUrl);
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Bearer " + accessToken);

        String verificationBody = "{\"dateOfBirth\":\"" + dob + "\",\"nid10Digit\":\"" + nid + "\",\"fingerEnums\":[\"RIGHT_THUMB\",\"RIGHT_INDEX\",\"LEFT_THUMB\",\"LEFT_INDEX\"]}";


        verificationHttpPost.setHeaders(headerMap);
        verificationHttpPost.setBody(verificationBody.toString());
        int verificationResponse = verificationHttpPost.execute();

        String verificationResponseBody;
        if (verificationResponse == 202) {
            verificationResponseBody = verificationHttpPost.getResponseBody();

            System.out.println(verificationResponseBody);
            String responseBody = verificationHttpPost.getResponseBody();


            JSONObject verificationJsonResponse = new JSONObject(responseBody.toString());
            JSONObject verificationSuccessData = verificationJsonResponse.getJSONObject("success").getJSONObject("data");
            fingerprintModel.setResultCheckUrl(verificationSuccessData.getString("resultCheckApi"));

            System.out.print("returnedResult" + fingerprintModel.getResultCheckUrl());

            JSONArray fingerUploadUrls = verificationSuccessData.getJSONArray("fingerUploadUrls");

            for (int i = 0; i < fingerUploadUrls.length(); i++) {
                JSONObject fingerUrlObject = fingerUploadUrls.getJSONObject(i);

                String finger = fingerUrlObject.getString("finger");
                String url = fingerUrlObject.getString("url");
                if (finger.equals("RIGHT_THUMB")) {
                    fingerprintModel.setRightThumbUrl(url);

                } else if (finger.equals("RIGHT_INDEX")) {
                    fingerprintModel.setRightIndexUrl(url);
                } else if (finger.equals("LEFT_THUMB")) {
                    fingerprintModel.setLeftThumbUrl(url);
                } else if (finger.equals("LEFT_INDEX")) {
                    fingerprintModel.setLeftIndexUrl(url);
                }

                System.out.println(finger + " URL: " + url);
            }


        }


        return fingerprintModel;
    }

    // third call
    String fingerDataUpload(FingerprintModel fingerprintModel, HashMap<String, byte[]> fingers) {
        String rightThumbStatus = FingerUploadService.fingerUpload(fingers.get(Fingers.RIGHT_THUMB.name()), fingerprintModel.getRightThumbUrl());
        String rightIndexStatus = FingerUploadService.fingerUpload(fingers.get(Fingers.RIGHT_INDEX.name()), fingerprintModel.getRightIndexUrl());
        String leftThumbStatus = FingerUploadService.fingerUpload(fingers.get(Fingers.LEFT_THUMB.name()), fingerprintModel.getLeftThumbUrl());
        String leftIndexStatus = FingerUploadService.fingerUpload(fingers.get(Fingers.LEFT_INDEX.name()), fingerprintModel.getLeftIndexUrl());
        return "";
    }

    // 4th call... result check
    Object resultCheck(FingerprintModel fingerprintModel, String accessToken) throws IOException {
        String resultCheckUrl = "https://prportal.nidw.gov.bd" + fingerprintModel.getResultCheckUrl();
        System.out.println("resultCheckUrl" + resultCheckUrl);

        String returnedResult = ResultCheckService.resultCheck(resultCheckUrl, accessToken);
        String returnedResult2 = ResultCheckService.resultCheck(resultCheckUrl, accessToken);
        String returnedResult3 = ResultCheckService.resultCheck(resultCheckUrl, accessToken);


        if (returnedResult.equals("PROCESSING_PENDING") || returnedResult.equals("PROCESSING")) {

            String matchingResult = ResultCheckService.resultCheck(resultCheckUrl, accessToken);

            if (matchingResult.equals("MATCH FOUND")) {
                System.out.println("MATCH FOUND");
            }

        }
        return new Object();
    }
}
