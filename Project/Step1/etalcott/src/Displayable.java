package src;

public class Displayable {
    private int visible = 1;
    private int posX;
    private int posY;

    public Displayable() { }

    public void setVisible(int _visible) { visible = _visible; }

    public int getVisible() { return visible; }

    public void setPosX(int _posX) { posX = _posX; }

    public int getPosX() { return posX; }

    public void setPosY(int _posY) { posY = _posY; }

    public int getPosY() { return posY; }

    @Override
    public String toString() {
        String str = "visible: " + visible + "\n";
        str += "posX: " + posX + "\n";
        str += "posY: " + posY + "\n";
        return str;
    }
}
