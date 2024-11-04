import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class to parse XML into OldCard objects
public class OldCardParser {
    public static List<OldCard> parseXML(String filePath) {
        List<OldCard> oldCards = new ArrayList<>();
        try {
            // Set up XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            NodeList nodeList = doc.getElementsByTagName("OldCard");

            // Loop through each OldCard element
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                OldCard oldCard = new OldCard();
                oldCard.setId(element.getAttribute("id"));
                oldCard.setType(element.getAttribute("type"));
                oldCard.setIsSent(Boolean.parseBoolean(element.getAttribute("isSent")));
                oldCard.setThema(element.getElementsByTagName("Thema").item(0).getTextContent());
                oldCard.setCountry(element.getElementsByTagName("Country").item(0).getTextContent());
                oldCard.setYear(Integer.parseInt(element.getElementsByTagName("Year").item(0).getTextContent()));
                oldCard.setAuthor(element.getElementsByTagName("Author").item(0).getTextContent());
                oldCard.setValuable(element.getElementsByTagName("Valuable").item(0).getTextContent());

                oldCards.add(oldCard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sort cards by year
        Collections.sort(oldCards, (a, b) -> a.getYear() - b.getYear());
        return oldCards;
    }
}
