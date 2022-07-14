/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.mastermind.data;

import com.sg.mastermind.models.Game;
import com.sg.mastermind.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
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
public class MastermindRoundDatabaseDao implements roundDao{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Round add(Round round, Game game) {
        final String sql = "INSERT INTO round(result, times, guess, gameId) VALUES(?,?,?,?);";
        
        //get current time
        Timestamp roundTime = new Timestamp(System.currentTimeMillis());
        
        //compute the answer 
        String answer = game.getAnswer();
        String guess = round.getGuess();
        int exactMatch = 0;
        int partialMatch = 0;
        String epResult = "";
        
        
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
        
        if(exactMatch == 4){
            game.setInProgress(false); 
            final String gsql = "UPDATE game set "
                    + "inProgress = ? WHERE id = ?";
            jdbcTemplate.update(gsql, game.isInProgress(), game.getGameId());
        }
        round.setTime(roundTime);
        epResult = "e:"+Integer.toString(exactMatch)+":p"+Integer.toString(partialMatch);
        round.setResult(epResult);
        
        
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, round.getResult());
            statement.setTimestamp(2, round.getTime());
            statement.setString(3, round.getGuess());
            statement.setInt(4,game.getGameId());
            return statement;

        }, keyHolder);

        round.setRoundId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        final String sql = "SELECT * FROM round WHERE gameId = ?;";
        return jdbcTemplate.query(sql, new RoundMapper(), gameId);
    }

    @Override
    public Round getRoundById(int roundId) {
        final String sql = "SELECT id, result, times, guess, gameId "
                + "FROM game WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new RoundMapper(), roundId);
    }

    @Override
    public boolean updateRound(Round round) {
        final String sql = "UPDATE round SET "
                + "id = ?, "
                + "result = ?, "
                + "times = ? "
                + "guess = ? "
                + "gameId = ?;";
        
        
        return jdbcTemplate.update(sql,
                round.getRoundId(),
                round.getResult(),
                round.getTime(), 
                round.getGuess(), 
                round.getGameId()) > 0;
    }

    @Override
    public boolean deleteRoundById(int roundId) {
        final String sql = "DELETE FROM round WHERE id = ?;";
        return jdbcTemplate.update(sql, roundId) > 0;
    }
    
    private static final class RoundMapper implements RowMapper<Round>{
        @Override 
        public Round mapRow(ResultSet rs, int index) throws SQLException{
            Round round = new Round();
            round.setRoundId(rs.getInt("id"));
            round.setResult(rs.getString("result"));
            round.setTime(rs.getTimestamp("times"));
            round.setGuess(rs.getString("guess"));
            round.setGameId(rs.getInt("gameId"));
            return round;             
        }
    }
    
}
