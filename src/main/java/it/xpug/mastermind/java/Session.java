package it.xpug.mastermind.java;

public class Session {
	
	private String id_session;
	private String nickname;
	
	public Session(String id, String nickname) {
		this.id_session = id;
		this.nickname = nickname;
	}
	
	public String getSessionId() {
		return this.id_session;
	}
	
	public String getNickname() {
		return this.nickname;
	}
		
}
