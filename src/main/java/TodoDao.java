import java.sql.Connection;
import java.sql.DriverManager;

public class TodoDao {
    private final String databasePath;

    public TodoDao(String databasePath) {
        this.databasePath = databasePath;
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.databasePath);
        } catch (Exception e) {
            System.out.println("Could not establish server connection.");
            System.out.println("Error: " + e);
        }
        return conn;
    }
}
