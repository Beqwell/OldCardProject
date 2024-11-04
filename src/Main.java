import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

// Main class to run the program
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        URI xmlUri = null;
        URI xsdUri = null;
        try {
            // Load resources
            URL xmlUrl = Main.class.getClassLoader().getResource("old_cards.xml");
            URL xsdUrl = Main.class.getClassLoader().getResource("old_cards.xsd");

            if (xmlUrl == null || xsdUrl == null) {
                logger.severe("Missing XML or XSD file.");
                return;
            }

            xmlUri = xmlUrl.toURI();
            xsdUri = xsdUrl.toURI();
        } catch (URISyntaxException e) {
            logger.severe("URI conversion error: " + e.getMessage());
            return;
        }
        // Validate XML
        if (XMLValidator.validateXMLSchema(xsdUri, xmlUri)) {
            logger.info("XML is valid.");
            List<OldCard> oldCards = OldCardParser.parseXML(xmlUri.getPath());
            oldCards.forEach(card -> logger.info(card.toString()));
        } else {
            logger.severe("XML validation failed.");
        }
    }
}
