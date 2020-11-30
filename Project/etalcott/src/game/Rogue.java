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
    private static int moves = 0;
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static ArrayList<Room> rooms;
    private static ArrayList<Passage> passages;
    private static Room playerRoom;
    private static int hallucinate_count = 0;
    private static ArrayList<Char> validChars = new ArrayList<>();
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
                displayGrid.repaint();
            }
            for (int j = 0; j < GAMEHEIGHT; j += 1) {
                objectGrid[i][j + TOPHEIGHT] = new Stack();
                objectGrid[i][j + TOPHEIGHT].push(new Char(' '));
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT);
                displayGrid.repaint();
            }
            for (int j = 0; j < BOTTOMHEIGHT; j += 1) {
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT] = new Stack();
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT].push(new Char(' '));
                objectGrid[i][j + TOPHEIGHT + GAMEHEIGHT].push(new Char(' '));
                displayGrid.addObjectToDisplay(new Char(' '), i, j + TOPHEIGHT + GAMEHEIGHT);
                displayGrid.repaint();
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
                displayGrid.repaint();
                displayGrid.addObjectToDisplay(new Char('X'), i, room.getPosY() + room.getHeight() + TOPHEIGHT - 1);
                displayGrid.repaint();
            }
            // Sides of room
            for (int i = room.getPosY(); i < room.getPosY() + room.getHeight(); i++) {
                objectGrid[room.getPosX()][i + TOPHEIGHT].push(new Char('X'));
                objectGrid[room.getPosX() + room.getWidth() - 1][i + TOPHEIGHT].push(new Char('X'));
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX(), i + TOPHEIGHT);
                displayGrid.repaint();
                displayGrid.addObjectToDisplay(new Char('X'), room.getPosX() + room.getWidth() - 1, i + TOPHEIGHT);
                displayGrid.repaint();
            }
            // Interior of room
            for (int i = room.getPosX() + 1; i < room.getPosX() + room.getWidth() - 1; i++) {
                for (int j = room.getPosY() + 1; j < room.getPosY() + room.getHeight() - 1; j++) {
                    objectGrid[i][j + TOPHEIGHT].push(new Char('.'));
                    displayGrid.addObjectToDisplay(new Char('.'), i, j + TOPHEIGHT);
                    displayGrid.repaint();
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
                        displayGrid.repaint();
                    } else if (item.getName().contains("Sword")) {
                        displayGrid.addObjectToDisplay(new Char(')'), room.getPosX() + item.getPosX(), room.getPosY() + item.getPosY() + TOPHEIGHT);
                        displayGrid.repaint();
                    } else if (item.getName().contains("Armor")) {
                        displayGrid.addObjectToDisplay(new Char(']'), room.getPosX() + item.getPosX(), room.getPosY() + item.getPosY() + TOPHEIGHT);
                        displayGrid.repaint();
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
                        displayGrid.repaint();
                        PLAYER_X = room.getPosX() + creature.getPosX();
                        PLAYER_Y = room.getPosY() + creature.getPosY() + TOPHEIGHT;
                        printHpScore(creature.getHp());
                        playerRoom = room;
                    } else {
                        displayGrid.addObjectToDisplay(new Char(creature.getDisplayChar()), room.getPosX() + creature.getPosX(), room.getPosY() + creature.getPosY() + TOPHEIGHT);
                        displayGrid.repaint();
                    }
                }
            }
        }

        // Add items owned by player to inventory
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        for (Item item : playerRoom.getItems()) {
            if (item.getOwner() == player) {
                inventory.add(item);
                item.setOwner(null);
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
                        displayGrid.repaint();

                    }
                }
                // Vertical section
                if (x == next_x) {
                    for (int j = y; j <= next_y; j++) {
                        objectGrid[x][j + TOPHEIGHT].push(new Char('#'));
                        displayGrid.addObjectToDisplay(new Char('#'), x, j + TOPHEIGHT);
                        displayGrid.repaint();

                    }
                }
            }
            // Add pluses at passage termini
            int x = xPos.get(0);
            int y = yPos.get(0);
            objectGrid[x][y + TOPHEIGHT].push(new Char('+'));
            displayGrid.addObjectToDisplay(new Char('+'), x, y + TOPHEIGHT);
            displayGrid.repaint();
            x = xPos.get(len - 1);
            y = yPos.get(len - 1);
            objectGrid[x][y + TOPHEIGHT].push(new Char('+'));
            displayGrid.addObjectToDisplay(new Char('+'), x, y + TOPHEIGHT);
            displayGrid.repaint();
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
        moves += 1;
        // Check if hpMoves threshold reached
        Player play = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        if (moves >= play.getHpMoves()) {
            int hp = play.getHp() + 1;
            play.setHp(hp);
            printHpScore(hp);
            moves = 0;
        }

        // Check if player is hallucinating
        if (hallucinate_count > 0) {
            hallucinate();
            hallucinate_count -= 1;
            if (hallucinate_count == 0) {
                stop_hallucinating();
            }
        }

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
            displayGrid.repaint();
            displayGrid.addObjectToDisplay(new Char(objectGrid[new_x][new_y].peek().getDisplayChar()), new_x, new_y);
            displayGrid.repaint();
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

    public void hallucinate() {
        Random rn = new Random();
        // Iterate through whole game area and replace each displayable with another valid character
        System.out.format("Doing some hallucinating: %d!\n", hallucinate_count);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = TOPHEIGHT; j < TOPHEIGHT + GAMEHEIGHT; j++) {
                for (int k = 0; k < validChars.size(); k++) {
                    if (validChars.get(k).getChar() == objectGrid[i][j].peek().getDisplayChar()) {
                        displayGrid.addObjectToDisplay(validChars.get(rn.nextInt(validChars.size())), i, j);
                        displayGrid.repaint();
                        break;
                    }
                }
            }
        }
    }

    public void stop_hallucinating() {
        // Iterate through whole game area and set the correct display character for each position
        System.out.format("Doing some hallucinating: %d!\n", hallucinate_count);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = TOPHEIGHT; j < TOPHEIGHT + GAMEHEIGHT; j++) {
                displayGrid.addObjectToDisplay(new Char(objectGrid[i][j].peek().getDisplayChar()), i, j);
                displayGrid.repaint();
            }
        }
    }

    public void drop(int item) {
        // Drop if item exists
        if (inventory.size() >= item) {
            // Remove player from stack
            Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].pop();
            // Put item on floor and remove from inventory
            Item inv_item = inventory.remove(item - 1);
            if (inv_item.getOwner() == player && inv_item instanceof Sword) {
                inv_item.setName(inv_item.getName().substring(0, inv_item.getName().length() - 4));
                inv_item.setOwner(null);
                player.setWeapon(null);
            }
            else if (inv_item.getOwner() == player && inv_item instanceof Armor) {
                inv_item.setName(inv_item.getName().substring(0, inv_item.getName().length() - 4));
                inv_item.setOwner(null);
                player.setArmor(null);
            }
            objectGrid[PLAYER_X][PLAYER_Y].add(inv_item);
            // Put player back on stack
            objectGrid[PLAYER_X][PLAYER_Y].push(player);
        }
        else {
            printInfo("Item " + item + " does not exist!");
        }
    }

    public void equip_weapon(int item) {
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        if (inventory.size() < item) {
            printInfo("Item " + item + " does not exist!");
        }
        else if (inventory.get(item - 1) instanceof Sword) {
            remove_weapon();
            Sword sword = (Sword) inventory.get(item - 1);
            sword.setName(sword.getName() + " (w)");
            sword.setOwner(player);
            player.setWeapon(sword);
        }
        else {
            printInfo("Item " + item + " is not a sword!");
        }
    }

    public void remove_weapon() {
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        for (Item item : inventory) {
            if (item.getOwner() == player && item instanceof Sword) {
                item.setName(item.getName().substring(0, item.getName().length() - 4));
                item.setOwner(null);
                player.setWeapon(null);
                break;
            }
        }
    }

    public void equip_armor(int item) {
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        if (inventory.size() < item) {
            printInfo("Item " + item + " does not exist!");
        }
        else if (inventory.get(item - 1) instanceof Armor) {
            if (player.getArmor() != null){
                remove_armor();
            }
            Armor armor = (Armor) inventory.get(item - 1);
            armor.setName(armor.getName() + " (a)");
            armor.setOwner(player);
            player.setArmor(armor);
        }
        else {
            printInfo("Item " + item + " is not armor!");
        }
    }

    public void remove_armor() {
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        for (Item item : inventory) {
            if (item.getOwner() == player && item instanceof Armor) {
                item.setName(item.getName().substring(0, item.getName().length() - 4));
                item.setOwner(null);
                player.setArmor(null);
                return;
            }
        }
        printInfo("No armor was equipped.");
    }

    public void read_scroll(int item) {
        Player player = (Player) objectGrid[PLAYER_X][PLAYER_Y].peek();
        if (inventory.size() < item) {
            printInfo("Item " + item + " does not exist!");
        }
        else if (inventory.get(item - 1) instanceof Scroll) {
            Scroll scroll = (Scroll) inventory.remove(item - 1);
            ArrayList<ItemAction> actions = scroll.getActions();
            for (Action action : actions) {
                if (action.getName().equalsIgnoreCase("hallucinate")) {
                    hallucinate_count = action.getActionIntValue();
                    printInfo("Hallucinations will last for " + action.getActionIntValue() + " moves.");
                }
                else if (action.getActionCharValue() == 'a') {
                    Armor armor = player.getArmor();
                    if (armor != null) {
                        armor.setItemIntValue(armor.getItemIntValue() + action.getActionIntValue());
                        if (action.getActionIntValue() < 0) {
                            printInfo(armor.getName() + " cursed for " + action.getActionIntValue() + " points!");
                        }
                        else {
                            printInfo(armor.getName() + " blessed for " + action.getActionIntValue() + " points!");
                        }
                    }
                    else {
                        printInfo(action.getName() + " had no effect because no armor is equipped.");
                    }
                }
                else if (action.getActionCharValue() == 'w') {
                    Sword sword = player.getWeapon();
                    if (sword != null) {
                        sword.setItemIntValue(sword.getItemIntValue() + action.getActionIntValue());
                        if (action.getActionIntValue() < 0) {
                            printInfo(sword.getName() + " cursed for " + action.getActionIntValue() + " points!");
                        }
                        else {
                            printInfo(sword.getName() + " blessed for " + action.getActionIntValue() + " points!");
                        }
                    }
                    else {
                        printInfo(action.getName() + " had no effect because no sword is equipped.");
                    }
                }
            }
        }
        else {
            printInfo("Item " + item + " is not a scroll!");
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
            // Add sword value to calculated player hit (make sure hit doesn't go below 0)
            if (player.getWeapon() != null) {
                playerHit += player.getWeapon().getItemIntValue();
                if (playerHit < 0) {
                    playerHit = 0;
                }
            }
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
                            printInfo(action.getMessage());
                            teleport(monster, x, y);
                        }
                    }
                }
                // Monster attacks player
                int monsterHit = rn.nextInt(monster.getMaxHit() + 1);
                // Subtract armor value from hit (make sure hit doesn't go below 0)
                if (player.getArmor() != null) {
                    monsterHit -= player.getArmor().getItemIntValue();
                    if (monsterHit < 0) {
                        monsterHit = 0;
                    }
                }
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
                            else if (action.getName().equalsIgnoreCase("ChangeDisplayType") || action.getName().equalsIgnoreCase("ChangeDisplayedType")) {
                                removeDisplayable(player, PLAYER_X, PLAYER_Y);
                                player.setDisplayChar(action.getActionCharValue());
                                addDisplayable(player, PLAYER_X, PLAYER_Y);
                            }
                        }
                    }
                }
                // Player hit actions
                else {
                    for (CreatureAction action : player.getActions()) {
                        if (action.getName().equalsIgnoreCase("DropPack")) {
                            if (inventory.size() > 0) {
                                printInfo(action.getMessage());
                                drop(1);
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

    public void teleport(Monster monster, int x, int y) {
        // Get valid coordinates to teleport to
        Random rn = new Random();
        int new_x, new_y;
        Displayable thing;
        while(true) {
            new_x = rn.nextInt(WIDTH - 1);
            new_y = rn.nextInt(GAMEHEIGHT - 1) + TOPHEIGHT;
            thing = objectGrid[new_x][new_y].peek();
            if (thing instanceof Item || (thing instanceof Char && (thing.getDisplayChar() == '.' || thing.getDisplayChar() == '#'))) {
                break;
            }
        }
        // Move monster to new coordinates
        removeDisplayable(monster, x, y);
        addDisplayable(monster, new_x, new_y);
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
            if (inventory.get(i) instanceof Sword || inventory.get(i) instanceof Armor) {
                inv.append(" (").append(inventory.get(i).getItemIntValue()).append(")");
            }
            if (i < inventory.size() - 1) {
                inv.append(", ");
            }
        }
        setMessageRow(inv.toString(), TOPHEIGHT + GAMEHEIGHT);
    }

    public void printCommands() {
        printInfo("h,l,k,j,i,?,H,c,d,p,r,T,w,E. H <cmd> for more info");
    }

    public void printHelp(char ch) {
        switch (ch) {
            case 'h':
            case 'j':
            case 'k':
            case 'l': printInfo("h: move left, l: move right, k: move up, j: move down"); break;
            case 'i': printInfo("i: show pack contents"); break;
            case '?': printInfo("?: show all commands"); break;
            case 'H': printInfo("H<command>: show more detailed info about <command>"); break;
            case 'c': printInfo("c: take off armor"); break;
            case 'd': printInfo("d<item number>: drop <item number> item from pack"); break;
            case 'p': printInfo("p: pick up item and put into pack"); break;
            case 'r': printInfo("r<item number>: read <item number> scroll from pack"); break;
            case 'T': printInfo("T<item number>: equip <item number> sword from pack"); break;
            case 'w': printInfo("w<item number>: equip <item number> armor from pack"); break;
            case 'E': printInfo("E<char>: end game if <char> is Y or y"); break;
            default: break;
        }
    }

    public void endGame(char ch) {
        if (Arrays.asList('Y', 'y').contains(ch)) {
            printInfo("Game ended by player via E command.");
            displayGrid.setObserve(false);
        }
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
        displayGrid.repaint();
    }

    public void removeDisplayable(Displayable displayable, int x, int y) {
        Displayable removed = objectGrid[x][y].pop();
        if (removed == displayable) {
            displayGrid.addObjectToDisplay(new Char(objectGrid[x][y].peek().getDisplayChar()), x, y);
            displayGrid.repaint();
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

            // Initialize valid display characters
            validChars.add(new Char('X'));
            validChars.add(new Char('.'));
            validChars.add(new Char('#'));
            validChars.add(new Char('+'));
            validChars.add(new Char('T'));
            validChars.add(new Char('H'));
            validChars.add(new Char('S'));
            validChars.add(new Char('@'));
            validChars.add(new Char('?'));
            validChars.add(new Char(']'));
            validChars.add(new Char(')'));

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
