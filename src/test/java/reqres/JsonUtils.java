package reqres;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    public static String readJsonFile(String filePath, String key) {
        try {
            // Read JSON file as a String
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON and get the specific section (addPet or updatePet)
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.getJSONObject(key).toString();

        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage());
        }
    }
}