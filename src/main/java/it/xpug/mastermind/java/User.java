package it.xpug.mastermind.java;

import java.security.*;
import java.util.*;

// classe creata a partire dalla classe Cashier dell'esempio supermarket del prof,

public class User {
	private String nickname; 
	private String password;
	private String mail;
	private String enc; // stringa utilizzata per criptare la password (in modo da non utilizzare solamente la password stessa)

	public User(String nickname, String password, String mail) {
		this.nickname = nickname;
		this.password = password;
		this.mail = mail;
		Random random = new Random();
		String r = Integer.toString(random.nextInt(10000000));
		this.enc = r;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getPassword() {
		return this.password;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getEnc() {
		return this.enc;
	}

	public String encryptedPassword() {
		try {
			String cript = "" + this.password + this.enc;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(cript.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return toHexString(digest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static String toHexString(byte [] bytes) {
		String symbols = "0123456789abcdef";
		String result = "";
		for (byte b : bytes) {
			int i = b & 0xFF;
			result += symbols.charAt(i / 16);
			result += symbols.charAt(i % 16);
		}
		return result;
	}
}