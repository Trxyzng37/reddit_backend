package com.trxyzng.trung.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.function.Supplier;

public class JsonUtils {
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    public static JsonNode getJsonNodeFromString(String input) {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        try {
            return objectMapper.readTree(input);
        } catch (JsonProcessingException e) {
            return JsonNodeFactory.instance.objectNode();
        }
    }

    public static boolean isEmptyJsonNode(JsonNode jsonNode) {
        return jsonNode.size() == 0;
    }

    public static String readJsonProperty(JsonNode node, String input) {
        return node.get(input).asText();
    }

    public static <T> T getObjectFromString(String input, Class<T> classType, Supplier<T> supplier) {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        try {
            return objectMapper.readValue(input, classType);
        } catch (JsonProcessingException e) {
            return supplier.get();
        }
    }

    /**
     *
     * @param object
     * @return String represent the object or ""
     */
    public static String getStringFromObject(Object object) {
        try {
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            String string = objectMapper.writeValueAsString(object);
            System.out.println(string);
            return string;
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
