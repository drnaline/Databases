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
package cs4347.jdbcGame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * NOTE: This class / functionality belongs in the unit testing project. But it
 * is in the student and development projects because its functionality requires
 * knowledge of the underlying database schema. Under normal circumstances this
 * knowledge would not be a problem, but each student project will have a
 * different schema with individual tables and column names.
 *
 */
public class TestingUtil
{
    /**
     * Return first Player ID for testing purposes.
     */
    static public Long getPlayerID(DataSource ds) throws Exception
    {
        List<Long> ids = getPlayerIDs(ds, 1);
        return ids.get(0);
    }

    // This query retrieves all player IDs in the result set
    // e.g. "select id from player"
    static String selectPlayerIDs = "To Be Added By Student";

    /**
     * Return N current Player IDs for testing purposes.
     */
    static public List<Long> getPlayerIDs(DataSource ds, int count) throws Exception
    {
        Connection connection = ds.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectPlayerIDs);
            List<Long> result = new ArrayList<Long>();
            for (int idx = 0; idx < count; idx++) {
                rs.next();
                Long id = rs.getLong(1);
                result.add(id);
            }
            statement.close();
            return result;
        }
        finally {
            connection.close();
        }
    }

    /**
     * Return first Game ID for testing purposes.
     */
    static public Long getGameID(DataSource ds) throws Exception
    {
        List<Long> ids = getGameIDs(ds, 1);
        return ids.get(0);
    }

    // This query retrieves all game IDs in the result set
    // e.g. "select id from game"
    static String selectGameIDs = "To Be Added By Student";

    /**
     * Return N current Game IDs for testing purposes.
     */
    static public List<Long> getGameIDs(DataSource ds, int count) throws Exception
    {
        Connection connection = ds.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectGameIDs);
            List<Long> result = new ArrayList<Long>();
            for (int idx = 0; idx < count; idx++) {
                rs.next();
                Long id = rs.getLong(1);
                result.add(id);
            }
            statement.close();
            return result;
        }
        finally {
            connection.close();
        }
    }

    // This query will return the distinct game IDs being played by the given player ID. 
    // e.g. "select distinct game_id from games_played where player_id = ?"
    static String selectDistinctPlayedGameIDs = "To Be Added By Student";
    
    static public List<Long> retrieveGamesPlayedForPlayer(DataSource ds, Long playerID) throws Exception
    {
        Connection connection = ds.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(selectDistinctPlayedGameIDs);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();

            List<Long> result = new ArrayList<Long>();
            while (rs.next()) {
                Long id = rs.getLong(1);
                result.add(id);
            }
            ps.close();
            return result;
        }
        finally {
            connection.close();
        }
    }

    // This query will return the distinct game IDs owned by the given player ID. 
    // e.g. "select distinct game_id from games_owned where player_id = ?"
    static String selectDistinctOwnedGameIDs = "To Be Added By Student";
    
    static public List<Long> retrieveGamesOwnedForPlayer(DataSource ds, Long playerID) throws Exception
    {
        Connection connection = ds.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(selectDistinctOwnedGameIDs);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();

            List<Long> result = new ArrayList<Long>();
            while (rs.next()) {
                Long id = rs.getLong(1);
                result.add(id);
            }
            ps.close();
            return result;
        }
        finally {
            connection.close();
        }
    }
}
