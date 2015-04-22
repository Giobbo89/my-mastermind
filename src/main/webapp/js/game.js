$(document).ready(function() {
	$("#new_game_button").click(on_new_game);
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
	$("#play_game").text("Game id: " + data.game_id + "   Try to guess: " + data.sequence);
	$("#play_game").show();
}

function on_error(data) {
	$("#login_result").text(JSON.stringify(data.responseJSON));
}