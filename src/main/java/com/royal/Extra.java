package com.royal;

import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.output.JsonStream;
import com.royal.model.Usuario;
import com.royal.servlet.Sistema;
import java.math.BigDecimal;
import java.util.Random;

/**
 *
 * @author suporte
 */
public class Extra {
    private Extra(){}
    
    public static void main(String[] args) {
	System.out.println(new Usuario("nome", "email", "senha", BigDecimal.ONE, false).token());
    }
}
