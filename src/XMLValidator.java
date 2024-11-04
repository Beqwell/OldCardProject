import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.URI;

// Class to validate XML file against XSD schema
public class XMLValidator {
    public static boolean validateXMLSchema(URI xsdUri, URI xmlUri) {
        try {
            // Set up schema factory
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdUri));
            Validator validator = schema.newValidator();
            // Validate XML
            validator.validate(new StreamSource(new File(xmlUri)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
