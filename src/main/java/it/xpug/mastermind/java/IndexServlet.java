package it.xpug.mastermind.java;

import java.io.*;
import java.util.*;
import it.xpug.generic.db.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IndexServlet extends HttpServlet {

	private DatabaseConfiguration configuration;

	public IndexServlet(DatabaseConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginController login = new LoginController(request, response);
		RegisterController register = new RegisterController(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uri = request.getRequestURI();
		if (uri.equals("/login"))
			login.service();
		else if (uri.equals("/register"))
			register.service();
		else
			out.println("Niente, mi spiace");
	}
}