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
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.services.GameService;
import cs4347.jdbcGame.util.DAOException;

public class GameServiceImpl implements GameService
{

    private DataSource dataSource;

    public GameServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    public Game create(Game game) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public Game retrieve(long gameID) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public int update(Game game) throws DAOException, SQLException
    {
        return 0;
    }

    @Override
    public int delete(long gameID) throws DAOException, SQLException
    {
        return 0;
    }

    @Override
    public int count() throws DAOException, SQLException
    {
        return 0;
    }

    @Override
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
        return null;
    }

    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
        return null;
    }

}
