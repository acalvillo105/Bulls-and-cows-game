/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import java.util.List;

/**
 *
 * @author acalvillo
 */
public interface roundDao {
    Round add(Round round, Game game);
    List<Round> getAllRounds(int gameId);
    Round getRoundById(int roundId);
    //true if it esists and it's updated
    boolean updateRound(Round round);
    //true if exists and it is deleted
    boolean deleteRoundById(int roundId);
}
