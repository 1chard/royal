package com.royal.servlet;

import com.royal.dao.CategoriaDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.TransferenciaUsuario;
import com.royal.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author richard
 */
@WebServlet(name = "Gambi", urlPatterns = {"/gambi"})
public class Gambi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if(UsuarioDAO.buscar("usuario@email.com") != null){
//	    Usuario usuario = new Usuario("Usuário", "usuario@email.com", "123", false);
//	    
//	    UsuarioDAO.gravar(usuario);
//	    
//	    usuario = UsuarioDAO.buscar("usuario@email.com");
//	    
	
//	} 
    }


}
