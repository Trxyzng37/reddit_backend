package com.trxyzng.trung.authentication.signin.usernamepassword;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import com.trxyzng.trung.authentication.shared.user.UserDetail;
import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
import com.trxyzng.trung.authentication.signin.pojo.UsernamePasswordSignInRequest;
import com.trxyzng.trung.authentication.signin.pojo.UsernamePasswordSignInResponse;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

//@CrossOrigin(origins = Constant.frontEndAddress, allowCredentials = "true")
//@CrossOrigin
@RestController
public class UsernamePasswordSignInController {
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserEntityRepo userEntityRepo;
    @Value("${frontendAddress}")
    private String frontEndAddress;
    @Value("${fullFrontendAddress}")
    private String fullFrontendAddress;

    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody UsernamePasswordSignInRequest requestBody) {
        try {
            int usernameExist = userEntityRepo.existByUsername(requestBody.getUsername());
            int emailExist = userEntityRepo.existByEmail(requestBody.getUsername());
            if(usernameExist == 1) {
                String expected_password = userEntityRepo.getPasswordByUsername(requestBody.getUsername());
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();  
                boolean isPasswordMatches = bcrypt.matches(requestBody.getPassword(), expected_password);  
                if(isPasswordMatches) {
                    int uid = userEntityRepo.findUidByUsername(requestBody.getUsername());
                    String token = RefreshTokenUtil.generateRefreshToken(uid);
                    refreshTokenService.saveRefreshToken(uid, token);
                    UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(true, false, uid);
                    String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age="+RefreshTokenUtil.EXPIRE_DURATION +"; SameSite=None; Secure; Path=/; HttpOnly; " +"Domain=" + frontEndAddress);
                    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
                }
                else {
                    UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(false, true, 0);
                    String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
                    return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
                }
            }
            if (emailExist == 1) {
                String expected_password = userEntityRepo.getPasswordByEmail(requestBody.getUsername());
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();  
                boolean isPasswordMatches = bcrypt.matches(requestBody.getPassword(), expected_password);  
                if(isPasswordMatches) {
                    int uid = userEntityRepo.findUidByEmail(requestBody.getUsername());
                    String token = RefreshTokenUtil.generateRefreshToken(uid);
                    refreshTokenService.saveRefreshToken(uid, token);
                    UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(true, false, uid);
                    String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age="+RefreshTokenUtil.EXPIRE_DURATION +"; SameSite=None; Secure; Path=/; HttpOnly; " +"Domain=" + frontEndAddress);
                    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
                }
                else {
                    UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(false, true, 0);
                    String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
                    return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
                }
            }
            UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(false, true, 0);
            String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
            return new ResponseEntity<>(responseBody, new HttpHeaders(), HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Error authenticate user", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/ping",method = RequestMethod.GET)
    public ResponseEntity<String> get() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(InetAddress.getLocalHost().getHostName());
        // Remote address
        System.out.println(InetAddress.getLoopbackAddress().getHostAddress());
        System.out.println(InetAddress.getLoopbackAddress().getHostName());
        HttpHeaders headers = new HttpHeaders();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy");
        String content = ZonedDateTime.now(ZoneId.of("UTC+7")).truncatedTo(ChronoUnit.SECONDS).format(formatter);
        System.out.println("ping server: "+content);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}

