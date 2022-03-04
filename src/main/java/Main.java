public class Main {
    public static void main(String[] args) {
        String databasePath = "jdbc:h2:./data/todo-database";
        TodoDao todoDao = new TodoDao(databasePath);

        //Add todo
        Todo todoToAdd = new Todo("Programming", "Learn about h2 and jdbc connection.", false);
        todoDao.addTodo(todoToAdd);
        System.out.println("A todo was added to the database.");
        System.out.println("---");

        //Get todo by ID
        System.out.println(todoDao.getTodo(1));
        System.out.println("---");

        //Get all todo
        for (Todo todo : todoDao.getAllTodos()) {
            System.out.println(todo);
        }
        System.out.println("---");

        //Update todo
        Todo updatedTodo = new Todo("Programming", "Learn about h2 and jdbc connection.", true);
        todoDao.updateTodo(1, updatedTodo);
        System.out.println("---");

        //Delete todo
        todoDao.deleteTodo(1);
    }
}
