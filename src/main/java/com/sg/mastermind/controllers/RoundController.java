/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.controllers;


import com.sg.mastermind.models.Round;
import com.sg.mastermind.service.MastermindServiceLayerImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author acalvillo
 */

@RestController
public class RoundController {
    
    @Autowired
    private MastermindServiceLayerImpl service;

    @RequestMapping("/rounds/{gameId}")
    public List<Round> all(@PathVariable int gameId) {
        return service.getAllRounds(gameId);
    }
    
    //@PostMapping
    @RequestMapping(method = RequestMethod.POST, value = "/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round createRound(@RequestBody Round round) {
        return service.addRound(round);
    }
}
