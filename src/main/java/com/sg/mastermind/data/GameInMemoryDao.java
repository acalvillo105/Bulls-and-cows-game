/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acalvillo
 */

@Repository
@Profile("memory")
public class GameInMemoryDao implements gameDao{
    private static final List<Game> games = new ArrayList<>();    
    
    @Override
    public Game add(Game game) { 
        //gives next available id value
        
        int nextId = games.stream()
                .mapToInt(i -> i.getGameId())
                .max()
                .orElse(0) + 1;
        
        
        //create new answer with unique values
        int answerLenght = 4;
        int randomNum = 0;
        String answer = "";
        Random rand = new Random();
        Map<Integer, Integer> uniqueValues = new HashMap<Integer, Integer>();
        
        for(int i = 0; i < answerLenght; i++){
            do{
                randomNum = rand.nextInt(9); //we want a random single-digit number from 0-9
            }while(uniqueValues.containsKey(randomNum)); //will keep looping until new unique value is chosen 
            
            uniqueValues.put(randomNum, i);
            answer += Integer.toString(randomNum); 
            
        }
        
        //initialize new game
        game.setGameId(nextId);
        game.setInProgress(true); //if we start a new game, it is in progress
        game.setAnswer(answer);
        
        games.add(game);
        
        return game;
                        
    }

    @Override
    public List<Game> getAllGames() { 
        return new ArrayList<>(games);
    }

    @Override
    public Game getGameById(int gameId) {
        return games.stream()
                .filter(i -> i.getGameId() == gameId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateGame(Game game) {
        int index = 0;
        while (index < games.size()
                && games.get(index).getGameId() != game.getGameId()) {
            index++;
        }

        if (index < games.size()) {
            games.set(index, game);
        }
        return index < games.size();
    }

    @Override
    public boolean deleteGameById(int gameId) {
        return games.removeIf(i -> i.getGameId() == gameId);
    }
    
}
