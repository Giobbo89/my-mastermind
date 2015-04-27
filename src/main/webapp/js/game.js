var cont_attempts;

$(document).ready(function() {
	$("#new_game_button").click(on_new_game);
	$("#try_sequence").submit(on_move);
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
	$("#attempt_result").text(data.result);
}

function on_error(data) {
	$("#login_result").text(JSON.stringify(data.responseJSON));
}