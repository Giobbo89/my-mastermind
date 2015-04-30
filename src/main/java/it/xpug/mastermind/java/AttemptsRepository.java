package it.xpug.mastermind.java;

import it.xpug.generic.db.*;

// classe repository per la gestione della tabella attempts, in cui vengono salvati i vari tentativi fatti da un
// utente durante una partita

public class AttemptsRepository {
	
	private Database database;
	
	public AttemptsRepository(Database database) {
		this.database = database;
	}
	
	// metodo che permette di aggiungere un attempt alla tabella attempts
	public void createNewAttempt(String game_id, String nickname, String attempt, int att_num, 
			String secret_seq, String result) {
		String sql = "INSERT INTO attempts (game_id, user_nickname, attempt, att_number, secret_seq, result)" +
				" VALUES (?, ?, ?, ?, ?, ?)";
		database.execute(sql, game_id, nickname, attempt, att_num, secret_seq, result);
	}
	
	public ListOfRows getAllGameAttempts(String game_id) {
		String sql = "SELECT * FROM attempts WHERE game_id = ? ORDER BY att_number";
		ListOfRows result = database.select(sql, game_id);
		return result;	
	}
}
