package com.royal;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author suporte
 */
public class MapBuilder {
    private Map<String, Object> map = new HashMap<>();
    
    public MapBuilder add(String key, Object value){
	map.put(key, value);
	return this;
    }
}






