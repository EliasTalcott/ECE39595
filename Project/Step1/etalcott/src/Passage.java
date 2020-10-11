import javafx.util.Pair;

public class Passage extends Structure {
    private Pair<Integer, Integer> id;

    public Passage(int _visible, int _posX, int _posY, String _id1, String _id2) {
        super(_visible, _posX, _posY);
        id = new Pair<Integer, Integer>(Integer.parseInt(_id1), Integer.parseInt(_id2));
    }

    public Passage(int _posX, int _posY, String _id1, String _id2) {
        super(_posX, _posY);
        id = new Pair<Integer, Integer>(Integer.parseInt(_id1), Integer.parseInt(_id2));
    }

    public void setId(int _id1, int _id2) { id = new Pair<Integer, Integer>(_id1, _id2); }

    public Pair<Integer, Integer> getId() { return id; }

    @Override
    public String toString() {
        String str = "Passage: \n";
        str += "id: " + id + "\n";
        str += super.toString();
        return str;
    }
}
