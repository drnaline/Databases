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
package cs4347.jdbcGame.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedDAOImpl implements GamesPlayedDAO
{

	final String insertSQL = "INSERT INTO gamesplayed (id, player_id, game_id, time_finished, score)" + "VALUES (?, ?, ?, ?, ?)";
    @Override
    public GamesPlayed create(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
        if(gamesPlayed.getId() != null){
            throw new DAOException("Attempting to INSERT gamesplayed CONTAINING NON-NULL ID");
        }
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, gamesPlayed.getId());
            ps.setLong(2, gamesPlayed.getPlayerID());
            ps.setLong(3, gamesPlayed.getGameID());
            ps.setDate(3, new java.sql.Date(gamesPlayed.getTimeFinished().getTime()));
            ps.setInt(5, gamesPlayed.getScore());
            int result = ps.executeUpdate();
            if(result != 1){
                throw new DAOException("Number of Rows not created/updated correctly");
            }

            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            gamesPlayed.setId((long)lastKey);
            return gamesPlayed;
        }
        finally{
            if(ps != null && !ps.isClosed()){
                ps.close();
            }
        }
    }
    final static String selectIDSQL = "SELECT id, player_id, games_id, time_finished, score FROM gamesplayed where id = ?";

    
    @Override
    public GamesPlayed retrieveID(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
    	 if (gamePlayedID == null) {
             throw new DAOException("Trying to retrieve Games Played with NULL ID");
         }

         PreparedStatement ps = null;
         try {
             ps = connection.prepareStatement(selectIDSQL);
             ps.setLong(1, gamePlayedID);
             ResultSet rs = ps.executeQuery();
             if (!rs.next()) {
                 return null;
             }

             GamesPlayed gp = extractGPFromRS(rs);
             return gp;
         }
         finally {
             if (ps != null && !ps.isClosed()) {
                 ps.close();
             }
         }
    }
    
    final static String selectByPlayerGameIDSQL = "SELECT id, player_id, games_id, time_finished, score FROM gamesplayed where id = ?";

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(Connection connection, Long playerID, Long gameID)
            throws SQLException, DAOException
    {
    	if (playerID == null) {
            throw new DAOException("Trying to retrieve Games Played with NULL ID");
        }
    	List<GamesPlayed> result = new ArrayList<GamesPlayed>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectIDSQL);
            ps.setLong(1, playerID);
            ps.setLong(2, gameID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                GamesPlayed gp = extractGPFromRS(rs);
                result.add(gp);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String selectByPlayerIDSQL = "SELECT id, player_id, games_id, time_finished, score FROM gamesplayed where player_id = ?";

    @Override
    public List<GamesPlayed> retrieveByPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	List<GamesPlayed> result = new ArrayList<GamesPlayed>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectByPlayerIDSQL);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GamesPlayed gp = extractGPFromRS(rs);
                result.add(gp);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String selectByGameIDSQL = "SELECT id, player_id, games_id, time_finished, score FROM gamesplayed where id = ?";

    @Override
    public List<GamesPlayed> retrieveByGame(Connection connection, Long gameID) throws SQLException, DAOException
    {
    	List<GamesPlayed> result = new ArrayList<GamesPlayed>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectByGameIDSQL);
            ps.setLong(1, gameID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GamesPlayed gp = extractGPFromRS(rs);
                result.add(gp);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String updateSQL = "UPDATE gamesplayed SET playerID = ?, gameID = ?, timeFinished = ?, score = ? WHERE player_id = ?;";
    
    @Override
    public int update(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
    	Long id = gamesPlayed.getId();
        if (id == null) {
            throw new DAOException("Trying to update gamesPlayed with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setLong(1, gamesPlayed.getPlayerID());
            ps.setLong(2, gamesPlayed.getGameID());
            ps.setDate(3, new java.sql.Date(gamesPlayed.getTimeFinished().getTime()));
            ps.setFloat(4, gamesPlayed.getScore());
            ps.setLong(5, id);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
   
    final static String deleteSQL = "delete from gamesplayed where player_id = ?;";

    @Override
    public int delete(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
    	 if (gamePlayedID == null) {
             throw new DAOException("Trying to delete games played with NULL ID");
         }

     PreparedStatement ps = null;
     try {
         ps = connection.prepareStatement(deleteSQL);
         ps.setLong(1, gamePlayedID);

         int rows = ps.executeUpdate();
         return rows;
     }
     finally {
         if (ps != null && !ps.isClosed()) {
             ps.close();
         }
     }
    }
    final static String countSQL = "select count(*) from gamesplayed";

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(countSQL);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new DAOException("No Count Returned");
            }
            int count = rs.getInt(1);
            return count;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    private GamesPlayed extractGPFromRS(ResultSet rs) throws SQLException
    {
        GamesPlayed gp = new GamesPlayed();
        gp.setId(rs.getLong("id"));
        gp.setPlayerID(rs.getLong("player_id"));
        gp.setGameID(rs.getLong("game_id"));
        gp.setTimeFinished(rs.getDate("time_finished"));
        gp.setScore(rs.getInt("score"));
        return gp;
    }

}
