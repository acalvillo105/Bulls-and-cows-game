/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.controllers;

import com.sg.mastermind.models.Game;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sg.mastermind.service.MastermindServiceLayerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *
 * @author acalvillo
 */

@RestController
//@RequestMapping("/api/game")
public class GameController {

    //private final gameDao dao;
    @Autowired
    private MastermindServiceLayerImpl service;
    

    //@GetMapping
    @RequestMapping("/game") //returns list of all games
    public List<Game> all() {
        //modify this so that it only shows the answer for those games that are done
        return service.getAllGames();
    }
    
    @RequestMapping("/game/{gameId}")
    public Game getGame(@PathVariable int gameId){ //returns one game
        return service.getGame(gameId);
    }
    
    //@PostMapping
    @RequestMapping(method = RequestMethod.POST, value = "/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@RequestBody Game game) {
        return service.addGame(game);
    }
}