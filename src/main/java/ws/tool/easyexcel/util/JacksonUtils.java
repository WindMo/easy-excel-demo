package ws.tool.easyexcel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public class JacksonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj) {

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseJson(String json, Class<T> cla) {

        try {
            return OBJECT_MAPPER.readValue(json, cla);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseJson(String json, TypeReference<T> trf) {

        try {
            return OBJECT_MAPPER.readValue(json, trf);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
