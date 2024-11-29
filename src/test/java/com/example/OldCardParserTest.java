package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

// Unit tests for OldCardParser
public class OldCardParserTest {

    @Test
    public void testDOMParser() {
        // Test the DOM parser with the old_cards.xml file and verify parsed data
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("old_cards.xml");
        assertNotNull(xmlStream, "XML file not found.");
        List<OldCard> oldCards = OldCardParser.parseWithDOM(xmlStream);
        assertNotNull(oldCards);
        assertEquals(29, oldCards.size());
        OldCard firstCard = oldCards.get(0);
        assertEquals("card11", firstCard.getId());
        assertEquals("greeting", firstCard.getType());
        assertEquals("architecture", firstCard.getThema());
        assertEquals("Spain", firstCard.getCountry());
        assertEquals(1885, firstCard.getYear());
        assertEquals("Antoni Gaudi", firstCard.getAuthor());
        assertEquals("Historical", firstCard.getValuable());
    }

    @Test
    public void testSAXParser() {
        // Test the SAX parser with the old_cards.xml file and verify parsed data
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("old_cards.xml");
        assertNotNull(xmlStream, "XML file not found.");
        List<OldCard> oldCards = OldCardParser.parseWithSAX(xmlStream);
        assertNotNull(oldCards);
        assertEquals(29, oldCards.size());
        OldCard firstCard = oldCards.get(0);
        assertEquals("card11", firstCard.getId());
        assertEquals("greeting", firstCard.getType());
        assertEquals("architecture", firstCard.getThema());
        assertEquals("Spain", firstCard.getCountry());
        assertEquals(1885, firstCard.getYear());
        assertEquals("Antoni Gaudi", firstCard.getAuthor());
        assertEquals("Historical", firstCard.getValuable());
    }

    @Test
    public void testStAXParser() {
        // Test the StAX parser with the old_cards.xml file and verify parsed data
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("old_cards.xml");
        assertNotNull(xmlStream, "XML file not found.");
        List<OldCard> oldCards = OldCardParser.parseWithStAX(xmlStream);
        assertNotNull(oldCards);
        assertEquals(29, oldCards.size());
        OldCard firstCard = oldCards.get(0);
        assertEquals("card11", firstCard.getId());
        assertEquals("greeting", firstCard.getType());
        assertEquals("architecture", firstCard.getThema());
        assertEquals("Spain", firstCard.getCountry());
        assertEquals(1885, firstCard.getYear());
        assertEquals("Antoni Gaudi", firstCard.getAuthor());
        assertEquals("Historical", firstCard.getValuable());
    }

    @Test
    public void testXMLValidation() {
        // Test XML validation against XSD schema
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("old_cards.xml");
        InputStream xsdStream = getClass().getClassLoader().getResourceAsStream("old_cards.xsd");
        assertNotNull(xmlStream, "XML file not found.");
        assertNotNull(xsdStream, "XSD file not found.");
        boolean isValid = XMLValidator.validateXMLSchema(xsdStream, xmlStream);
        assertTrue(isValid, "XML should be valid against XSD.");
    }

    @Test
    public void testXSLTransformation() {
        // Test XSLT transformation of the old_cards.xml file
        InputStream xmlStream = getClass().getClassLoader().getResourceAsStream("old_cards.xml");
        InputStream xslStream = getClass().getClassLoader().getResourceAsStream("transform.xsl");
        assertNotNull(xmlStream, "XML file not found.");
        assertNotNull(xslStream, "XSL file not found.");
        String outputPath = "src/main/resources/test_transformed_output.xml";
        try (OutputStream transformedOutput = new FileOutputStream(outputPath)) {
            XSLTransformer.transformXML(xmlStream, xslStream, transformedOutput, "Type");
        } catch (Exception e) {
            fail("Exception during XSL transformation: " + e.getMessage());
        }

        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists(), "Transformed output file should exist.");
        assertTrue(outputFile.length() > 0, "Transformed output file should not be empty.");
    }
}
