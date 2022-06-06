package com.royal.servlet;

import com.royal.dao.CategoriaDAO;
import com.royal.dao.TransferenciaUsuarioDAO;
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
        if(UsuarioDAO.buscar("usuario@email.com") != null){
	    Usuario usuario = new Usuario("Usuário", "usuario@email.com", "123", false);
	    
	    UsuarioDAO.gravar(usuario);
	    
	    usuario = UsuarioDAO.buscar("usuario@email.com");
	    

	    TransferenciaUsuarioDAO.gravar(
		    new TransferenciaUsuario.Builder(new BigDecimal("-10"), Date.valueOf("2022-01-22"), "Café de janeiro", false, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
	    );
	    
	    TransferenciaUsuarioDAO.gravar(
		    new TransferenciaUsuario.Builder(new BigDecimal("-10"), Date.valueOf("2022-02-02"), "Café de fevereiro", false, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
	    );
	    
	    TransferenciaUsuarioDAO.gravar(
		    new TransferenciaUsuario.Builder(new BigDecimal("-10"), Date.valueOf("2022-04-19"), "Café de abril", false, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
	    );
	    
	    TransferenciaUsuarioDAO.gravar(
		    new TransferenciaUsuario.Builder(new BigDecimal("-10"), Date.valueOf("2022-05-01"), "Café de março", false, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
	    );
	    
	    TransferenciaUsuarioDAO.gravar(
		    new TransferenciaUsuario.Builder(new BigDecimal("1205.32"), Date.valueOf("2022-06-02"), "Pagamento Extra", true, false, false, usuario.id, CategoriaDAO.buscarNome("Outras receitas").idCategoria).setObservacao("Recebimento do pagamento extra de alguém").build()
	    );
	    
	    resp.getWriter().print(true);
	} 
    }


}
