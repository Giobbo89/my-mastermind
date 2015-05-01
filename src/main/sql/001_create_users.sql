
create table users (
  nickname varchar(255) not null,
  password varchar(255) not null,
  mail varchar(255) not null,
  enc varchar(255) not null,
  num_games integer not null,
  average real not null,
  PRIMARY KEY (nickname)
);

update schema_info set version = 1;