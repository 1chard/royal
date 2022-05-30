package com.royal.servlet;

import com.royal.Sistema;
import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.qsoniter.any.Any;
import com.qsoniter.output.JsonStream;
import com.qsoniter.spi.JsonException;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	int httpStatus;
	Status status;
	var response = new HashMap<String, Object>();
	
	try {
	    var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());
	    
	    final var email = json.get("email").mustBe(ValueType.STRING).asString();
	    final var senha = json.get("senha").mustBe(ValueType.STRING).asString();
	    final var manterConectado = json.get("manter").mustBe(ValueType.BOOLEAN).asBoolean();
	    
	   
		final var pessoa = UsuarioDAO.buscar(email, senha);
		
		
		if(pessoa != null){
		    
		    //instancia tudo
		    var sessao = new Sistema.Sessao();
		    String token = UUID.nameUUIDFromBytes(
			    Sistema.ENCRIPTA.encrypt(
				    Integer.toOctalString(pessoa.id)
			    ).getBytes(StandardCharsets.ISO_8859_1)
		    ).toString();
		    
		    sessao.sessoes.add(req.getSession(true));
		    sessao.usuario = pessoa;
		    
		    Sistema.PESSOAS.put(token, sessao);
		    
		    response.put("found", true);
		    response.put("token", token);
		    
		    if(manterConectado){
			AutoLogin.ativarAutoLogin(token, pessoa.id);
		    }
		    
		    httpStatus = 200;
		} else {
		    response.put("found", false);
		    httpStatus = 204;
		}
		
		
		status = Status.OK;

//		httpStatus = 400;
//		status = Status.CAMPO_INVALIDO;
	    
	    
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	}
	
	resp.setStatus(httpStatus);
	response.put("status", status.codigo);
	JsonStream.serialize(response, resp.getOutputStream());
    }
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
    
    
    
}
