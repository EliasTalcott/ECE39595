package game;

public class Char extends Displayable {

    public static final String CLASSID = "Char";

    public Char(char c) {
        super();
        setDisplayChar(c);
    }
    
    public char getChar( ) {
        return getDisplayChar();
    }
}
