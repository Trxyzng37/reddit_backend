//package com.trxyzng.trung;
//
//import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenService;
//import com.trxyzng.trung.authentication.shared.user.UserEntityRepo;
//import com.trxyzng.trung.authentication.signin.usernamepassword.UsernamePasswordSignInController;
//import com.trxyzng.trung.authentication.signin.usernamepassword.UsernamePasswordSignInFilter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UsernamePasswordSignInController.class)
//@AutoConfigureMockMvc
//public class OnlyPartApplicationContextTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserEntityRepo userEntityRepo;
//    @MockBean
//    private BeanUtils beanUtils;
//    @MockBean
//    private RefreshTokenService refreshTokenService;
//    @MockBean
//    private AuthenticationManager userPasswordAuthenticationManager;
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//    @MockBean
//    private ApplicationContext context;
//
//    @BeforeEach
//    void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//                .addFilters(new UsernamePasswordSignInFilter())
//                .build();
//    }
//    @Test
//    public void testUsernamePasswordSignInControllerShouldSignInSuccessfully() throws Exception {
//        mockMvc.perform(post("/signin/username-password").with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"test\",\"password\":\"Trxyzng10032001@\"}"))
//                .andExpect(status().is(200));
//    }
//}
