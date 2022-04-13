package com.royal;

import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.output.JsonStream;
import java.util.Random;

/**
 *
 * @author suporte
 */
public class Extra {
    private Extra(){}
    
    public static void main(String[] args) {
	System.out.println(new Random().nextInt(10));
    }
}
