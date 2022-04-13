package com.royal.servlet;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.royal.Status;
import com.royal.dao.RecuperacaoDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.external.Mail;
import com.royal.model.Recuperacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Resetar", urlPatterns = {"/resetar"})
public class Resetar extends HttpServlet {

    private final static Random RANDOM = new Random();
    private final static Set<String> EMAILS_ATIVOS = Collections.synchronizedSet(new HashSet<>());

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");

	int httpStatus;
	Status status;
	var map = new HashMap<String, Object>();
	
	try {
	    var json = JsonIterator.deserialize(req.getInputStream().readAllBytes());

	    var tipo = json.get("tipo");

	    if (tipo.valueType() == ValueType.STRING) {
		switch (tipo.toString()) {
		    case "PEDIR": {
			var email = json.get("email");

			if (email.valueType() == ValueType.STRING) {
			    status = Status.OK;
			    httpStatus = 200;

			    //TODO virar procedure
			    var usuario = UsuarioDAO.buscar(email.toString());

			    if (usuario != null) {
				var codigo = RANDOM.nextInt(999999);

				RecuperacaoDAO.gravar(new Recuperacao(codigo, usuario.id));

				Mail.enviar("Royal Finance - Recuperação de senha", "codigo=" + codigo + "\nDuração: 15 min", usuario.email);
			    }
			} else {
			    status = Status.CAMPO_INVALIDO;
			    httpStatus = 400;
			}

			break;
		    }
		    case "USAR": {
			var email = json.get("email");
			var codigo = json.get("codigo");
			
			if(email.valueType() == ValueType.STRING && codigo.valueType() == ValueType.STRING){
			    status = Status.OK;
			    httpStatus = 200;
			    
			    var emailString = email.toString();
			    var recuperacao = RecuperacaoDAO.ativa(emailString);
			    
			    
			    if(recuperacao != null && codigo.toString().equals(Integer.toString(recuperacao.codigo))){
				EMAILS_ATIVOS.add(emailString);
				map.put("reset", true);
			    } else {
				map.put("reset", false);
			    }
			} else {
			    status = Status.CAMPO_INVALIDO; 
			    httpStatus = 400;
			}
			
			break;
		    }
		    case "MUDAR": {
			var email = json.get("email");
			var senha = json.get("senha");
			
			
			    System.out.println(EMAILS_ATIVOS);
			
			if(email.valueType() == ValueType.STRING && senha.valueType() == ValueType.STRING){
			    var senhaString = senha.toString();
			    
			    if(senhaString.isBlank()){
				status = Status.CAMPO_INVALIDO;
				httpStatus = 400;
			    } 
			    else if(!EMAILS_ATIVOS.contains(email.toString())){
				status = Status.REQUISICAO_INVALIDA;
				httpStatus = 400;
			    }
			    else {
				status = Status.OK;
				httpStatus = 200;
				
				UsuarioDAO.editarSenha(email.toString(), senhaString);
			    }
			} else {
			    status = Status.CAMPO_INVALIDO; 
			    httpStatus = 400;
			}
			
			break;
		    }
		    default: {
			status = Status.CAMPO_INVALIDO;
			httpStatus = 400;
			break;
		    }
		}

	    } else {
		status = Status.CAMPO_INVALIDO;
		httpStatus = 400;
	    }
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	resp.setStatus(httpStatus);
	map.put("status", status.codigo);
	JsonStream.serialize(map, resp.getOutputStream());
    }

}
