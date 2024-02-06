package com.trxyzng.trung.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static JsonNode getJsonNodeFromString(String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(input);
        return jsonNode;
    }

    public static String readJsonProperty(JsonNode node, String input) {
        return node.get(input).asText();
    }

    public static <T> T getJsonObjectFromString(String input, Class<T> classType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        T obj = objectMapper.readValue(input, classType);
        return obj;
    }
}
