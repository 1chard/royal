package com.royal.servlet;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.jsoniter.spi.TypeMismatchException;
import com.royal.MustHasFailedException;
import com.royal.Status;
import com.royal.StringMust;
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
			var email = json.get("email").mustBe(ValueType.STRING).asString();

			    status = Status.OK;
			    httpStatus = 200;

			    //TODO virar procedure
			    var usuario = UsuarioDAO.buscar(email);

			    if (usuario != null) {
				var codigo = RANDOM.nextInt(999999);

				RecuperacaoDAO.gravar(new Recuperacao(codigo, usuario.id));

				new Thread(
					() -> Mail.enviar("Royal Finance - Recuperação de senha", "codigo=" + codigo + "\nDuração: 15 min", usuario.email)
				).start();
			    }
			

			break;
		    }
		    case "USAR": {
			    status = Status.OK;
			    httpStatus = 200;
			    
			    var emailString = json.get("email").mustBe(ValueType.STRING).asString();
			    var recuperacao = RecuperacaoDAO.ativa(emailString);
			    
			    
			    if(recuperacao != null && json.get("codigo").mustBe(ValueType.STRING).asString().equals(Integer.toString(recuperacao.codigo))){
				EMAILS_ATIVOS.add(emailString);
				map.put("reset", true);
			    } else {
				map.put("reset", false);
			    }
			
			break;
		    }
		    case "MUDAR": {
			var email = json.get("email").mustBe(ValueType.STRING).asString();
			var senha = new StringMust(json.get("senha").mustBe(ValueType.STRING).asString()).notBlank().get();
			    
			    if(EMAILS_ATIVOS.contains(email)){
				status = Status.OK;
				httpStatus = 200;
				
				UsuarioDAO.editarSenha(email, senha);
			    }
			    else {
				status = Status.REQUISICAO_INVALIDA;
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
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} catch (TypeMismatchException e){
	    status = Status.CAMPO_TIPO_INCORRETO;
	    httpStatus = 400;
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	} catch (MustHasFailedException e) {
	    status = Status.CAMPO_INVALIDO;
	    httpStatus = 400;
	}

	resp.setStatus(httpStatus);
	map.put("status", status.codigo);
	JsonStream.serialize(map, resp.getOutputStream());
    }

}
