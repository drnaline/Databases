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

import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.util.DAOException;

public interface GamesOwnedService
{
    GamesOwned create(GamesOwned gamesOwned) throws DAOException, SQLException;

    GamesOwned retrieveByID(long gamesOwnedID) throws DAOException, SQLException;

    GamesOwned retrievePlayerGameID(long playerID, long gameID) throws DAOException, SQLException;

    List<GamesOwned> retrieveByGame(long gameID) throws DAOException, SQLException;

    List<GamesOwned> retrieveByPlayer(long playerID) throws DAOException, SQLException;

    int update(GamesOwned gamesOwned) throws DAOException, SQLException;

    int delete(long gamesOwnedID) throws DAOException, SQLException;

    int count() throws DAOException, SQLException;

}
