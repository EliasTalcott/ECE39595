package src;

public class CreatureAction extends Action {
    public CreatureAction(String _name, String _type) {
        super(_name, _type);
    }

    @Override
    public String toString() {
        String str = "CreatureAction: \n";
        str += super.toString();
        return str;
    }
}
