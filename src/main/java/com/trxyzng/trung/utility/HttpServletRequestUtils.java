package com.trxyzng.trung.utility;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpServletRequestUtils {
    public static String readRequestBody(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            return requestBody.toString();
        }
        catch (IOException e) {
            System.out.println("Error read body of HttpServletRequest");
        }
        return "";
    }
}
