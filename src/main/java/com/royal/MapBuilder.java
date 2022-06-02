package com.royal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suporte
 */
public class MapBuilder {
    private final Map<String, Object> map = new HashMap<>();

    public MapBuilder add(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return Collections.unmodifiableMap(map);
    }
}






