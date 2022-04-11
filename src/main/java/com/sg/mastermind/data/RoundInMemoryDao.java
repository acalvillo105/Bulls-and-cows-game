/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acalvillo
 */

@Repository
@Profile("memory")
public class RoundInMemoryDao implements roundDao {
    
    private static final List<Round> rounds = new ArrayList<>();

    @Override
    public Round add(Round round, Game game) { //----------- need to pass answer to compare with guess... -------------
        //gives next available id value
        int nextId = rounds.stream()
                .mapToInt(i -> i.getRoundId())
                .max()
                .orElse(0) + 1;
        
        //get current time
        Timestamp roundTime = new Timestamp(System.currentTimeMillis());
        
        //compute the answer 
        String answer = game.getAnswer();
        String guess = round.getGuess();
        int exactMatch = 0;
        int partialMatch = 0;
        String epResult = "";
        
        if(answer.contentEquals(guess)){//if everything matches, no need to compute the rest
            game.setInProgress(false);
            round.setResult("e:4:p:0");
        }
        else{
            for(int e = 0; e < answer.length(); e++){
                if(Character.compare(answer.charAt(e), guess.charAt(e)) == 0){
                    exactMatch++;                    
                } 
                for(int p = 0; p < answer.length(); p++){
                    if(e != p){
                        if(Character.compare(answer.charAt(e), guess.charAt(p)) == 0){
                            partialMatch++;                    
                        } 
                    }
                }
            }
        }
        
        //initialize remaining fields (result and timeStamp
        round.setRoundId(nextId);        
        round.setTime(roundTime);
        epResult = "e:"+Integer.toString(exactMatch)+":p"+Integer.toString(partialMatch);
        round.setResult(epResult);
        rounds.add(round);
        
        return round;
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        return rounds.stream().filter(i -> i.getGameId() == gameId).collect(Collectors.toList());
    }

    @Override
    public Round getRoundById(int roundId) {
        return rounds.stream()
                .filter(i -> i.getRoundId() == roundId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateRound(Round round) {
        int index = 0;
        while (index < rounds.size()
                && rounds.get(index).getRoundId() != round.getRoundId()) {
            index++;
        }

        if (index < rounds.size()) {
            rounds.set(index, round);
        }
        return index < rounds.size();
    }

    @Override
    public boolean deleteRoundById(int roundId) {
        return rounds.removeIf(i -> i.getGameId() == roundId);
    }
    
}
