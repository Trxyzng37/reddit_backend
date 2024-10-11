package com.trxyzng.trung.search.user_profile;

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

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "test_get_user_profile_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class GetUserProfileTest {
	
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    	public void test_get_user_info_by_uid_not_exist_fail() throws Exception {
		UserProfileEntity expected_user_info = new UserProfileEntity(0,"","",null,0,0,"");
		String expected_string = JsonUtils.getStringFromObject(expected_user_info);
        mockMvc.perform(get("/get-user-info-by-uid")
        			.with(csrf())
        			.param("uid", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_string));
    }
    
    @Test
    public void test_get_user_info_by_uid_exist_success() throws Exception {
    	UserProfileEntity expected_user_info = new UserProfileEntity(
    			100000, "trung", "Hi, my name is Trung", Instant.parse("2024-06-16T08:21:34.000Z"), 0, 0, 
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png"
    		);
        String expectedJson = JsonUtils.getStringFromObject(expected_user_info);
        mockMvc.perform(get("/get-user-info-by-uid")
        			.with(csrf())
        			.param("uid", "100000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }


}
