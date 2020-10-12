public class ItemAction extends Action {
    public ItemAction(String _name, String _type) {
        super(_name, _type);
    }

    @Override
    public String toString() {
        String str = "ItemAction: \n";
        str += super.toString();
        return str;
    }
}
