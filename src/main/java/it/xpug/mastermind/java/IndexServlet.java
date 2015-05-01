package it.xpug.mastermind.java;

import java.io.*;
import it.xpug.generic.db.*;
import javax.servlet.*;
import javax.servlet.http.*;

// servlet dell'applicazione alla quale pervengono tutte le richieste Ajax

public class IndexServlet extends HttpServlet {

	private DatabaseConfiguration configuration;

	public IndexServlet(DatabaseConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database database = new Database(configuration);

		UsersRepository users_rep = new UsersRepository(database);
		SessionsRepository sessions_rep = new SessionsRepository(database); 
		GamesRepository games_rep = new GamesRepository(database);
		AttemptsRepository attempts_rep = new AttemptsRepository(database);
		
		RegisterController reg_controller = new RegisterController(request, response, users_rep);
		LoginController log_controller = new LoginController(request, response, users_rep, sessions_rep);
		GameController game_controller = new GameController(request, response, users_rep, sessions_rep, games_rep, attempts_rep);
		RankingController ranking_controller = new RankingController(request, response, users_rep, games_rep, attempts_rep);
		
		// prendo l'uri della richiesta ed eseguo l'operazione ad esso associata 
		String uri = request.getRequestURI();
		
		if (uri.equals("/register")) {
			reg_controller.register();
		}
		if (uri.equals("/login")) {
			log_controller.login();
		}
		if (uri.equals("/check_log")) {
			log_controller.check_log();
		}
		if (uri.equals("/logout")) {
			log_controller.logout();
		}
		if (uri.equals("/new_game")) {
			game_controller.new_game();
		}
		if (uri.equals("/move")) {
			game_controller.move();
		}
		if (uri.equalsIgnoreCase("/abandon")) {
			game_controller.abandon();
		}
		if (uri.equalsIgnoreCase("/load_ranking")) {
			ranking_controller.load_ranking();
		}
		if (uri.equalsIgnoreCase("/user_games")) {
			ranking_controller.user_games();
		}
		if (uri.equalsIgnoreCase("/game_attempts")) {
			ranking_controller.game_attempts();
		}
	}
}