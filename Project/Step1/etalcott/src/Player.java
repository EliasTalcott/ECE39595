public class Player extends Creature {
    private int hpMoves;

    public Player(int _room, int _serial) {
        super("Player", _room, _serial);
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
