package com.ecfingerprint.ecfingerprint.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class DecodeUtil {

//    public static void decodeAndSaveAsWSQ(String fingerData, String fileName) {
//        // Decode fingerData from Base64
//        byte[] decodedBytes = Base64.getDecoder().decode(fingerData);
//
//        // Save decoded bytes as a WSQ file with the specified file name
//        // You can adjust the directory as needed
//        ClassLoader classLoader = DecodeUtil.class.getClassLoader();
//        URL resource = classLoader.getResource("assets/");
//        String directory = resource.getPath();
//        System.out.println("choyon" + directory);
//        Path wsqFilePath = Paths.get(directory + fileName);
//        try (FileOutputStream fos = new FileOutputStream(wsqFilePath.toFile())) {
//            fos.write(decodedBytes);
//            System.out.println("Decoded finger data saved as WSQ file successfully: " + fileName);
//        } catch (IOException e) {
//            System.err.println("Error occurred while saving WSQ file: " + e.getMessage());
//        }
//    }
}
