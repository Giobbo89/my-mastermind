package it.xpug.mastermind.java;

import it.xpug.generic.db.*;
import java.io.*;
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
	
	public void load() throws IOException {
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

}
