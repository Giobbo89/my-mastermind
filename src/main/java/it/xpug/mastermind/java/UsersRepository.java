package it.xpug.mastermind.java;

import java.security.*;
import java.util.*;

import it.xpug.generic.db.*;

// classe repository per la gestione della tabella users, in cui vengono salvati i vari utenti che si registrano

public class UsersRepository {
	private Database database;

	public UsersRepository(Database database) {
		this.database = database;
	}
	
	// metodo che permette di aggiungere un nuovo utente alla tabella users
	public void createNewUser(User user) {
		String sql = "INSERT INTO users (nickname, password, mail, enc, num_games, average) VALUES (?, ?, ?, ?, ?, ?)";
		database.execute(sql, user.getNickname(), user.encryptedPassword(), user.getMail(), user.getEnc(), 0, 0);
	}

	// metodo che permette di verificare che la password passata tramite argomento sia uguale a quella salvata
	// nel database relativamente all'utente specificato tramite argomento
	public boolean passwordIsCorrect(String nickname, String password) {
		String sql = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows result = database.select(sql, nickname);
		HashMap<String, Object> user = (HashMap<String, Object>) result.get(0);
		String enc = (String) user.get("enc");
		String pass_calculated = encryptedPassword(password, enc);
		String enc_password = (String) user.get("password");
		boolean correct = pass_calculated.equals(enc_password);
		return correct;
	}
	
	// metodo che cripta la password a partire dalla password stessa e da una stringa
	private String encryptedPassword(String password, String enc) {
		try {
			String cript = "" + password + enc;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(cript.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return User.toHexString(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public int getNumberGames(String nickname) {
		String sql = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows result = database.select(sql, nickname);
		HashMap<String, Object> user = (HashMap<String, Object>) result.get(0);
		int num_games = (Integer) user.get("num_games");
		return num_games;
	}
	
	public float getAverage(String nickname) {
		String sql = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows result = database.select(sql, nickname);
		HashMap<String, Object> user = (HashMap<String, Object>) result.get(0);
		float average = (float) user.get("average");
		return average;
	}
	
	public ListOfRows getAllUsersByAverage() {
		String sql = "SELECT * FROM users ORDER BY average";
		ListOfRows result = database.select(sql);
		return result;
	}
	
	// metodo che permette di verificare se il nickname passato come argomento esiste nel database
	public boolean nicknameExists(String nickname) {
		String sql = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows rows = database.select(sql, nickname);
		boolean result = (rows.size() != 0);
		return result;
	}
	
	// metodo che aggiorna le informazioni presenti nel database (numero di partite e punteggio medio) relativamente
	// ad un utente passato come argomento
	public void updateGameFinished(String nickname, int points, int total) {
		String sql1 = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows result = database.select(sql1, nickname);
		HashMap<String, Object> user = (HashMap<String, Object>) result.get(0);
		int num_games = (int) user.get("num_games");
		num_games = num_games + 1;
		String sql2 = "UPDATE users SET num_games = ? WHERE nickname = ?";
		database.execute(sql2, num_games, nickname);
		float average;
		if (num_games == 1) {
			average = (float)(total);
		} else {
			System.out.println("Calcolo media: "+ total + " / " + num_games);
			average = (float)(total)/(float)num_games;
		}
		String sql3 = "UPDATE users SET average = ? WHERE nickname = ?";
		database.execute(sql3, average, nickname);
	}
}