package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class LoginController extends Controller{
	
	private UsersRepository users_rep;
	
	public LoginController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep) {
		super(request, response);
		this.users_rep = users_rep;
	}
	
	public void service() throws IOException {
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		if(nickname.equals("") || password.equals("")) {
			writeBody(toJson("description", "Insert nickname e password for login"));
		} else if(!users_rep.nicknameExists(nickname)) {
			writeBody(toJson("description", "This nickname doesn't exist"));
		}
		else if (!users_rep.passwordIsCorrect(nickname, password)) {
			writeBody(toJson("description", "The password is wrong"));
		}
		else {
			writeBody(toJson("description", "Welcome " + nickname));
		}		
	}
	
}
