package it.xpug.mastermind.java;

import it.xpug.generic.db.*;

import java.util.*;

public class GamesRepository {
	
	private Database database;

	public GamesRepository(Database database) {
		this.database = database;
	}
	
	public Game createGame(String nickname) {
		Random random = new Random();
		int id = 1 + random.nextInt(2147483646);
		String game_id = String.valueOf(id);
		String sql = "INSERT INTO games (game_id, user_nickname, secret_seq, attempts) VALUES (?, ?, ?, ?)";
		String sequence = secretSeqMaker();
		Game game = new Game(game_id, nickname, sequence, "0");
		database.execute(sql, game_id, nickname, sequence, "0");
		return game;
	}
	
	private String secretSeqMaker(){
		Random random = new Random();
		String seq = "";
		for(int i=0; i<4; i++){
			int number = 1 + random.nextInt(6);
			seq = seq + number;
		}
		return seq;
		
	}
}