package game;

public class Player extends Creature {
    private int hpMoves;
    private Sword weapon;
    private Armor armor;

    public Player(int _room, int _serial) {
        super("Player", _room, _serial);
        setDisplayChar('@');
    }

    public void setHpMoves(int _hpMoves) { hpMoves = _hpMoves; }

    public int getHpMoves() { return hpMoves; }

    public void setWeapon(Sword _sword) { weapon = _sword; }

    public Sword getWeapon() { return weapon; }

    public void setArmor(Armor _armor) { armor = _armor; }

    public Armor getArmor() { return armor; }

    @Override
    public String toString() {
        String str = "Player: \n";
        str += super.toString();
        str += "hpMoves: " + hpMoves + "\n";
        return str;
    }
}
