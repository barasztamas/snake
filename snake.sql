CREATE DATABASE IF NOT EXISTS snake;
USE snake;
CREATE TABLE IF NOT EXISTS HighScore (
  Name VARCHAR(100) NOT NULL,
  Score      INT,
  PRIMARY KEY(Name)
);
grant select, insert, update, delete on snake.* to 'tanulo'@'localhost'