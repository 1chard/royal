package com.royal.servlet;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.royal.API;
import com.royal.Status;
import com.royal.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author suporte
 */
@WebServlet(name = "Contas", urlPatterns = {"/contas"})
public class Contas extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");
	
	int httpStatus;
	Status status;
	var response = new HashMap<String, Object>();
	
	try {
	    var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());
	    
	    System.out.println(json);
	    
	    final var email = json.get("email");
	    final var senha = json.get("senha");
	    final var manterConectado = json.get("manter");
	    
	    if(email.valueType() == ValueType.STRING && senha.valueType() == ValueType.STRING && manterConectado.valueType() == ValueType.BOOLEAN){
		final var pessoa = UsuarioDAO.buscar(email.toString(), senha.toString());		
		
		if(pessoa != null){
		    
		    //instancia tudo
		    var sessao = new Sistema.Sessao();
		    String token = UUID.nameUUIDFromBytes(
			    Sistema.ENCRIPTA.encrypt(
				    Integer.toOctalString(pessoa.id)
			    ).getBytes(StandardCharsets.ISO_8859_1)
		    ).toString();
		    
		    sessao.httpSession = req.getSession(true);
		    sessao.id = pessoa.id;
		    
		    
		    response.put("found", true);
		    response.put("token", token);
		    
		    Sistema.PESSOAS.put(token, sessao);
		    
		    httpStatus = 200;
		} else {
		    response.put("found", false);
		    httpStatus = 202;
		}
		
		
		status = Status.OK;
	    } else {
		httpStatus = 400;
		status = Status.CAMPO_INVALIDO;
	    }
	    
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	
	resp.setStatus(httpStatus);
	response.put("status", status.codigo);
	JsonStream.serialize(response, resp.getOutputStream());
    }
    
    
    
}
