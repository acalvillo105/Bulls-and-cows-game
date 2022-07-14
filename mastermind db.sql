DROP DATABASE IF EXISTS mastermindDB;
CREATE DATABASE mastermindDB;

USE mastermindDB;

CREATE TABLE game(
	id INT PRIMARY KEY AUTO_INCREMENT,
	inProgress BOOLEAN DEFAULT true,
	answer VARCHAR(50)
);

CREATE TABLE round(
	id INT PRIMARY KEY AUTO_INCREMENT,
    result VARCHAR(50),
    times TIMESTAMP,
    guess VARCHAR(50),
    gameId INT NOT NULL,
    FOREIGN KEY (gameId) 
    REFERENCES game(id)    
);
