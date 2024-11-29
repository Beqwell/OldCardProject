package com.example;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.util.logging.Logger;

// Validates XML against XSD schema
public class XMLValidator {
    private static final Logger logger = Logger.getLogger(XMLValidator.class.getName());

    // Validates XML with XSD
    public static boolean validateXMLSchema(InputStream xsdStream, InputStream xmlStream) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            return true;
        } catch (Exception e) {
            logger.severe("XML Validation Error: " + e.getMessage());
            return false;
        }
    }
}
