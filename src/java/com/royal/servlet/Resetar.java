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
import java.util.Map;
import java.util.Random;
import org.apache.tomcat.util.json.JSONParser;

/**
 *
 * @author suporte
 */
@WebServlet(name = "Resetar", urlPatterns = {"/resetar"})
public class Resetar extends HttpServlet {

    private final static Random RANDOM = new Random();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	resp.setContentType("application/json");
	resp.setHeader("Access-Control-Allow-Origin", "*");

	int httpStatus;
	Status status;

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
			    status = Status.CAMPOS_INVALIDOS;
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
			    
			    var recuperacao = RecuperacaoDAO.ativa(email.toString());
			    
			    
			    if(recuperacao != null && codigo.toString().equals(Integer.toString(recuperacao.codigo))){
				var usuario = UsuarioDAO.buscar(email.toString());
				
			    }
			} else {
			    status = Status.CAMPOS_INVALIDOS;
			    httpStatus = 400;
			}
			
			break;
		    }
		    default: {
			status = Status.CAMPOS_INVALIDOS;
			httpStatus = 400;
			break;
		    }
		}

	    } else {
		status = Status.CAMPOS_INVALIDOS;
		httpStatus = 400;
	    }
	} catch (JsonException e) {
	    status = Status.JSON_INVALIDO;
	    httpStatus = 400;
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	resp.setStatus(httpStatus);
	JsonStream.serialize(Map.of("status", status.codigo), resp.getOutputStream());
    }

}
