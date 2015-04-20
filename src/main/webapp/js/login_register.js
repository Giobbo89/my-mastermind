$(document).ready(function() {
	$("#register").submit(on_register);
	$("#login").submit(on_login);
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
		window.setTimeout("show_new_game()", 1100);
}

function show_new_game() {
	$("#not_logged").hide();
	$("#logged").show("slide", { direction: "down" }, 1000);
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
	$("#nickname_login").removeClass("error");
	$("#password_login").removeClass("error");
	$("#nickname_reg").removeClass("error");
	$("#password_reg").removeClass("error");
	$("#password_reg_rep").removeClass("error");
	$("#mail_reg").removeClass("error");
	$("#login_result").css("color", "#DC143C");
	var result = data.description;
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
		$("#welcome").hide("slide", { direction: "right" }, 1500);
		$("#welcome").text("Welcome " + result + "!");
		$("#welcome").show("slide", { direction: "right" }, 1500);
		hide_login_register();
	}
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

function on_register_success(data) {
	$("#nickname_login").removeClass("error");
	$("#password_login").removeClass("error");
	$("#nickname_reg").removeClass("error");
	$("#password_reg").removeClass("error");
	$("#password_reg_rep").removeClass("error");
	$("#mail_reg").removeClass("error");
	$("#register_result").css("color", "#DC143C");
	var result = data.description;
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
		$("#register_result").css("color", "#000000");
		$("#register_result").text("Well done! Now you can login");
		$("#form_register")[0].reset();
	}
}

function on_error(data) {
	$("#login_result").text(JSON.stringify(data.responseJSON));
}