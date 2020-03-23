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
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.GamesOwnedDAO;
import cs4347.jdbcGame.dao.impl.GamesOwnedDAOImpl;
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
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            GamesOwned go = GmOwnDAOImpl.create(connection, gamesOwned);
            connection.commit();
            return go;
        }
        catch (Exception ex){
            // Rollback will set Autocommit back to true
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
    public GamesOwned retrieveByID(long gamesOwnedID) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            GamesOwned go = GmOwnDAOImpl.retrieveID(connection, gamesOwnedID);
            connection.commit();
            return go;
        }
        catch (Exception ex){
            // Rollback will set Autocommit back to true
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
    public GamesOwned retrievePlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            GamesOwned go = GmOwnDAOImpl.retrievePlayerGameID(connection, playerID, gameID);
            connection.commit();
            return go;
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
    public List<GamesOwned> retrieveByGame(long gameID) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<GamesOwned> listofgo = GmOwnDAOImpl.retrieveByGame(connection, gameID);
            connection.commit();
            return listofgo;
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
    public List<GamesOwned> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<GamesOwned> listofgo = GmOwnDAOImpl.retrieveByPlayer(connection, playerID);
            connection.commit();
            return listofgo;
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
    public int update(GamesOwned gamesOwned) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int updateID = GmOwnDAOImpl.update(connection, gamesOwned);
            connection.commit();
            return updateID;
        }
        catch (Exception ex){
            // Rollback will set Autocommit back to true
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
    public int delete(long gameOwnedID) throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int deleteID = GmOwnDAOImpl.delete(connection, gameOwnedID);
            connection.commit();
            return deleteID;
        }
        catch (Exception ex){
            // Rollback will set Autocommit back to true
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

    // COUNT IS THE ONLY TEST CASE THAT PASSED
    // (THIS MAY BE BECAUSE OF THE "GamesOwnedDAOImpl.java" not working currently)
    @Override
    public int count() throws DAOException, SQLException
    {
        GamesOwnedDAO GmOwnDAOImpl = new GamesOwnedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int countID = GmOwnDAOImpl.count(connection);
            connection.commit();
            return countID;
        }
        catch (Exception ex){
            // Rollback will set Autocommit back to true
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

}