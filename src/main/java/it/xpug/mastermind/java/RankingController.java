package it.xpug.mastermind.java;

import it.xpug.generic.db.*;
import java.io.*;
import java.sql.Timestamp;

import javax.servlet.http.*;

public class RankingController extends Controller{
	
	private UsersRepository users_rep;
	private GamesRepository games_rep;
	
	public RankingController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep, 
			GamesRepository games_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.games_rep = games_rep;
	}
	
	public void load_ranking() throws IOException {
		ListOfRows all_users = users_rep.getAllUsersByAverage();
		String[] names = {"position", "nickname","num_games", "average"};
		String ranking = "[";
		for (int i = 0; i < all_users.size(); i++){
			String nickname = (String) all_users.get(i).get("nickname");
			int num_games = (Integer) all_users.get(i).get("num_games");
			float average = (float) all_users.get(i).get("average");
			String[] values = {String.valueOf(i+1), nickname, String.valueOf(num_games), String.valueOf(average)};
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
	
	public void user_games() throws IOException {
		String nickname = request.getParameter("nickname");
		games_rep.deleteGamesNotFinished(nickname);
		ListOfRows all_games = games_rep.getAllUserGames(nickname);
		String[] names = {"number", "game_id","start_date", "finish_date", "points"};
		String games = "[";
		for (int i = 0; i < all_games.size(); i++){
			String game_id = (String) all_games.get(i).get("game_id");
			Timestamp start = (Timestamp) all_games.get(i).get("start_date");
			Timestamp finish = (Timestamp) all_games.get(i).get("finish_date");
			int points = (int) all_games.get(i).get("points");
			String[] values = {String.valueOf(i+1), game_id, String.valueOf(start), String.valueOf(finish), String.valueOf(points)};
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

}
