import javafx.util.Pair;

import java.util.ArrayList;

public class Passage extends Structure {
    private Pair<Integer, Integer> id;
    private ArrayList<Integer> posXList;
    private ArrayList<Integer> posYList;

    public Passage(String _id1, String _id2) {
        super();
        id = new Pair<Integer, Integer>(Integer.parseInt(_id1), Integer.parseInt(_id2));
        posXList = new ArrayList<>();
        posYList = new ArrayList<>();
    }

    public void setId(int _id1, int _id2) { id = new Pair<Integer, Integer>(_id1, _id2); }

    public Pair<Integer, Integer> getId() { return id; }

    public void setPosX(int _posX) {
        super.setPosX(_posX);
        posXList.add(_posX);
    }

    public ArrayList<Integer> getPosXList() { return posXList; }

    public void setPosY(int _posY) {
        super.setPosY(_posY);
        posYList.add(_posY);
    }

    public ArrayList<Integer> getPosYList() { return posYList; }

    @Override
    public String toString() {
        String str = "Passage: \n";
        str += "id: " + id + "\n";
        str += super.toString();
        str += "posXList: " + posXList + "\n";
        str += "posYList: " + posYList + "\n";
        return str;
    }
}
