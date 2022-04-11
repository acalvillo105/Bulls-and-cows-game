/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.service;


import com.sg.mastermind.data.gameDao;
import com.sg.mastermind.data.roundDao;
import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 *
 * @author acalvillo
 */

@Service
public class MastermindServiceLayerImpl implements MastermindServiceLayer{
    gameDao gameDao;
    roundDao roundDao;

    public MastermindServiceLayerImpl(gameDao gameDao, roundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }
    
    @Override
    public List<Game> getAllGames(){
        return gameDao.getAllGames(); //needs to come from database
    }

    @Override
    public Game getGame(int gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public Game addGame(Game game) {
        return gameDao.add(game);
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        return roundDao.getAllRounds(gameId);
    }

    @Override
    public Round addRound(Round round) {
        return roundDao.add(round, gameDao.getGameById(round.getGameId()));
    }
    
  
    
}
