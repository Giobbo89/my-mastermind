$(document).ready(function() {
	$("#register").submit(on_register);
	$("#login").submit(on_login);
	$("#bbb").click(activate_hide);
	loop_h1();
	loop_h2();
});

function loop_h1() {
    $("h1").animate({color: "#DC143C"}, 5000, function(){
        $(this).animate({color: "#FFA500"}, 5000, function(){
            $(this).animate({color: "#FFD700"},5000, function(){
                loop_h1();
            });
        });
    });
}

function loop_h2() {
    $("h2").animate({color: "#0000CD"}, 5000, function(){
        $(this).animate({color: "#1E90FF"}, 5000, function(){
            $(this).animate({color: "#228B22"},5000, function(){
                loop_h2();
            });
        });
    });
}

function activate_hide() {
	$("#form_login").hide("blind", 1000);
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