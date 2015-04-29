var session_id;

$(document).ready(function() {
	$("#register").submit(on_register);
	$("#login").submit(on_login);
	check_log();
	
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

function hide_login_register() {
		$("#form_login").hide("slide", 1000);
		$("#form_register").hide("slide", { direction: "right" }, 1000);
		window.setTimeout(function() {
			$("#game").show("slide", { direction: "down" }, 2000);
			$("#table_menu").show("slide", { direction: "left" }, 2000);
			$("#logout").show("slide", { direction: "left" }, 2000);
			$("#user_inf").show("slide", { direction: "right" }, 2000);
		}, 1100);
}

function on_register() {
	$.ajax({
		url: '/register',
		method: 'post',
		success: on_register_success,
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

function on_logout() {
	document.cookie = "session_id = '" + session_id + "'; expires = Sun, 03 Dec 1989 00:00:00 UTC"; 
	$.ajax({
		url: '/logout',
		method: 'post',
		success: on_logout_success,
		data: {
			session_id: session_id,
		},
	});
}

function on_logout_success(data) {
	$("#game").hide("slide", { direction: "up" }, 1000);
	$("#rules").hide("slide", { direction: "up" }, 1000);
	$("#ranking").hide("slide", { direction: "up" }, 1000);
	$("#table_menu").hide("slide", { direction: "left" }, 1000);
	$("#logout").hide("slide", { direction: "left" }, 1000);
	$("#user_inf").hide("slide", { direction: "right" }, 1000);
	$("#welcome").hide("slide", { direction: "right" }, 1500);
	$("#welcome").text("Welcome!");
	$("#welcome").show("slide", { direction: "right" }, 1500);
	window.setTimeout(function() {
		$("#form_login").show("slide", { direction: "left" }, 2000);
		$("#form_register").show("slide", { direction: "right" }, 2000);
	}, 1100);
}

function check_log() {
	$.ajax({
		url: '/check_log',
		method: 'get',
		success: on_login_success,
		error: on_error,
	});
	return false;
}

function on_login_success(data) {
	$(".login_input").removeClass("error");
	$(".reg_input").removeClass("error");
	$("#login_result").text(" ");
	$("#register_result").text(" ");
	$("#login_result").css("color", "#DC143C");
	var result = data.result;
	if (result == "all") {
		$("#login_result").text("Insert nickname and password to login");
		$("#nickname_login").addClass("error");
		$("#password_login").addClass("error");
	} else if (result == "nick") {
		$("#login_result").text("This nickname doesn't exists");
		$("#nickname_login").addClass("error");
	} else if (result == "pass") {
		$("#login_result").text("The password is wrong");
		$("#password_login").addClass("error");
	} else {
		$(".login_input").removeClass("error");
		$(".reg_input").removeClass("error");
		$("#login_result").text("");
		$("#welcome").hide("slide", { direction: "right" }, 1500);
		$("#welcome").text("Welcome " + result + "!");
		$("#welcome").show("slide", { direction: "right" }, 1500);
		$("#login")[0].reset();
		$("#register")[0].reset();
		session_id = data.session_id;
		template_user_inf(data.num_games, data.average);
		hide_login_register();
	}
}

function template_user_inf(num_games, average) {
	var template = $('#template_user_inf').html();
	var render = Mustache.render(template, {
		n_games: num_games,
		avg: average
	});
	$("#user_inf").html(render);
}

function on_register_success(data) {
	$(".login_input").removeClass("error");
	$(".reg_input").removeClass("error");
	$("#login_result").text(" ");
	$("#register_result").text(" ");
	$("#register_result").css("color", "#DC143C");
	var result = data.result;
	if (result == "all") {
		$("#register_result").text("All fields are mandatory");
		$("#nickname_reg").addClass("error");
		$("#password_reg").addClass("error");
		$("#password_reg_rep").addClass("error");
		$("#mail_reg").addClass("error");
	} else if (result == "mail") {
		$("#register_result").text("Mail not valid (doesn't have the '@')");
		$("#mail_reg").addClass("error");
	} else if (result == "short") {
		$("#register_result").text("Password is too short (minimum8 characters)");
		$("#password_reg").addClass("error");
	} else if (result == "equal") {
		$("#register_result").text("Passwords are not equal");
		$("#password_reg").addClass("error");
		$("#password_reg_rep").addClass("error");	
	} else if (result == "nick") {
		$("#register_result").text("This nickname is already in use; please, select another");
		$("#nickname_reg").addClass("error");
	} else if (result == "done") {
		$(".login_input").removeClass("error");
		$(".reg_input").removeClass("error");
		$("#register_result").css("color", "#000000");
		$("#register_result").text("Well done! Now you can login");
		$("#register")[0].reset();
		$("#login")[0].reset();
	}
}

function on_error(data) {
	alert(JSON.stringify(data.responseJSON));
}