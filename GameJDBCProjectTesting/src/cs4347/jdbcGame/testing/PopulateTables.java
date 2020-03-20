/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of every team member to the Provost Office for academic 
 * dishonesty. 
 */

package cs4347.jdbcGame.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.GamesOwnedDAO;
import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.dao.impl.CreditCardDAOImpl;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.dao.impl.GamesOwnedDAOImpl;
import cs4347.jdbcGame.dao.impl.GamesPlayedDAOImpl;
import cs4347.jdbcGame.dao.impl.PlayerDAOImpl;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.entity.Player;

public class PopulateTables
{
    private File playerFile;
    private File ccardFile;
    private File gameFile;

    private void initialize()
    {
        playerFile = new File("csvData/players.csv");
        ccardFile = new File("csvData/creditCards.csv");
        gameFile = new File("csvData/games.csv");
    }

    public static void main(String args[])
    {
        try {
            DataSource dataSource = DataSourceManager.getDataSource();
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PopulateTables app = new PopulateTables();
            app.initialize();

            Map<Long, Player> playersMap = app.buildPlayers();
            app.addCreditCards(playersMap);
            System.out.println("Finished building players: " + playersMap.size());
            app.insertPlayers(connection, playersMap);
            System.out.println("Finished inserting players");

            Map<Long, Game> gamesMap = app.buildGames();
            System.out.println("Finished building games: " + gamesMap.size());
            app.insertGames(connection, gamesMap);
            System.out.println("Finished inserting games");

            Player[] players = (Player[]) playersMap.values().toArray(new Player[0]);
            Game[] games = (Game[]) gamesMap.values().toArray(new Game[0]);

            List<GamesOwned> gamesOwned = app.buildGamesOwned(players, games);
            app.insertGamesOwned(connection, gamesOwned);
            System.out.println("Finished inserting gamesOwned");

            List<GamesPlayed> gamesPlayed = app.buildGamesPlayed(players, games);
            app.insertGamesPlayed(connection, gamesPlayed);
            System.out.println("Finished inserting gamesPlayed");

            connection.commit();
            System.out.println("Finished Initializing Database");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Random rnGen = new Random();

    private List<GamesOwned> buildGamesOwned(Player[] players, Game[] games)
    {

        List<GamesOwned> gamesOwned = new ArrayList<GamesOwned>();

        for (Player player : players) {
            for (int idx2 = 0; idx2 < 3; idx2++) {
                Game game = games[rnGen.nextInt(games.length)];
                GamesOwned go = buildGamesOwned(player, game);
                gamesOwned.add(go);
            }
        }
        return gamesOwned;
    }

    private List<GamesPlayed> buildGamesPlayed(Player[] players, Game[] games)
    {

        List<GamesPlayed> gamesPlayed = new ArrayList<GamesPlayed>();

        for (Player player : players) {
            for (int idx2 = 0; idx2 < 3; idx2++) {
                Game game = games[rnGen.nextInt(games.length)];
                GamesPlayed gp = buildGamesPlayed(player, game);
                gamesPlayed.add(gp);
            }
        }
        return gamesPlayed;
    }

    private GamesPlayed buildGamesPlayed(Player player, Game game)
    {
        GamesPlayed gp = new GamesPlayed();
        gp.setGameID(game.getId());
        gp.setPlayerID(player.getId());
        gp.setScore(rnGen.nextInt(100));
        gp.setTimeFinished(player.getJoinDate());
        return gp;
    }

    private GamesOwned buildGamesOwned(Player player, Game game)
    {
        GamesOwned go = new GamesOwned();
        go.setGameID(game.getId());
        go.setPlayerID(player.getId());
        go.setPurchaseDate(game.getReleaseDate());
        go.setPurchasePrice(rnGen.nextFloat() * 100f);
        return go;
    }

    private Map<Long, Game> buildGames() throws Exception
    {
        Map<Long, Game> gameMap = new HashMap<Long, Game>();
        FileReader fr = new FileReader(gameFile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            Object item[] = parseGame(line);
            gameMap.put((Long) item[0], (Game) item[1]);
        }
        br.close();
        return gameMap;
    }

    SimpleDateFormat gameSDF = new SimpleDateFormat("MM/dd/yyyy");

    private Object[] parseGame(String line) throws ParseException
    {
        StringTokenizer st = new StringTokenizer(line, ",");

        Game product = new Game();
        Long id = Long.parseLong(st.nextToken());
        product.setTitle(st.nextToken());
        product.setDescription(st.nextToken());
        product.setReleaseDate(gameSDF.parse(st.nextToken()));
        product.setVersion(st.nextToken());

        Object[] result = { id, product };
        return result;
    }

    private Map<Long, Player> buildPlayers() throws Exception
    {
        Map<Long, Player> playerMap = new HashMap<Long, Player>();
        FileReader fr = new FileReader(playerFile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            Object item[] = parsePlayer(line);
            playerMap.put((Long) item[0], (Player) item[1]);
        }
        br.close();
        return playerMap;
    }

    private void addCreditCards(Map<Long, Player> playerMap) throws Exception
    {
        FileReader fr = new FileReader(ccardFile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            Object items[] = parseCreditCard(line);
            Player player = playerMap.get(items[0]);
            player.getCreditCards().add((CreditCard) items[1]);
        }
        br.close();
    }

    SimpleDateFormat playerSDF = new SimpleDateFormat("MM/dd/yyyy");

    private Object[] parsePlayer(String line) throws ParseException
    {
        StringTokenizer st = new StringTokenizer(line, ",");
        Player player = new Player();
        Long id = Long.parseLong(st.nextToken());
        player.setFirstName(st.nextToken());
        player.setLastName(st.nextToken());
        Date joinDate = new java.sql.Date(playerSDF.parse(st.nextToken()).getTime());
        player.setJoinDate(joinDate);
        player.setEmail(st.nextToken());

        Object[] result = { id, player };
        return result;
    }

    private Object[] parseCreditCard(String line) throws Exception
    {
        StringTokenizer st = new StringTokenizer(line, ",");

        CreditCard ccard = new CreditCard();
        ccard.setCcName(st.nextToken());
        ccard.setCcNumber(st.nextToken());
        ccard.setExpDate(st.nextToken());
        Integer secCode = Integer.valueOf(st.nextToken());
        ccard.setSecurityCode(secCode);

        Long id = Long.parseLong(st.nextToken());
        return new Object[] { id, ccard };
    }

    private void insertPlayers(Connection connection, Map<Long, Player> playerMap) throws Exception
    {
        PlayerDAO playerDAO = new PlayerDAOImpl();
        CreditCardDAO ccDAO = new CreditCardDAOImpl();
        for (Player player : playerMap.values()) {
            playerDAO.create(connection, player);

            for (CreditCard ccard : player.getCreditCards()) {
                ccDAO.create(connection, ccard, player.getId());
            }
        }
    }

    private void insertGames(Connection connection, Map<Long, Game> gameMap) throws Exception
    {
        GameDAO gameDAO = new GameDAOImpl();
        for (Game game : gameMap.values()) {
            gameDAO.create(connection, game);
        }
    }

    private void insertGamesPlayed(Connection connection, List<GamesPlayed> gamesPlayed) throws Exception
    {
        GamesPlayedDAO gamesplayedDAO = new GamesPlayedDAOImpl();
        for (GamesPlayed gp : gamesPlayed) {
            gamesplayedDAO.create(connection, gp);
        }
    }

    private void insertGamesOwned(Connection connection, List<GamesOwned> gamesOwned) throws Exception
    {
        GamesOwnedDAO gamesownedDAO = new GamesOwnedDAOImpl();
        for (GamesOwned go : gamesOwned) {
            gamesownedDAO.create(connection, go);
        }
    }
}
