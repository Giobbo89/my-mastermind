$(document).ready(function() {
	$("#register").submit(on_register);
	$("#login").submit(on_login);
});

function on_login() {
	$.ajax({
		url: '/login',
		method: 'post',
		success: on_login_success,
		error: on_error,
		data: {
			nickname: $("#nickname_login").val(),
			password: $("#password_login").val(),
		},
	});
	return false;
}

function on_login_success(data) {
	$("#result").text(data.description);
}

function on_register() {
	$.ajax({
		url: '/register',
		method: 'post',
		success: on_login_success,
		error: on_error,
		data: {
			nickname: $("#nickname_reg").val(),
			password: $("#password_reg").val(),
			password_rep: $("#password_reg_rep").val(),
			mail: $("#mail_reg").val(),
		},
	});
	return false;
}

function on_register_success(data) {
	$("#result").text(data.description);
}

function on_error(data) {
	$("#result").text(JSON.stringify(data.responseJSON));
}