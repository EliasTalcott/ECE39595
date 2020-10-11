public class CreatureAction extends Action {
    public CreatureAction(String _name, String _type, String _message, int _actionIntValue, char _actionCharValue) {
        super(_name, _type, _message, _actionIntValue, _actionCharValue);
    }

    @Override
    public String toString() {
        String str = "CreatureAction: \n";
        str += super.toString();
        return str;
    }
}
