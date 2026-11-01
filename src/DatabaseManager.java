package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class DatabaseManager {
    private final String DB_URL = "jdbc:sqlite:football.db";
    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }

    public void createTables() throws SQLException {
        String players = "CREATE TABLE IF NOT EXISTS players (" +
                "player_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, height REAL, " +
                "weight REAL, age INTEGER, speed REAL, agility INTEGER)";
        String sessions = "CREATE TABLE IF NOT EXISTS sessions (" +
                "session_id INTEGER PRIMARY KEY AUTOINCREMENT, player_id INTEGER, " +
                "date TEXT, bmi REAL, FOREIGN KEY (player_id) REFERENCES players(player_id))";
        Statement st = connection.createStatement();
        st.execute(players);
        st.execute(sessions);
        st.close();
    }

    public void savePlayer(Player p) throws SQLException {
        String sql = "INSERT INTO players(name, height, weight, age, speed, agility) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getHeight());
        ps.setDouble(3, p.getWeight());
        ps.setInt(4, p.getAge());
        ps.setDouble(5, p.getSpeed());
        ps.setInt(6, p.getAgility());
        ps.executeUpdate();
        ps.close();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
        if (rs.next()) p.setPlayerId(rs.getInt(1));
        rs.close();
        st.close();
    }

    public Player getPlayer(int id) throws SQLException {
        String sql = "SELECT * FROM players WHERE player_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Player p = null;
        if (rs.next()) {
            p = new Player(rs.getInt("player_id"), rs.getString("name"),
                    rs.getDouble("height"), rs.getDouble("weight"),
                    rs.getInt("age"), rs.getDouble("speed"), rs.getInt("agility"));
        }
        rs.close();
        ps.close();
        return p;
    }

    public List<Player> getAllPlayers() throws SQLException {
        String sql = "SELECT * FROM players";
        List<Player> players = new ArrayList<Player>();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Player p = new Player(rs.getInt("player_id"), rs.getString("name"),
                    rs.getDouble("height"), rs.getDouble("weight"),
                    rs.getInt("age"), rs.getDouble("speed"), rs.getInt("agility"));
            players.add(p);
        }
        rs.close();
        ps.close();
        return players;
    }

    public void saveSession(Session s) throws SQLException {
        String sql = "INSERT INTO sessions(player_id, date, bmi) VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, s.getPlayerId());
        ps.setString(2, s.getDate());
        ps.setDouble(3, s.getBmi());
        ps.executeUpdate();
        ps.close();
    }

    public List<Session> getSessions(int playerId) throws SQLException {
        String sql = "SELECT * FROM sessions WHERE player_id = ?";
        List<Session> sessions = new ArrayList<Session>();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, playerId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Session s = new Session(rs.getInt("session_id"), rs.getInt("player_id"),
                    rs.getString("date"), rs.getDouble("bmi"));
            sessions.add(s);
        }
        rs.close();
        ps.close();
        return sessions;
    }
}
