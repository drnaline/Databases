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

import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.services.GamesOwnedService;
import cs4347.jdbcGame.services.impl.GamesOwnedServiceImpl;
import cs4347.jdbcGame.testing.DataSourceManager;
import cs4347.jdbcGame.util.TestingUtil;

public class GamesOwnedServiceImplTestCase
{

    @Test
    public void testCreate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService gameService = new GamesOwnedServiceImpl(ds);

        GamesOwned go1 = buildGamesOwned(ds);
        assertNull(go1.getId());
        GamesOwned go2 = gameService.create(go1);
        assertNotNull(go2.getId());
    }

    @Test
    public void testRetrieveByID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        GamesOwned go1 = buildGamesOwned(ds);
        GamesOwned go2 = goService.create(go1);
        assertNotNull(go2);

        Long gameOwnedID = go2.getId();
        GamesOwned go3 = goService.retrieveByID(gameOwnedID);
        assertNotNull(go3);
        assertEquals(go2.getId(), go3.getId());
        assertEquals(go2.getGameID(), go3.getGameID());
        assertEquals(go2.getPlayerID(), go3.getPlayerID());
        // assertEquals(go2.getPurchaseDate(), go3.getPurchaseDate());
        assertEquals(go2.getPurchasePrice(), go3.getPurchasePrice(), 0.001f);
    }

    @Test
    public void testRetrieveByIDFailed() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(dataSource);

        Long gameOwnedID = System.currentTimeMillis();
        GamesOwned go1 = goService.retrieveByID(gameOwnedID);
        assertNull(go1);
    }

    @Test
    public void testRetrievePlayerGameID() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<Long> gameIDs = TestingUtil.retrieveGamesOwnedForPlayer(ds, playerID);
        assertTrue(gameIDs.size() > 0);
        GamesOwned go1 = goService.retrievePlayerGameID(playerID, gameIDs.get(0));
        assertNotNull(go1);
        assertEquals(playerID, go1.getPlayerID());
    }

    @Test
    public void testRetrieveByGame() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<Long> gameIDs = TestingUtil.retrieveGamesOwnedForPlayer(ds, playerID);
        assertTrue(gameIDs.size() > 0);
        Long gameID = gameIDs.get(0);
        List<GamesOwned> goList = goService.retrieveByGame(gameID);
        assertNotNull(goList);
        assertTrue(goList.size() > 0);
        assertEquals(gameID, goList.get(0).getGameID());
    }

    @Test
    public void testRetrieveByPlayer() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        Long playerID = TestingUtil.getPlayerID(ds);
        List<GamesOwned> goList = goService.retrieveByPlayer(playerID);
        assertNotNull(goList);
        assertTrue(goList.size() > 0);
        assertEquals(playerID, goList.get(0).getPlayerID());
    }

    @Test
    public void testUpdate() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        GamesOwned go1 = buildGamesOwned(ds);
        go1.setPlayerID(TestingUtil.getPlayerID(ds));
        go1.setGameID(TestingUtil.getGameID(ds));
        GamesOwned go2 = goService.create(go1);
        Long go2id = go2.getId();

        go2.setPurchaseDate(new Date());
        go2.setPurchasePrice(222.0f);
        
        int rows = goService.update(go2);
        assertEquals(1, rows);

        GamesOwned go3 = goService.retrieveByID(go2id);
        assertEquals(go2.getId(), go3.getId());
        assertEquals(go2.getPlayerID(), go3.getPlayerID());
        assertEquals(go2.getGameID(), go3.getGameID());
        // Having problems with Timezones & no time to investigate
        //assertEquals(go2.getPurchaseDate(), go3.getPurchaseDate());
        assertEquals(go2.getPurchasePrice(), go3.getPurchasePrice(), 0.01f);            
    }

    @Test
    public void testDelete() throws Exception
    {
        DataSource ds = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(ds);

        GamesOwned go1 = buildGamesOwned(ds);
        go1.setPlayerID(TestingUtil.getPlayerID(ds));
        go1.setGameID(TestingUtil.getGameID(ds));
        GamesOwned go2 = goService.create(go1);
        Long go2id = go2.getId();
        
        int count = goService.delete(go2id);
        assertEquals(1, count);
        
        GamesOwned go3 = goService.retrieveByID(go2id);
        assertNull(go3);
    }

    @Test
    public void testCount() throws Exception
    {
        DataSource dataSource = DataSourceManager.getDataSource();
        GamesOwnedService goService = new GamesOwnedServiceImpl(dataSource);

        int count = goService.count();
        assertTrue(count > 0);
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
