import javafx.util.Pair;

public class Scroll extends Item {
    private Pair<Integer, Integer> id;

    public Scroll(int _visible, int _posX, int _posY, String _name, int _room, int _serial) {
        super(_visible, _posX, _posY, _name);
        id = new Pair<>(_room, _serial);
    }

    public Scroll(int _posX, int _posY, String _name, int _room, int _serial) {
        super(_posX, _posY, _name);
        id = new Pair<>(_room, _serial);
    }

    public void setId(int _room, int _serial) { id = new Pair<>(_room, _serial); }

    public Pair<Integer, Integer> getId() { return id; }

    @Override
    public String toString() {
        String str = "Scroll: \n";
        str += "id: " + id + "\n";
        str += super.toString();
        return str;
    }
}
