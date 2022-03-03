public class Todo {
    private final int id;
    private final String name;
    private final String description;
    private final boolean done;

    public Todo(int id, String name, String description, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public Todo(String name, String description, boolean done) {
        this(-1, name, description, done);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
