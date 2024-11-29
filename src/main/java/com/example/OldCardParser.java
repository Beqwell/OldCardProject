package com.example;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// Parses OldCard XML using DOM, SAX, and StAX
public class OldCardParser {
    private static final Logger logger = Logger.getLogger(OldCardParser.class.getName());

    // DOM parser method
    public static List<OldCard> parseWithDOM(InputStream xmlStream) {
        List<OldCard> oldCards = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlStream);
            NodeList nodeList = doc.getElementsByTagName("OldCard");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                OldCard oldCard = new OldCard();

                // Get attributes
                oldCard.setId(element.getAttribute("id"));
                oldCard.setType(element.getAttribute("Type"));
                oldCard.setThema(element.getAttribute("Thema"));
                oldCard.setCountry(element.getAttribute("Country"));
                oldCard.setYear(Integer.parseInt(element.getAttribute("Year")));
                oldCard.setAuthor(element.getAttribute("Author"));
                oldCard.setValuable(element.getAttribute("Valuable"));

                // Get content
                String content = element.getTextContent().trim();
                if (!content.isEmpty()) {
                    logger.fine("Content: " + content);
                }

                oldCards.add(oldCard);
            }

        } catch (Exception e) {
            logger.severe("DOM Parsing Error: " + e.getMessage());
        }
        oldCards.sort((a, b) -> Integer.compare(a.getYear(), b.getYear()));
        return oldCards;
    }

    // SAX parser method
    public static List<OldCard> parseWithSAX(InputStream xmlStream) {
        List<OldCard> oldCards = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                OldCard currentCard = null;
                StringBuilder content = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("OldCard")) {
                        currentCard = new OldCard();
                        currentCard.setId(attributes.getValue("id"));
                        currentCard.setType(attributes.getValue("Type"));
                        currentCard.setThema(attributes.getValue("Thema"));
                        currentCard.setCountry(attributes.getValue("Country"));
                        currentCard.setYear(Integer.parseInt(attributes.getValue("Year")));
                        currentCard.setAuthor(attributes.getValue("Author"));
                        currentCard.setValuable(attributes.getValue("Valuable"));
                    }
                    content.setLength(0);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (currentCard != null && qName.equalsIgnoreCase("OldCard")) {
                        oldCards.add(currentCard);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    content.append(new String(ch, start, length));
                }
            };

            saxParser.parse(xmlStream, handler);
        } catch (Exception e) {
            logger.severe("SAX Parsing Error: " + e.getMessage());
        }
        oldCards.sort((a, b) -> Integer.compare(a.getYear(), b.getYear()));
        return oldCards;
    }

    // StAX parser method
    public static List<OldCard> parseWithStAX(InputStream xmlStream) {
        List<OldCard> oldCards = new ArrayList<>();
        OldCard currentCard = null;
        String currentElement = "";

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(xmlStream);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    currentElement = startElement.getName().getLocalPart();
                    if (currentElement.equals("OldCard")) {
                        currentCard = new OldCard();
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        Attribute typeAttr = startElement.getAttributeByName(new QName("Type"));
                        Attribute themaAttr = startElement.getAttributeByName(new QName("Thema"));
                        Attribute countryAttr = startElement.getAttributeByName(new QName("Country"));
                        Attribute yearAttr = startElement.getAttributeByName(new QName("Year"));
                        Attribute authorAttr = startElement.getAttributeByName(new QName("Author"));
                        Attribute valuableAttr = startElement.getAttributeByName(new QName("Valuable"));

                        if (idAttr != null) currentCard.setId(idAttr.getValue());
                        if (typeAttr != null) currentCard.setType(typeAttr.getValue());
                        if (themaAttr != null) currentCard.setThema(themaAttr.getValue());
                        if (countryAttr != null) currentCard.setCountry(countryAttr.getValue());
                        if (yearAttr != null) currentCard.setYear(Integer.parseInt(yearAttr.getValue()));
                        if (authorAttr != null) currentCard.setAuthor(authorAttr.getValue());
                        if (valuableAttr != null) currentCard.setValuable(valuableAttr.getValue());
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("OldCard")) {
                        oldCards.add(currentCard);
                        currentCard = null;
                    }
                    currentElement = "";
                }
            }
        } catch (Exception e) {
            logger.severe("StAX Parsing Error: " + e.getMessage());
        }
        oldCards.sort((a, b) -> Integer.compare(a.getYear(), b.getYear()));
        return oldCards;
    }
}
