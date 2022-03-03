import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TodoDao {
    private final String databasePath;

    public TodoDao(String databasePath) {
        this.databasePath = databasePath;
    }

    public void addTodo(Todo todo) {
        try (Connection conn = createConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into Todo (name, description, done) values (?,?,?)");
            stmt.setString(1, todo.getName());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isDone());
            stmt.executeUpdate();
            System.out.println("A record added to the table.");
        } catch (SQLException e) {
            System.out.println("Could not prepare statement to add Todo.");
            System.out.println("Error: " + e);
        }
    }

    public Todo getTodo(int id) {
        Todo todo = null;
        try (Connection conn = createConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from Todo where id = ?");
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                todo = new Todo(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("done"));
            }
        } catch (Exception e) {
            System.out.println("Could not prepare statement to query a single Todo record!");
            System.out.println("Error: " + e);
        }
        return todo;
    }

    public ArrayList<Todo> getAllTodos() {
        ArrayList<Todo> todos = null;
        try (Connection conn = createConnection()) {
            todos = new ArrayList<>();
            ResultSet resultSet = conn.createStatement().executeQuery("select * from Todo");
            while (resultSet.next()) {
                todos.add(new Todo(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("done")));
            }
        } catch (SQLException e) {
            System.out.println("Could not prepare statement to query the records from the Todo table!");
            System.out.println("Error: " + e);
        }
        return todos;
    }

    private Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(this.databasePath);
        try {
            conn.prepareStatement("create table if not exists Todo (id int auto_increment primary key, name varchar(255) not null, description varchar(255), done boolean not null)").execute();
            System.out.println("A connection created.");
        } catch (SQLException e) {
            System.out.println("Could not establish server connection.");
            System.out.println("Error: " + e);
        }
        return conn;
    }
}
