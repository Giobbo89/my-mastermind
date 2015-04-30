// FILE .js CONTENENTE FUNZIONI RELATIVE AL RANKING, ALLO STORICO DELLE PARTITE E I TENTATIVI DI UNA DI QUESTE

// Funzione per visualizzare il ranking degli utenti

function on_load_ranking() {
	$.ajax({
		url: '/load_ranking',
		method: 'post',
		success: on_load_ranking_success,
		error: on_error,
	});
	return false;
}

// Se la chiamata Ajax va a buon fine, tramite il template apposito visualizza la classifica degli utenti
// in base alla loro punteggio medio

function on_load_ranking_success(data) {
	var template = $('#template_ranking').html();
	var render = Mustache.render(template, data);
	$("#spinner_ranking").hide();
	$("#ranking_table_div").html(render);
}

// Funzione per visualizzare lo storico partite di un utente

function on_user_games(nickname){
	$.ajax({
		url: '/user_games',
		method: 'post',
		success: on_user_games_success,
		error: on_error,
		data: {
			nickname: nickname,
		},
	});
}

// Se la chiamata Ajax va a buon fine, tramite il template apposito visualizza lo storico partite
// di un utente

function on_user_games_success(data) {
	var template = $('#template_user_games').html();
	var render = Mustache.render(template, data);
	$("#ranking_table_div").html("");
	$("#ranking_table_div").html(render);
}

// Funzione per visualizzare i tentativi di una partita giocata

function on_game_attempts(game_id) {
	$.ajax({
		url: '/game_attempts',
		method: 'post',
		success: on_game_attempts_success,
		error: on_error,
		data: {
			game_id: game_id,
		},
	});
}

// Se la chiamata Ajax va a buon fine, tramite il template apposito visualizza i tentativi fatti in
// una partita giocata

function on_game_attempts_success(data) {
	var template = $('#template_game_attempts').html();
	var render = Mustache.render(template, data);
	$("#ranking_table_div").html("");
	$("#ranking_table_div").html(render);
}