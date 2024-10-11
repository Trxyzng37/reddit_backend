package com.trxyzng.trung.post.get_post;

import com.trxyzng.trung.post.get_post.pojo.GetDetailPostResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "test_get_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class GetPostTest {
	
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//                .addFilters(new UsernamePasswordSignInFilter())
                .build();
    }
   
    

    @Test
    	public void test_get_post_by_user_id_not_exist_and_post_id_not_exist() throws Exception {
		GetDetailPostResponse expected_detail_post = new GetDetailPostResponse(
        		0, "", 0, "", 
        		"", 
        		0, "", "", 
        		"", "", null, 0, 1, 0, null, null, null, 
        		0, 0, null, null, 0, null
		);
		String expected_string = JsonUtils.getStringFromObject(expected_detail_post);
        mockMvc.perform(get("/get-detail-post")
        			.with(csrf())
        			.param("uid", "0")
        			.param("pid", "100000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_string));
    }
    
    @Test
    public void test_get_post_by_user_id_not_exist_and_post_id_exist() throws Exception {
        GetDetailPostResponse expectedResponse = new GetDetailPostResponse(
        		100052, "editor", 100000, "trung", 
        		"https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png", 
        		100002, "movies", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg", 
        		"post with title and no content", "", Instant.parse("2024-07-20T14:35:29.568Z"), 2, 1, 0, null, null, null, 
        		0, 0, null, null, 0, null
        );
        String expectedJson = JsonUtils.getStringFromObject(expectedResponse);
        
        mockMvc.perform(get("/get-detail-post")
        			.with(csrf())
        			.param("uid", "0")
        			.param("pid", "100052")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }


    
    @Test
    public void test_get_posts_by_user_id_not_exist_and_array_of_post_ids_not_exist() throws Exception {
    		ArrayList<GetDetailPostResponse> expectedDetailPostList = new ArrayList<>();
    		expectedDetailPostList.add(new GetDetailPostResponse(100050, "", 0, "", "", 0, "", "", "", "", null, 0, 0, 0, 0, "none", 0, 0, 0, null, null, 0, null));
    		expectedDetailPostList.add(new GetDetailPostResponse(100051, "", 0, "", "", 0, "", "", "", "", null, 0, 0, 0, 0, "none", 0, 0, 0, null, null, 0, null));
        String expectedJson = JsonUtils.getStringFromObject(expectedDetailPostList);
        
        mockMvc.perform(get("/get-detail-post-by-post-id")
        			.with(csrf())
        			.param("uid", "0")
        			.param("pid", "100050,100051")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_posts_by_user_id_not_exist_and_array_of_post_id_exist() throws Exception {
    		ArrayList<GetDetailPostResponse> expectedDetailPostList = new ArrayList<>();
    		expectedDetailPostList.add(new GetDetailPostResponse(100052, "editor", 100000, "trung", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png", 100002, "movies", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg", "post with title and no content", "", Instant.parse("2024-07-20T14:35:29.568Z"), 2, 1, 0, 0, "none", 0, 0, 0, null, null, 0, null));
    		expectedDetailPostList.add(new GetDetailPostResponse(100053, "editor", 100000, "trung", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png", 100002, "movies", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg", "post with title and content", "<p>A simple line of text for content</p>", Instant.parse("2024-07-20T14:36:59.276Z"), 1, 1, 0, 0, "none", 0, 0, 0, null, null, 0, null));
        String expectedJson = JsonUtils.getStringFromObject(expectedDetailPostList);
        
        mockMvc.perform(get("/get-detail-post-by-post-id")
        			.with(csrf())
        			.param("uid", "0")
        			.param("pid", "100052,100053")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    @Test
    public void test_get_posts_by_user_id_exist_and_array_of_post_id_exist() throws Exception {
    		ArrayList<GetDetailPostResponse> expectedDetailPostList = new ArrayList<>();
    		expectedDetailPostList.add(new GetDetailPostResponse(100052, "editor", 100000, "trung", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png", 100002, "movies", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg", "post with title and no content", "", Instant.parse("2024-07-20T14:35:29.568Z"), 2, 1, 0, 0, "none", 0, 1, 0, null, null, 0, null));
    		expectedDetailPostList.add(new GetDetailPostResponse(100053, "editor", 100000, "trung", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727192375/assets/default_reddit_user_icon_g64y3s.png", 100002, "movies", "https://res.cloudinary.com/trxyzngstorage/image/upload/v1727706755/assets/movie_vjmbxk.jpg", "post with title and content", "<p>A simple line of text for content</p>", Instant.parse("2024-07-20T14:36:59.276Z"), 1, 1, 0, 0, "none", 0, 1, 0, null, null, 0, null));
        String expectedJson = JsonUtils.getStringFromObject(expectedDetailPostList);
        mockMvc.perform(get("/get-detail-post-by-post-id")
        			.with(csrf())
        			.param("uid", "100000")
        			.param("pid", "100052,100053")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    

    
    @Test
    public void test_get_all_post_ids_not_deleted_and_allowed_for_popular_page_by_user_id_exsit_and_sort_by_new() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100054);
    		expectedPostIdList.add(100052);
    		expectedPostIdList.add(100053);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-popular-posts")
        			.with(csrf())
        			.param("uid", "100000")
        			.param("sort", "new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_deleted_and_allowed_by_community_id_and_sort_by_new() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100054);
    		expectedPostIdList.add(100052);
    		expectedPostIdList.add(100053);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-community-posts")
        			.with(csrf())
        			.param("cid", "100002")
        			.param("sort", "new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_allowed_by_community_id() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100055);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-control-posts")
        			.with(csrf())
        			.param("cid", "100001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_deleted_and_allowed_by_search_term_and_sort_by_new() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100052);
    		expectedPostIdList.add(100053);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-search-posts")
        			.with(csrf())
        			.param("text", "post")
        			.param("sort", "new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_deleted_and_allowed_by_uid_sort_by_new() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100054);
    		expectedPostIdList.add(100052);
    		expectedPostIdList.add(100053);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-posts-by-uid")
        			.with(csrf())
        			.param("uid", "100000")
        			.param("sort", "new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_deleted_and_not_allowed_by_uid() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100055);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-posts-by-uid-not-delete-not-allow")
        			.with(csrf())
        			.param("uid", "100000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_not_deleted_and_allowed_by_community_id() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
    		expectedPostIdList.add(100052);
    		expectedPostIdList.add(100053);
    		expectedPostIdList.add(100054);
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-allowed-post-in-community")
        			.with(csrf())
        			.param("cid", "100002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_deleted_and_not_allowed_by_community_id() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-deleted-post-in-community")
        			.with(csrf())
        			.param("cid", "100002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
    
    
    
    @Test
    public void test_get_all_post_ids_editted_by_community_id() throws Exception {
    		ArrayList<Integer> expectedPostIdList = new ArrayList<>();
        String expectedJson = JsonUtils.getStringFromObject(expectedPostIdList);        
        mockMvc.perform(get("/get-editted-post-ids-in-community")
        			.with(csrf())
        			.param("cid", "100002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }
}
