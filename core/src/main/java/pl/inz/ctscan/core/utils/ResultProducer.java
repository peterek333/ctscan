package pl.inz.ctscan.core.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultProducer {

    public static Map<String, Object> createResponse(boolean state) {
        return Collections.singletonMap("result", state);
    }

    public static Map<String, Object> createResponse(boolean state, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", state);
        response.put("message", message);

        return response;
    }
}
