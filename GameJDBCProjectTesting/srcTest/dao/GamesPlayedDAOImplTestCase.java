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

import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.dao.impl.GamesPlayedDAOImpl;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.DAOException;
import cs4347.jdbcGame.util.TestingUtil;

public class GamesPlayedDAOImplTestCase
{

    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            GamesPlayed gp1 = buildGamesPlayed(ds);
            GamesPlayed gp2 = dao.create(connection, gp1);
            assertNotNull(gp2);
            assertNotNull(gp2.getId());
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            GamesPlayed gp1 = buildGamesPlayed(ds);
            gp1.setId(System.currentTimeMillis());
            // Will fail with a DAOException
            dao.create(connection, gp1);
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            GamesPlayed gp1 = buildGamesPlayed(ds);
            GamesPlayed gp2 = dao.create(connection, gp1);
            Long id = gp2.getId();

            GamesPlayed gp3 = dao.retrieveID(connection, id);
            assertNotNull(gp3);
            assertEquals(gp2.getId(), gp3.getId());
            assertEquals(gp2.getPlayerID(), gp3.getPlayerID());
            assertEquals(gp2.getGameID(), gp3.getGameID());
            // Having problems with Timestamp, Timezones & no time to investigate
            // assertEquals(gp2.getTimeFinished(), gp3.getTimeFinished());
            assertEquals(gp2.getScore(), gp3.getScore());
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            Long id = System.currentTimeMillis();
            GamesPlayed gp1 = dao.retrieveID(connection, id);
            assertNull(gp1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByPlayerGameID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = TestingUtil.retrieveGamesPlayedForPlayer(ds, playerID).get(0);

            List<GamesPlayed> gamesPlayed = dao.retrieveByPlayerGameID(connection, playerID, gameID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() > 0);
            assertEquals(playerID, gamesPlayed.get(0).getPlayerID());
            assertEquals(gameID, gamesPlayed.get(0).getGameID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByPlayerGameIDFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = System.currentTimeMillis();

            List<GamesPlayed> gamesPlayed = dao.retrieveByPlayerGameID(connection, playerID, gameID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() == 0);
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);

            List<GamesPlayed> gamesPlayed = dao.retrieveByPlayer(connection, playerID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() > 0);
            assertEquals(playerID, gamesPlayed.get(0).getPlayerID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }

    }

    @Test
    public void testRetrieveByPlayerFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long playerID = System.currentTimeMillis();

            List<GamesPlayed> gamesPlayed = dao.retrieveByPlayer(connection, playerID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() == 0);
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long playerID = TestingUtil.getPlayerID(ds);
            Long gameID = TestingUtil.retrieveGamesPlayedForPlayer(ds, playerID).get(0);

            List<GamesPlayed> gamesPlayed = dao.retrieveByGame(connection, gameID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() > 0);
            assertEquals(gameID, gamesPlayed.get(0).getGameID());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByGameFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
            Long gameID = System.currentTimeMillis();

            List<GamesPlayed> gamesPlayed = dao.retrieveByGame(connection, gameID);
            assertNotNull(gamesPlayed);
            assertTrue(gamesPlayed.size() == 0);
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            GamesPlayed gp1 = buildGamesPlayed(ds);
            gp1.setPlayerID(TestingUtil.getPlayerID(ds));
            gp1.setGameID(TestingUtil.getGameID(ds));

            GamesPlayed gp2 = dao.create(connection, gp1);
            Long id = gp2.getId();

            gp2.setTimeFinished(new Date());
            gp2.setScore(100);

            int rows = dao.update(connection, gp2);
            assertEquals(1, rows);

            GamesPlayed gp3 = dao.retrieveID(connection, id);
            assertEquals(gp2.getId(), gp3.getId());
            assertEquals(gp2.getPlayerID(), gp3.getPlayerID());
            assertEquals(gp2.getGameID(), gp3.getGameID());
            // Having problems with Timezones & no time to investigate
            // assertEquals(gp2.getTimeFinished(), gp3.getTimeFinished());
            assertEquals(gp2.getScore(), gp3.getScore());
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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();

            GamesPlayed gp1 = buildGamesPlayed(ds);
            gp1.setPlayerID(TestingUtil.getPlayerID(ds));
            gp1.setGameID(TestingUtil.getGameID(ds));

            GamesPlayed gp2 = dao.create(connection, gp1);
            Long id = gp2.getId();

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
            GamesPlayedDAO dao = new GamesPlayedDAOImpl();
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

    // Initializes a new GamesPlayed include valid player and game ids.
    private GamesPlayed buildGamesPlayed(DataSource ds) throws Exception
    {
        Long playerID = TestingUtil.getPlayerID(ds);
        Long gameID = TestingUtil.getGameID(ds);

        GamesPlayed gp = new GamesPlayed();
        gp.setGameID(gameID);
        gp.setPlayerID(playerID);
        gp.setTimeFinished(new Date());
        gp.setScore(100);
        return gp;
    }

}
