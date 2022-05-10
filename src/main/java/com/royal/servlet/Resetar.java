package com.royal.servlet;

import com.qsoniter.JsonIterator;
import com.qsoniter.ValueType;
import com.qsoniter.output.JsonStream;
import com.qsoniter.spi.JsonException;
import com.qsoniter.spi.TypeMismatchException;
import com.royal.validation.MustHasFailedException;
import com.royal.Status;
import com.royal.validation.StringMust;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private final static NumberFormat FORMATTER = new DecimalFormat("000000");
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
	    
	    System.out.println(json);
	    
	    var tipo = json.get("tipo");

	    if (tipo.valueType() == ValueType.STRING) {
		switch (tipo.mustBe(ValueType.STRING).asString()) {
		    case "PEDIR" -> {
			var email = json.get("email").mustBe(ValueType.STRING).asString();

			    status = Status.OK;
			    httpStatus = 200;

			    
				Integer codigo = RecuperacaoDAO.pedir(email);
				
				if(codigo != null){
				
				    Mail.enviar("Royal Finance - Recuperação de senha", "codigo=" + FORMATTER.format(codigo) + "\nDuração: 15 min", email);
				}
			    }
		    
		    case "USAR" ->  {
			    status = Status.OK;
			    httpStatus = 200;
			    
			    var emailString = json.get("email").mustBe(ValueType.STRING).asString();
			    var recuperacao = RecuperacaoDAO.ativa(emailString);
			    
			    
			    if(recuperacao != null && json.get("codigo").mustBe(ValueType.STRING).asString().equals(recuperacao.toString())){
				EMAILS_ATIVOS.add(emailString);
				map.put("reset", true);
			    } else {
				map.put("reset", false);
			    }
			
		    }
		    case "MUDAR" ->  {
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
			
		    }
		    default ->  {
			status = Status.CAMPO_INVALIDO;
			httpStatus = 400;
		    }
		}

	    } else {
		status = Status.CAMPO_INVALIDO;
		httpStatus = 400;
	    }
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
