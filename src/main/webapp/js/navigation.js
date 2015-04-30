// FILE .js CONTENENTE FUNZIONI RELATIVE ALLA NAVIGAZIONE TRA LE VARIE SEZIONI DELL'APPLICAZIONE 

// Funzione che permette di visualizzare la sezione relativa a una nuova partita

function go_to_game() {
	if ($("#game").is(":hidden")) {
		$("#rules").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#game").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

// Funzione che permette di visualizzare la sezione relativa alle regole

function go_to_rules() {
	if ($("#rules").is(":hidden")) {
		$("#game").hide("slide", { direction: "up" }, 1000);
		$("#ranking").hide("slide", { direction: "up" }, 1000);
		window.setTimeout(function() {
			$("#rules").show("slide", { direction: "down" }, 2000);
		}, 1100);
	}
}

// Funzione che permette di visualizzare la sezione relativa al ranking

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