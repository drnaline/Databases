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
package cs4347.jdbcGame.services;

import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.util.DAOException;

public interface GamesPlayedService
{
    GamesPlayed create(GamesPlayed gamesPlayed) throws DAOException, SQLException;

    GamesPlayed retrieveByID(long gamePlayedID) throws DAOException, SQLException;

    List<GamesPlayed> retrieveByPlayerGameID(long playerID, long gameID) throws DAOException, SQLException;

    List<GamesPlayed> retrieveByGame(long gameID) throws DAOException, SQLException;

    List<GamesPlayed> retrieveByPlayer(long playerID) throws DAOException, SQLException;

    int update(GamesPlayed gamesPlayed) throws DAOException, SQLException;

    int delete(long gamePlayedID) throws DAOException, SQLException;

    int count() throws DAOException, SQLException;
}
