package com.inglo.giggle.address.domain.service;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AddressService {

    private static final String NONE = "none";

    public List<List<String>> parseRegionSets(String region1Depth, String region2Depth, String region3Depth) {
        List<String> region1DepthList = parseSingleDepth(region1Depth);
        List<String> region2DepthList = parseSingleDepth(region2Depth);
        List<String> region3DepthList = parseSingleDepth(region3Depth);

        List<List<String>> regionSets = new ArrayList<>();
        int maxSize = Math.max(region1DepthList.size(), Math.max(region2DepthList.size(), region3DepthList.size()));

        for (int i = 0; i < maxSize; i++) {
            String region1 = i < region1DepthList.size() ? region1DepthList.get(i) : null;
            String region2 = i < region2DepthList.size() ? region2DepthList.get(i) : null;
            String region3 = i < region3DepthList.size() ? region3DepthList.get(i) : null;

            if (!(region1 == null && region2 == null && region3 == null)) {
                regionSets.add(Arrays.asList(region1, region2, region3));
            }
        }
        return regionSets;
    }

    public List<String> getDepthList(List<List<String>> regionSets, Integer depth) {
        return regionSets.stream()
                .map(regionSet -> regionSet.get(depth))
                .toList();
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */
    private List<String> parseSingleDepth(String region) {
        if (region == null || region.isEmpty()) return new ArrayList<>(); // 빈 리스트 반환
        return Arrays.stream(region.split(","))
                .map(String::trim)
                .map(value -> value.equalsIgnoreCase(NONE) ? null : value) // "none"을 null로 변환
                .toList();
    }
}
