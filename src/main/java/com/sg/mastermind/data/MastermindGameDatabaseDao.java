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
public class MastermindGameDatabaseDao implements gameDao{
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MastermindGameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {
        final String sql = "INSERT INTO game(id, inProgress, answer) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, game.getGameId());
            statement.setBoolean(2, game.isInProgress());
            statement.setString(3, game.getAnswer());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT id, inProgress, answer FROM game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        final String sql = "SELECT id, inProgress, answer "
                + "FROM game WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
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
