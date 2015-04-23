package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class LoginController extends Controller{
	
	private UsersRepository users_rep;
	private SessionsRepository sessions_rep;
	
	public LoginController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep,
			SessionsRepository sessions_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.sessions_rep = sessions_rep;
	}
	
	public void login() throws IOException {
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		if(nickname.equals("") || password.equals("")) {
			writeBody(toJson("result", "all"));
		} else if(!users_rep.nicknameExists(nickname)) {
			writeBody(toJson("result", "nick"));
		}
		else if (!users_rep.passwordIsCorrect(nickname, password)) {
			writeBody(toJson("result", "pass"));
		}
		else {
			writeBody(toJson("result", nickname));
			Session session = sessions_rep.createNewSession(nickname);
			response.addCookie(new Cookie("id_session", session.getSessionId()));
		}		
	}
	
	public void check_log() throws IOException {
		try {
			for (Cookie cookie : request.getCookies()) {
				Session session = sessions_rep.getSession(cookie.getValue());
				if (session != null) {
					String nickname = session.getNickname();
					writeBody(toJson("result", nickname));
					return;
				}
			}
		} catch (NullPointerException e) { }
	}
	
}
