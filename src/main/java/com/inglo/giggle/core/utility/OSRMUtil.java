package com.inglo.giggle.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inglo.giggle.core.dto.RouteResponseDto;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OSRMUtil {

    @Value("${osrm.url}")
    private String osrmUrl;

    @Value("${osrm.path}")
    private String path;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String createOSRMRequestUrl(double startLat, double startLon, double endLat, double endLon) {
        return UriComponentsBuilder.fromHttpUrl(osrmUrl)
                .path(path)
                .path("/{startLon},{startLat};{endLon},{endLat}")
                .queryParam("steps", "false")
                .queryParam("skip_waypoints", "true")
                .buildAndExpand(startLon, startLat, endLon, endLat)
                .toUriString();
    }

    public RouteResponseDto mapToRouteResponseDto(JSONObject jsonObject) throws Exception {
        return objectMapper.readValue(jsonObject.toString(), RouteResponseDto.class);
    }



}
