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
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.dao.impl.CreditCardDAOImpl;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.DAOException;
import cs4347.jdbcGame.util.TestingUtil;

public class CreditCardDAOImplTestCase
{

    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            CreditCard cc1 = buildCreditCard();
            CreditCard cc2 = dao.create(connection, cc1, playerID);
            assertNotNull(cc2);
            assertNotNull(cc2.getId());
            System.out.println("Generated Key: " + cc2.getId());
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
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            CreditCard cc1 = buildCreditCard();
            cc1.setId(System.currentTimeMillis());
            // Fails with DAOException
            dao.create(connection, cc1, playerID);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    final static String selectSQL = "SELECT player_id, cc_name, cc_number, exp_date, security_code FROM creditcard where id = ?";

    @Test
    public void testRetrieve() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            CreditCard cc1 = buildCreditCard();
            CreditCard cc2 = dao.create(connection, cc1, playerID);
            Long id = cc2.getId();
            CreditCard cc3 = dao.retrieve(connection, id);
            assertNotNull(cc3);
            assertEquals(cc2.getId(), cc3.getId());
            assertEquals(cc2.getPlayerID(), cc3.getPlayerID());
            assertEquals(cc2.getCcName(), cc3.getCcName());
            assertEquals(cc2.getCcNumber(), cc3.getCcNumber());
            assertEquals(cc2.getExpDate(), cc3.getExpDate());
            assertEquals(cc2.getSecurityCode(), cc3.getSecurityCode());
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
            CreditCardDAO dao = new CreditCardDAOImpl();

            Long id = System.currentTimeMillis();
            CreditCard cc1 = dao.retrieve(connection, id);
            assertNull(cc1);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void testRetrieveCreditCardsForPlayer() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        Connection connection = ds.getConnection();
        // Do not commit any changes made by this test.
        connection.setAutoCommit(false);
        try {
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            List<CreditCard> ccList = dao.retrieveCreditCardsForPlayer(connection, playerID);
            assertNotNull(ccList);
            assertEquals(2, ccList.size());
            for (CreditCard cc : ccList) {
                assertNotNull(cc);
            }
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
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            CreditCard cc1 = buildCreditCard();

            CreditCard cc2 = dao.create(connection, cc1, playerID);
            Long id = cc2.getId();

            String newCCName = "New CCName" + System.currentTimeMillis();
            cc2.setCcName(newCCName);
            String newCCNumber = "New CCNumber" + System.currentTimeMillis();
            cc2.setCcNumber(newCCNumber);
            String newExpDate = "1/11";
            cc2.setExpDate(newExpDate);
            int newSecCode = 123;
            cc2.setSecurityCode(newSecCode);

            int rows = dao.update(connection, cc2);
            assertEquals(1, rows);

            CreditCard cc3 = dao.retrieve(connection, id);
            assertEquals(cc2.getId(), cc3.getId());
            assertEquals(cc2.getPlayerID(), cc3.getPlayerID());
            assertEquals(cc2.getCcName(), cc3.getCcName());
            assertEquals(cc2.getCcNumber(), cc3.getCcNumber());
            assertEquals(cc2.getExpDate(), cc3.getExpDate());
            assertEquals(cc2.getSecurityCode(), cc3.getSecurityCode());
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
            CreditCardDAO dao = new CreditCardDAOImpl();

            List<Long> playerIDs = TestingUtil.getPlayerIDs(ds, 1);
            Long playerID = playerIDs.get(0);

            CreditCard cc1 = buildCreditCard();
            CreditCard cc2 = dao.create(connection, cc1, playerID);
            Long id = cc2.getId();

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
            CreditCardDAO dao = new CreditCardDAOImpl();

            int count = dao.count(connection);
            assert (count > 0);
            System.out.println("CreditCard Row Count: " + count);
        }
        finally {
            // Do not commit changes made by this test.
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private CreditCard buildCreditCard()
    {
        CreditCard cc = new CreditCard();
        cc.setCcName("Hank Snow");
        cc.setCcNumber("2233344434");
        cc.setExpDate("12/25");
        cc.setSecurityCode(123);
        return cc;
    }

}
