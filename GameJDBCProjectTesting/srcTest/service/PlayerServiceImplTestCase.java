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
package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.services.PlayerService;
import cs4347.jdbcGame.services.impl.PlayerServiceImpl;
import cs4347.jdbcGame.testing.DataSourceManager;

public class PlayerServiceImplTestCase
{

    @Test
    public void testCreate() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);

        Player p1 = buildPlayerWithCC();
        assertNull(p1.getId());
        Player p2 = playerService.create(p1);
        assertNotNull(p2.getId());
    }

    @Test
    public void testRetrieve() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);

        Player p1 = buildPlayerWithCC();
        Player p2 = playerService.create(p1);
        
        Player p3 = playerService.retrieve(p1.getId());
        assertNotNull(p3);
        assertEquals(p2.getId(), p3.getId());
        assertEquals(p2.getFirstName(), p3.getFirstName());
        assertEquals(p2.getLastName(), p3.getLastName());
        //assertEquals(p2.getJoinDate(), p3.getJoinDate());
        assertEquals(p2.getEmail(), p3.getEmail());
        assertEquals(p2.getCreditCards().size(), p3.getCreditCards().size());
        
        for(int idx = 0; idx < p2.getCreditCards().size(); idx++) {
            CreditCard cc2 = p2.getCreditCards().get(idx);
            CreditCard cc3 = p3.getCreditCards().get(idx);
            assertEquals(cc2.getId(), cc3.getId());
            assertEquals(cc2.getCcName(), cc3.getCcName());
            assertEquals(cc2.getCcNumber(), cc3.getCcNumber());
            assertEquals(cc2.getExpDate(), cc3.getExpDate());
            assertEquals(cc2.getSecurityCode(), cc3.getSecurityCode());
        }
    }

    @Test
    public void testRetrieveFail() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);

        Long playerID = System.currentTimeMillis();
        Player p1 = playerService.retrieve(playerID);
        assertNull(p1);
    }
    
    /**
     * Test updating the player's info.
     */
    @Test
    public void testUpdate() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);
        Player p1 = buildPlayerWithCC();
        Player p2 = playerService.create(p1);
        
        String firstName = "New First Name";
        p2.setFirstName(firstName);
        String lastName = "New Last Name";
        p2.setLastName(lastName);
        String email = "New Email";
        p2.setEmail(email);
        
        int count = playerService.update(p2);
        assertEquals(1, count);
        
        Player p3 = playerService.retrieve(p2.getId());
        assertEquals(firstName, p3.getFirstName());
        assertEquals(lastName, p3.getLastName());
        assertEquals(email, p3.getEmail());
    }
    
    /**
     * Test updating the player's credit card.
     */
    @Test
    public void testUpdateCC() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);

        Player p1 = buildPlayerWithCC();
        Player p2 = playerService.create(p1);
        
        int p2ccCount = p2.getCreditCards().size();
        
        p2.getCreditCards().add(buildCreditCard());
        playerService.update(p2);
        
        Player p3 = playerService.retrieve(p2.getId());
        assertEquals(p2ccCount + 1, p3.getCreditCards().size());
    }

    @Test
    public void testDelete() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);
        
        Player p1 = buildPlayerWithCC();
        Player p2 = playerService.create(p1);
        
        Long playerID = p2.getId();
        int count = playerService.delete(playerID);
        assertEquals(1, count);
        int ccCount = playerService.countCreditCardsForPlayer(playerID);
        assertEquals(0, ccCount);
    }

    @Test
    public void testCount() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);
        
        int count = playerService.count();
        assertTrue(count > 0);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Test
    public void testRetrieveByJoinDate() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        PlayerService playerService = new PlayerServiceImpl(dataSource);
        
        Date startDate = sdf.parse("1/1/2017");
        Date endDate = sdf.parse("1/1/2019");
        
        List<Player> playerList = playerService.retrieveByJoinDate(startDate, endDate);
        assertTrue(playerList.size() > 0);
    }

    private Player buildPlayerWithCC()
    {
        Player p = new Player();
        p.setFirstName("Test First Name");
        p.setLastName("Test Last Name");
        p.setJoinDate(new Date());
        p.setEmail("test@test.com");
        p.getCreditCards().add(buildCreditCard());
        p.getCreditCards().add(buildCreditCard());
        return p;
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
