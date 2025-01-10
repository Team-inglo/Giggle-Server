package com.inglo.giggle.core.utility;

import com.inglo.giggle.posting.domain.type.EWorkDaysPerWeek;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EnumParseUtil {

    public static List<Integer> parseIntegerToEnums(String input) {
        if (input == null || input.isEmpty()) return null;
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(value -> EWorkDaysPerWeek.toInt(Enum.valueOf(EWorkDaysPerWeek.class, value)))
                .toList();
    }

    public static <E extends Enum<E>> List<E> parseEnums(String input, Class<E> enumClass) {
        if (input == null || input.isEmpty()) return null;
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(value -> Enum.valueOf(enumClass, value))
                .toList();
    }
}
