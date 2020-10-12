import javafx.util.Pair;

public class Armor extends Item {
    public Armor(String _name, int _room, int _serial) {
        super(_name, _room, _serial);
    }

    @Override
    public String toString() {
        String str = "Armor: \n";
        str += super.toString();
        return str;
    }
}
