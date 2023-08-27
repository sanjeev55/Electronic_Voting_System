package charlie.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.Map;
import java.lang.reflect.Type;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static Integer parseInteger(String s) {
        if (!hasText(s))
            return 0;

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static Map<String, Integer> convertJsonStringToMap(String jsonString) {
        if(!hasText(jsonString))
            return Collections.emptyMap();
        
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Integer>>(){}.getType();
        return gson.fromJson(jsonString, mapType);
    }

}