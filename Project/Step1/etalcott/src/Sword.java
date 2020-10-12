import javafx.util.Pair;

public class Sword extends Item {
    public Sword(String _name, int _room, int _serial) {
        super(_name, _room, _serial);
    }

    @Override
    public String toString() {
        String str = "Sword: \n";
        str += super.toString();
        return str;
    }
}
