package com.trxyzng.trung.photo_api;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
public class PhotoController {
    @Value("${photo_storage_url}")
    private String photo_storage_url;
    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public ResponseEntity<String> UsernamePasswordSignUp() throws IOException {
        Cloudinary cloudinary = new Cloudinary(photo_storage_url);
        cloudinary.config.secure = true;
        cloudinary.uploader().upload(
        "https://www.youtube.com/watch?v=QohH89Eu5iM",
            ObjectUtils.asMap(
        "public_id", "test_video",
                "folder", "test",
                "use_filename", true,
                "unique_filename", false,
                "allowed_formats", "mp4, jpg, png"
            )
        );
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpGet httpget = new HttpGet("https://res.cloudinary.com/trxyzng-photo-storage/image/upload/v1711289964/test/test.jpg");
//
////            httpget.addHeader("content-type", "image/jpg");
//            httpClient.execute(httpget, response -> {
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    FileOutputStream outstream = new FileOutputStream(new File("download-image.jpg"));
//                    entity.writeTo(outstream);
//                }
//                HttpHeaders headers = new HttpHeaders();
//                return new ResponseEntity<>(headers, HttpStatus.OK);
//                });

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
