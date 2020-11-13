package game;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private Rogue rogue;

    public KeyStrokePrinter(ObjectDisplayGrid grid, Rogue r) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        rogue = r;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;

        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                } else {
                    if (Arrays.asList('h', 'j', 'k', 'l').contains(ch)) {
                        rogue.move(ch);
                    }
                    else if (ch == 'p') {
                        rogue.pick_up();
                    }
                    else if (ch == 'd') {
                        // Wait for second input and make sure it is an integer
                        while (inputQueue.peek() == null) { ; }
                        char ch2 = inputQueue.poll();
                        try {
                            rogue.drop(Integer.parseInt(String.valueOf(ch2)));
                        }
                        catch (Exception e) {
                            // Make sure the program doesn't end if a non-integer is entered after 'd'
                        }
                    }
                    else if (ch == 'i') {
                        rogue.printInventory();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
}
