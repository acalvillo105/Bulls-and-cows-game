/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.mastermind.service;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import java.util.List;

/**
 *
 * @author acalvillo
 */
public interface MastermindServiceLayer {
    public List<Game> getAllGames();
    public Game getGame(int gameId);
    public Game addGame(Game game);
    
    public List<Round> getAllRounds(int gameId);
    public Round addRound(Round round);
}
