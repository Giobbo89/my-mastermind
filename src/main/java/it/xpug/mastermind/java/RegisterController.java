package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class RegisterController extends Controller{
	
	private UsersRepository users_rep;
	
	public RegisterController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep) {
		super(request, response);
		this.users_rep = users_rep;
	}
	
	public void service() throws IOException {
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password_rep = request.getParameter("password_rep");
		String mail = request.getParameter("mail");
		if(nickname.equals("") || password.equals("") || password_rep.equals("") || mail.equals("")) {
			writeBody(toJson("description", "All field is mandatory"));
		} else if(mail.indexOf('@')==-1){
			writeBody(toJson("description", "Mail not valid (doesn't have the '@')"));
		}
		else if (password.length()<8){
			writeBody(toJson("description", "Password is too short (minimum8 characters)"));
		}
		else if (!password.equals(password_rep)){
			writeBody(toJson("description", "Passwords are not equal"));
		}
		else if (users_rep.nicknameExists(nickname)){
			writeBody(toJson("description", "This nickname is already in use; please, select another"));
		}
		else {
			User new_user = new User(nickname, password, mail);
			users_rep.add(new_user);
			writeBody(toJson("description", "Well done! You are registered now"));
		}
	}

}
