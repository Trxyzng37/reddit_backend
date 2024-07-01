//package com.trxyzng.trung;
//
//import com.trxyzng.trung.authentication.signin.usernamepassword.UsernamePasswordSignInFilter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
//
//@SpringBootTest
//public class MainApplicationContextTest {
//    @Autowired
//    private WebApplicationContext wac;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//                .addFilters(new UsernamePasswordSignInFilter())
//                .build();
//    }
//
//    @Test
//    public void testUsernamePasswordSignInControllerShouldSignInSuccessfully() throws Exception {
//        mockMvc.perform(post("/signin/username-password").with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"trxyzng\",\"password\":\"Trxyzng10032001@\"}"))
//                .andExpect(status().is(200));
//    }
//
//    @Test
//    public void testUsernamePasswordSignInControllerShouldSignInFail() throws Exception {
//        mockMvc.perform(post("/signin/username-password").with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"trxyz\",\"password\":\"Trxyzng10032001@\"}"))
//                .andExpect(status().is(200));
//    }
//}
