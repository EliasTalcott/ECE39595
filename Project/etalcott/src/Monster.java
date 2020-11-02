package src;

public class Monster extends Creature {
    public Monster(String _name, int _room, int _serial) {
        super(_name, _room, _serial);
    }

    @Override
    public String toString() {
        String str = "Monster: \n";
        str += super.toString();
        return str;
    }
}
