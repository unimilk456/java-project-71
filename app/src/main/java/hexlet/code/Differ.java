package hexlet.code;

import com.fasterxml.jackson.core.JsonParseException;
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

    public static String generate(Path file1, Path file2) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map1 = readMapFromFile(mapper, file1);
        Map<String, Object> map2 = readMapFromFile(mapper, file2);

        List<Map<String, Object>> diff = getDifferenceInJSONFormat(map1, map2);

        return buildDifferenceString(diff);
    }

    private static String buildDifferenceString(List<Map<String, Object>> diff) {
        StringBuilder difference = new StringBuilder("{\n");
        for (Map<String, Object> map : diff) {
            appendEntryToDifferenceString(difference, map);
        }
        difference.append("}");

        return difference.toString();
    }

    private static void appendEntryToDifferenceString(StringBuilder difference, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(TYPE)) {
                difference.append(entry.getValue());
            } else {
                difference.append(entry.getKey())
                        .append(":")
                        .append(entry.getValue())
                        .append("\n");
            }
        }
    }

    public static List<Map<String, Object>> getDifferenceInJSONFormat(
            Map<String, Object> map1, Map<String, Object> map2) {
        TreeSet<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<Map<String, Object>> result = new ArrayList<>();
        for (String key : allKeys) {
            if (bothMapsHaveTheSameValueForKey(map1, map2, key)) {
                result.add(createMapWithDifference(key, String.valueOf(map1.get(key)), NOCHANGES));
            } else {
                addRemovedKeyToResultIfPresent(map1, result, key);
                addAddedKeyToResultIfPresent(map2, result, key);
            }
        }
        return result;
    }

    private static Map<String, Object> createMapWithDifference(String key, String value, String type) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(TYPE, type);
        map.put(key, value);

        return map;
    }

    public static Map<String, Object> readMapFromFile(ObjectMapper mapper, Path file) {
        Map<String, Object> map = new HashMap<>();
        try {
            String content = Files.readString(file);
            map = mapper.readValue(content, new TypeReference<>() {
            });
        } catch (JsonParseException jsonException) {
            LOG.error("Error parsing JSON", jsonException);
            System.out.println("Error json parsing");
        } catch (IOException e) {
            LOG.error("IO Error", e);
        }
        return map;
    }

    private static boolean bothMapsHaveTheSameValueForKey(
            Map<String, Object> map1, Map<String, Object> map2, String key) {
        return String.valueOf(map1.get(key)).equals(String.valueOf(map2.get(key)));
    }

    private static void addRemovedKeyToResultIfPresent(
            Map<String, Object> map1, List<Map<String, Object>> result, String key) {
        if (map1.containsKey(key)) {
            result.add(createMapWithDifference(key, String.valueOf(map1.get(key)), REMOVED));
        }
    }

    private static void addAddedKeyToResultIfPresent(
            Map<String, Object> map2, List<Map<String, Object>> result, String key) {
        if (map2.containsKey(key)) {
            result.add(createMapWithDifference(key, String.valueOf(map2.get(key)), ADDED));
        }
    }

}
