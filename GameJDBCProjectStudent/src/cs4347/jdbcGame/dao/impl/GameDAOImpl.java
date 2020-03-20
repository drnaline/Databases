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
import java.util.Date;
import java.util.List;

import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.util.DAOException;

public class GameDAOImpl implements GameDAO
{

    private static final String insertSQL = "INSERT INTO game (title, description, release_date, version) VALUES (?, ?, ?, ?);";

    @Override
    public Game create(Connection connection, Game game) throws SQLException, DAOException
    {
        if (game.getId() != null) {
            throw new DAOException("Trying to insert Game with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, game.getTitle());
            ps.setString(2, game.getDescription());
            ps.setDate(3, new java.sql.Date(game.getReleaseDate().getTime()));
            ps.setString(4, game.getVersion());
            ps.executeUpdate();

            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            game.setId((long) lastKey);
            return game;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String selectSQL = "SELECT id, title, description, release_date, version FROM game where id = ?";

    @Override
    public Game retrieve(Connection connection, Long gameID) throws SQLException, DAOException
    {
        if (gameID == null) {
            throw new DAOException("Trying to retrieve Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectSQL);
            ps.setLong(1, gameID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Game game = extractFromRS(rs);
            return game;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String updateSQL = "UPDATE game SET title = ?, description = ?, release_date = ?, version = ? WHERE id = ?;";

    @Override
    public int update(Connection connection, Game game) throws SQLException, DAOException
    {
        Long id = game.getId();
        if (id == null) {
            throw new DAOException("Trying to update Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setString(1, game.getTitle());
            ps.setString(2, game.getDescription());
            ps.setDate(3, new java.sql.Date(game.getReleaseDate().getTime()));
            ps.setString(4, game.getVersion());
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

    final static String deleteSQL = "delete from game where id = ?;";

    @Override
    public int delete(Connection connection, Long id) throws SQLException, DAOException
    {
        if (id == null) {
            throw new DAOException("Trying to delete Game with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSQL);
            ps.setLong(1, id);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String countSQL = "select count(*) from game";

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

    final static String retrieveByTitleSQL = "select id,title,description,release_date,version from game where title like ?";

    @Override
    public List<Game> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException
    {
        List<Game> result = new ArrayList<Game>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveByTitleSQL);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Game game = extractFromRS(rs);
                result.add(game);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String retrieveByRelDate = "select id,title,description,release_date,version from game where release_date between ? and ?";

    @Override
    public List<Game> retrieveByReleaseDate(Connection connection, Date start, Date end)
            throws SQLException, DAOException
    {
        List<Game> result = new ArrayList<Game>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveByRelDate);
            ps.setDate(1, new java.sql.Date(start.getTime()));
            ps.setDate(2, new java.sql.Date(end.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Game game = extractFromRS(rs);
                result.add(game);
            }
            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    private Game extractFromRS(ResultSet rs) throws SQLException
    {
        Game game = new Game();
        game.setId(rs.getLong("id"));
        game.setTitle(rs.getString("title"));
        game.setDescription(rs.getString("description"));
        game.setReleaseDate(rs.getDate("release_date"));
        game.setVersion(rs.getString("version"));
        return game;
    }
}
