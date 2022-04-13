package com.royal.servlet;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.royal.Cripto;
import com.royal.Status;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Cadastro", urlPatterns = {"/cadastro"})
public class Cadastro extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");
	
	int httpStatus;
	Status status;
	
	
	try {
	    var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());
	    System.out.println(json);
	    
	    var email = json.get("email");
	    var senha = json.get("senha");
	    var nome = json.get("nome");
	    
	    if(email.valueType() == ValueType.STRING && senha.valueType() == ValueType.STRING && email.valueType() == ValueType.STRING ){
		var emailString = email.toString();
		var senhaString = senha.toString();
		var nomeString = nome.toString();
		
		if( emailString.length() <= 100 && senhaString.length() <= 100 && nomeString.length() <= 200 && !emailString.isBlank() && !senhaString.isBlank() && !nomeString.isBlank()){
		    UsuarioDAO.gravar(
			    new Usuario(
				    nomeString,
				    emailString,
				    senhaString,
				    BigDecimal.ZERO,
				    false, 
				    null,
				    null
			    )
		    );
		    httpStatus = 200;
		    status = Status.OK;
		} else {
		    status = Status.CAMPOS_INVALIDOS;
		    httpStatus = 400;
		}
		
		
	    } else {
		status = Status.CAMPOS_INVALIDOS;
		httpStatus = 400;
	    }
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	} catch (SQLException e){
	    if(e.getErrorCode() == Status.EMAIL_REPETIDO.codigo){
		status = Status.EMAIL_REPETIDO;
		httpStatus = 202;
	    } else {
		throw new RuntimeException(e);
	    }
	} 
	
	resp.setStatus(httpStatus);
	JsonStream.serialize(Map.of("status", status.codigo), resp.getOutputStream());
    }
    
}
