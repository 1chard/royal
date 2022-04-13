package com.royal;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author suporte
 */
public enum Status {
    OK(0),
    EMAIL_REPETIDO(1062),
    CAMPO_VAZIO_OU_GRANDE(1),
    CAMPOS_INVALIDOS(2),
    JSON_INVALIDO(43)
    ;
    
    private Status(int codigo){
	this.codigo = codigo;
    }
    
    public static Status porCodigo(int codigo){
	return Stream.of(Status.values()).filter(status -> status.codigo == codigo).findAny().orElse(null);
    }
    
    public final int codigo;
    
    
}
