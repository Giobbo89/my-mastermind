$(document).ready(function() {
	$("#register").submit(on_register);
	$("#login").submit(on_login);
	loop();
});

function loop(){
    $("#titolo").animate({color: "#fff555"}, "slow", function(){
        $(this).animate({color: "#c5c5c5"}, 3000, function(){
            $(this).animate({color: "#333333"},1500, function(){
                loop();
            });
        });
    });
}

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
	$("#result").css("color", "red");
	$("#result").text(data.description);
}

function on_error(data) {
	$("#result").text(JSON.stringify(data.responseJSON));
}