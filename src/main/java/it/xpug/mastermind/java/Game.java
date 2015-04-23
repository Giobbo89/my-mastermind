package it.xpug.mastermind.java;

public class Game {
	private String game_id;
	private String user_nickname;
	private String secret_seq;
	private String attempts;
	
	public Game (String game_id, String user_nickname, String secret_seq, String attempts){
		this.game_id = game_id;
		this.user_nickname = user_nickname;
		this.secret_seq = secret_seq;
		this.attempts = attempts;
	}
	
	public String getGameId(){
		return this.game_id;
	}
	
	public String getNickname(){
		return this.user_nickname;
	}
	
	public String getSecretSeq(){
		return this.secret_seq;
	}
	
	public String getAttempts(){
		return this.attempts;
	}
}