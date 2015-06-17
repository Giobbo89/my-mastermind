package it.xpug.mastermind.java;

public class Session {
	
	private String id_session;
	private String user_nickname;
	
	public Session(String id, String nickname) {
		this.id_session = id;
		this.user_nickname = nickname;
	}
	
	public String getSessionId() {
		return this.id_session;
	}
	
	public String getUserNickname() {
		return this.user_nickname;
	}
		
}