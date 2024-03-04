package com.trxyzng.trung.authentication.signin.usernamepassword;

import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenUtil;
import com.trxyzng.trung.authentication.shared.user.UserDetail;
import com.trxyzng.trung.authentication.signin.pojo.UsernamePasswordSignInResponse;
import com.trxyzng.trung.utility.Constant;
import com.trxyzng.trung.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

//@CrossOrigin(origins = Constant.frontEndAddress, allowCredentials = "true")
//@CrossOrigin
@RestController
public class UsernamePasswordSignInController {
    @Autowired
    RefreshTokenService refreshTokenService;
//    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login() {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int uid = user.getId();
        String token = RefreshTokenUtil.generateRefreshToken(uid);
        System.out.println("refresh_token using username password: " + token);
        refreshTokenService.saveRefreshToken(uid, token);
        System.out.println("Save refresh_token to database.");
        UsernamePasswordSignInResponse usernamePasswordSignInResponse = new UsernamePasswordSignInResponse(true, false);
        String responseBody = JsonUtils.getStringFromObject(usernamePasswordSignInResponse);
        HttpHeaders headers = new HttpHeaders();
        if(responseBody.equals(""))
            return new ResponseEntity<>("Error get string from json", headers, HttpStatus.BAD_REQUEST);
        headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=120; SameSite=None; Secure; Path=/; Domain=www.trxyzng.click; HttpOnly");
        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/ping",method = RequestMethod.GET)
    public ResponseEntity<String> get() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(InetAddress.getLocalHost().getHostName());
        // Remote address
        System.out.println(InetAddress.getLoopbackAddress().getHostAddress());
        System.out.println(InetAddress.getLoopbackAddress().getHostName());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("get /", headers, HttpStatus.OK);
    }
}

