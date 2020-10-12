import java.util.ArrayList;

public class Room extends Structure {
    private int id;
    private int width;
    private int height;
    private ArrayList<Creature> creatures;
    private ArrayList<Item> items;

    public Room(String _id) {
        super();
        id = Integer.parseInt(_id);
        creatures = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void setId(int _id) { id = _id; }

    public int getId() { return id; }

    public void setWidth(int _width) { width = _width; }

    public int getWidth() { return width; }

    public void setHeight(int _height) { height = _height; }

    public int getHeight() { return height; }

    public void addCreature(Creature creature) { creatures.add(creature); }

    public ArrayList<Creature> getCreatures() { return creatures; }

    public void addItem(Item item) { items.add(item); }

    public ArrayList<Item> getItems() { return items; }

    @Override
    public String toString() {
        String str = "Room: \n";
        str += "id: " + id + "\n";
        str += super.toString();
        str += "width: " + width + "\n";
        str += "height: " + height + "\n";
        str += "creatures: " + creatures + "\n";
        str += "items: " + items + "\n";
        return str;
    }
}
