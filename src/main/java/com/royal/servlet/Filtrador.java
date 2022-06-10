package com.royal.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * @author richard
 */
@WebFilter("/*")
public class Filtrador implements Filter {

    public static final int MAXIMO_TRANSFERENCIA = 50;
    public final Semaphore semaforo = new Semaphore(MAXIMO_TRANSFERENCIA);

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        var req = (HttpServletRequest) sr;
        var res = (HttpServletResponse) sr1;

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT");
        res.setHeader("Access-Control-Allow-Headers", "*");

	System.out.println(req.getRequestURL() + "?" + req.getQueryString());
	
        fc.doFilter(sr, sr1);

    }

}
