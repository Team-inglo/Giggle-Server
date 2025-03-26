package com.inglo.giggle.core.utility;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class RestClientUtil {

    private final RestClient restClient = RestClient.create();

    public JSONObject sendGetMethod(String url, HttpHeaders headers) {
        return new JSONObject(Objects.requireNonNull(restClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                })
                .toEntity(JSONObject.class).getBody()));
    }

    //TODO: 의존성 분리
    public JSONObject sendGetMethodWithAuthorizationHeader(String url, String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + token);

        return new JSONObject(Objects.requireNonNull(restClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                })
                .toEntity(JSONObject.class).getBody()));
    }

    public JSONObject sendMultipartFormDataPostMethod(String url, HttpHeaders headers, MultiValueMap<String, Object> body) {
        try {
            return new JSONObject(Objects.requireNonNull(restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                    })
                    .toEntity(JSONObject.class).getBody()));
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public JSONObject sendPostMethod(String url, HttpHeaders headers, String body) {
        try {
            return new JSONObject(Objects.requireNonNull(restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .contentType(APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                    })
                    .toEntity(JSONObject.class).getBody()));
        } catch (Exception e) {
            throw new RuntimeException("Error sending POST request", e);
        }
    }

    public void sendFormUrlEncodedPostMethod(String url, HttpHeaders headers, MultiValueMap<String, String> body) {
        try {

            String encodedBody = UriComponentsBuilder.newInstance()
                    .queryParams(body)
                    .build()
                    .encode()
                    .toUriString()
                    .replaceFirst("\\?", ""); // 맨 앞에 ? 제거

            restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(encodedBody)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        log.error("4xx Error sending POST request => {}", response.getStatusCode());
                        throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        log.error("5xx Error sending POST request => {}", response.getStatusCode());
                        throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                    })
                    .body(String.class);
        } catch (Exception e) {
            log.error("Unexpected error during POST request", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void sendNonReturnPostMethod(String url, HttpHeaders headers, Object body) {
        try {
            restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                    })
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Error occurred while sending POST request: {}", e.getMessage());
        }
    }
}
