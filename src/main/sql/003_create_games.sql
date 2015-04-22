
create table games (
  game_id varchar(255) not null,
  user_nickname varchar(255) not null,
  secret_seq varchar(255) not null,
  attempts varchar(255) not null,
  primary key (game_id, user_nickname)
);

update schema_info set version = 3;