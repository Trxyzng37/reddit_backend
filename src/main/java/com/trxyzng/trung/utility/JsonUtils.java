package com.trxyzng.trung.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    public static ObjectMapper  getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
    public static JsonNode getJsonNodeFromString(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        return objectMapper.readTree(input);
    }
    public static String readJsonProperty(JsonNode node, String input) {
        return node.get(input).asText();
    }
    public static <T> T getJsonObjectFromString(String input, Class<T> classType) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        return objectMapper.readValue(input, classType);
    }
}
