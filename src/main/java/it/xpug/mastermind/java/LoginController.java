package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

// classe gestisce le operazioni relative al login di un utente

public class LoginController extends Controller{
	
	private UsersRepository users_rep;
	private SessionsRepository sessions_rep;
	
	// creazione dell'oggetto LoginController a cui vengono passati tramite argomenti i riferimenti alle
	// classi repository che gestiscono le tabelle users e sessions
	public LoginController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep,
			SessionsRepository sessions_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.sessions_rep = sessions_rep;
	}
	
	// metodo che viene chiamato quando un utente tenta di eseguire il login
	public void login() throws IOException {
		// prendo, dalla richiesta del client, il nickname e la password specificati dall'utente nel form apposito
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		// controllo che tutti i campi siano stati riempiti
		if(nickname.equals("") || password.equals("")) {
			writeBody(toJson("result", "all"));
		}
		// controllo che il nickname specificato sia di un utente effettivamente specificato
		else if(!users_rep.nicknameExists(nickname)) {
			writeBody(toJson("result", "nick"));
		}
		// controllo che la password specificata associata a quel nickname sia corretta
		else if (!users_rep.passwordIsCorrect(nickname, password)) {
			writeBody(toJson("result", "pass"));
		}
		// se supero tutti i controlli, eseguo il login dell'utente e creo una nuova sessione per quell'utente
		else {
			writeBody(toJson("result", nickname));
			Session session = sessions_rep.createNewSession(nickname);
			response.addCookie(new Cookie("id_session", session.getSessionId()));
		}		
	}
	
	// metodo che viene chiamato quando la pagina viene caricata: controlla, tramite i cookie, se vi è una
	// sessione nel database
	public void check_log() throws IOException {
		try {
			for (Cookie cookie : request.getCookies()) {
				Session session = sessions_rep.getSession(cookie.getValue());
				// se vi è una sessione specificata nel cookie, eseguo il login per quell'utente
				if (session != null) {
					String nickname = session.getNickname();
					writeBody(toJson("result", nickname));
					return;
				}
			}
		} catch (NullPointerException e) { }
	}
	
}
