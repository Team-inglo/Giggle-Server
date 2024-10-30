package com.inglo.giggle.core.utility;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class RestClientUtil {

    private final RestClient restClient = RestClient.create();

    public JSONObject sendGetMethod(String url) {
        return new JSONObject(Objects.requireNonNull(restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
                })
                .toEntity(JSONObject.class).getBody()));
    }

}
