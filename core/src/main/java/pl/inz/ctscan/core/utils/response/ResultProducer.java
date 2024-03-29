package pl.inz.ctscan.core.utils.response;

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

    public static Map<String, Object> createResponseByReflection(Object... objects) {
        Map<String, Object> response = new HashMap<>();

        for(Object o: objects) {
            response.put(o.getClass().getSimpleName(), o);
        }

        return response;
    }

    public static Map<String, Object> prepareXY(Object axisX, Object axisY) {
        Map<String, Object> result = new HashMap<>();

        result.put("X", axisX);
        result.put("Y", axisY);

        return result;
    }
}
