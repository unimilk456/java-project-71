package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Differ {
    private static final Logger LOG = LoggerFactory.getLogger(Differ.class);
    private static final String TYPE = "type";
    private static final String ADDED = " + ";
    private static final String REMOVED = " - ";
    private static final String NOCHANGES = "   ";
    public static String generate(Path file1, Path file2) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map1 = readMapFromFile(mapper,file1);
        Map<String, Object> map2 = readMapFromFile(mapper,file2);

        List<Map<String, Object>> diff = getDifferenceInJSONFormat(map1, map2);
        StringBuilder difference = new StringBuilder("{\n");
        for (Map<String, Object> map : diff) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals(TYPE)) {
                    difference.append(entry.getValue());
                } else {
                    difference.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
                }
            }
        }
        difference.append("}");

        return difference.toString();
    }

    public static List<Map<String, Object>> getDifferenceInJSONFormat(Map<String, Object> map1, Map<String, Object> map2) {
        TreeSet<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<Map<String, Object>> result = new ArrayList<>();
        for (String key : allKeys) {
            Object val1 = map1.get(key);
            Object val2 = map2.get(key);

            if (String.valueOf(val1).equals(String.valueOf(val2))) {
                result.add(createMapWithDifference(key, String.valueOf(val1), NOCHANGES));
            } else {
                if (map1.containsKey(key)) {
                    result.add(createMapWithDifference(key, String.valueOf(val1), REMOVED));
                }
                if (map2.containsKey(key)) {
                    result.add(createMapWithDifference(key, String.valueOf(val1), ADDED));
                }
            }
        }
        return result;
    }

    private static Map<String, Object> createMapWithDifference(String key, String value, String type ){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(TYPE, type);
        map.put(key, value);

        return map;
    }


    private static Map<String, Object> readMapFromFile(ObjectMapper mapper, Path file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            String content = Files.readString(file);
            map = mapper.readValue(content, new TypeReference<>() {});
        } catch (JsonProcessingException jsonException) {
            LOG.error("Error parsing JSON", jsonException);
            System.out.println("Error json parsing");
        }
        return map;
    }

}
