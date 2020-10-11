public class Item extends Displayable {
    private String name;
    private Creature owner = null;

    public Item(int _visible, int _posX, int _posY, String _name) {
        super(_visible, _posX, _posY);
        name = _name;
    }

    public Item(int _posX, int _posY, String _name) {
        super(_posX, _posY);
        name = _name;
    }

    public void setOwner(Creature _owner) { owner = _owner; }

    public Creature getOwner() { return owner; }

    @Override
    public String toString() {
        String str = "name: " + name + "\n";
        str += super.toString();
        str += "owner: " + owner + "\n";
        return str;
    }
}
