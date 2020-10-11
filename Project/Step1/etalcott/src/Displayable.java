public class Displayable {
    private int visible;
    private int posX;
    private int posY;

    public Displayable(int _visible, int _posX, int _posY) {
        visible = _visible;
        posX = _posX;
        posY = _posY;
    }

    public Displayable(int _posX, int _posY) {
        visible = 1;
        posX = _posX;
        posY = _posY;
    }

    public void setInvisible() { visible = 0; }

    public void setVisible() { visible = 1; }

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
