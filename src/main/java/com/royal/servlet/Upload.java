package com.royal.servlet;

import com.royal.API;
import com.royal.Sistema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author suporte
 */
@WebServlet(name = "Upload", urlPatterns = {"/upload/*"})
public class Upload extends HttpServlet {
	public static final int TAMANHO = 1024 * 1024 * 5;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		var args = API.parameters(req);

		if (args.length > 0) {
			Files.copy(Paths.get(Sistema.PASTA, args[0]), resp.getOutputStream());
		}


	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (Sistema.PESSOAS.containsKey(req.getParameter("k"))) {

			var input = req.getInputStream().readNBytes(TAMANHO);

			var nome = System.currentTimeMillis() + "_" + System.nanoTime() + "_";
			Path path;
			int i = 0;

			do {
				nome = nome.substring(0, nome.lastIndexOf('_')) + "_" + i;
				path = Paths.get(Sistema.PASTA, nome);
			} while (Files.isRegularFile(path));

			Files.write(Files.createFile(path), input);

			resp.getWriter().append(nome);

		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}


}
