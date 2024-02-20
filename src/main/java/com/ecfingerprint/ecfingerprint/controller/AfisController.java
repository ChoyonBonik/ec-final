//package com.ecfingerprint.ecfingerprint.controller;
//
//import com.ecfingerprint.ecfingerprint.model.FingerprintModel;
//import com.ecfingerprint.ecfingerprint.service.FingerUploadService;
//import com.ecfingerprint.ecfingerprint.service.ResultCheckService;
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.*;
//import java.net.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Base64;
//
//@Controller
//public class AfisController {
//
//    private byte[] right_thumb;
//    private byte[] right_index;
//    private byte[] left_thumb;
//    private byte[] left_index;
//
//    @Value("${spring.datasource.baseUrl}")
//    private String baseUrl;
//
//    @Value("${spring.datasource.bankusername}")
//    private String bankUsername;
//
//    @Value("${spring.datasource.bankpassword}")
//    private String bankPassword;
//
//    private String rightThumbUrl;
//    private String rightIndexUrl;
//    private String leftThumbUrl;
//    private String leftIndexUrl;
//
//
//    private String fileDirectoryPath;
//    private String access_token;
//
//    private String resultCheckApi;
//
//    private String  verificationPayload;
//
//
//    @PostMapping("/callEcSdk")
//    public String callEcSdk(HttpEntity<String> httpEntity) throws URISyntaxException, IOException {
//        String jsonResponse = httpEntity.getBody();
//        //System.out.println("Received JSON: " + jsonResponse);
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.fromJson(jsonResponse, JsonElement.class);
//        FingerprintModel fingerprintModel = new FingerprintModel();
//        fingerprintModel.setDateOfBirth(jsonElement.getAsJsonObject().get("dateOfBirth").getAsString());
//        System.out.println("cric"+fingerprintModel.getDateOfBirth());
//        String nid = jsonElement.getAsJsonObject().get("nid").getAsString();
//        if (nid.length() == 10) {
//            fingerprintModel.setNid10Digit(nid);
//        } else if (nid.length() == 17) {
//            fingerprintModel.setNid17Digit(nid);
//        }
//        fingerprintModel.setRightThumb(jsonElement.getAsJsonObject().get("right_thumb").getAsString());
//        fingerprintModel.setRightIndex(jsonElement.getAsJsonObject().get("right_index").getAsString());
//        fingerprintModel.setLeftThumb(jsonElement.getAsJsonObject().get("right_index").getAsString());
//        fingerprintModel.setLeftIndex(jsonElement.getAsJsonObject().get("left_index").getAsString());
//
//        right_thumb = Base64.getDecoder().decode(fingerprintModel.getRightThumb());
//        right_index = Base64.getDecoder().decode(fingerprintModel.getRightIndex());
//        left_thumb = Base64.getDecoder().decode(fingerprintModel.getLeftThumb());
//        left_index = Base64.getDecoder().decode(fingerprintModel.getLeftIndex());
//
//
////        decodeAndSaveAsWSQ(fingerprintModel.getRightThumb(), "right_thumb.wsq", nid);
////        decodeAndSaveAsWSQ(fingerprintModel.getRightIndex(), "right_index.wsq", nid);
////        decodeAndSaveAsWSQ(fingerprintModel.getLeftThumb(), "left_thumb.wsq", nid);
////        decodeAndSaveAsWSQ(fingerprintModel.getLeftIndex(), "left_index.wsq", nid);
//
//
//        try {
//            URL loginUrl = new URL(baseUrl+"/partner-service/rest/auth/login");
//            HttpURLConnection loginConnection = (HttpURLConnection) loginUrl.openConnection();
//            loginConnection.setRequestMethod("POST");
//            loginConnection.setRequestProperty("Content-Type", "application/json");
//
//            loginConnection.setDoOutput(true);
//            String jsonInputString = "{\"password\":\""+bankPassword+"\",\"username\":\""+bankUsername+"\"}";
//            System.out.println("jsonInputString" + jsonInputString);
//            try (DataOutputStream outputStream = new DataOutputStream(loginConnection.getOutputStream())) {
//                outputStream.writeBytes(jsonInputString);
//                outputStream.flush();
//            }
//
//            int loginResponseCode = loginConnection.getResponseCode();
//            if (loginResponseCode == HttpURLConnection.HTTP_OK) {
//                try (BufferedReader in = new BufferedReader(new InputStreamReader(loginConnection.getInputStream()))) {
//                    String inputLine;
//                    StringBuilder response = new StringBuilder();
//                    while ((inputLine = in.readLine()) != null) {
//                        response.append(inputLine);
//                    }
//
//                    Gson loginGson = new Gson();
//                    JsonElement loginJsonElement = loginGson.fromJson(response.toString(), JsonElement.class);
//
//                    if (loginJsonElement.isJsonObject()) {
//                        JsonObject loginJsonObject = loginJsonElement.getAsJsonObject();
//
//                        if (loginJsonObject.has("success") && loginJsonObject.getAsJsonObject("success").has("data")) {
//                            JsonObject loginDataObject = loginJsonObject.getAsJsonObject("success").getAsJsonObject("data");
//
//                            if (loginDataObject.has("access_token")) {
//                                access_token = loginDataObject.get("access_token").getAsString();
//                                System.out.println("Access Token: " + access_token);
//
//                            } else {
//                                System.out.println("Access Token not found in the response.");
//                            }
//                        } else {
//                            System.out.println("Invalid response structure.");
//                        }
//                    } else {
//                        System.out.println("Invalid JSON response.");
//                    }
//
//                }
//            } else {
//                System.out.println("POST request failed with response code: " + loginResponseCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            HttpURLConnection verificationConnection;
//            URL verificationUrl = new URL(baseUrl+"/partner-service/rest/afis/verification-secured");
//            verificationConnection = (HttpURLConnection) verificationUrl.openConnection();
//
//            verificationConnection.setRequestMethod("POST");
//            verificationConnection.setRequestProperty("Content-Type", "application/json");
//
//            System.out.println("2access_token" + access_token);
//            verificationConnection.setRequestProperty("Authorization", "Bearer " + access_token);
//
//            verificationConnection.setDoInput(true);
//            verificationConnection.setDoOutput(true);
//            verificationConnection.setUseCaches(false);
//
//
//
//            if (fingerprintModel.getNid10Digit() != null && !fingerprintModel.getNid10Digit().isEmpty()) {
//                 verificationPayload = "{ \"dateOfBirth\":\""+fingerprintModel.getDateOfBirth()+"\", \"nid10Digit\":\""+fingerprintModel.getNid10Digit()+"\", \"fingerEnums\": [\"RIGHT_THUMB\", \"RIGHT_INDEX\", \"LEFT_THUMB\", \"LEFT_INDEX\"] }";
//            } else if (fingerprintModel.getNid17Digit() != null && !fingerprintModel.getNid17Digit().isEmpty()){
//                 verificationPayload = "{ \"dateOfBirth\":\""+fingerprintModel.getDateOfBirth()+"\", \"nid17Digit\":\""+fingerprintModel.getNid17Digit()+"\", \"fingerEnums\": [\"RIGHT_THUMB\", \"RIGHT_INDEX\", \"LEFT_THUMB\", \"LEFT_INDEX\"] }";
//            }
//
//            try (DataOutputStream outputStream = new DataOutputStream(verificationConnection.getOutputStream())) {
//                outputStream.writeBytes(verificationPayload);
//                outputStream.flush();
//            }
//
//            int verificationResponseCode = verificationConnection.getResponseCode();
//            System.out.println("Response Code: " + verificationResponseCode);
//
//            if (verificationResponseCode == HttpURLConnection.HTTP_OK || verificationResponseCode == HttpURLConnection.HTTP_ACCEPTED) {
//                System.out.println("Request succeeded. Response Message: " + verificationConnection.getResponseMessage());
//
//                StringBuilder responseStringBuilder = new StringBuilder();
//                BufferedReader reader2 = new BufferedReader(new InputStreamReader(verificationConnection.getInputStream()));
//                String line2;
//                while ((line2 = reader2.readLine()) != null) {
//                    responseStringBuilder.append(line2);
//                }
//                reader2.close();
//
//                JSONObject verificationJsonResponse = new JSONObject(responseStringBuilder.toString());
//                JSONObject verificationSuccessData = verificationJsonResponse.getJSONObject("success").getJSONObject("data");
//                String resultCheckApi = verificationSuccessData.getString("resultCheckApi");
//
//                System.out.print("returnedResult" + resultCheckApi);
//
//                JSONArray fingerUploadUrls = verificationSuccessData.getJSONArray("fingerUploadUrls");
//
//                for (int i = 0; i < fingerUploadUrls.length(); i++) {
//                    JSONObject fingerUrlObject = fingerUploadUrls.getJSONObject(i);
//
//                    String finger = fingerUrlObject.getString("finger");
//                    String url = fingerUrlObject.getString("url");
//                    if (finger.equals("RIGHT_THUMB")) {
//                        rightThumbUrl = url;
//                        System.out.println("rightThumbUrl" + rightThumbUrl);
//                    } else if (finger.equals("RIGHT_INDEX")) {
//                        rightIndexUrl = url;
//                    } else if (finger.equals("LEFT_THUMB")) {
//                        leftThumbUrl = url;
//                    } else if (finger.equals("LEFT_INDEX")) {
//                        leftIndexUrl = url;
//                    }
//                    System.out.println(finger + " URL: " + url);
//                }
//
////                String directoryPath = fileDirectoryPath + "\\right_thumb.wsq";
////                System.out.println("directoryPath is" + directoryPath);
////                FingerUploadService.fingerUpload(directoryPath, rightThumbUrl);
////                directoryPath = fileDirectoryPath + "\\right_index.wsq";
////                FingerUploadService.fingerUpload(directoryPath, rightIndexUrl);
////                directoryPath = fileDirectoryPath + "\\left_thumb.wsq";
////                FingerUploadService.fingerUpload(directoryPath, leftThumbUrl);
////                directoryPath = fileDirectoryPath + "\\left_index.wsq";
////                FingerUploadService.fingerUpload(directoryPath, leftIndexUrl);
//
//
//
//                FingerUploadService.fingerUpload(right_thumb,rightThumbUrl);
//
//                FingerUploadService.fingerUpload(right_index,rightIndexUrl);
//
//                FingerUploadService.fingerUpload(left_thumb,leftThumbUrl);
//
//                FingerUploadService.fingerUpload(left_index,leftIndexUrl);
//
//                try {
//                    String resultCheckUrl = baseUrl + resultCheckApi;
//                    System.out.println("resultCheckUrl" + resultCheckUrl);
//
//                    String returnedResult = ResultCheckService.resultCheck(resultCheckUrl, access_token);
//
//                    if (returnedResult.equals("PROCESSING_PENDING") || returnedResult.equals("PROCESSING")) {
//
//                        String matchingResult = ResultCheckService.resultCheck(resultCheckUrl, access_token);
//
//                        if (matchingResult.equals("MATCH FOUND")) {
//                            System.out.println("MATCH FOUND");
//                        }
//
//                    } else {
//                        System.out.println("Error. Response Code: " + verificationResponseCode);
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                reader2.close();
//
//                System.out.println("Response Body: " + verificationJsonResponse.toString());
//            }
//
//            verificationConnection.disconnect();
//
//
//            verificationConnection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    void decodeAndSaveAsWSQ(String fingerData, String fileName, String nid) throws URISyntaxException {
//
//        byte[] decodedBytes = Base64.getDecoder().decode(fingerData);
//
//        URI uri = ClassLoader.getSystemResource("assets").toURI();
//        String mainPath = Paths.get(uri).toString();
//        Path assetsPath = Paths.get(mainPath);
//
//        Path directoryPath = assetsPath.resolve(nid);
//        fileDirectoryPath = directoryPath.toString();
//
//        if (!Files.exists(directoryPath)) {
//            try {
//                Files.createDirectories(directoryPath);
//            } catch (IOException e) {
//                System.err.println("Error creating directory: " + e.getMessage());
//            }
//        }
//
//        Path wsqFilePath = directoryPath.resolve(fileName);
//        try (FileOutputStream fos = new FileOutputStream(wsqFilePath.toFile())) {
//            fos.write(decodedBytes);
//            System.out.println("Decoded finger data saved as WSQ file successfully: " + fileName);
//        } catch (IOException e) {
//            System.err.println("Error occurred while saving WSQ file: " + e.getMessage());
//        }
//    }
//
//}
//
//
//
