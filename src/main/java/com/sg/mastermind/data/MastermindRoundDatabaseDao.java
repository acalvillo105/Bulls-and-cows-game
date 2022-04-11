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
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MastermindRoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Round add(Round round, Game game) {
        final String sql = "INSERT INTO round(id, result, times, guess, gameId) VALUES(?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getRoundId());
            statement.setString(2, round.getResult());
            statement.setTimestamp(3, round.getTime());
            statement.setString(4, round.getGuess());
            statement.setInt(5,game.getGameId());
            return statement;

        }, keyHolder);

        round.setGameId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        final String sql = "SELECT iid, result, times, guess, gameId FROM round;";
        return jdbcTemplate.query(sql, new RoundMapper());
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
