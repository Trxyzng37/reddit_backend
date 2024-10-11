package com.trxyzng.trung.post.create_post;

import com.trxyzng.trung.post.PostRepo;
import com.trxyzng.trung.post.create_post.pojo.CreatePostRequest;
import com.trxyzng.trung.post.create_post.pojo.CreatePostResponse;
import com.trxyzng.trung.post.get_post.pojo.GetPostResponse;
import com.trxyzng.trung.utility.JsonUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class CreatePostTest {

    private MockMvc mockMvc;

    @Autowired
    PostRepo postRepo;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    String getString(int type) {
        try {
            String base64String = "";
            if (type == 0) {
                base64String = new String(Files.readAllBytes(
                        Paths.get("src/test/java/com/trxyzng/trung/post/create_post/test_img_base64.txt")));
            } else {
                base64String = new String(Files.readAllBytes(
                        Paths.get("src/test/java/com/trxyzng/trung/post/create_post/test_video_base64.txt")));
            }
            return base64String;
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    @Order(1)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_edior_without_img_success() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("editor", 100000, 100000, "title", "content",
                "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(true, 900000);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }

    @Test
    @Order(2)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_edior_with_img_success() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("editor", 100000, 100000, "title",
                "<p><img src=\"" + getString(0) + "\"></p>", "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(true, 900000);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }

    @Test
    @Order(3)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_img_with_img_success() throws Exception {
        String sample_contentString = "[{"
                + "\"data\":\"" + getString(0) + "\","
                + "\"caption\":\"caption\","
                + "\"link\":\"link\""
                + "}]";
        CreatePostRequest postRequest = new CreatePostRequest("img", 100000, 100000, "title", sample_contentString,
                "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(true, 900000);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }

    @Test
    @Order(4)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_link_success() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("link", 100000, 100000, "title",
                "https://www.sciencenews.org/article/bizarre-video-eyeballs-age-pupils-size",
                "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(true, 900000);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }

    @Test
    @Order(5)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_video_success() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("video", 100000, 100000, "title", getString(1),
                "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(true, 900000);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }

    @Test
    @Order(1)
    @Sql(scripts = "/prepare_schema_sequence_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "test_create_post_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void test_create_post_type_edior_with_invalid_user_id_fail() throws Exception {
        CreatePostRequest postRequest = new CreatePostRequest("editor", 100005, 100000, "title", "content",
                "2024-06-30T08:50:34.000Z", 1);
        String request_body = JsonUtils.getStringFromObject(postRequest);
        CreatePostResponse response = new CreatePostResponse(false, 0);
        String expected_response = JsonUtils.getStringFromObject(response);
        mockMvc.perform(post("/create-post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request_body))
                .andExpect(status().is(200))
                .andExpect(content().json(expected_response));
    }
}
