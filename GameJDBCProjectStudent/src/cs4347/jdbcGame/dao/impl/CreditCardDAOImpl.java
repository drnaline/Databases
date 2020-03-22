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

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.util.DAOException;

public class CreditCardDAOImpl implements CreditCardDAO
{
    private static final String insertSQL = "INSERT INTO creditcard (cc_name, cc_number, exp_date, security_code, player_id) VALUES (?,?,?,?,?);";

    @Override
    public CreditCard create(Connection connection, CreditCard creditCard, Long playerID)
            throws SQLException, DAOException
    {
        if (creditCard.getId() != null) {
            throw new DAOException("Trying to insert CreditCard with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, creditCard.getCcName());
            ps.setString(2, creditCard.getCcNumber());
            ps.setString(3, creditCard.getExpDate());
            ps.setInt(4, creditCard.getSecurityCode());
            ps.setLong(5, playerID);
            ps.executeUpdate();
            
            
            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            creditCard.setId((long) lastKey);
            creditCard.setPlayerID(playerID);
            return creditCard;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String selectSQL = "SELECT id, player_id, cc_name, cc_number, security_code, exp_date FROM games.creditcard where id = ?;";

    @Override
    public CreditCard retrieve(Connection connection, Long ccID) throws SQLException, DAOException
    {
    	if (ccID == null) {
            throw new DAOException("Trying to retrieve CreditCard with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectSQL);
            ps.setLong(1, ccID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            CreditCard cc = extractCCFromRS(rs);
            return cc;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String selectforplayerSQL = "SELECT id, player_id, cc_name, cc_number, security_code, exp_date FROM games.creditcard where player_id = ?;";

    
    @Override
    public List<CreditCard> retrieveCreditCardsForPlayer(Connection connection, Long playerID)
            throws SQLException, DAOException
    {
    	if (playerID == null) {
            throw new DAOException("Trying to retrieve PlayerID with NULL ID");
        }
    	List<CreditCard> result = new ArrayList<CreditCard>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectforplayerSQL);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CreditCard cc = extractCCFromRS(rs);
                result.add(cc);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
  
    }
    
    final static String updateSQL = "UPDATE creditcard SET cc_name = ?, cc_number = ?, exp_date = ?, security_code = ? WHERE id = ?;";
    
    @Override
    public int update(Connection connection, CreditCard creditCard) throws SQLException, DAOException
    {
    	Long id = creditCard.getId();
        if (id == null) {
            throw new DAOException("Trying to update Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setString(1, creditCard.getCcName());
            ps.setString(2, creditCard.getCcNumber());
            ps.setString(3, creditCard.getExpDate());
            ps.setLong(4, creditCard.getSecurityCode());
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

    final static String deleteSQL = "delete from games.creditcard where id = ?;";

    @Override
    public int delete(Connection connection, Long ccID) throws SQLException, DAOException
    {
        if (ccID == null) {
            throw new DAOException("Trying to delete Credit Card with NULL ID");
        }

    PreparedStatement ps = null;
    try {
        ps = connection.prepareStatement(deleteSQL);
        ps.setLong(1, ccID);

        int rows = ps.executeUpdate();
        return rows;
    }
    finally {
        if (ps != null && !ps.isClosed()) {
            ps.close();
        }
    }

}
    final static String deleteForPlayerSQL = "DELETE FROM creditcard WHERE PLAYER_ID = player.id";
   
    @Override
    public int deleteForPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
        
        if(playerID == null){
            throw new DAOException("Attempting to delete credit card with NULL PLAYER ID");
        }
        PreparedStatement pStatement = null;
        try{
            pStatement = connection.prepareStatement(deleteForPlayerSQL);
            pStatement.setLong(1, playerID);
            pStatement.executeUpdate();
        }
        finally{
            if(pStatement != null && !pStatement.isClosed()){
                pStatement.close();
            }
        }
        return 0;
    }

    final static String countSQL = "select count(*) from creditcard";
    
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
    private CreditCard extractCCFromRS(ResultSet rs) throws SQLException
    {
        CreditCard cc = new CreditCard();
        cc.setId(rs.getLong("id"));
        cc.setPlayerID(rs.getLong("player_id"));
        cc.setCcName(rs.getString("cc_name"));
        cc.setCcNumber(rs.getString("cc_number"));
        cc.setSecurityCode(rs.getInt("security_code"));
        cc.setExpDate(rs.getString("exp_date"));
        
        return cc;
    }

}
