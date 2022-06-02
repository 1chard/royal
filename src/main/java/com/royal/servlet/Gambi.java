package com.royal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author richard
 */
@WebServlet(name = "Gambi", urlPatterns = {"/gambi"})
public class Gambi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(
                new String(Runtime.getRuntime().exec("find ./").getInputStream().readAllBytes())
        );
    }


}
