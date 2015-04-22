package it.xpug.mastermind.java;

import java.security.*;
import java.util.*;
import it.xpug.generic.db.*;

public class UsersRepository {
	private Database database;

	public UsersRepository(Database database) {
		this.database = database;
	}
	
	public void add(User user) {
		String sql = "INSERT INTO users (nickname, password, mail, enc, num_games, average) VALUES (?, ?, ?, ?, ?, ?)";
		database.execute(sql, user.getNickname(), user.encryptedPassword(), user.getMail(), user.getEnc(), 0, 0);
	}

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
	
	public String encryptedPassword(String password, String enc) {
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
	
	public boolean nicknameExists(String nickname) {
		String sql = "SELECT * FROM users WHERE nickname = ?";
		ListOfRows rows = database.select(sql, nickname);
		boolean result = (rows.size() != 0);
		return result;
	}
}