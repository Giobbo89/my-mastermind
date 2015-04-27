
create table attempts (
  game_id varchar(255) not null,
  user_nickname varchar(255) not null,
  attempt varchar(255) not null,
  att_number integer not null,
  secret_seq varchar(255) not null,
  result varchar(255) not null,
  primary key (game_id, user_nickname, att_number)
);

update schema_info set version = 4;