package it.xpug.mastermind.java;

import it.xpug.generic.db.*;
import java.util.*;

// classe repository per la gestione della tabella sessions, in cui vengono salvate le varie sessioni relative agli utenti

public class SessionsRepository {

	private Database database;
	
	public SessionsRepository(Database database) {
		this.database = database;
	}

	// metodo che permette di creare una nuova sessione e aggiungerla alla tabella sessions
	public Session createNewSession(String nickname) {
		Random random = new Random();
		int id = 1 + random.nextInt(2147483646);
		String session_id = String.valueOf(id);
		Session session = new Session(session_id, nickname);
		String sql = "INSERT INTO sessions (session_id, nickname) VALUES (?, ?)";
		database.execute(sql, session.getSessionId(), session.getNickname());
		return session;
	}

	// metodo che restituisce un oggetto sessione a partire dall'id della sessione, passato come argomento 
	public Session getSession(String session_id) throws IndexOutOfBoundsException {
		try {
		String sql = "SELECT * FROM sessions WHERE session_id = ?";
		ListOfRows rows = database.select(sql, session_id);
		HashMap<String, Object> result = (HashMap<String, Object>) rows.get(0);
		String nickname = (String) result.get("nickname");
		Session session = new Session(session_id, nickname);
		return session;
		} catch(IndexOutOfBoundsException e) { return null; }
	}

}
