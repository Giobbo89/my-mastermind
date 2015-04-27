package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

// classe gestisce le operazioni relative alla registrazione di un nuovo utente

public class RegisterController extends Controller{
	
	private UsersRepository users_rep;
	
	// creazione dell'oggetto RegisterController a cui vengono passati tramite argomenti i riferimenti alla
	// classe repository che gestisce la tabella users del database
	public RegisterController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep) {
		super(request, response);
		this.users_rep = users_rep;
	}
	
	// metodo che viene chiamato quando un nuovo utente cerca di registrarsi 
	public void register() throws IOException {
		// prendo, dalla richiesta del client, il nickname, la password, la ripetizione della pass e la
		// mail specificati dall'utente nel form apposito
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password_rep = request.getParameter("password_rep");
		String mail = request.getParameter("mail");
		// controllo che tutti i campi siano stati riempiti
		if(nickname.equals("") || password.equals("") || password_rep.equals("") || mail.equals("")) {
			writeBody(toJson("result", "all"));
		} 
		// controllo che la mail specificata contenga il carattere @
		else if(mail.indexOf('@') == -1){
			writeBody(toJson("result", "mail"));
		}
		// controllo che la password sia lunga almeno 8 caratteri
		else if (password.length() < 8){
			writeBody(toJson("result", "short"));
		}
		// controllo che le due password siano uguali
		else if (!password.equals(password_rep)){
			writeBody(toJson("result", "equal"));
		}
		// controllo che il nickname specificato non sia già utilizzato da qualche altro utente
		else if (users_rep.nicknameExists(nickname)){
			writeBody(toJson("result", "nick"));
		}
		// se supero tutti i controlli, creo il nuovo utente
		else {
			User new_user = new User(nickname, password, mail);
			users_rep.createNewUser(new_user);
			writeBody(toJson("result", "done"));
		}
	}

}
