/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.jdbcGame.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.util.DAOException;

/**
 * The GamesPlayedDAO exclusively updates the GAMES_PLAYED table.
 */
public interface GamesPlayedDAO
{

    /**
     * The given GamesPlayed must have the player and game ID fields populated with
     * valid Player and Game IDs.
     */
    GamesPlayed create(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException;

    /**
     * Return the single instance by ID. Return null if no GamesPlayed exist.
     */
    GamesPlayed retrieveID(Connection connection, Long gamePlayedID) throws SQLException, DAOException;

    /**
     * Returns a list of GamesPlayed associated with the given Game ID
     */
    List<GamesPlayed> retrieveByPlayerGameID(Connection connection, Long payerID, Long gameID)
            throws SQLException, DAOException;

    /**
     * Returns a list of GamesPlayed associated with the given Game ID
     */
    List<GamesPlayed> retrieveByGame(Connection connection, Long gameID) throws SQLException, DAOException;

    /**
     * Returns a list of GamesPlayed associated with the given Player ID
     */
    List<GamesPlayed> retrieveByPlayer(Connection connection, Long playerID) throws SQLException, DAOException;

    /**
     * 
     */
    int update(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException;

    /**
     * 
     */
    int delete(Connection connection, Long gamePlayedID) throws SQLException, DAOException;

    /**
     * Returns the number of GamesPlayed i.e. the number of rows
     */
    int count(Connection connection) throws SQLException, DAOException;

}
