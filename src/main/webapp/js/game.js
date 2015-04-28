var cont_attempts;

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
	$("#secret_seq").text(data.sequence);
	$("#play_game").show();
}

function on_move() {
	cont_attempts = cont_attempts + 1;
	var cont = cont_attempts.toString();
	$.ajax({
		url: '/move',
		method: 'post',
		success: on_move_success,
		error: on_error,
		data: {
			game_id: $("#game_id").text(),
			attempt: $("#attempt").val(),
			att_number: cont,
		},
	});
	return false;
}

function on_move_success(data) {
	$("#inf_gen").text("Attempt number: " + cont_attempts);
	$("#attempt_result").text(data.result);
	if (data.result == "++++") {
		$("#abandon").hide();
		$("#spinner_end_game").show();
		$("#inf_gen").text("WELL DONE! YOU WIN!");
		window.setTimeout(function() {
			$("#play_game").hide();
			$("#try_sequence")[0].reset();
			$("#start_new_game").show("slide", { direction: "down" }, 1500);
			template_user_inf(data.num_games, data.average);
		}, 3000);
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
	$("#try_sequence")[0].reset();
	$("#start_new_game").show("slide", { direction: "down" }, 1000);
	template_user_inf(data.num_games, data.average);
}

function on_error(data) {
	$("#login_result").text(JSON.stringify(data.responseJSON));
}