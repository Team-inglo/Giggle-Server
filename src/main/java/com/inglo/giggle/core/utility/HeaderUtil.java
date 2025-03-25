package com.inglo.giggle.core.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class HeaderUtil {

    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            return Optional.empty();
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }

    public static HttpHeaders createHeaders(Map<String, String> headers, MediaType mediaType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        httpHeaders.setContentType(mediaType);
        return httpHeaders;
    }

    public static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }



//    public static Optional<String> refineHeader(StompHeaderAccessor request, String header, String prefix) {
//        String unpreparedToken = request.getFirstNativeHeader(header);
//
//        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
//            return Optional.empty();
//        }
//
//        return Optional.of(unpreparedToken.substring(prefix.length()));
//    }
}
