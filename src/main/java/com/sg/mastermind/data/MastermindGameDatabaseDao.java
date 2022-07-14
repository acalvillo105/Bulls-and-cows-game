/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acalvillo
 */
@Repository
@Profile("database")
public class MastermindGameDatabaseDao implements gameDao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Game add(Game game) {
        final String sql = "INSERT INTO game(inProgress, answer) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        //set answer
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
        game.setAnswer(answer);
        game.setInProgress(true);
        
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setBoolean(1, game.isInProgress());
            statement.setString(2, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAllGames() {      
        
        final String sql = "SELECT * FROM game";        
        List<Game> gameList = jdbcTemplate.query(sql, new GameMapper());
        
        for (Game current: gameList){
            if (current.isInProgress()){
                current.setAnswer("");
            }
        }
        
        return gameList;
        
    }

    @Override
    public Game getGameById(int gameId) {
        Game currentGame;
        final String sql = "SELECT id, inProgress, answer "
                + "FROM game WHERE id = ?";
        currentGame =  jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
        
        
        return currentGame;
    }

    @Override
    public boolean updateGame(Game game) {
        final String sql = "UPDATE game SET "
                + "id = ?, "
                + "inProgress = ?, "
                + "answer = ?; ";
        
        return jdbcTemplate.update(sql,
                game.getGameId(),
                game.isInProgress(),
                game.getAnswer()) > 0;
                
    }

    @Override
    public boolean deleteGameById(int gameId) {
        final String sql = "DELETE FROM game WHERE id = ?;";
        return jdbcTemplate.update(sql, gameId) > 0;
    }
    
    private static final class GameMapper implements RowMapper<Game>{
        @Override 
        public Game mapRow(ResultSet rs, int index) throws SQLException{
            Game game = new Game();
            game.setGameId(rs.getInt("id"));
            game.setInProgress(rs.getBoolean("inProgress"));
            game.setAnswer(rs.getString("answer"));
            return game;             
        }
    }
}
