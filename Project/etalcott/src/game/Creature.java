package game;

import java.util.ArrayList;

public class Creature extends Displayable {
    private String name;
    private int room;
    private int serial;
    private char type;
    private int hp;
    private int maxHit;
    private ArrayList<CreatureAction> actions;

    public Creature(String _name, int _room, int _serial) {
        super();
        name = _name;
        room = _room;
        serial = _serial;
        actions = new ArrayList<>();
    }

    public void setName(String _name) { name = _name; }

    public String getName() { return name; }

    public void setRoom(int _room) { room = _room; }

    public int getRoom() { return room; }

    public void setSerial(int _serial) { serial = _serial; }

    public int getSerial() { return serial; }

    public void setType(char _type) { type = _type; }

    public char getType() { return type; }

    public void setHp(int _hp) { hp = _hp; }

    public int getHp() { return hp; }

    public void setMaxHit(int _maxHit) { maxHit = _maxHit; }

    public int getMaxHit() { return maxHit; }

    public void addAction(CreatureAction action) { actions.add(action); }

    public ArrayList<CreatureAction> getActions() { return actions; }

    @Override
    public String toString() {
        String str = "name: " + name + "\n";
        str += "room: " + room + "\n";
        str += "serial: " + serial + "\n";
        str += super.toString();
        str += "type: " + type + "\n";
        str += "hp: " + hp + "\n";
        str += "maxHit: " + maxHit + "\n";
        str += "actions: " + actions + "\n";
        return str;
    }
}
