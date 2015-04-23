package it.xpug.mastermind.java;

import java.io.*;
import javax.servlet.http.*;

public class GameController extends Controller{
		
	private UsersRepository users_rep;
	private SessionsRepository sessions_rep;
	private GamesRepository games_rep;
	
	public GameController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep, 
			SessionsRepository sessions_rep, GamesRepository games_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.sessions_rep = sessions_rep;
		this.games_rep = games_rep;
	}

	public void new_game() throws IOException {
		for (Cookie cookie : request.getCookies()) {
			Session session = sessions_rep.getSession(cookie.getValue());
			if (session != null) {
				Game game = games_rep.createGame(session.getNickname());
				String[] names = {"game_id","sequence"};
				String[] values = {game.getGameId(),game.getSecretSeq()};
				writeBody(toJson(names, values));
			}
		}
	}
	
	public void move() throws IOException {
		String attempt = request.getParameter("attempt");
		String secret_seq = games_rep.getGameSecretSeq(request.getParameter("game_id"));
		String result = compareSequence(secret_seq, attempt);
		writeBody(toJson("result", result));
	}
	
	private String compareSequence(String secret_seq, String attempt) {
		Boolean check[] = {false, false, false, false};
		String result = "";
		for (int i=0; i<4; i++) {
			if (attempt.charAt(i) == secret_seq.charAt(i))
				result = result + "+";
			else {
				for (int j=0; j<4; j++) {
					if (attempt.charAt(i) == secret_seq.charAt(j)) {
						result = result + "-";
						break;
					}
				}
			}
		}
		return result;
	}

}