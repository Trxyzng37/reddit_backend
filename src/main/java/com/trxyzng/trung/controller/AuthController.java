package com.trxyzng.trung.controller;

import com.trxyzng.trung.entity.User;
import com.trxyzng.trung.filter.JwtTokenProvider;
import com.trxyzng.trung.service.userdetail.UserByEmailService;
import com.trxyzng.trung.service.userdetail.UserDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
    private JwtTokenProvider JwtFilter;

    @Autowired
    private UserByEmailService userByEmailService;

    private String googleJwtToken = "";

    @Value("${server.address}")
    private String address;

    @ResponseBody
    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody User payload) throws AuthenticationException {
        try {
            //get user from payload and try to authenticate
            Authentication authentication = userPasswordAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            String username = authentication.getName();
            //generate jwt token
            String token = JwtFilter.generateAccessToken(username);
            Principal p = (Principal) authentication.getPrincipal();
            System.out.println("Username: " + payload.getUsername());
            System.out.println("Password: " + payload.getPassword());
            System.out.println("Token using username password: " + token);
            System.out.println("IP address " + address);
            return ResponseEntity.ok(token);
        }

        catch (AuthenticationException e){
            System.out.println("Error authenticating user. Username or password is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
        }
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
                String token = JwtFilter.generateAccessToken(username);
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
    @RequestMapping(value = "signin/check-google-jwt-token", method = RequestMethod.GET)
    ResponseEntity isJwtToken(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println("Check token: "+this.googleJwtToken);;
        return ResponseEntity.ok(this.googleJwtToken);
    }

//    @RequestMapping(value="singout", method = RequestMethod.GET)
//    public void singout(HttpSession s) {
//        try {
//            System.out.println("Call logout...");
//            System.out.println(s.getId());
//            System.out.println("Does this session is new: " + s.isNew());
//            System.out.println("Get session username: " + s.getAttribute("username"));
//            s.invalidate();
//            Authentication a = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println("Authencate user in securitycontext: "+ a.getName());
//            //below line will throw error
//            System.out.println("Check if session is delete: " + s.getAttribute("username"));
//        }
//        catch (IllegalStateException e){
//            System.out.println("Session is invalid");
//            SecurityContextHolder.clearContext();
//            SecurityContextHolder.getContext().setAuthentication(null);
//            Authentication a = SecurityContextHolder.getContext().getAuthentication();
//            if (a == null)
//                System.out.println("Is authencate user delete from securitycontext: yes");
//            else
//                System.out.println("Is authencate user delete from securitycontext: no");
//        }
//    }
}

