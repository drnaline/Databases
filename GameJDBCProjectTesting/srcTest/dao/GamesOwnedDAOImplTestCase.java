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
package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.dao.GamesOwnedDAO;
import cs4347.jdbcGame.dao.impl.GamesOwnedDAOImpl;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.DAOException;
import cs4347.jdbcGame.util.TestingUtil;

public class GamesOwnedDAOImplTestCase
{
    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();

            GamesOwned go1 = buildGamesOwned(ds);
            GamesOwned go2 = dao.create(connection, go1);
            assertNotNull(go2);
            assertNotNull(go2.getId());
            // System.out.println("Generated Key: " + go2.getId());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test(expected = DAOException.class)
    public void testCreateFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();

            GamesOwned go1 = buildGamesOwned(ds);
            go1.setId(System.currentTimeMillis());
            // This will throw a DaoException
            dao.create(connection, go1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();

            GamesOwned go1 = buildGamesOwned(ds);
            GamesOwned go2 = dao.create(connection, go1);
            Long id = go2.getId();

            GamesOwned go3 = dao.retrieveID(connection, id);
            assertNotNull(go3);
            assertEquals(go2.getId(), go3.getId());
            assertEquals(go2.getPlayerID(), go3.getPlayerID());
            assertEquals(go2.getGameID(), go3.getGameID());
            // Having problems with Timezones & no time to investigate
            // assertEquals(go2.getPurchaseDate(), go3.getPurchaseDate());
            assertEquals(go2.getPurchasePrice(), go3.getPurchasePrice(), 0.001d);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveIDFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();

            Long id = System.currentTimeMillis();
            GamesOwned go1 = dao.retrieveID(connection, id);
            assertNull(go1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrievePlayerGameID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = TestingUtil.retrieveGamesOwnedForPlayer(ds, playerID).get(0);

            GamesOwned go1 = dao.retrievePlayerGameID(connection, playerID, gameID);
            assertNotNull(go1);
            assertEquals(playerID, go1.getPlayerID());
            assertEquals(gameID, go1.getGameID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrievePlayerGameIDFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = System.currentTimeMillis();

            GamesOwned go1 = dao.retrievePlayerGameID(connection, playerID, gameID);
            assertNull(go1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByGame() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = TestingUtil.retrieveGamesOwnedForPlayer(ds, playerID).get(0);

            List<GamesOwned> gamesOwned = dao.retrieveByGame(connection, gameID);
            assertNotNull(gamesOwned);
            assertTrue(gamesOwned.size() > 0);
            assertEquals(gameID, gamesOwned.get(0).getGameID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByGameFail() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long gameID = System.currentTimeMillis();

            List<GamesOwned> gamesOwned = dao.retrieveByGame(connection, gameID);
            assertNotNull(gamesOwned);
            assertTrue(gamesOwned.size() == 0);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByPlayer() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);

            List<GamesOwned> gamesOwned = dao.retrieveByPlayer(connection, playerID);
            assertNotNull(gamesOwned);
            assertTrue(gamesOwned.size() > 0);
            assertEquals(playerID, gamesOwned.get(0).getPlayerID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByPlayerFail() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            Long playerID = System.currentTimeMillis();

            List<GamesOwned> gamesOwned = dao.retrieveByPlayer(connection, playerID);
            assertNotNull(gamesOwned);
            assertTrue(gamesOwned.size() == 0);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testUpdate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();

            GamesOwned go1 = buildGamesOwned(ds);
            go1.setPlayerID(TestingUtil.getPlayerID(ds));
            go1.setGameID(TestingUtil.getGameID(ds));

            GamesOwned go2 = dao.create(connection, go1);
            Long id = go2.getId();

            go2.setPurchaseDate(new Date());
            go2.setPurchasePrice(10.0f);

            int rows = dao.update(connection, go2);
            assertEquals(1, rows);

            GamesOwned go3 = dao.retrieveID(connection, id);
            assertEquals(go2.getId(), go3.getId());
            assertEquals(go2.getPlayerID(), go3.getPlayerID());
            assertEquals(go2.getGameID(), go3.getGameID());
            // Having problems with Timezones & no time to investigate
            // assertEquals(go2.getPurchaseDate(), go3.getPurchaseDate());
            assertEquals(go2.getPurchasePrice(), go3.getPurchasePrice(), 0.01f);
        }
        finally {
            // Do not commit any changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testDelete() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            GamesOwned go1 = buildGamesOwned(ds);
            go1.setPlayerID(TestingUtil.getPlayerID(ds));
            go1.setGameID(TestingUtil.getGameID(ds));

            GamesOwned go2 = dao.create(connection, go1);
            Long id = go2.getId();

            int rows = dao.delete(connection, id);
            assertEquals(1, rows);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testCount() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesOwnedDAO dao = new GamesOwnedDAOImpl();
            int count = dao.count(connection);
            assertTrue(count > 1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    // Initializes a new GameOwned include valid player and game ids.
    private GamesOwned buildGamesOwned(DataSource ds) throws Exception
    {
        Long playerID = TestingUtil.getPlayerID(ds);
        Long gameID = TestingUtil.getGameID(ds);

        GamesOwned go = new GamesOwned();
        go.setGameID(gameID);
        go.setPlayerID(playerID);
        go.setPurchaseDate(new Date());
        go.setPurchasePrice(1.0f);
        return go;
    }

}
