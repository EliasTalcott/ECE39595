import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DungeonXMLHandler extends DefaultHandler {

    // the two lines that follow declare a DEBUG flag to control
    // debug print statements and to allow the class to be easily
    // printed out.  These are not necessary for the parser.
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";

    // data can be called anything, but it is the variables that
    // contains information found while parsing the xml file
    private StringBuilder data = null;

    // When the parser parses the file it will add references to
    // Student objects to this array so that it has a list of
    // all specified students.  Had we covered containers at the
    // time I put this file on the web page I would have made this
    // an ArrayList of Students (ArrayList<Student>) and not needed
    // to keep tract of the length and maxStudents.  You should use
    // an ArrayList in your project.
    private Dungeon dungeon;

    // The XML file contains a Dungeon and a list of Displayables
    // and Actions that describe the Dungeon. When the XML file
    // initially defines one of these objects, many of the fields
    // have not been filled in. Additional lines in the XML file
    // give the values of the fields. Having access to the current
    // Dungeon, Displayable, and Action allows setters on those
    // objects to be called to initialize those fields.
    private Room roomBeingParsed = null;
    private Passage passageBeingParsed = null;
    private Monster monsterBeingParsed = null;
    private Player playerBeingParsed = null;
    private Item itemBeingParsed = null;
    private Action actionBeingParsed = null;

    // The bX fields here indicate that at corresponding field is
    // having a value defined in the XML file.  In particular, a
    // line in the xml file might be:
    // <instructor>Brook Parke</instructor>
    // The startElement method (below) is called when <instructor>
    // is seen, and there we would set bInstructor.  The endElement
    // method (below) is called when </instructor> is found, and
    // in that code we check if bInstructor is set.  If it is,
    // we can extract a string representing the instructor name
    // from the data variable above.
    private boolean bVisible = false;
    private boolean bPosX = false;
    private boolean bPosY = false;
    private boolean bWidth = false;
    private boolean bHeight = false;
    private boolean bType = false;
    private boolean bHp = false;
    private boolean bHpMoves = false;
    private boolean bMaxHit = false;
    private boolean bActionMessage = false;
    private boolean bActionIntValue = false;
    private boolean bActionCharValue = false;

    // Used by code outside the class to get the dungeon that has been constructed
    public Dungeon getDungeon() { return dungeon; }

    // A constructor for this class.  It makes an implicit call to the
    // DefaultHandler zero arg constructor, which does the real work
    // DefaultHandler is defined in org.xml.sax.helpers.DefaultHandler;
    // imported above, and we don't need to write it.  We get its
    // functionality by deriving from it!
    public DungeonXMLHandler() { }

    // startElement is called when a <some element> is called as part of
    // <some element> ... </some element> start and end tags.
    // Rather than explain everything, look at the xml file in one screen
    // and the code below in another, and see how the different xml elements
    // are handled.
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        // Create new objects
        if (qName.equalsIgnoreCase("Dungeon")) {
            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            dungeon = new Dungeon(name, width, topHeight, gameHeight, bottomHeight);
        }
        // Structures
        else if (qName.equalsIgnoreCase("Room")) {
            String id = attributes.getValue("room");
            Room room = new Room(id);
            dungeon.addRoom(room);
            roomBeingParsed = room;
        }
        else if (qName.equalsIgnoreCase("Passage")) {
            String id1 = attributes.getValue("room1");
            String id2 = attributes.getValue("room2");
            Passage passage = new Passage(id1, id2);
            dungeon.addPassage(passage);
            passageBeingParsed = passage;
        }
        // Creatures
        else if (qName.equalsIgnoreCase("Monster")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Monster monster = new Monster(name, room, serial);
            roomBeingParsed.addCreature(monster);
            monsterBeingParsed = monster;
        }
        else if (qName.equalsIgnoreCase("Player")) {
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Player player = new Player(room, serial);
            roomBeingParsed.addCreature(player);
            playerBeingParsed = player;
        }
        // Items
        else if (qName.equalsIgnoreCase("Scroll")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name, room, serial);
            roomBeingParsed.addItem(scroll);
            itemBeingParsed = scroll;
        }
        else if (qName.equalsIgnoreCase("Armor")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name, room, serial);
            roomBeingParsed.addItem(armor);
            itemBeingParsed = armor;
        }
        else if (qName.equalsIgnoreCase("Sword")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name, room, serial);
            roomBeingParsed.addItem(sword);
            itemBeingParsed = sword;
        }
        // Actions
        else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            CreatureAction creatureAction = new CreatureAction(name, type);
            if (monsterBeingParsed != null) {
                monsterBeingParsed.addAction(creatureAction);
            }
            else {
                playerBeingParsed.addAction(creatureAction);
            }
            actionBeingParsed = creatureAction;
        }
        else if (qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            ItemAction itemAction = new ItemAction(name, type);
            itemBeingParsed.addAction(itemAction);
            actionBeingParsed = itemAction;
        }

        // Add attributes to existing objects
        else if (qName.equalsIgnoreCase("visible")) {
            bVisible = true;
        }
        else if (qName.equalsIgnoreCase("posX")) {
            bPosX = true;
        }
        else if (qName.equalsIgnoreCase("posY")) {
            bPosY = true;
        }
        else if (qName.equalsIgnoreCase("width")) {
            bWidth = true;
        }
        else if (qName.equalsIgnoreCase("height")) {
            bHeight = true;
        }
        else if (qName.equalsIgnoreCase("type")) {
            bType = true;
        }
        else if (qName.equalsIgnoreCase("hp")) {
            bHp = true;
        }
        else if (qName.equalsIgnoreCase("hpMoves")) {
            bHpMoves = true;
        }
        else if (qName.equalsIgnoreCase("maxHit")) {
            bMaxHit = true;
        }
        else if (qName.equalsIgnoreCase("actionMessage")) {
            bActionMessage = true;
        }
        else if (qName.equalsIgnoreCase("actionIntValue")) {
            bActionIntValue = true;
        }
        else if (qName.equalsIgnoreCase("actionCharValue")) {
            bActionCharValue = true;
        }

        // Handle "do nothing" tags and unrecognized tags
        else if (qName.equalsIgnoreCase("Rooms") || qName.equalsIgnoreCase("Passages")) {
            assert true;
        }
        else {
            System.out.println("Unknown qname: " + qName);
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Add attributes to objects being parsed
        if (bVisible) {
            if (itemBeingParsed != null) {
                itemBeingParsed.setVisible(Integer.parseInt(data.toString()));
            }
            else if (monsterBeingParsed != null) {
                monsterBeingParsed.setVisible(Integer.parseInt(data.toString()));
            }
            else if (playerBeingParsed != null) {
                playerBeingParsed.setVisible(Integer.parseInt(data.toString()));
            }
            else if (roomBeingParsed != null) {
                roomBeingParsed.setVisible(Integer.parseInt(data.toString()));
            }
            else {
                passageBeingParsed.setVisible(Integer.parseInt(data.toString()));
            }
            bVisible = false;
        }
        else if (bPosX) {
            if (itemBeingParsed != null) {
                itemBeingParsed.setPosX(Integer.parseInt(data.toString()));
            }
            else if (monsterBeingParsed != null) {
                monsterBeingParsed.setPosX(Integer.parseInt(data.toString()));
            }
            else if (playerBeingParsed != null) {
                playerBeingParsed.setPosX(Integer.parseInt(data.toString()));
            }
            else if (roomBeingParsed != null) {
                roomBeingParsed.setPosX(Integer.parseInt(data.toString()));
            }
            else {
                passageBeingParsed.setPosX(Integer.parseInt(data.toString()));
            }
            bPosX = false;
        }
        else if (bPosY) {
            if (itemBeingParsed != null) {
                itemBeingParsed.setPosY(Integer.parseInt(data.toString()));
            }
            else if (monsterBeingParsed != null) {
                monsterBeingParsed.setPosY(Integer.parseInt(data.toString()));
            }
            else if (playerBeingParsed != null) {
                playerBeingParsed.setPosY(Integer.parseInt(data.toString()));
            }
            else if (roomBeingParsed != null) {
                roomBeingParsed.setPosY(Integer.parseInt(data.toString()));
            }
            else {
                passageBeingParsed.setPosY(Integer.parseInt(data.toString()));
            }
            bPosY = false;
        }
        else if (bWidth) {
            roomBeingParsed.setWidth(Integer.parseInt(data.toString()));
            bWidth = false;
        }
        else if (bHeight) {
            roomBeingParsed.setHeight(Integer.parseInt(data.toString()));
            bHeight = false;
        }
        else if (bType) {
            monsterBeingParsed.setType(data.toString().charAt(0));
            bType = false;
        }
        else if (bHp) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setHp(Integer.parseInt(data.toString()));
            }
            else {
                playerBeingParsed.setHp(Integer.parseInt(data.toString()));
            }
            bHp = false;
        }
        else if (bHpMoves) {
            playerBeingParsed.setHpMoves(Integer.parseInt(data.toString()));
            bHpMoves = false;
        }
        else if (bMaxHit) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
            }
            else {
                playerBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
            }
            bMaxHit = false;
        }
        else if (bActionMessage) {
            actionBeingParsed.setMessage(data.toString());
            bActionMessage = false;
        }
        else if (bActionIntValue) {
            actionBeingParsed.setActionIntValue(Integer.parseInt(data.toString()));
            bActionIntValue = false;
        }
        else if (bActionCharValue) {
            actionBeingParsed.setActionCharValue(data.toString().charAt(0));
            bActionCharValue = false;
        }

        // Change xBeingParsed to null once finished with parsing
        if (qName.equalsIgnoreCase("Dungeon")) {
            assert true;
        }
        else if (qName.equalsIgnoreCase("Room")) {
            roomBeingParsed = null;
        }
        else if (qName.equalsIgnoreCase("Passage")) {
            passageBeingParsed = null;
        }
        else if (qName.equalsIgnoreCase("Monster")) {
            monsterBeingParsed = null;
        }
        else if (qName.equalsIgnoreCase("Player")) {
            playerBeingParsed = null;
        }
        else if (qName.equalsIgnoreCase("Scroll") || (qName.equalsIgnoreCase("Armor")) || (qName.equalsIgnoreCase("Sword"))) {
            itemBeingParsed = null;
        }
        else if (qName.equalsIgnoreCase("CreatureAction") || (qName.equalsIgnoreCase("ItemAction"))) {
            actionBeingParsed = null;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }

//    @Override
//    public String toString() {
//        String str = "StudentsXMLHandler\n";
//        str += "   maxStudents: " + maxStudents + "\n";
//        str += "   studentCount: " + studentCount + "\n";
//        for (Student student : students) {
//            str += student.toString() + "\n";
//        }
//        str += "   studentBeingParsed: " + studentBeingParsed.toString() + "\n";
//        str += "   activityBeingParsed: " + activityBeingParsed.toString() + "\n";
//        str += "   bInstructor: " + bInstructor + "\n";
//        str += "   bCredit: " + bInstructor + "\n";
//        str += "   bName: " + bInstructor + "\n";
//        str += "   bNumber: " + bInstructor + "\n";
//        str += "   bLocation: " + bInstructor + "\n";
//        str += "   bMeetingTime: " + bInstructor + "\n";
//        str += "   bMeetingDay: " + bInstructor + "\n";
//        return str;
//    }
}
