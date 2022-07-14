# Bulls and Cows Game

## Project Summary:

    - This is a number guessing game known as "Bulls and Cows"
    - In each game, there is a unique 4-digit number generated 
    - For each round, the user guesses a number and is told the exact and partial matches (format: e:0:p:0)
    - This is a Spring Boot REST aplication
    - Used POST and GET methods
    
## Technologies used:
 
    - Java
    - MySQL
    - REST API
    - JDBC Template (to access database)
    
## This program can be tested through Postman

    - To create a new game: GET localhost:8080/begin
    - To get a list of all games: GET localhost:8080/game
    - To get details about a specific game: GET localhost:8080/game/(enter id of game here)    
    - To make a guess: POST localhost:8080/guess 
        - JSON example: 
            {
                "gameId": 3,
                "guess" : 5824
            }
        - your response looks like this:
            {
                "roundId": 1,
                "result": "e:4:p0",
                "time": "2022-07-14T23:31:43.450+0000",
                "guess": "5824",
                "gameId": 3
            }
    - To get a list of your guesses: GET localhost:8080/rounds/(enter if of round here)
