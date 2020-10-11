import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Test {

    public static void main(String[] args) {
        String fileName;
        if (args.length == 1) {
            fileName = "xmlFiles/" + args[0];
        }
        else {
            System.out.println("java Test <xmlFileName>");
            return;
        }

        // Create a saxParserFactory that will allow use to create a parser
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//
//        try {
//            SAXParser saxParser = saxParserFactory.newSAXParser();
//            // Initialize XML Handler
//            DungeonXMLHandler handler = new DungeonXMLHandler();
//            // Parse the XML file given by fileName
//            saxParser.parse(new File(fileName), handler);
//            // Get and print dungeon from XML parser
//            Dungeon dungeon = handler.getDungeon();
//            System.out.println(dungeon);
//        }
//        catch (ParserConfigurationException | SAXException | IOException e) {
//            e.printStackTrace(System.out);
//        }
    }
}
