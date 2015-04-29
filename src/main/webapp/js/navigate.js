function go_to_main() {
	if ($("#game").is(":hidden")) {
		$("#rules").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#game").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

function go_to_rules() {
	if ($("#rules").is(":hidden")) {
		$("#game").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#rules").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

function go_to_ranking() {
	if ($("#ranking").is(":hidden")) {
		$("#game").hide("slide", { direction: "up" }, 1000);
		$("#rules").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#ranking").show("slide", { direction: "down" }, 2000);
			on_load_ranking();
		}, 1100);
	}
}