package com.ecfingerprint.ecfingerprint.service;

import com.ecfingerprint.ecfingerprint.enums.RequestStatus;
import com.ecfingerprint.ecfingerprint.util.httpUtil.HttpPost;
import com.ecfingerprint.ecfingerprint.util.httpUtil.HttpPut;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FingerUploadService {

    public static String fingerUpload(byte[] fingerData, String Url) {

        String requestStatus = null;

        try {

            HttpPut fingerUploadHttpPost = new HttpPut(Url);
            System.out.println("zzk"+fingerData);

            fingerUploadHttpPost.setHeader("Content-Type", "application/octet-stream");
            fingerUploadHttpPost.setByteBody(fingerData);
            int response = fingerUploadHttpPost.execute();
            String responseBody = "";
            if (response == 200) {
                responseBody = fingerUploadHttpPost.getResponseBody().toString();
                System.out.println("finger Upload"+responseBody.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestStatus;
    }
}
