// FILE .js CONTENENTE FUNZIONI RELATIVE ALLA CREAZIONE E INTERAZIONE DI UNA PARTITA

var cont_attempts; // variabile utile per salvare il numero di tentativi di una partita (per visualizzazione tabella e punti)
var attempt; // variabile utile per salvare il tentativo fatto (per visualizzazione tabella)
var array_attempts; // variabile utile per salvare la sequenza di tentativi fatti (per visualizzazione tabella)

$(document).ready(function() {
	$("#new_game_button").click(on_new_game);
	$("#try_sequence").submit(on_attempt);
	$("#abandon").click(on_abandon);
});

// Funzione per la creazione di una nuova partita

function on_new_game() {
	$.ajax({
		url: '/new_game',
		method: 'get',
		success: on_new_game_success,
		error: on_error,
	});
	return false;
}

// Se la chiamata Ajax va a buon fine, inizializzo le mie variabili e visualizzo la form per giocare

function on_new_game_success(data) {
	cont_attempts = 0;
	array_attempts = [];
	$("#start_new_game").hide();
	$("#game_id").text(data.game_id);
	$("#attempts_table_div").text("");
	$("#table_menu").hide("slide", { direction: "left" }, 2000);
	$("#logout").hide("slide", { direction: "left" }, 1000);
	window.setTimeout(function() {
		$("#game_inf").show("slide", { direction: "left" }, 1000);
	}, 1100);
	$("#play_game").show();
}

// Funzione per l'invio di un tentativo

function on_attempt() {
	cont_attempts = cont_attempts + 1;
	attempt = $("#attempt").val();
	var cont = cont_attempts.toString();
	$.ajax({
		url: '/move',
		method: 'post',
		success: on_attempt_success,
		error: on_error,
		data: {
			game_id: $("#game_id").text(),
			attempt: attempt,
			att_number: cont,
		},
	});
	return false;
}

// Se la chiamata Ajax va a buon fine, verifico che la sequenza inviata sia valida (not empty || < 4) e, nel caso,
// se è vincente oppure no. Se lo è, lo comunico e salvo le statistiche nel db, per poi tornare alla prima schermata

function on_attempt_success(data) {
	$("#try_sequence")[0].reset();
	if (data.result == "invalid_try") {
		alert("Not valid sequence!");
		cont_attempts = cont_attempts - 1;
	} else {
		array_attempts.push({num: cont_attempts, attempt: attempt, result: data.result});
		on_update_table(array_attempts);
		if (data.result == "++++") {
			$("#abandon").hide();
			$("#spinner_end_game").show();
			$("#game_message").text("WELL DONE! YOU WIN!");
			window.setTimeout(function() {
				$("#play_game").hide();
				$("#game_inf").hide("slide", { direction: "left" }, 1000);
				window.setTimeout(function() {
					$("#logout").show("slide", { direction: "left" }, 1000);
					$("#table_menu").show("slide", { direction: "left" }, 1500);
				}, 1100);
				$("#try_sequence")[0].reset();
				$("#start_new_game").show("slide", { direction: "down" }, 1500);
				template_user_inf(data.num_games, data.average);
				$("#spinner_end_game").hide();
				$("#abandon").show();
			}, 3000);
		} else {
			$("#game_message").text("Mmmmm...is not this. Try again!");
		}
	}
}

// Funzione che, tramite il template apposito, visualizza mano a mano i tentativi fatti e i relativi risultati

function on_update_table(attempts) {
	var template = $("#template_attempts").html();
	var render = Mustache.render(template, {
	    items: attempts,
	 });
	 $("#attempts_table_div").html(render);
}

// Funzione che viene eseguita se si vuole abbandonare la partita tramite il pulsante apposito

function on_abandon() {
	var answer = confirm("Are you sure to abandon? Abandon entail 15 penalty points");
	if (answer) {
		on_abandon_confirm();
	} else {
		answer.close();
	}
}

// Funzione che viene eseguita se si conferma l'abbandono della partita

function on_abandon_confirm() {
	var cont = cont_attempts.toString();
	$.ajax({
		url: '/abandon',
		method: 'post',
		success: on_abandon_success,
		error: on_error,
		data: {
			game_id: $("#game_id").text(),
			att_number: cont,
		},
	});
	return false;
}

// Se la chiamata Ajax va a buon fine, salvo le statistiche della partita (+15 punti di penalità) e torno alla
// schermata iniziale

function on_abandon_success(data) {
	$("#play_game").hide();
	$("#game_inf").hide("slide", { direction: "left" }, 1000);
	window.setTimeout(function() {
		$("#table_menu").show("slide", { direction: "left" }, 1500);
		$("#logout").show("slide", { direction: "left" }, 1000);
	}, 1100);
	$("#try_sequence")[0].reset();
	$("#start_new_game").show("slide", { direction: "down" }, 1000);
	template_user_inf(data.num_games, data.average);
}

function on_error(data) {
	alert(JSON.stringify(data.responseJSON));
}