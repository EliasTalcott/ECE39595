public class Action {
    private String name;
    private String type;
    private String message;
    private int actionIntValue;
    private char actionCharValue;

    public Action(String _name, String _type, String _message, int _actionIntValue, char _actionCharValue) {
        name = _name;
        type = _type;
        message = _message;
        actionIntValue = _actionIntValue;
        actionCharValue = _actionCharValue;
    }

    public void setName(String _name) { name = _name; }

    public String getName() { return name; }

    public void setType(String _type) { type = _type; }

    public String getType() { return type; }

    public void setMessage(String _message) { message = _message; }

    public String getMessage() { return message; }

    public void setActionIntValue(int _actionIntValue) { actionIntValue = _actionIntValue; }

    public int getActionIntValue() { return actionIntValue; }

    public void setActionCharValue(char _actionCharValue) { actionCharValue = _actionCharValue; }

    public char getActionCharValue() { return actionCharValue; }

    @Override
    public String toString() {
        String str = "name: " + name + "\n";
        str += "type: " + type + "\n";
        str += "message: " + message + "\n";
        str += "actionIntValue: " + actionIntValue + "\n";
        str += "actionCharValue: " + actionCharValue + "\n";
        return str;
    }
}
