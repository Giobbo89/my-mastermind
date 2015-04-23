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
	$("#start_new_game").hide();
	$("#game_id").text(data.game_id);
	$("#secret_seq").text(data.sequence);
	$("#play_game").show();
}

function on_move() {
	$.ajax({
		url: '/move',
		method: 'post',
		success: on_move_success,
		error: on_error,
		data: {
			game_id: $("#game_id").text(),
			attempt: $("#attempt").val(),
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