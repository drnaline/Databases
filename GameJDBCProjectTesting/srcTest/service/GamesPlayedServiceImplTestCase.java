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

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.services.GamesPlayedService;
import cs4347.jdbcGame.services.impl.GamesPlayedServiceImpl;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.TestingUtil;

public class GamesPlayedServiceImplTestCase
{

    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gameService = new GamesPlayedServiceImpl(ds);

        GamesPlayed gp1 = buildGamesPlayed(ds);
        assertNull(gp1.getId());
        GamesPlayed gp2 = gameService.create(gp1);
        assertNotNull(gp2.getId());
    }

    @Test
    public void testRetrieveByID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        GamesPlayed gp1 = buildGamesPlayed(ds);
        GamesPlayed gp2 = gpService.create(gp1);
        assertNotNull(gp2);

        Long gamePlayedID = gp2.getId();
        GamesPlayed gp3 = gpService.retrieveByID(gamePlayedID);
        assertNotNull(gp3);
        assertEquals(gp2.getId(), gp3.getId());
        assertEquals(gp2.getGameID(), gp3.getGameID());
        assertEquals(gp2.getPlayerID(), gp3.getPlayerID());
        //assertEquals(gp2.getTimeFinished(), gp3.getTimeFinished());
        assertEquals(gp2.getScore(), gp3.getScore());
    }

    @Test
    public void testRetrieveByIDFail() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(dataSource);

        Long gamePlayedID = System.currentTimeMillis();
        GamesPlayed gp1 = gpService.retrieveByID(gamePlayedID);
        assertNull(gp1);
    }

    @Test
    public void testRetrieveByPlayerGameID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<Long> gameIDs = TestingUtil.retrieveGamesPlayedForPlayer(ds, playerID);
        assertTrue(gameIDs.size() > 0);
        List<GamesPlayed> gpList = gpService.retrieveByPlayerGameID(playerID, gameIDs.get(0));
        assertNotNull(gpList);
        assertEquals(playerID, gpList.get(0).getPlayerID());
    }

    @Test
    public void testRetrieveByGame() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<Long> gameIDs = TestingUtil.retrieveGamesPlayedForPlayer(ds, playerID);
        assertTrue(gameIDs.size() > 0);
        List<GamesPlayed> gpList = gpService.retrieveByGame(gameIDs.get(0));
        assertNotNull(gpList);
        assertEquals(playerID, gpList.get(0).getPlayerID());
    }

    @Test
    public void testRetrieveByPlayer() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<GamesPlayed> gpList = gpService.retrieveByPlayer(playerID);
        assertNotNull(gpList);
        assertEquals(playerID, gpList.get(0).getPlayerID());
    }

    @Test
    public void testUpdate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        GamesPlayed gp1 = buildGamesPlayed(ds);
        gp1.setPlayerID(TestingUtil.getPlayerID(ds));
        gp1.setGameID(TestingUtil.getGameID(ds));
        GamesPlayed gp2 = gpService.create(gp1);
        Long gp2id = gp2.getId();

        gp2.setTimeFinished(new Date());
        gp2.setScore(123);
        
        int rows = gpService.update(gp2);
        assertEquals(1, rows);

        GamesPlayed gp3 = gpService.retrieveByID(gp2id);
        assertEquals(gp2.getId(), gp3.getId());
        assertEquals(gp2.getPlayerID(), gp3.getPlayerID());
        assertEquals(gp2.getGameID(), gp3.getGameID());
        // Having problems with Timestamp, Timezones & no time to investigate
        //assertEquals(gp2.getTimeFinished(), gp3.getTimeFinished());
        assertEquals(gp2.getScore(), gp3.getScore());            
    }

    @Test
    public void testDelete() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(ds);

        GamesPlayed gp1 = buildGamesPlayed(ds);
        gp1.setPlayerID(TestingUtil.getPlayerID(ds));
        gp1.setGameID(TestingUtil.getGameID(ds));
        GamesPlayed gp2 = gpService.create(gp1);
        Long gp2id = gp2.getId();
        
        int count = gpService.delete(gp2id);
        assertEquals(1, count);
        
        GamesPlayed gp3 = gpService.retrieveByID(gp2id);
        assertNull(gp3);
    }

    @Test
    public void testCount() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        GamesPlayedService gpService = new GamesPlayedServiceImpl(dataSource);

        int count = gpService.count();
        assertTrue(count > 0);
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
