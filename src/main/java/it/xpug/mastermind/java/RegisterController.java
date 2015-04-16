package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class RegisterController extends Controller{
	
	public RegisterController(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	public void service() throws IOException {
		response.setContentType("text/html");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password_rep = request.getParameter("password_rep");
		String mail = request.getParameter("mail");
		PrintWriter out = response.getWriter();
		out.write(nickname + ", " + password + ", " + password_rep + ", " + mail);
	}

}
