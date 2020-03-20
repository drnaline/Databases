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
package cs4347.jdbcGame.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.util.DAOException;

/**
 * The PlayerDAO exclusively updates the PLAYER table.
 */
public interface PlayerDAO
{
    /**
     * The create method must throw a DAOException if the given Player has a
     * non-null ID.The create method must return the same Player with the ID
     * attribute set to the value set by the application's auto-increment primary
     * key column.
     * 
     * @throws DAOException if the given Player has a non-null id.
     */
    Player create(Connection connection, Player player) throws SQLException, DAOException;

    /**
     * The update method must throw DAOException if the provided ID is null.
     */
    Player retrieve(Connection connection, Long playerID) throws SQLException, DAOException;

    /**
     * The update method must throw DAOException if the provided Player has a NULL
     * id.
     */
    int update(Connection connection, Player player) throws SQLException, DAOException;

    /**
     * The delete method must throw DAOException if the provided ID is null.
     */
    int delete(Connection connection, Long playerID) throws SQLException, DAOException;

    /**
     * Returns the number of players i.e. the number of rows
     */
    int count(Connection connection) throws SQLException, DAOException;

    /**
     * Retrieve players in the given join date range
     */
    List<Player> retrieveByJoinDate(Connection connection, Date start, Date end) throws SQLException, DAOException;

}
