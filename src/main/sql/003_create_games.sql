
create table games (
  game_id varchar(255) not null,
  user_nickname varchar(255) not null,
  secret_seq varchar(255) not null,
  score integer not null,
  start_date timestamp not null,
  finish_date timestamp,
  PRIMARY KEY (game_id, user_nickname),
  FOREIGN KEY (user_nickname) REFERENCES users (nickname) ON UPDATE CASCADE ON DELETE CASCADE
);

update schema_info set version = 3;