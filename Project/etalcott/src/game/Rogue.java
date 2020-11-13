package game;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private static ObjectDisplayGrid displayGrid;
    private Thread keyStrokePrinter;
    private static int WIDTH = 0;
    private static int TOPHEIGHT = 0;
    private static int GAMEHEIGHT = 0;
    private static int BOTTOMHEIGHT = 0;
    private static int INVENTORYHEIGHT = 0;
    private static int PLAYER_X;
    private static int PLAYER_Y;
    private static int score = 0;
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static ArrayList<Room> rooms;
    private static ArrayList<Passage> passages;
    private Stack<Displayable>[][] objectGrid = null;

    public Rogue(int width, int height) {
        displayGrid = new ObjectDisplayGrid(width, height);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        // Initialize objectGrid
        objectGrid = (Stack<Displayable>[][]) new Stack[WIDTH][TOPHEIGHT + GAMEHEIGHT + BOTTOMHEIGHT];

        // Initialize top, game, and bottom sections
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < TOPHEIGHT; j += 1) {
                objectGrid[i][j] = new Stack();
                objectGrid[i][j].push(new Char(' '));
                objectGrid[i][j].push(new Char(' '));
                displayGrid.addObjectToDisplay(new Char(' '), i, j);
            }
            for (int j = 0; j < GAMEHEIGHT; j += 1) {
                objectGrid[i][j + TOPHEIGHT] = new Stack();
                objectGrid[i][j + TOPHEIGHT].push(new Char(' '));
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT);
            }
            for (int j = 0; j < BOTTOMHEIGHT; j += 1) {
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT] = new Stack();
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT].push(new Char(' '));
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT].push(new Char(' '));
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT + GAMEHEIGHT);
            }
        }
        // Initialize info section
        setMessageRow("Info: ", TOPHEIGHT + GAMEHEIGHT + INVENTORYHEIGHT);

        // Add rooms to game section
        for (Room room: rooms) {
            // Top and bottom of room
            for (int i = room.getPosX(); i < room.getPosX() + room.getWidth(); i++) {
                objectGrid[i][room.getPosY() + TOPHEIGHT].push(new Char('X'));
                objectGrid[i][room.getPosY() + room.getHeight() + TOPHEIGHT - 1].push(new Char('X'));
                displayGrid.addObjectToDisplay(new Char('X'), i, room.getPosY() + TOPHEIGHT);
                displayGrid.addObjectToDisplay(new Char('X'), i, room.getPosY() + room.getHeight() + TOPHEIGHT - 1);
            }
            // Sides of room
            for (int i = room.getPosY(); i < room.getPosY() + room.getHeight(); i++) {
                objectGrid[room.getPosX()][i + TOPHEIGHT].push(new Char('X'));
                objectGrid[room.getPosX() + room.getWidth() - 1][i + TOPHEIGHT].push(new Char('X'));
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX(), i + TOPHEIGHT);
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX() + room.getWidth() - 1, i + TOPHEIGHT);
            }
            // Interior of room
            for (int i = room.getPosX() + 1; i < room.getPosX() + room.getWidth() - 1; i++) {
                for (int j = room.getPosY() + 1; j < room.getPosY() + room.getHeight() - 1; j++) {
                    objectGrid[i][j + TOPHEIGHT].push(new Char('.'));
                    displayGrid.addObjectToDisplay(new Char('.'), i, j + TOPHEIGHT);
                }
            }

            // Items in room
            ArrayList<Item> items = room.getItems();
            for (Item item: items) {
                int x = item.getPosX();
                int y = item.getPosY();
                if (x > 0 && x < room.getWidth() - 1 && y > 0 && y < room.getHeight() - 1) {
                    objectGrid[room.getPosX() + item.getPosX()][room.getPosY() + item.getPosY() + TOPHEIGHT].push(item);
                    if (item.getName().contains("Scroll")) {
                        displayGrid.addObjectToDisplay(new Char('?'), room.getPosX() + item.getPosX(), room.getPosY() + item.getPosY() + TOPHEIGHT);
                    } else if (item.getName().contains("Sword")) {
                        displayGrid.addObjectToDisplay(new Char(')'), room.getPosX() + item.getPosX(), room.getPosY() + item.getPosY() + TOPHEIGHT);
                    } else if (item.getName().contains("Armor")) {
                        displayGrid.addObjectToDisplay(new Char(']'), room.getPosX() + item.getPosX(), room.getPosY() + item.getPosY() + TOPHEIGHT);
                    }
                }
            }

            // Creatures in room
            ArrayList<Creature> creatures = room.getCreatures();
            for (Creature creature: creatures) {
                int x = creature.getPosX();
                int y = creature.getPosY();
                if (x > 0 && x < room.getWidth() - 1 && y > 0 && y < room.getHeight() - 1) {
                    objectGrid[room.getPosX() + creature.getPosX()][room.getPosY() + creature.getPosY() + TOPHEIGHT].push(creature);
                    if (creature.getName().equalsIgnoreCase("Player")) {
                        displayGrid.addObjectToDisplay(new Char('@'), room.getPosX() + creature.getPosX(), room.getPosY() + creature.getPosY() + TOPHEIGHT);
                        PLAYER_X = room.getPosX() + creature.getPosX();
                        PLAYER_Y = room.getPosY() + creature.getPosY() + TOPHEIGHT;
                        printHpScore(creature.getHp());
                    } else {
                        displayGrid.addObjectToDisplay(new Char(creature.getDisplayChar()), room.getPosX() + creature.getPosX(), room.getPosY() + creature.getPosY() + TOPHEIGHT);
                    }
                }
            }
        }

        // Add passages to game section
        for (Passage passage: passages) {
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
                        objectGrid[j][y + TOPHEIGHT].push(new Char('#'));
                        displayGrid.addObjectToDisplay(new Char('#'), j, y + TOPHEIGHT);
                    }
                }
                // Vertical section
                if (x == next_x) {
                    for (int j = y; j <= next_y; j++) {
                        objectGrid[x][j + TOPHEIGHT].push(new Char('#'));
                        displayGrid.addObjectToDisplay(new Char('#'), x, j + TOPHEIGHT);
                    }
                }
            }
            // Add pluses at passage termini
            int x = xPos.get(0);
            int y = yPos.get(0);
            objectGrid[x][y + TOPHEIGHT].push(new Char('+'));
            displayGrid.addObjectToDisplay(new Char('+'), x, y + TOPHEIGHT);
            x = xPos.get(len - 1);
            y = yPos.get(len - 1);
            objectGrid[x][y + TOPHEIGHT].push(new Char('+'));
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

    public void move(char ch) {
        int new_x = PLAYER_X;
        int new_y = PLAYER_Y;

        // Move up
        if (ch == 'k') { new_y -= 1; }
        // Move down
        else if (ch == 'j') { new_y += 1; }
        // Move left
        else if (ch == 'h') { new_x -= 1; }
        // Move right
        else if (ch == 'l') { new_x += 1; }
        // Invalid move
        else return;

        // Move available
        if (Arrays.asList('.', '+', '#', ')', ']', '?').contains(objectGrid[new_x][new_y].peek().getDisplayChar())) {
            // Move player in objectGrid
            Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].pop();
            objectGrid[new_x][new_y].push(player);
            // Update display
            displayGrid.addObjectToDisplay(new Char(objectGrid[PLAYER_X][PLAYER_Y].peek().getDisplayChar()), PLAYER_X, PLAYER_Y);
            displayGrid.addObjectToDisplay(new Char(objectGrid[new_x][new_y].peek().getDisplayChar()), new_x, new_y);
            // Update player location
            PLAYER_X = new_x;
            PLAYER_Y = new_y;
        }
        // Attack available
        if (Arrays.asList('T', 'H', 'S').contains(objectGrid[new_x][new_y].peek().getDisplayChar())) {
            Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
            attack(player, new_x, new_y);
        }
    }

    public void pick_up() {
        // Remove player from stack and look beneath for an item
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].pop();
        if (objectGrid[PLAYER_X][PLAYER_Y].peek() instanceof Item) {
            // Pick up thing and add to inventory
            Item item = (Item) objectGrid[PLAYER_X][PLAYER_Y].pop();
            inventory.add(item);
        }
        // Put player back on top of stack
        objectGrid[PLAYER_X][PLAYER_Y].push(player);
    }

    public void drop(int item) {
        // Drop if item exists
        if (inventory.size() >= item) {
            // Remove player from stack
            Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].pop();
            // Put item on floor and remove from inventory
            objectGrid[PLAYER_X][PLAYER_Y].add(inventory.remove(item - 1));
            // Put player back on stack
            objectGrid[PLAYER_X][PLAYER_Y].push(player);
        }
    }

    public void attack(Player player, int x, int y) {
        Monster monster = null;

        // Find opponent to attack
        for (Displayable thing : objectGrid[x][y]) {
            if (thing instanceof Monster) {
                monster = (Monster) thing;
                break;
            }
        }

        // Attack that boi
        if (monster != null) {
            // Player attacks monster
            Random rn = new Random();
            int playerHit = rn.nextInt(player.getMaxHit() + 1);
            int monsterHp = monster.getHp() - playerHit;
            monster.setHp(monsterHp);
            printInfo(monster.getName() + " was hit for " + playerHit + " HP");
            // Handle monster death
            if (monster.getHp() < 1) {
                // Monster death actions
                for (CreatureAction action : monster.getActions()) {
                    if (action.getType().equalsIgnoreCase("Death")) {
                        // Remove monster
                        if (action.getName().equalsIgnoreCase("Remove")) {
                            removeDisplayable(monster, x, y);
                        }
                        // Display monster death message
                        else if (action.getName().equalsIgnoreCase("YouWin")) {
                            printInfo(action.getMessage());
                        }
                    }
                }
            }
            else {
                // Monster hit actions
                for (CreatureAction action : monster.getActions()) {
                    if (action.getType().equalsIgnoreCase("hit")) {
                        if (action.getName().equalsIgnoreCase("Teleport")) {
                            teleport(monster);
                        }
                    }
                }
                // Monster attacks player
                int monsterHit = rn.nextInt(monster.getMaxHit() + 1);
                int playerHp = player.getHp() - monsterHit;
                player.setHp(playerHp);
                printHpScore(playerHp);
                printInfo("Player was hit for " + monsterHit + " HP");
                // Handle player death
                if (player.getHp() < 1) {
                    for (CreatureAction action : player.getActions()) {
                        if (action.getType().equalsIgnoreCase("Death")) {
                            // Display player death message and stop input
                            if (action.getName().equalsIgnoreCase("EndGame") || action.getName().equalsIgnoreCase("YouWin")) {
                                printInfo(action.getMessage());
                                displayGrid.setObserve(false);
                            }
                        }
                    }
                }
            }
        }
        else {
            System.out.println("Tried to attack imaginary monster..");
        }
    }

    public void teleport(Monster monster) {
        System.out.format("%s teleports!\n", monster.getName());
    }

    public void printHpScore(int hp) {
        if (hp < 1) {
            hp = 0;
        }
        setMessageRow(String.format("HP: %-3d Score: %d", hp, score), 0);
    }

    public void printInventory() {
        StringBuilder inv = new StringBuilder("Pack: ");
        for (int i = 0; i < inventory.size(); i += 1) {
            inv.append(i + 1).append(": ").append(inventory.get(i).getName());
            if (i < inventory.size() - 1) {
                inv.append(", ");
            }
        }
        setMessageRow(inv.toString(), TOPHEIGHT + GAMEHEIGHT);
    }

    public void printInfo(String string) {
        if (string.length() < WIDTH - 6) {
            // Add message to bottom row of message space and push others up
            int messageTop = TOPHEIGHT + GAMEHEIGHT + INVENTORYHEIGHT;
            int messageBottom = TOPHEIGHT + GAMEHEIGHT + BOTTOMHEIGHT - 1;
            for (int i = messageTop; i < messageBottom; i += 1) {
                for (int j = 6; j < WIDTH; j += 1) {
                    removeDisplayable(objectGrid[j][i].peek(), j, i);
                    addDisplayable(objectGrid[j][i + 1].peek(), j, i);
                }
            }
            setMessageRow("      " + string, messageBottom);
        }
        else {
            System.out.println("String too long!\n");
        }
    }

    public void setMessageRow(String string, int row) {
        // Clear row
        for (int i = 0; i < WIDTH; i += 1) {
            removeDisplayable(objectGrid[i][row].peek(), i, row);
        }
        // Set row with string
        for (int i = 0; i < string.length(); i += 1) {
            addDisplayable(new Char(string.charAt(i)), i, row);
        }
        // Fill in rest of row
        for (int i = string.length(); i < WIDTH; i += 1) {
            addDisplayable(new Char(' '), i, row);
        }
    }

    public void addDisplayable(Displayable displayable, int x, int y) {
        objectGrid[x][y].push(displayable);
        displayGrid.addObjectToDisplay(new Char(displayable.getDisplayChar()), x, y);
    }

    public void removeDisplayable(Displayable displayable, int x, int y) {
        Displayable removed = objectGrid[x][y].pop();
        if (removed == displayable) {
            displayGrid.addObjectToDisplay(new Char(objectGrid[x][y].peek().getDisplayChar()), x, y);
        }
        else {
            System.out.println("Tried to remove wrong object\n");
        }
    }

    public static void main(String[] args) {
        String fileName;
        if (args.length == 1) {
            fileName = "xmlFiles/" + args[0];
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
            INVENTORYHEIGHT = BOTTOMHEIGHT / 2;

            // Get rooms and passages from dungeon
            rooms = dungeon.getRooms();
            passages = dungeon.getPassages();

            Rogue rogue = new Rogue(WIDTH, TOPHEIGHT + GAMEHEIGHT + BOTTOMHEIGHT);
            Thread rogueThread = new Thread(rogue);
            rogueThread.start();

            rogue.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid, rogue));
            rogue.keyStrokePrinter.start();

            rogueThread.join();
            rogue.keyStrokePrinter.join();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
