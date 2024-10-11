package com.trxyzng.trung.search.community;

import com.trxyzng.trung.search.community.pojo.DeleteCommunityRequest;
import com.trxyzng.trung.utility.DefaultResponse;
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
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "test_get_community_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class GetCommunityTest {
	
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }
    
    
  
    @Test
    public void test_get_all_community_infos_by_search_term_no_results() throws Exception {
    		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/find-community")
        			.with(csrf())
        			.param("name", "w")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    @Test
    public void test_get_all_community_infos_by_search_term_return_results() throws Exception {
    		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
    		expectedCommunityInfoList.add(new CommunityEntity(
        			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
        			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
        			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
        			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
            ));
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/find-community")
        			.with(csrf())
        			.param("name", "m")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    

    @Test
    	public void test_get_community_info_by_community_id_not_exist() throws Exception {
		CommunityEntity expected_detail_post = new CommunityEntity(0,"",0,"",null,0,"","",0,0);
		String expected_string = JsonUtils.getStringFromObject(expected_detail_post);
        mockMvc.perform(get("/get-community-info")
        			.with(csrf())
        			.param("id", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_string));
    }
    
    @Test
    public void test_get_community_info_by_community_id_exist() throws Exception {
    		CommunityEntity expectedResponse = new CommunityEntity(
    			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
    			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
        );
        String expectedJson = JsonUtils.getStringFromObject(expectedResponse);
        mockMvc.perform(get("/get-community-info")
        			.with(csrf())
        			.param("id", "100002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
	public void test_get_community_info_by_community_name_not_exist() throws Exception {
    		CommunityEntity expected_detail_post = new CommunityEntity(0,"",0,"",null,0,"","",0,0);
    		String expected_string = JsonUtils.getStringFromObject(expected_detail_post);
        mockMvc.perform(get("/find-community-by-name")
        			.with(csrf())
        			.param("name", "movi")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_string));
    }

    @Test
    public void test_get_community_info_by_community_name_exist() throws Exception {
    		CommunityEntity expectedResponse = new CommunityEntity(
    			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
    			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
        );
        String expectedJson = JsonUtils.getStringFromObject(expectedResponse);
        mockMvc.perform(get("/find-community-by-name")
        			.with(csrf())
        			.param("name", "movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }


    
    @Test
    public void test_get_community_infos_in_database() throws Exception {
		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
		expectedCommunityInfoList.add(new CommunityEntity(
		        100000, "science", 100000, "This community is a place to share and discuss new scientific research. Read about the latest advances in astronomy, biology, medicine, physics, social science, and more. Find and submit new publications and popular science coverage of current research.", 
		        Instant.parse("2024-06-30T09:33:34.000Z"), 0, 
		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706757/assets/science_p1dpx1.png",
		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764760/assets/b-science_guwwnu.png", 1, 0
		));
		expectedCommunityInfoList.add(new CommunityEntity(
		        100001, "technology", 100000, "Subreddit dedicated to the news and discussions about the creation and use of technology and its surrounding issues.", 
		        Instant.parse("2024-06-30T08:21:34.000Z"), 0, 
		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706758/assets/technology_tdgj6o.png",
		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764760/assets/b-science_guwwnu.png", 1, 0
		));
		expectedCommunityInfoList.add(new CommunityEntity(
    			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
    			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
        ));
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/get-all-communities-info")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_community_infos_subscribed_by_user_using_user_id_return_no_results() throws Exception {
		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/get-subscribed-communities")
        			.with(csrf())
        			.param("uid", "100001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    @Test
    public void test_get_community_infos_subscribed_by_user_using_user_id() throws Exception {
		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
		expectedCommunityInfoList.add(new CommunityEntity(
    			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
    			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
    			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
        ));
    String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
    mockMvc.perform(get("/get-subscribed-communities")
    			.with(csrf())
    			.param("uid", "100000")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_community_infos_moderated_by_user_using_user_id_with_no_moderated_communities() throws Exception {
    		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/get-community-info-by-uid")
        			.with(csrf())
        			.param("uid", "100001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    @Test
    public void test_get_all_community_infos_moderated_by_user_using_user_id_with_moderated_communities() throws Exception {
    		ArrayList<CommunityEntity> expectedCommunityInfoList = new ArrayList<>();
    		expectedCommunityInfoList.add(new CommunityEntity(
    		        100000, "science", 100000, "This community is a place to share and discuss new scientific research. Read about the latest advances in astronomy, biology, medicine, physics, social science, and more. Find and submit new publications and popular science coverage of current research.", 
    		        Instant.parse("2024-06-30T09:33:34.000Z"), 0, 
    		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706757/assets/science_p1dpx1.png",
    		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764760/assets/b-science_guwwnu.png", 1, 0
    		));
    		expectedCommunityInfoList.add(new CommunityEntity(
    		        100001, "technology", 100000, "Subreddit dedicated to the news and discussions about the creation and use of technology and its surrounding issues.", 
    		        Instant.parse("2024-06-30T08:21:34.000Z"), 0, 
    		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706758/assets/technology_tdgj6o.png",
    		        "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764760/assets/b-science_guwwnu.png", 1, 0
    		));
    		expectedCommunityInfoList.add(new CommunityEntity(
        			100002, "movies", 100000, "The goal of /r/movies is to provide an inclusive place for discussions and news about films with major releases.", 
        			Instant.parse("2024-06-30T08:50:34.000Z"), 0, 
        			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg",
        			"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727764604/assets/b-movies_j7ossh.png",0,0
        ));
        String expectedJson = JsonUtils.getStringFromObject(expectedCommunityInfoList);
        mockMvc.perform(get("/get-community-info-by-uid")
        			.with(csrf())
        			.param("uid", "100000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }



    @Test
    public void test_delete_community_by_community_id_and_user_id_with_correct_community_id_and_incorect_user_id() throws Exception {
    		DeleteCommunityRequest requestBody = new DeleteCommunityRequest(100000, 100001, 1);
        String requestBodyJson = JsonUtils.getStringFromObject(requestBody);
        String expectedJson = JsonUtils.getStringFromObject(new DefaultResponse(1, "error delete community"));
        mockMvc.perform(post("/delete-community")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        		    .content(requestBodyJson)
        		)
        		.andExpect(status().is(400))
            .andExpect(content().json(expectedJson));
    }
    
    @Test
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "test_get_community_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_delete_community_by_community_id_and_user_id_with_correct_community_id_and_corect_user_id() throws Exception {
    		DeleteCommunityRequest requestBody = new DeleteCommunityRequest(100000, 100000, 1);
        String requestBodyJson = JsonUtils.getStringFromObject(requestBody);
        String expectedJson = JsonUtils.getStringFromObject(new DefaultResponse(0, ""));
        mockMvc.perform(post("/delete-community")
        			.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        		    .content(requestBodyJson)
        		)
        		.andExpect(status().is(200))
            .andExpect(content().json(expectedJson));
    }

}
