package com.trxyzng.trung.refresh_token_server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
public class RefreshTokenController {
    public void checkRefreshToken(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String cookieHeader = headers.getFirst(HttpHeaders.COOKIE);
        if (cookieHeader != null) {
            // Do something with the cookie header, parse it to extract individual cookies if needed
            System.out.println("Cookie Header: " + cookieHeader);
        } else {
            System.out.println("No Cookie Header found in the request");
        }
    }
}
