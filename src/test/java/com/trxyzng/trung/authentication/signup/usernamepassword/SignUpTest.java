package com.trxyzng.trung.authentication.signup.usernamepassword;

import com.trxyzng.trung.authentication.signup.pojo.UsernamePasswordSignUpResponse;
import com.trxyzng.trung.utility.JsonUtils;

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
@Sql(scripts = "test_sign_up_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class SignUpTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void test_sign_up_using_username_password_success() throws Exception {
    		UsernamePasswordSignUpResponse expected_response = new UsernamePasswordSignUpResponse(true, 0, 0);
    		String expected_response_String = JsonUtils.getStringFromObject(expected_response);
        mockMvc.perform(post("/signup/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                		+ "\"username\":\"trung1\","
                		+ "\"password\":\"Trung123456@\","
                		+ "\"email\":\"trung@gmail.com\""
                		+ "}"))
                .andExpect(status().is(200))
                .andExpect(content().string(expected_response_String));
    }



    @Test
    public void test_sign_up_using_username_password_with_existing_username_fail() throws Exception {
    		UsernamePasswordSignUpResponse expected_response = new UsernamePasswordSignUpResponse(false, 1, 0);
    		String expected_response_String = JsonUtils.getStringFromObject(expected_response);
        mockMvc.perform(post("/signup/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                		+ "\"username\":\"trung\","
                		+ "\"password\":\"Trung123456@\","
                		+ "\"email\":\"trung@gmail.com\""
                		+ "}"))
                .andExpect(status().is(200))
                .andExpect(content().string(expected_response_String));
    }
    
    
    
    @Test
    public void test_sign_up_using_username_password_with_username_more_than_16_characters_fail() throws Exception {
        mockMvc.perform(post("/signup/username-password")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                		+ "\"username\":\"trung1234567891011\","
                		+ "\"password\":\"Trung123456@\","
                		+ "\"email\":\"trung@gmail.com\""
                		+ "}"))
                .andExpect(status().is(400))
                .andExpect(content().string("Error: Constraint violation"));
    }
}
