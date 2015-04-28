package it.xpug.mastermind.java;

import java.io.*;

import javax.servlet.http.*;

// classe gestisce le operazioni relative ad una partita (creazione nuova partita e invio tentativo)

public class GameController extends Controller{
	
	private UsersRepository users_rep;
	private SessionsRepository sessions_rep;
	private GamesRepository games_rep;
	private AttemptsRepository attempts_rep;
	
	// creazione dell'oggetto GameController a cui vengono passati tramite argomenti i riferimenti alle varie
	// classi repository
	public GameController(HttpServletRequest request, HttpServletResponse response, UsersRepository users_rep, 
			SessionsRepository sessions_rep, GamesRepository games_rep, AttemptsRepository attempts_rep) {
		super(request, response);
		this.users_rep = users_rep;
		this.sessions_rep = sessions_rep;
		this.games_rep = games_rep;
		this.attempts_rep = attempts_rep;
	}

	// metodo che viene chiamato quando l'utente avvia una nuova partita
	public void new_game() throws IOException {
		for (Cookie cookie : request.getCookies()) {
			Session session = sessions_rep.getSession(cookie.getValue());
			if (session != null) {
				// prendo l'id della partita e la sequenza segreta da indovinare associato ad essa
				String game_id = this.games_rep.createNewGame(session.getNickname());
				String secret_seq = this.games_rep.getGameSecretSeq(game_id);
				// invio i due dati nella risposta al client
				String[] names = {"game_id","sequence"};
				String[] values = {game_id,secret_seq};
				writeBody(toJson(names, values));
			}
		}
	}
	
	// metodo che viene chiamato quando l'utente fa un tentativo per indovinare la sequenza segreta
	public void move() throws IOException {
		// prendo il tentativo fatto, il numero del tentativo e l'id della partita inviati dal client
		String attempt = request.getParameter("attempt");
		String num = request.getParameter("att_number");
		int att_number = Integer.parseInt(num);
		String game_id = request.getParameter("game_id");
		// dall'id della partita, risalgo all'utente che sta giocando la partita e alla sequenza segreta
		String nickname = games_rep.getGameUser(game_id);
		String secret_seq = games_rep.getGameSecretSeq(game_id);
		String result = "";
		String[] names = {"result","num_games", "average"};
		// confronto il tentativo inviato con la sequenza segreta
		if (attempt.isEmpty() || attempt.length()<4)
			result = "invalid_try";
		else {
			result = compareSequence(secret_seq, attempt);
			// aggiungo il tentativo al database (in particolare alla tabella apposita)
			this.attempts_rep.createNewAttempt(game_id, nickname, attempt, att_number, secret_seq, result);
			// se il tentativo è vincente...
			if (result.equals("++++")) {
				// ...aggiorno la fine della partita, i punti fatti e la media dell'utente
				this.games_rep.setGameFinishDate(game_id);
				this.games_rep.setGamePoints(game_id, att_number);
				int total = this.games_rep.getUserTotalPoints(nickname);
				this.users_rep.updateGameFinished(nickname, att_number, total);
				int num_games = this.users_rep.getNumberGames(nickname);
				float average = this.users_rep.getAverage(nickname);
				String[] values = {result, String.valueOf(num_games), String.valueOf(average)};
				writeBody(toJson(names, values));
			} else {
				String[] values = {result, "null", "null"};
				writeBody(toJson(names, values));
			}
		}
	}
	
	public void abandon() throws IOException {
		String num = request.getParameter("att_number");
		int att_number = Integer.parseInt(num);
		att_number = att_number + 15;
		String game_id = request.getParameter("game_id");
		// dall'id della partita, risalgo all'utente che sta giocando la partita e alla sequenza segreta
		String nickname = games_rep.getGameUser(game_id);
		String result = "";
		String[] names = {"result","num_games", "average"};
		this.games_rep.setGameFinishDate(game_id);
		this.games_rep.setGamePoints(game_id, att_number);
		int total = this.games_rep.getUserTotalPoints(nickname);
		this.users_rep.updateGameFinished(nickname, att_number, total);
		int num_games = this.users_rep.getNumberGames(nickname);
		float average = this.users_rep.getAverage(nickname);
		String[] values = {result, String.valueOf(num_games), String.valueOf(average)};
		writeBody(toJson(names, values));
	}
	
	// metodo privato che confronta i tentativi con la sequenza segreta
	private String compareSequence(String secret_seq, String attempt) {
		Boolean check[] = {false, false, false, false};
		String result = "";
		// controllo se vi sono numeri esatti in posizione esatta...
		for (int i=0; i<4; i++) {
			if (attempt.charAt(i) == secret_seq.charAt(i)) {
				// ...e nel caso aggiorno la stringa risultato e l'array boolean utilizzato nell'algoritmo
				result = result + "+";
				check[i] = true;
			}
		}
		// controllo se vi sono numeri esatti in posizione sbagliata
		for (int i=0; i<4; i++) {
			// se il numero i non era un numero esatto in posizione esatta...
			if (check[i] == false) {
				// ...lo confronto con tutti i numeri della sequenza segreta...
				for (int j=0; j<4; j++) {
					if (check[j] == false) {
						// ...e se c'è uno uguale, aggiorno la stringa risultato e l'array boolean
						if (attempt.charAt(i) == secret_seq.charAt(j)) {
							result = result + "-";
							check[j] = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}

}