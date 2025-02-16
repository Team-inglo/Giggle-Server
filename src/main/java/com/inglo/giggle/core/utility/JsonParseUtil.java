package com.inglo.giggle.core.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class JsonParseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON에서 키 값을 String으로 반환
     */
    public static String convertFromJsonToString(String key, String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            JsonNode keyNode = rootNode.path(key);

            if (keyNode.isMissingNode() || keyNode.isNull() || keyNode.asText().isEmpty()) {
                throw new IllegalArgumentException("[JsonParseUtil] Missing or null key: " + key);
            }

            return keyNode.asText();
        } catch (JsonProcessingException e) {
            log.error("[JsonParseUtil] JSON 파싱 오류: key = {}, message = {}", key, message, e);
            throw new RuntimeException("[JsonParseUtil] JSON 파싱 오류: " + key, e);
        }
    }

    /**
     * 문자열 키와 값을 JSON 문자열로 변환
     */
    public static String convertFromStringToJson(String key, Integer value) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put(key, value);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            log.error("[JsonParseUtil] JSON 생성 오류: key = {}, value = {}", key, value, e);
            throw new RuntimeException("[JsonParseUtil] JSON 생성 오류: " + key, e);
        }
    }

    /**
     * 여러 문자열 키-값 쌍을 JSON 문자열로 변환
     */
    public static String convertFromStringsToJson(Map<String, String> keyValueMap) {
        try {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            keyValueMap.forEach(jsonNode::put);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            log.error("[JsonParseUtil] JSON 생성 오류: keyValueMap = {}", keyValueMap, e);
            throw new RuntimeException("[JsonParseUtil] JSON 생성 오류", e);
        }
    }

    /**
     * JSON 문자열을 파싱하여 특정 키의 값을 반환
     */
    public static JsonNode parseJson(String message) {
        try {
            return objectMapper.readTree(message);
        } catch (JsonProcessingException e) {
            log.error("[JsonParseUtil] JSON 파싱 오류: message = {}", message, e);
            throw new RuntimeException("[JsonParseUtil] JSON 파싱 오류", e);
        }
    }

    /**
     * Object를 JSON 문자열로 변환
     */
    public static String convertFromObjectToJson(Object message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("[JsonParseUtil] JSON 변환 오류: responseDto = {}", message, e);
            throw new RuntimeException("[JsonParseUtil] JSON 변환 오류", e);
        }
    }
}
