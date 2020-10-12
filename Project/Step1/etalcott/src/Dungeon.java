import java.util.ArrayList;

public class Dungeon {
    private String name;
    private int width;
    private int topHeight;
    private int gameHeight;
    private int bottomHeight;
    private ArrayList<Room> rooms;
    private ArrayList<Passage> passages;

    public Dungeon(String _name, int _width, int _topHeight, int _gameHeight, int _bottomHeight) {
        name = _name;
        width = _width;
        topHeight = _topHeight;
        gameHeight = _gameHeight;
        bottomHeight = _bottomHeight;
        rooms = new ArrayList<>();
        passages = new ArrayList<>();
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setWidth(int _width) { width = _width; }

    public void setTopHeight(int _topHeight) { topHeight = _topHeight; }

    public void setGameHeight(int _gameHeight) { gameHeight = _gameHeight; }

    public void setBottomHeight(int _bottomHeight) { bottomHeight = _bottomHeight; }

    public void addRoom(Room room) { rooms.add(room); }

    public ArrayList<Room> getRooms() { return rooms; }

    public void addPassage(Passage passage) { passages.add(passage); }

    public ArrayList<Passage> getPassages() { return passages; }

    @Override
    public String toString() {
        String str = "Dungeon: \n";
        str += "name: " + name + "\n";
        str += "width: " + width + "\n";
        str += "topHeight: " + topHeight + "\n";
        str += "gameHeight: " + gameHeight + "\n";
        str += "bottomHeight: " + bottomHeight + "\n";
        str += "rooms: " + rooms + "\n";
        str += "passages: " + passages + "\n";
        return str;
    }
}
