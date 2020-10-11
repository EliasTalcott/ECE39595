public class Player extends Creature {
    private int hpMoves;

    public Player(int _visible, int _posX, int _posY, int _room, int _serial, int _hp, int _maxHit, int _hpMoves) {
        super(_visible, _posX, _posY, "Player", _room, _serial, '@', _hp, _maxHit);
        hpMoves = _hpMoves;
    }

    public Player(int _posX, int _posY, int _room, int _serial, int _hp, int _maxHit, int _hpMoves) {
        super(_posX, _posY, "Player", _room, _serial, '@', _hp, _maxHit);
        hpMoves = _hpMoves;
    }

    public void setHpMoves(int _hpMoves) { hpMoves = _hpMoves; }

    public int getHpMoves() { return hpMoves; }

    @Override
    public String toString() {
        String str = "Player: \n";
        str += super.toString();
        str += "hpMoves: " + hpMoves + "\n";
        return str;
    }
}
