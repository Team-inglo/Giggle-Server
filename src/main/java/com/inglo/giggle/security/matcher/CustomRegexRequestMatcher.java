package com.inglo.giggle.security.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CustomRegexRequestMatcher implements RequestMatcher {

    private final String pattern;
    private final boolean matchQueryString;

    public CustomRegexRequestMatcher(String pattern) {
        this.pattern = pattern;
        this.matchQueryString = pattern.contains("\\?");
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!matchQueryString) {
            return uri.matches(pattern);
        }
        String query = request.getQueryString();
        String fullUrl = (query != null && !query.isEmpty()) ? uri + "?" + query : uri;
        fullUrl = URLDecoder.decode(fullUrl, StandardCharsets.UTF_8);
        if (fullUrl.contains("?")) {
            String[] parts = fullUrl.split("\\?", 2);
            String queryPart = parts[1].replace("\"", "");
            fullUrl = parts[0] + "?" + queryPart;
        }
        return fullUrl.matches(pattern);
    }
}

