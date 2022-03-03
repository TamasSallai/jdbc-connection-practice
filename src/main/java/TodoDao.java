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
            PreparedStatement stmt = conn.prepareStatement("insert into todo (name, description, done) values (?,?,?)");
            stmt.setString(1, todo.getName());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isDone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not execute statement to add todo.");
            System.out.println("Error: " + e);
        }
    }

    public Todo getTodo(int id) {
        Todo todo = null;
        try (Connection conn = createConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from todo where id = ?");
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
            System.out.println("Could not execute statement to query a single todo record!");
            System.out.println("Error: " + e);
        }
        return todo;
    }

    public ArrayList<Todo> getAllTodos() {
        ArrayList<Todo> todos = null;
        try (Connection conn = createConnection()) {
            todos = new ArrayList<>();
            ResultSet resultSet = conn.createStatement().executeQuery("select * from todo");
            while (resultSet.next()) {
                todos.add(new Todo(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("done")));
            }
        } catch (SQLException e) {
            System.out.println("Could not execute statement to query the records from the todo table!");
            System.out.println("Error: " + e);
        }
        return todos;
    }

    public void updateTodo(int id, Todo modifiedTodo) {
        try (Connection conn = createConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update todo set name = ?, description = ?, done = ? where id = ?");
            stmt.setString(1, modifiedTodo.getName());
            stmt.setString(2, modifiedTodo.getDescription());
            stmt.setBoolean(3, modifiedTodo.isDone());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not execute statement to update a todo!");
            System.out.println("Error: " + e);
        }
    }

    public void deleteTodo(int id) {
        try (Connection conn = createConnection()) {
            PreparedStatement stmt = conn.prepareStatement("delete from todo where id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not delete record from the table todo!");
            System.out.println("Error: " + e);
        }
    }

    private Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(this.databasePath);
        try {
            conn.prepareStatement("create table if not exists todo (id int auto_increment primary key, name varchar(255) not null, description varchar(255), done boolean not null)").execute();
        } catch (SQLException e) {
            System.out.println("Could not establish server connection.");
            System.out.println("Error: " + e);
        }
        return conn;
    }
}
