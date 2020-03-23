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

import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.dao.impl.GamesPlayedDAOImpl;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.services.GamesPlayedService;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedServiceImpl implements GamesPlayedService
{
    private DataSource dataSource;

    public GamesPlayedServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    public GamesPlayed create(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            GamesPlayed gp = GmPlayDAOImpl.create(connection, gamesPlayed);
            connection.commit();
            return gp;
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
    public GamesPlayed retrieveByID(long gamePlayedID) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            GamesPlayed gp = GmPlayDAOImpl.retrieveID(connection, gamePlayedID);
            connection.commit();
            return gp;
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
    public List<GamesPlayed> retrieveByPlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<GamesPlayed> listofgp = GmPlayDAOImpl.retrieveByPlayerGameID(connection, playerID, gameID);
            connection.commit();
            return listofgp;
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
    public List<GamesPlayed> retrieveByGame(long gameID) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<GamesPlayed> listofgp = GmPlayDAOImpl.retrieveByGame(connection, gameID);
            connection.commit();
            return listofgp;
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
    public List<GamesPlayed> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            List<GamesPlayed> listofgp = GmPlayDAOImpl.retrieveByPlayer(connection, playerID);
            connection.commit();
            return listofgp;
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
    public int update(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int updateID = GmPlayDAOImpl.update(connection, gamesPlayed);
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
    public int delete(long gamePlayedID) throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int deleteID = GmPlayDAOImpl.delete(connection, gamePlayedID);
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

    @Override
    public int count() throws DAOException, SQLException
    {
        GamesPlayedDAO GmPlayDAOImpl = new GamesPlayedDAOImpl();
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);
            int countID = GmPlayDAOImpl.count(connection);
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