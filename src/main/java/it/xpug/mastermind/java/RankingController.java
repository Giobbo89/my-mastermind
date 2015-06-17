package it.xpug.mastermind.java;

import it.xpug.generic.db.*;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.*;

// classe che gestisce le operazioni relative alla creazione dinamica delle tabelle del ranking, dello storico
// delle partite di un utente e delle storico dei tentativi di una partita

public class RankingController extends Controller{
	
	private UsersRepository users_rep;
	private GamesRepository games_rep;
	private AttemptsRepository attempts_rep;
	
	public RankingController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep, 
			GamesRepository games_rep, AttemptsRepository attempts_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.games_rep = games_rep;
		this.attempts_rep = attempts_rep;
	}
	
	// metodo che viene chiamato quando l'utente vuole visualizzare il ranking degli utenti
	public void load_ranking() throws IOException {
		ListOfRows all_users = users_rep.getAllUsersByAverage();
		String[] names = {"position", "nickname","num_games", "average"};
		String ranking = "[";
		// dopo aver preso il riferimento a tutti gli utenti che hanno giocato almeno una partita,
		// per ogni utente prelevo nickname, numero di partite e punteggio medio
		for (int i = 0; i < all_users.size(); i++){
			String nickname = (String) all_users.get(i).get("nickname");
			int num_games = (Integer) all_users.get(i).get("num_games");
			float average = (float) all_users.get(i).get("average");
			String avg = String.format("%.2f", average);
			String[] values = {String.valueOf(i+1), nickname, String.valueOf(num_games), avg};
			if (i == 0) {
				ranking = ranking + toJson(names, values);
			} else {
				ranking = ranking + "," + toJson(names, values);
			}
		}
		ranking = ranking + "]";
		String json = toJson("users", ranking);
		json = json.replace("\"[", "[");
		json = json.replace("]\"", "]");
		writeBody(json);
	}
	
	// metodo che viene chiamato quando l'utente vuole visualizzare lo storico partite di un utente
	public void user_games() throws IOException {
		String nickname = request.getParameter("nickname");
		games_rep.deleteGamesNotFinished(nickname);
		ListOfRows all_games = games_rep.getAllUserGames(nickname);
		String[] names = {"number", "game_id","start_date", "finish_date", "score"};
		String games = "[";
		// dopo aver preso il riferimento a tutte le partite giocate e terminate da un utente,
		// per ogni partita prelevo game_id, dta e ora di inizio e fine partita e relativo punteggio
		for (int i = 0; i < all_games.size(); i++){
			String game_id = (String) all_games.get(i).get("game_id");
			Timestamp start_date = (Timestamp) all_games.get(i).get("start_date");
			Timestamp finish_date = (Timestamp) all_games.get(i).get("finish_date");
			String start = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(start_date);
			String finish = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(finish_date);
			int score = (int) all_games.get(i).get("score");
			String[] values = {String.valueOf(i+1), game_id, start, finish, String.valueOf(score)};
			if (i == 0) {
				games = games + toJson(names, values);
			} else {
				games = games + "," + toJson(names, values);
			}
		}
		games = games + "]";
		String json = toJson("games", games);
		json = json.replace("\"[", "[");
		json = json.replace("]\"", "]");
		writeBody(json);
	}
	
	// metodo che viene chiamato quando l'utente vuole visualizzare lo storico tentativi relativo ad una partita
	public void game_attempts() throws IOException {
		String game_id = request.getParameter("game_id");
		ListOfRows all_attempts = attempts_rep.getAllGameAttempts(game_id);
		String[] names = {"num", "attempt", "result"};
		String attempts = "[";
		// dopo aver preso il riferimento a tutti i tentativi fatti durante una partita (conclusa o abbandonata),
		// per ogni tentativo prelevo il numero, la sequenza tentata e il risultato
		for (int i = 0; i < all_attempts.size(); i++){
			int number = (int) all_attempts.get(i).get("att_number");
			String attempt = (String) all_attempts.get(i).get("attempt");
			String result = (String) all_attempts.get(i).get("result");
			String[] values = {String.valueOf(number), attempt, result};
			if (i == 0) {
				attempts = attempts + toJson(names, values);
			} else {
				attempts = attempts + "," + toJson(names, values);
			}
		}
		attempts = attempts + "]";
		String json = toJson("attempts", attempts);
		json = json.replace("\"[", "[");
		json = json.replace("]\"", "]");
		writeBody(json);
	}

}