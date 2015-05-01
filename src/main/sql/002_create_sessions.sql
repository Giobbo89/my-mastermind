
create table sessions (
  session_id varchar(255) not null,
  user_nickname varchar(255) not null,
  PRIMARY KEY (session_id),
  FOREIGN KEY (user_nickname) REFERENCES users (nickname) ON UPDATE CASCADE ON DELETE CASCADE
);

update schema_info set version = 2;