package com.olympus.MovieApp.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Utils {

    public static <T extends Object> String parseObject(T t) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String text = objectWriter.writeValueAsString(t);
        return text;
    }
}
