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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
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
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            Game gm = GmDAOImpl.create(connection, game);
            connection.commit();
            return gm;
        }
        catch (Exception ex){
            // Rollback set Autocommit back to true
            connection.rollback();
            throw ex;
        }
        finally{
            // Autocommit set back to true in finally block
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public Game retrieve(long gameID) throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            Game gm = GmDAOImpl.retrieve(connection, gameID);
            connection.commit();
            return gm;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public int update(Game game) throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            int updateGID = GmDAOImpl.update(connection, game);
            connection.commit();
            return updateGID;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public int delete(long gameID) throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            int deleteGID = GmDAOImpl.delete(connection, gameID);
            connection.commit();
            return deleteGID;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public int count() throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            int countGID = GmDAOImpl.count(connection);
            connection.commit();
            return countGID;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    // THIS IS THE ONLY TEST CASE WITH AN ERROR
    // ("YOU HAVE AN ERROR IN YOUR SQL SYNTAX...")
    @Override
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<Game> listofgm = GmDAOImpl.retrieveByTitle(connection, titlePattern);
            connection.commit();
            return listofgm;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
        GameDAO GmDAOImpl = new GameDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<Game> listofgm = GmDAOImpl.retrieveByReleaseDate(connection, start, end);
            connection.commit();
            return listofgm;
        }
        catch (Exception ex){
            connection.rollback();
            throw ex;
        }
        finally{
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

}
