package src.game;

import src.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;
    private static int WIDTH = 0;
    private static int TOPHEIGHT = 0;
    private static int GAMEHEIGHT = 0;
    private static int BOTTOMHEIGHT = 0;
    private static ArrayList<Room> rooms;
    private static ArrayList<Passage> passages;

    public Rogue(int width, int height) {
        displayGrid = new ObjectDisplayGrid(width, height);
    }

    @Override
    public void run() {
        displayGrid.fireUp();
        // Initialize top, game, and bottom sections
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < TOPHEIGHT; j += 1) {
                displayGrid.addObjectToDisplay(new Char(' '), i, j);
            }
            for (int j = 0; j < GAMEHEIGHT; j += 1) {
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT);
            }
            for (int j = 0; j < BOTTOMHEIGHT; j += 1) {
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT + GAMEHEIGHT);
            }
        }

        // Add rooms to game section
        for (Room room: rooms) {
            // Top and bottom of room
            for (int i = room.getPosX(); i < room.getPosX() + room.getWidth(); i++) {
                displayGrid.addObjectToDisplay(new Char('X'), i, room.getPosY() + TOPHEIGHT);
                displayGrid.addObjectToDisplay(new Char('X'), i, room.getPosY() + room.getHeight() + TOPHEIGHT - 1);
            }
            // Sides of room
            for (int i = room.getPosY(); i < room.getPosY() + room.getHeight(); i++) {
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX(), i + TOPHEIGHT);
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX() + room.getWidth() - 1, i + TOPHEIGHT);
            }
            // Interior of room
            for (int i = room.getPosX() + 1; i < room.getPosX() + room.getWidth() - 1; i++) {
                for (int j = room.getPosY() + 1; j < room.getPosY() + room.getHeight() - 1; j++) {
                    displayGrid.addObjectToDisplay(new Char('.'), i, j + TOPHEIGHT);
                }
            }
        }

        // Add passages to game section
        for (Passage passage: passages) {
            System.out.print(passage);
            ArrayList<Integer> xPos = passage.getPosXList();
            ArrayList<Integer> yPos = passage.getPosYList();
            int len = xPos.size();
            // Iterate through all coordinate pairs
            for (int i = 0; i < len - 1; i++) {
                int x = xPos.get(i);
                int y = yPos.get(i);
                int next_x = xPos.get(i + 1);
                int next_y = yPos.get(i + 1);
                // Make sure next is greater than current
                if (x > next_x) {
                    int temp = x;
                    x = next_x;
                    next_x = temp;
                }
                if (y > next_y) {
                    int temp = y;
                    y = next_y;
                    next_y = temp;
                }
                // Horizontal section
                if (y == next_y) {
                    for (int j = x; j <= next_x; j++) {
                        displayGrid.addObjectToDisplay(new Char('#'), j, y + TOPHEIGHT);
                    }
                }
                // Vertical section
                if (x == next_x) {
                    for (int j = y; j <= next_y; j++) {
                        displayGrid.addObjectToDisplay(new Char('#'), x, j + TOPHEIGHT);
                    }
                }
            }
            // Add pluses at passage termini
            int x = xPos.get(0);
            int y = yPos.get(0);
            displayGrid.addObjectToDisplay(new Char('+'), x, y + TOPHEIGHT);
            x = xPos.get(len - 1);
            y = yPos.get(len - 1);
            displayGrid.addObjectToDisplay(new Char('+'), x, y + TOPHEIGHT);
        }

        while(true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName;
        if (args.length == 1) {
            fileName = "etalcott/xmlFiles/" + args[0];
        }
        else {
            System.out.println("java game.Rogue <xmlFileName>");
            return;
        }

        // Create a saxParserFactory that will allow use to create a parser
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            // Initialize XML Handler
            DungeonXMLHandler handler = new DungeonXMLHandler();
            // Parse the XML file given by fileName
            saxParser.parse(new File(fileName), handler);
            // Get dungeon from XML parser
            Dungeon dungeon = handler.getDungeon();

            // Get dimensions from dungeon
            WIDTH = dungeon.getWidth();
            TOPHEIGHT = dungeon.getTopHeight();
            GAMEHEIGHT = dungeon.getGameHeight();
            BOTTOMHEIGHT = dungeon.getBottomHeight();

            // Get rooms and passages from dungeon
            rooms = dungeon.getRooms();
            passages = dungeon.getPassages();

            Rogue rogue = new Rogue(WIDTH, TOPHEIGHT + GAMEHEIGHT + BOTTOMHEIGHT);
            Thread rogueThread = new Thread(rogue);
            rogueThread.start();

            rogue.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
            rogue.keyStrokePrinter.start();

            rogueThread.join();
            rogue.keyStrokePrinter.join();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
