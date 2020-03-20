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
package cs4347.jdbcGame.services.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.services.GamesOwnedService;
import cs4347.jdbcGame.util.DAOException;

public class GamesOwnedServiceImpl implements GamesOwnedService
{
    private DataSource dataSource;

    public GamesOwnedServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    public GamesOwned create(GamesOwned gamesOwned) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public GamesOwned retrieveByID(long gamesOwnedID) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public GamesOwned retrievePlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public List<GamesOwned> retrieveByGame(long gameID) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public List<GamesOwned> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public int update(GamesOwned gamesOwned) throws DAOException, SQLException
    {
        return 0;
    }

    @Override
    public int delete(long gameOwnedID) throws DAOException, SQLException
    {
        return 0;
    }

    @Override
    public int count() throws DAOException, SQLException
    {
        return 0;
    }

}
