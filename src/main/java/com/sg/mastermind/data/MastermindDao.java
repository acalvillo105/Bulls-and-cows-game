/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import java.util.List;

/**
 *
 * @author acalvillo
 */

//CRUD operations 
public interface MastermindDao {
    Game add(Game game);
    List<Game> getAllGames();
    Game getGameById(int gameId);
    //true if it esists and it's updated
    boolean updateGame(Game game);
    //true if exists and it is deleted
    boolean deleteGameById(int gameId);
    
}
