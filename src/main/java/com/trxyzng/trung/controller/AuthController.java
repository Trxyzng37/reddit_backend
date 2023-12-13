package com.trxyzng.trung.controller;

import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.filter.AccessToken;
import com.trxyzng.trung.service.userdetail.UserByEmailService;
import com.trxyzng.trung.service.userdetail.UserDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;


@CrossOrigin("http://127.0.0.1:4200")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager userPasswordAuthenticationManager;

    @Autowired
    private AccessToken AccessToken;

    @Autowired
    private UserByEmailService userByEmailService;

    private String googleJwtToken = "";

    @Value("${server.address}")
    private String address;

//        public String getRequestBody(HttpServletRequest request) {
//            int s = request.getContentLength();
//            System.out.println("Content length " + s);
//            StringBuilder requestBody = new StringBuilder();
//            try (BufferedReader reader = request.getReader()) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    requestBody.append(line);
//                }
//            } catch (IOException e) {
//                System.out.println("Error reading body of the request: " + e.getMessage());
//            } catch (IllegalStateException e) {
//                System.out.println("Error fuck input: " + e.getMessage());
//            }
//            return requestBody.toString();
//        }
    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login() throws AuthenticationException {
        try {
            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetail) {
                UserDetail user = (UserDetail) principal;
                String username = user.getUsername();
                String password = user.getPassword();
                //generate jwt token
                String token = AccessToken.generateAccessToken(2, "w");
                System.out.println("Token using username password: " + token);
                System.out.println("IP address " + address);
                return ResponseEntity.ok(token);
            }
            else {
                System.out.println("Can not get username password");
            }
        }
        catch (AuthenticationException e){
            System.out.println("Error authenticating user. Username or password is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Empty");
    }

    @RequestMapping(value="/signin/google-authentication", method = RequestMethod.GET)
    public ResponseEntity user(@AuthenticationPrincipal OAuth2User authenticate_user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("User: " + authentication.getName());
            System.out.println("email: " +authenticate_user.getAttribute("email"));
        }
        String email = authenticate_user.getAttribute("email");
        try {
            UserDetail user = (UserDetail) userByEmailService.loadUserByUsername(email);
            if (email.equals(user.getEmail())) {
                String username = user.getUsername();
                int id = user.getId();
                String token = AccessToken.generateAccessToken(id, username);
                System.out.println("Jwt token using email: " + token);
                this.googleJwtToken = token;
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("http://localhost:4200/home"));
                return ResponseEntity.status(HttpStatus.SEE_OTHER)
                        .headers(headers)
                        .body(this.googleJwtToken);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not authenticate using auth2.0");
            }
        }
        catch (UsernameNotFoundException e){
            System.out.println("Can not find user with email " + email + "using auth2.0");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Can not find user with email " + email + " using auth2.0");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/signin/check-google-jwt-token", method = RequestMethod.GET)
    ResponseEntity isJwtToken()  {
        System.out.println("Check token: "+this.googleJwtToken);
        return ResponseEntity.ok(this.googleJwtToken);
    }

}

