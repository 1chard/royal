package com.royal.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author richard
 */
@WebFilter("/*")
public class Filtrador implements Filter{

	@Override
	public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
		var req = (HttpServletRequest) sr;
		var res = (HttpServletResponse) sr1;
		
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Max-Age", "86400");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT");
		res.setHeader("Access-Control-Allow-Headers", "*");
		
		System.out.println(req.getRequestURL());
		fc.doFilter(sr, sr1);
	}
	
}
