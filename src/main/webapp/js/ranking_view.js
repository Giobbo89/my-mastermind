function load_ranking() {
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