package com.trxyzng.trung.refresh_token_server.controller;

//@CrossOrigin(origins = "http://127.0.0.1:4200", allowCredentials = "true")
//@RestController
//public class RefreshTokenController {
//    public void checkRefreshToken(HttpRequest request) {
//        HttpHeaders headers = request.getHeaders();
//        String cookieHeader = headers.getFirst(HttpHeaders.COOKIE);
//        if (cookieHeader != null) {
//            // Do something with the cookie header, parse it to extract individual cookies if needed
//            System.out.println("Cookie Header: " + cookieHeader);
//        } else {
//            System.out.println("No Cookie Header found in the request");
//        }
//    }
//    @ResponseBody
//    @RequestMapping(value = "/signin/username-password",method = RequestMethod.POST)
//    public ResponseEntity<String> login() throws AuthenticationException {
//        try {
//            Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof UserDetail user) {
//                int id = user.getId();
//                String password = user.getPassword();
//                String token = RefreshTokenUtils.generateRefreshToken(id);
//                System.out.println("Token using username password: " + token);
//                HttpHeaders headers = new HttpHeaders();
//                headers.add(HttpHeaders.SET_COOKIE, "refresh_token=" + token + "; Max-Age=100; SameSite=None; Secure; Path=/; Domain=127.0.0.1");
//                ResponseEntity<String> responseEntity = new ResponseEntity<>(token, headers, HttpStatus.OK);
//                return responseEntity;
//            }
//            else {
//                System.out.println("Can not get username password");
//            }
//        }
//        catch (AuthenticationException e){
//            System.out.println("Error authenticating user. Username or password is incorrect.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error authenticate user using username password");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("Empty");
//    }
//}
