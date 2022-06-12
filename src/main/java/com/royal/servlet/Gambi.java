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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (UsuarioDAO.buscar("usuario@email.com") != null) {
			Usuario usuario = new Usuario("Usuário", "usuario@email.com", "123", false);

			UsuarioDAO.gravar(usuario);

			usuario = UsuarioDAO.buscar("usuario@email.com");


			//despesas
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
					new TransferenciaUsuario.Builder(new BigDecimal("-504.12"), Date.valueOf("2022-04-06"), "Compra no mercado", false, false, false, usuario.id, CategoriaDAO.buscarNome("Mercado").idCategoria).setObservacao("Recebimento do pagamento extra de alguém").build()
			);

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-120"), Date.valueOf("2022-03-12"), "Dentista", false, false, false, usuario.id, CategoriaDAO.buscarNome("Saúde").idCategoria).setObservacao("Foi feita limpeza dos dentes").build()
			);

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-100"), Date.valueOf("2022-03-02"), "Inspeção Geral", false, false, false, usuario.id, CategoriaDAO.buscarNome("Saúde").idCategoria).build()
			);

			//receitas

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("10"), Date.valueOf("2022-03-04"), "Rendimento SELIC março", false, false, false, usuario.id, CategoriaDAO.buscarNome("Saúde").idCategoria).build()
			);

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("9.43"), Date.valueOf("2022-05-14"), "Rendimento SELIC março", false, false, false, usuario.id, CategoriaDAO.buscarNome("Saúde").idCategoria).build()
			);

			//fixa


			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("1605.68"), Date.valueOf("2021-06-05"), "Salário Mensal", true, true, true, usuario.id, CategoriaDAO.buscarNome("Salário").idCategoria).setObservacao("Recebimento do salário").build()
			);

			//favorita

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-10"), Date.valueOf("2022-01-03"), "Café", true, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
			);

			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("120.32"), Date.valueOf("2022-06-02"), "Pagamento Extra", true, false, false, usuario.id, CategoriaDAO.buscarNome("Outras receitas").idCategoria).setObservacao("Recebimento do pagamento extra no serviço").build()
			);

			resp.getWriter().print(true);
		} else {
			resp.getWriter().print(false);
		}
	}


}
