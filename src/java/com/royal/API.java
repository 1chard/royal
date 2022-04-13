package com.royal;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author suporte
 */
public class API {

    private API() {
    }
    
    public static String[] parameters(HttpServletRequest req){
	var path = req.getPathInfo();
	
	return path != null && path.charAt(0) == '/' ? path.substring(1).split("/") : new String[]{"fd"};
    }
}
