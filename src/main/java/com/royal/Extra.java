package com.royal;

import java.math.BigDecimal;

/**
 *
 * @author suporte
 */
public class Extra {
    public static <T> T orDefault(T x1, T x2){
		return x1 != null ? x1 : x2;
    }
    
    public static Integer parseInteger(String string){
	try {
	    return Integer.valueOf(string);
	} catch (NumberFormatException e) {
	    return null;
	}
    }

	private Extra() {
	}

}
