import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    
    private static final String URL = "jdbc:mysql://localhost:3306/accounting_app"; // 修改為你的 DB 名稱 e.g jdbc:mysql://140.119.19.73:3315/
    private static final String USER = "yourDbUser";     // DB 帳號
    private static final String PASSWORD = "yourDbPass"; // DB 密碼
    
   
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<String> fetchAllUsers() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT username, password, weeklogin, hasloggedin, totalloggedin, currentweek, currentday FROM users";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String line = rs.getString("username") + "XXXX" +
                              rs.getString("password") + "XXXX" +
                              rs.getString("weeklogin") + "XXXX" +
                              rs.getInt("hasloggedin") + "XXXX" +
                              rs.getInt("totalloggedin") + "XXXX" +
                              rs.getInt("currentweek") + "XXXX" +
                              rs.getInt("currentday");
                list.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void upsertUser(String[] f) {
        String sql = "REPLACE INTO users (username, password, weeklogin, hasloggedin, totalloggedin, currentweek, currentday) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f[0]);
            ps.setString(2, f[1]);
            ps.setString(3, f[2]);
            ps.setInt(4, Integer.parseInt(f[3]));
            ps.setInt(5, Integer.parseInt(f[4]));
            ps.setInt(6, Integer.parseInt(f[5]));
            ps.setInt(7, Integer.parseInt(f[6]));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

