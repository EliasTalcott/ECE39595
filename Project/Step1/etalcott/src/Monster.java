public class Monster extends Creature {
    public Monster(int _visible, int _posX, int _posY, String _name, int _room, int _serial, char _type, int _hp, int _maxHit) {
        super(_visible, _posX, _posY, _name, _room, _serial, _type, _hp, _maxHit);
    }

    public Monster(int _posX, int _posY, String _name, int _room, int _serial, char _type, int _hp, int _maxHit) {
        super(_posX, _posY, _name, _room, _serial, _type, _hp, _maxHit);
    }

    @Override
    public String toString() {
        String str = "Monster: \n";
        str += super.toString();
        return str;
    }
}
