package com.royal;

import java.sql.SQLException;

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
    
    public static void main(String[] args) throws SQLException {
	System.out.println(String.join(",", new String[]{"tst", "adf"}));
    }
}
