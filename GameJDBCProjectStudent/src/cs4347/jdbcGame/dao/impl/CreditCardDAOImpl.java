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
import java.util.List;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.util.DAOException;

public class CreditCardDAOImpl implements CreditCardDAO
{
    private static final String insertSQL = "INSERT INTO creditcard(cc_name, cc_number, exp_date, security_code, player_id) "
            + "VALUES(?,?,?,?,?);";

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
            return creditCard;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }
    final static String selectSQL = "SELECT player.id,creditcard.id, player_id, cc_name, cc_number, security_code, exp_code FROM player, creditcard where player_id = ?";

    @Override
    public CreditCard retrieve(Connection connection, Long ccID) throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public List<CreditCard> retrieveCreditCardsForPlayer(Connection connection, Long playerID)
            throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public int update(Connection connection, CreditCard creditCard) throws SQLException, DAOException
    {
        return 0;
    }

    @Override
    public int delete(Connection connection, Long ccID) throws SQLException, DAOException
    {
        return 0;
    }

    @Override
    public int deleteForPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
        return 0;
    }

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
        return 0;
    }

}
