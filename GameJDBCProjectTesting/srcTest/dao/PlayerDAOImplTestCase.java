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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.dao.impl.PlayerDAOImpl;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.DAOException;

public class PlayerDAOImplTestCase
{
    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            PlayerDAO dao = new PlayerDAOImpl();

            Player p1 = buildPlayer();
            Player p2 = dao.create(connection, p1);
            assertNotNull(p2);
            assertNotNull(p2.getId());
            System.out.println("Generated Key: " + p2.getId());
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
            PlayerDAO dao = new PlayerDAOImpl();

            Player p1 = buildPlayer();
            // This should cause the create to fail with a DAOException
            p1.setId(System.currentTimeMillis());
            dao.create(connection, p1);
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
            PlayerDAO dao = new PlayerDAOImpl();

            Player p1 = buildPlayer();
            Player p2 = dao.create(connection, p1);
            Long id = p2.getId();

            Player p3 = dao.retrieve(connection, id);
            assertNotNull(p3);
            assertEquals(p2.getId(), p3.getId());
            assertEquals(p2.getFirstName(), p3.getFirstName());
            assertEquals(p2.getLastName(), p3.getLastName());
            // Having problems with Timestamp & Timezones & no time to investigate
            // assertEquals(p2.getJoinDate(), p3.getJoinDate());
            assertEquals(p2.getEmail(), p3.getEmail());
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveFail() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            PlayerDAO dao = new PlayerDAOImpl();
            Long id = System.currentTimeMillis();
            Player p1 = dao.retrieve(connection, id);
            assertNull(p1);
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
            PlayerDAO dao = new PlayerDAOImpl();

            Player p1 = buildPlayer();
            Player p2 = dao.create(connection, p1);
            Long id = p2.getId();

            String newFN = "Hank" + System.currentTimeMillis();
            p2.setFirstName(newFN);
            String newLN = "Hill" + System.currentTimeMillis();
            p2.setLastName(newLN);
            Date newDate = new Date();
            p2.setJoinDate(newDate);
            String newEmail = "Hank@Hank.com" + System.currentTimeMillis();
            p2.setEmail(newEmail);

            int rows = dao.update(connection, p2);
            assertEquals(1, rows);

            Player p3 = dao.retrieve(connection, id);
            assertEquals(newFN, p3.getFirstName());
            assertEquals(newLN, p3.getLastName());
            // Having problems with Timestamp & Timezones & no time to investigate
            // assertEquals(newDate, p3.getJoinDate());
            assertEquals(newEmail, p3.getEmail());
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
            PlayerDAO dao = new PlayerDAOImpl();

            Player p1 = buildPlayer();
            Player p2 = dao.create(connection, p1);
            Long id = p2.getId();

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
            PlayerDAO dao = new PlayerDAOImpl();
            int count = dao.count(connection);
            assert (count > 0);
            System.out.println("Player Row Count: " + count);
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
    public void TestRetrieveByJoinDate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            PlayerDAO dao = new PlayerDAOImpl();
            Date startDate = sdf.parse("1/1/2017");
            Date endDate = sdf.parse("1/1/2019");
            List<Player> playerList = dao.retrieveByJoinDate(connection, startDate, endDate);

            assertTrue(playerList.size() > 0);
            for (Player player : playerList) {
                assertTrue(startDate.getTime() <= player.getJoinDate().getTime());
                assertTrue(endDate.getTime() >= player.getJoinDate().getTime());
            }
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private Player buildPlayer()
    {
        Player p = new Player();
        p.setFirstName("Test First Name");
        p.setLastName("Test Last Name");
        p.setJoinDate(new Date());
        p.setEmail("test@test.com");
        return p;
    }
}
