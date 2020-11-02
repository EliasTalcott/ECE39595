package src;

public class Scroll extends Item {
    public Scroll(String _name, int _room, int _serial) {
        super(_name, _room, _serial);
    }

    @Override
    public String toString() {
        String str = "Scroll: \n";
        str += super.toString();
        return str;
    }
}
