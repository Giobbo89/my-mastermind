function go_to_main() {
	if ($("#new_game").is(":hidden")) {
		$("#rules").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#new_game").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

function go_to_rules() {
	if ($("#rules").is(":hidden")) {
		$("#new_game").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#rules").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

function go_to_ranking() {
	if ($("#ranking").is(":hidden")) {
		$("#new_game").hide("slide", { direction: "up" }, 1000);
		$("#rules").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#ranking").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}