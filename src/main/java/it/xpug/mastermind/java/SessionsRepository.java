package it.xpug.mastermind.java;

import it.xpug.generic.db.*;
import java.util.*;

public class SessionsRepository {

	private Database database;
	
	public SessionsRepository(Database database) {
		this.database = database;
	}

	public Session createNewSession(String nickname) {
		Random random = new Random();
		int id = random.nextInt(2147483646);
		String session_id = String.valueOf(id);
		Session session = new Session(session_id, nickname);
		String sql = "INSERT INTO sessions (session_id, nickname) VALUES (?, ?)";
		database.execute(sql, session.getSessionId(), session.getNickname());
		return session;
	}

	public Session getSession(String session_id) throws IndexOutOfBoundsException {
		String sql = "SELECT * FROM sessions WHERE session_id = ?";
		ListOfRows rows = database.select(sql, session_id);
		HashMap<String, Object> result = (HashMap<String, Object>) rows.get(0);
		String nickname = (String) result.get("nickname");
		Session session = new Session(session_id, nickname);
		return session;
	}

}
