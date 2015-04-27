
create table games (
  game_id varchar(255) not null,
  user_nickname varchar(255) not null,
  secret_seq varchar(255) not null,
  points integer not null,
  start_date timestamp not null,
  finish_date timestamp,
  primary key (game_id, user_nickname)
);

update schema_info set version = 3;