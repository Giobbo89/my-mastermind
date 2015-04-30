function Game() {
  this.attempts = [];
  this.observers = [];
}

Game.prototype.on_attempt = function(num, attempt, result) {
  this.attempts.push({num: num, attempt: attempt, result: result});
  this.notify_all_observers();
}

Game.prototype.add_observer = function(observer) {
  this.observers.push(observer);
}

Game.prototype.notify_all_observers = function() {
  for (var i=0; i < this.observers.length; i++) {
    this.observers[i].notify(this);
  };
}


function GameMovesView(template, target) {
	  this.template = template;
	  this.target = target;
	}

	GameMovesView.prototype.notify = function(game) {
	  var template = $(this.template).html();
	  var render = Mustache.render(template, {
	    items: game.attempts,
	  });
	  $(this.target).html(render);
	}