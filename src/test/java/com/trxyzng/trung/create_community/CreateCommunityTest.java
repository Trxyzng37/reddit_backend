package com.trxyzng.trung.create_community;

import com.trxyzng.trung.create_community.pojo.CreateCommunityRequest;
import com.trxyzng.trung.create_community.pojo.CreateCommunityResponse;
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
@Sql(scripts = "test_create_community_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class CreateCommunityTest {
	
    private MockMvc mockMvc;
    
	private String test_img_base64 = "data:image/png;base64,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";

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }
    
    
    
    @Test
	public void test_create_community_fail_by_using_exsiting_name() throws Exception {
	CreateCommunityRequest communityRequest = new CreateCommunityRequest(100000, "movies", "description for test community", test_img_base64, test_img_base64, 0);
	String request_body = JsonUtils.getStringFromObject(communityRequest);
	CreateCommunityResponse response = new CreateCommunityResponse(0, 1);
	String expected_response = JsonUtils.getStringFromObject(response);
    mockMvc.perform(post("/create-community")
    			.with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(request_body)
    		)
            .andExpect(status().is(400))
            .andExpect(content().json(expected_response));
    }
    
    
    
    @Test
	public void test_create_community_fail_by_using_invalid_img() throws Exception {
	CreateCommunityRequest communityRequest = new CreateCommunityRequest(100000, "test1", "description for test community", "avatar", "banner", 0);
	String request_body = JsonUtils.getStringFromObject(communityRequest);
	CreateCommunityResponse response = new CreateCommunityResponse(0, 2);
	String expected_response = JsonUtils.getStringFromObject(response);
    mockMvc.perform(post("/create-community")
    			.with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(request_body)
    		)
            .andExpect(status().is(400))
            .andExpect(content().json(expected_response));
    }
    

    @Test
    	public void test_create_community_success() throws Exception {
		CreateCommunityRequest communityRequest = new CreateCommunityRequest(100000, "test", "description for test community", test_img_base64, test_img_base64, 0);
		String request_body = JsonUtils.getStringFromObject(communityRequest);
		CreateCommunityResponse response = new CreateCommunityResponse(900000, 0);
		String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-community")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body)
        		)
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }
   


}
