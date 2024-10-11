package com.trxyzng.trung.authentication.signin.usernamepassword;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "test_sign_in_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class SignInTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new UsernamePasswordSignInFilter())
                .build();
    }

    @Test
    public void test_sign_in_using_username_password_success() throws Exception {
        mockMvc.perform(post("/signin/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"trung\",\"password\":\"Trung123456@\"}"))
                .andExpect(status().is(200))
                .andExpect(content().json("{'isSignIn':true,'passwordError': false, 'uid': 900000}"));
    }

    @Test
    public void test_sign_in_using_username_password_with_wrong_username_fail() throws Exception {
        mockMvc.perform(post("/signin/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"trun\",\"password\":\"Trung123456@\"}"))
                .andExpect(status().is(200))
                .andExpect(content().json("{'isSignIn':false,'passwordError': true, 'uid': 0}"));
    }

    @Test
    public void test_sign_in_using_username_password_with_wrong_password_fail() throws Exception {
        mockMvc.perform(post("/signin/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"trung\",\"password\":\"Trung123455@\"}"))
                .andExpect(status().is(200))
                .andExpect(content().json("{'isSignIn':false,'passwordError': true, 'uid': 0}"));
    }

}
