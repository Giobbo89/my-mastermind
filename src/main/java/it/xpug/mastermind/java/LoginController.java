package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class LoginController extends Controller{
	
	public LoginController(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	public void service() throws IOException {
		response.setContentType("text/html");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		out.write(nickname + ", " + password);
	}
}
