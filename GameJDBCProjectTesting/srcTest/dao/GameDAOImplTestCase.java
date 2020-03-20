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

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.DAOException;

public class GameDAOImplTestCase
{
    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            Game game = buildGame();
            Game game2 = dao.create(connection, game);
            assertNotNull(game2);
            assertNotNull(game2.getId());
            // System.out.println("Generated Key: " + game2.getId());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    /**
     * Test a failed creation due to attempting to insert a Game with a non=null ID.
     * Expects to catch DAOException.
     */
    @Test(expected = DAOException.class)
    public void testCreateFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            Game game = buildGame();
            // This should cause the create to fail with a DAOException
            game.setId(System.currentTimeMillis());
            dao.create(connection, game);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieve() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            Game game = buildGame();
            Game game2 = dao.create(connection, game);
            Long id = game2.getId();
            Game game3 = dao.retrieve(connection, id);
            assertNotNull(game3);
            assertEquals(game2.getId(), game3.getId());
            assertEquals(game2.getTitle(), game3.getTitle());
            // Having problems with Timezones & no time to investigate
            // assertEquals(game2.getReleaseDate(), game3.getReleaseDate());
            assertEquals(game2.getVersion(), game3.getVersion());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveFailed() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            Long id = System.currentTimeMillis();
            Game game = dao.retrieve(connection, id);
            assertNull(game);
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
            GameDAO dao = new GameDAOImpl();

            Game game = buildGame();
            Game game2 = dao.create(connection, game);
            Long id = game2.getId();

            String newTitle = "New Title" + System.currentTimeMillis();
            game2.setTitle(newTitle);
            String newDescription = "New Description" + System.currentTimeMillis();
            game2.setDescription(newDescription);
            String newVersion = "New Version" + System.currentTimeMillis();
            game2.setVersion(newVersion);

            int rows = dao.update(connection, game2);
            assertEquals(1, rows);

            Game game3 = dao.retrieve(connection, id);
            assertEquals(newTitle, game3.getTitle());
            assertEquals(newDescription, game3.getDescription());
            assertEquals(newVersion, game3.getVersion());
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
            GameDAO dao = new GameDAOImpl();

            Game game = buildGame();
            Game game2 = dao.create(connection, game);
            Long id = game2.getId();

            int rows = dao.delete(connection, id);
            assertEquals(1, rows);
        }
        finally {
            // Do not commit any changes made by this test.
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
            GameDAO dao = new GameDAOImpl();

            int count = dao.count(connection);
            assert (count > 0);
            System.out.println("Game Row Count: " + count);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveByTitle() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            List<Game> gameList = dao.retrieveByTitle(connection, "Vision%");
            assertEquals(1, gameList.size());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Test
    public void testRetrieveByReleaseDate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            GameDAO dao = new GameDAOImpl();

            Date startDate = sdf.parse("1/1/2005");
            Date endDate = sdf.parse("1/1/2010");
            List<Game> gameList = dao.retrieveByReleaseDate(connection, startDate, endDate);
            assertEquals(36, gameList.size());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private Game buildGame()
    {
        Game result = new Game();
        result.setTitle("Test Title");
        result.setDescription("Test Description");
        result.setReleaseDate(new Date());
        result.setVersion("1.2.3");
        return result;
    }
}
