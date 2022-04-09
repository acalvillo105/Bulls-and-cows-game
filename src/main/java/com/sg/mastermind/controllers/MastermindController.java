/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.controllers;

import com.sg.mastermind.data.MastermindDao;
import com.sg.mastermind.models.Game;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author acalvillo
 */

@RestController
@RequestMapping("/api/game")
public class MastermindController {

    private final MastermindDao dao;

    public MastermindController(MastermindDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Game> all() {
        return dao.getAllGames();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(@RequestBody Game game) {
        return dao.add(game);
    }
}