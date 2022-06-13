package com.royal.servlet;

import com.royal.dao.CategoriaDAO;
import com.royal.dao.TransferenciaUsuarioDAO;
import com.royal.dao.UsuarioDAO;
import com.royal.model.Frequencia;
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
		Usuario usuario = UsuarioDAO.buscar("usuario@gmail.com");
		
		if (usuario == null) {
			UsuarioDAO.gravar(new Usuario("Usuário", "usuario@gmail.com", "123", false));
			usuario = UsuarioDAO.buscar("usuario@gmail.com");
		}

			
			


			//despesas
			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-230"), Date.valueOf("2021-07-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-120"), Date.valueOf("2021-07-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );   

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-210"), Date.valueOf("2021-08-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);
            
                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-100"), Date.valueOf("2021-08-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );  

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-190"), Date.valueOf("2021-09-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-130"), Date.valueOf("2021-09-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );  

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-280"), Date.valueOf("2021-10-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-140"), Date.valueOf("2021-10-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );  
            
            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-250"), Date.valueOf("2021-11-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-110"), Date.valueOf("2021-11-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );  

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-190"), Date.valueOf("2021-12-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-130"), Date.valueOf("2021-12-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );  

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-210"), Date.valueOf("2022-01-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-150"), Date.valueOf("2022-01-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-200"), Date.valueOf("2022-02-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-120"), Date.valueOf("2022-02-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-180"), Date.valueOf("2022-03-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-140"), Date.valueOf("2022-03-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-250"), Date.valueOf("2022-04-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);
                
                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-90"), Date.valueOf("2022-04-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-170"), Date.valueOf("2022-05-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);

                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-120"), Date.valueOf("2022-05-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
                );

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-200"), Date.valueOf("2022-06-05"), "Conta de luz", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
			);
                TransferenciaUsuarioDAO.gravar(
                        new TransferenciaUsuario.Builder(new BigDecimal("-110"), Date.valueOf("2022-06-05"), "Conta de água", false, false, false, usuario.id, CategoriaDAO.buscarNome("Casa").idCategoria).build()
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
					new TransferenciaUsuario.Builder(new BigDecimal("10.13"), Date.valueOf("2022-03-04"), "Rendimento SELIC março", false, false, false, usuario.id, CategoriaDAO.buscarNome("Saúde").idCategoria).build()
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
			
            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-250"), Date.valueOf("2021-06-05"), "Conta de luz", true, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
			);

            TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-150"), Date.valueOf("2021-06-05"), "Conta de água", true, false, false, usuario.id, CategoriaDAO.buscarNome("Alimentação").idCategoria).build()
			);            
			// parcelada 
			TransferenciaUsuarioDAO.gravar(
					new TransferenciaUsuario.Builder(new BigDecimal("-1999.99"), Date.valueOf("2022-01-02"), "Celular", false, true, false, usuario.id, CategoriaDAO.buscarNome("Lazer").idCategoria).setObservacao("Meu celular").setParcelas(12).setFrequencia(Frequencia.MESES).build()
			);


			resp.getWriter().print(true);
	}


}
