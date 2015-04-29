package it.xpug.mastermind.java;

import it.xpug.generic.db.*;

import java.sql.Timestamp;
import java.util.*;

// classe repository per la gestione della tabella games, in cui vengono salvate le varie partite avviate

public class GamesRepository {
	
	private Database database;

	public GamesRepository(Database database) {
		this.database = database;
	}
	
	// metodo che permette di aggiungere una partita alla tabella attempts, restituendo l'id asscoiato alla partita
	public String createNewGame(String nickname) {
		// creo in maniera random l'id da associare alla partita
		Random random = new Random();
		int id = 1 + random.nextInt(2147483646);
		String game_id = String.valueOf(id);
		// prendo data e ora attuale per poter specificarle come inizio della partita
		Timestamp start_date = new Timestamp(new Date().getTime());
		String sql = "INSERT INTO games (game_id, user_nickname, secret_seq, points, start_date, finish_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		String sequence = secretSeqMaker();
		database.execute(sql, game_id, nickname, sequence, 0, start_date, null);
		return game_id;
	}
	
	// metodo privato con cui viene creata la sequenza segreta della partita
	private String secretSeqMaker(){
		Random random = new Random();
		String seq = "";
		for(int i=0; i<4; i++){
			int number = random.nextInt(6) + 1;
			seq = seq + number;
		}
		return seq;
		
	}
	
	// metodo che restituisce l'utente associato alla partita il cui id è dato come parametro
	public String getGameUser(String game_id){
		String sql = "SELECT * FROM games WHERE game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> game = (HashMap<String, Object>) rows.get(0);
		String nickname = (String) game.get("user_nickname");
		return nickname;	
	}
	
	// metodo che restituisce la sequenza segreta della partita il cui id è dato come parametro
	public String getGameSecretSeq(String game_id) {
		String sql = "SELECT * FROM games WHERE game_id = ?";
		ListOfRows rows = database.select(sql, game_id);
		HashMap<String, Object> game = (HashMap<String, Object>) rows.get(0);
		String secret_seq = (String) game.get("secret_seq");
		return secret_seq;
	}
	
	// metodo che calcola e restituisce il punteggio totale di tutte le partite dell'utente specificato come
	// argomento , utile per poi poter calcolare il punteggio medio di un utente
	public int getUserTotalPoints(String nickname) {
		String sql = "SELECT * FROM games WHERE user_nickname = ?";
		ListOfRows rows = database.select(sql, nickname);
		HashMap<String, Object> game;
		int total= 0; int n = 0;
		for (int i=0; i<rows.size(); i++) {
			game = (HashMap<String, Object>) rows.get(i);
			n = (int) game.get("points");
			total = total + n;
		}
		return total;
	}
	
	public ListOfRows getAllUserGames(String nickname) {
		String sql = "SELECT * FROM games WHERE user_nickname = ? ORDER BY start_date";
		ListOfRows result = database.select(sql, nickname);
		return result;	
	}
	
	// metodo che permette di specificare data e ora di conclusione della partita il cui id è dato come parametro 
	public void setGameFinishDate(String game_id) {
		Timestamp finish_date = new Timestamp(new Date().getTime());
		String sql = "UPDATE games SET finish_date = ? WHERE game_id = ?";
		database.execute(sql, finish_date, game_id);
	}
	
	// metodo che permette di specificare il punteggio della partita il cui id è dato come parametro
	public void setGamePoints(String game_id, int points) {
		String sql = "UPDATE games SET points = ? WHERE game_id = ?";
		database.execute(sql, points, game_id);
	}
	
	public void deleteGamesNotFinished(String nickname) {
		String sql = "DELETE FROM games WHERE user_nickname = ? AND finish_date IS NULL";
		database.execute(sql, nickname);
	}
	
}