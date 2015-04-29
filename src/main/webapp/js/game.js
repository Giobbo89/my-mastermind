var cont_attempts;
var attempt;
var game;

$(document).ready(function() {
	$("#new_game_button").click(on_new_game);
	$("#try_sequence").submit(on_move);
	$("#abandon").click(on_abandon);
});

function on_new_game() {
	$.ajax({
		url: '/new_game',
		method: 'get',
		success: on_new_game_success,
		error: on_error,
	});
	return false;
}

function on_new_game_success(data) {
	cont_attempts = 0;
	$("#start_new_game").hide();
	$("#game_id").text(data.game_id);
	$("#attempts_table_div").text("");
	$("#logout").hide("slide", { direction: "left" }, 1000);
	window.setTimeout(function() {
		$("#game_inf").show("slide", { direction: "left" }, 1000);
	}, 1100);
	$("#play_game").show();
	game = new Game();
	var moves_view = new GameMovesView("#template_attempts", "#attempts_table_div");
	game.add_observer(moves_view);
}

function on_move() {
	cont_attempts = cont_attempts + 1;
	attempt = $("#attempt").val();
	var cont = cont_attempts.toString();
	$.ajax({
		url: '/move',
		method: 'post',
		success: on_move_success,
		error: on_error,
		data: {
			game_id: $("#game_id").text(),
			attempt: attempt,
			att_number: cont,
		},
	});
	return false;
}

function on_move_success(data) {
	$("#try_sequence")[0].reset();
	if (data.result == "invalid_try") {
		alert("Not valid sequence!");
		cont_attempts = cont_attempts - 1;
	} else {
		game.on_attempt(cont_attempts, attempt, data.result);
		if (data.result == "++++") {
			$("#abandon").hide();
			$("#spinner_end_game").show();
			$("#inf_gen").text("WELL DONE! YOU WIN!");
			window.setTimeout(function() {
				$("#play_game").hide();
				$("#game_inf").hide("slide", { direction: "left" }, 1000);
				window.setTimeout(function() {
					$("#logout").show("slide", { direction: "left" }, 1000);
				}, 1100);
				$("#try_sequence")[0].reset();
				$("#start_new_game").show("slide", { direction: "down" }, 1500);
				template_user_inf(data.num_games, data.average);
			}, 3000);
		}
	}
}

function on_abandon() {
	var answer = confirm("Are you sure to abandon? Abandon entail 15 penalty points");
	if (answer) {
		on_abandon_confirm();
	} else {
		answer.close();
	}
}

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

function on_abandon_success(data) {
	$("#play_game").hide();
	$("#game_inf").hide("slide", { direction: "left" }, 1000);
	window.setTimeout(function() {
		$("#logout").show("slide", { direction: "left" }, 1000);
	}, 1100);
	$("#try_sequence")[0].reset();
	$("#start_new_game").show("slide", { direction: "down" }, 1000);
	template_user_inf(data.num_games, data.average);
}

function on_error(data) {
	alert(JSON.stringify(data.responseJSON));
}