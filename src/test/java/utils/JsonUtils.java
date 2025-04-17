package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String readJsonFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static <T> T readJsonAsObject(String filePath, String key, Class<T> clazz) throws IOException {
        String fullJson = readJsonFile(filePath);
        JSONObject root = new JSONObject(fullJson);
        return mapper.readValue(root.getJSONObject(key).toString(), clazz);
    }

    public static <T> String convertObjectToJson(T object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
