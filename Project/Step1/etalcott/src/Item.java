import javafx.util.Pair;

import java.util.ArrayList;

public class Item extends Displayable {
    private String name;
    private int room;
    private int serial;
    private Pair<Integer, Integer> id;
    private Creature owner = null;
    private ArrayList<ItemAction> actions;

    public Item(String _name, int _room, int _serial) {
        super();
        name = _name;
        room = _room;
        serial = _serial;
        id = new Pair<>(_room, _serial);
        actions = new ArrayList<>();
    }

    public void setName(String _name) { name = _name; }

    public String getName() { return name; }

    public void setRoom(int _room) { room = _room; }

    public int getRoom() { return room; }

    public void setSerial(int _serial) { serial = _serial; }

    public int getSerial() { return serial; }

    public void setId(int _room, int _serial) { id = new Pair<>(_room, _serial); }

    public Pair<Integer, Integer> getId() { return id; }

    public void setOwner(Creature _owner) { owner = _owner; }

    public Creature getOwner() { return owner; }

    public void addAction(ItemAction action) { actions.add(action); }

    public ArrayList<ItemAction> getActions() { return actions; }

    @Override
    public String toString() {
        String str = "name: " + name + "\n";
        str += "id: " + id + "\n";
        str += super.toString();
        str += "owner: " + owner + "\n";
        str += "actions: " + actions + "\n";
        return str;
    }
}
