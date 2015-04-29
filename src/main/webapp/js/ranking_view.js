function on_load_ranking() {
	$.ajax({
		url: '/load_ranking',
		method: 'post',
		success: on_load_ranking_success,
		error: on_error,
	});
	return false;
}

function on_load_ranking_success(data) {
	var template = $('#template_ranking').html();
	var render = Mustache.render(template, data);
	$("#spinner_ranking").hide();
	$("#ranking_table_div").html(render);
}

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

function on_user_games_success(data) {
	var template = $('#template_user_games').html();
	var render = Mustache.render(template, data);
	$("#ranking_table_div").html("");
	$("#ranking_table_div").html(render);
}