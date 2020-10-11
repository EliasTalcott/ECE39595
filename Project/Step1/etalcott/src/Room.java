import java.lang.reflect.Array;
import java.util.ArrayList;

public class Room extends Structure {
    private int id;
    private int width;
    private int height;
    private ArrayList<Creature> creatures;
    private ArrayList<Item> items;

    public Room(int _visible, int _posX, int _posY, String _id, int _width, int _height) {
        super(_visible, _posX, _posY);
        id = Integer.parseInt(_id);
        width = _width;
        height = _height;
    }

    public Room(int _posX, int _posY, String _id) {
        super(_posX, _posY);
        id = Integer.parseInt(_id);
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
        return str;
    }
}
