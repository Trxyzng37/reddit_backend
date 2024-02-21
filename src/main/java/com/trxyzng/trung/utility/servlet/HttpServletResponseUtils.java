package com.trxyzng.trung.utility.servlet;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public final class HttpServletResponseUtils {
    public static void sendResponseToClient(HttpServletResponse response, int status, String contentType, String encodeType, String body) {
        response.setStatus(status);
        response.setContentType(contentType);
        response.setCharacterEncoding(encodeType);
        try {
            response.getWriter().println(body);
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
