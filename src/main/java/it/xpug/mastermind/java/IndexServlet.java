package it.xpug.mastermind.java;

import java.io.*;
import it.xpug.generic.db.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
		
		GameController game_controller = new GameController(request, response, users_rep, sessions_rep, games_rep);
		RegisterController reg_controller = new RegisterController(request, response, users_rep);
		LoginController log_controller = new LoginController(request, response, users_rep, sessions_rep);
		
		String uri = request.getRequestURI();
		
		if (uri.equals("/register")) {
			reg_controller.service();
		}
		if (uri.equals("/login")) {
			log_controller.login();
		}
		if (uri.equals("/check_log")) {
			log_controller.check_log();
		}
		if (uri.equals("/new_game")) {
			game_controller.new_game();
		}
		if (uri.equals("/move")) {
			game_controller.move();
		}
	}
}