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

import cs4347.jdbcGame.dao.GamesOwnedDAO;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.util.DAOException;



public class GamesOwnedDAOImpl implements GamesOwnedDAO
{
	private static final String insertSQL = "INSERT INTO games.gamesowned (player_id, game_id, purchase_date, purchase_price) VALUES (?, ?, ?, ?);";

    @Override
    public GamesOwned create(Connection connection, GamesOwned gamesOwned) throws SQLException, DAOException
    {
    	 if (gamesOwned.getId() != null) {
             throw new DAOException("Trying to insert GamesOwned with NON-NULL ID");
         }

         PreparedStatement ps = null;
         try {
        	 ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
             ps.setLong(1,gamesOwned.getPlayerID());
             ps.setLong(2, gamesOwned.getGameID());
             ps.setDate(3, new java.sql.Date(gamesOwned.getPurchaseDate().getTime()));
             ps.setFloat(4, gamesOwned.getPurchasePrice());
             ps.executeUpdate();
             
             // Copy the assigned ID to the game instance.
             ResultSet keyRS = ps.getGeneratedKeys();
             keyRS.next();
             int lastKey = keyRS.getInt(1);
             gamesOwned.setId((long) lastKey);


             return gamesOwned;
         }
         finally {
             if (ps != null && !ps.isClosed()) {
                 ps.close();
             }
         }
    }

    final static String selectIDSQL = "SELECT id, player_id, game_id, purchase_date, purchase_price FROM games.gamesowned where id = ?";
    @Override
    public GamesOwned retrieveID(Connection connection, Long gamesOwnedID) throws SQLException, DAOException
    {
        if (gamesOwnedID == null) {
            throw new DAOException("Trying to retrieve GamesOwned with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectIDSQL);
            ps.setLong(1, gamesOwnedID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            GamesOwned go = extractGOFromRS(rs);
            return go;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String selectByPlayerGameIDSQL = "SELECT id, player_id, game_id, purchase_date, purchase_price FROM games.gamesowned where player_id = ? and game_id = ?";
    @Override
	public GamesOwned retrievePlayerGameID(Connection connection, Long playerID, Long gameID)
            throws SQLException, DAOException
    {
        if (playerID == null) {
            throw new DAOException("Trying to retrieve Games Owned with NULL ID");
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectByPlayerGameIDSQL);
            ps.setLong(1, playerID);
            ps.setLong(2, gameID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            GamesOwned go = extractGOFromRS(rs);
            return go;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    
    final static String selectByGameIDSQL = "SELECT id, player_id, game_id, purchase_date, purchase_price FROM games.gamesowned where game_id = ?";
    @Override
    public List<GamesOwned> retrieveByGame(Connection connection, Long gameID) throws SQLException, DAOException
    {
        List<GamesOwned> result = new ArrayList<GamesOwned>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectByGameIDSQL);
            ps.setLong(1, gameID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GamesOwned go = extractGOFromRS(rs);
                result.add(go);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String selectByPlayerIDSQL = "SELECT id, player_id, game_id, purchase_date, purchase_price FROM games.gamesowned where player_id = ?";
    @Override
    public List<GamesOwned> retrieveByPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	if (playerID == null) {
            throw new DAOException("Trying to retrieve gamesOwned with NULL ID");
        }
    	List<GamesOwned> result = new ArrayList<GamesOwned>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectByPlayerIDSQL);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GamesOwned go = extractGOFromRS(rs);
                result.add(go);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String updateSQL = "UPDATE games.gamesowned SET player_id = ?, game_id = ?, purchase_date = ?, purchase_price = ? WHERE id = ?;";
    
    @Override
    public int update(Connection connection, GamesOwned gamesOwned) throws SQLException, DAOException
    {
    	Long id = gamesOwned.getId();
        if (id == null) {
            throw new DAOException("Trying to update gamesOwned with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setLong(1, gamesOwned.getPlayerID());
            ps.setLong(2, gamesOwned.getGameID());
            ps.setDate(3, new java.sql.Date(gamesOwned.getPurchaseDate().getTime()));
            ps.setFloat(4, gamesOwned.getPurchasePrice());
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

    final static String deleteSQL = "delete from games.gamesowned where id = ?;";
    
    @Override
    public int delete(Connection connection, Long gameOwnedID) throws SQLException, DAOException
    {
    	 if (gameOwnedID == null) {
             throw new DAOException("Trying to delete games owned with NULL ID");
         }

     PreparedStatement ps = null;
     try {
         ps = connection.prepareStatement(deleteSQL);
         ps.setLong(1, gameOwnedID);

         int rows = ps.executeUpdate();
         return rows;
     }
     finally {
         if (ps != null && !ps.isClosed()) {
             ps.close();
         }
     }

    }

    final static String countSQL = "select count(*) from games.gamesowned;";
    
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
    private GamesOwned extractGOFromRS(ResultSet rs) throws SQLException
    {
        GamesOwned go = new GamesOwned();
        go.setId(rs.getLong("id"));
        go.setPlayerID(rs.getLong("player_id"));
        go.setGameID(rs.getLong("game_id"));
        go.setPurchaseDate(rs.getDate("purchase_date"));
        go.setPurchasePrice(rs.getInt("purchase_price"));
        return go;
    }
}
