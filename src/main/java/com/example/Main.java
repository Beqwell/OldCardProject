package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.LogManager;

// Main application class
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // Saves parsed data to a file
    public static void saveParsedDataToFile(List<OldCard> oldCards, String outputPath) {
        try (OutputStream writer = new FileOutputStream(outputPath)) {
            for (OldCard card : oldCards) {
                writer.write((card.toString() + System.lineSeparator()).getBytes());
            }
            logger.info("Parsed data saved to " + outputPath);
        } catch (IOException e) {
            logger.severe("Error writing parsed data to file: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        // Load logging configuration
        try (InputStream configFile = Main.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (configFile != null) {
                LogManager.getLogManager().readConfiguration(configFile);
            } else {
                logger.warning("Could not find logging.properties file.");
            }
        } catch (IOException e) {
            logger.severe("Could not setup logger configuration: " + e.getMessage());
        }

        // Load resources
        URL xmlUrl = Main.class.getClassLoader().getResource("old_cards.xml");
        URL xsdUrl = Main.class.getClassLoader().getResource("old_cards.xsd");
        URL xslUrl = Main.class.getClassLoader().getResource("transform.xsl");

        if (xmlUrl == null || xsdUrl == null || xslUrl == null) {
            logger.severe("Missing XML, XSD, or XSL file.");
            return;
        }

        try (InputStream xmlStream = xmlUrl.openStream();
             InputStream xsdStream = xsdUrl.openStream()) {

            // Validate XML
            boolean isValid = XMLValidator.validateXMLSchema(xsdStream, xmlStream);
            if (isValid) {
                logger.info("XML is valid.");

                // Re-open XML stream for parsing
                try (InputStream xmlStreamForParsing = xmlUrl.openStream()) {
                    // Choose parser method
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Choose parsing method:");
                    System.out.println("1. DOM");
                    System.out.println("2. SAX");
                    System.out.println("3. StAX");
                    System.out.print("Your choice (1-3): ");
                    int parserChoice = scanner.nextInt();
                    scanner.nextLine(); // Clear input buffer
                    List<OldCard> oldCards = null;
                    switch (parserChoice) {
                        case 1:
                            oldCards = OldCardParser.parseWithDOM(xmlStreamForParsing);
                            logger.info("Parsed using DOM Parser.");
                            break;
                        case 2:
                            oldCards = OldCardParser.parseWithSAX(xmlStreamForParsing);
                            logger.info("Parsed using SAX Parser.");
                            break;
                        case 3:
                            oldCards = OldCardParser.parseWithStAX(xmlStreamForParsing);
                            logger.info("Parsed using StAX Parser.");
                            break;
                        default:
                            logger.severe("Invalid choice.");
                            return;
                    }

                    if (oldCards == null || oldCards.isEmpty()) {
                        logger.severe("No data parsed from XML.");
                        return;
                    }

                    // Save parsed data to file
                    String parsedOutputPath = "src/main/resources/parsed_output.txt";
                    saveParsedDataToFile(oldCards, parsedOutputPath);
                    // Choose grouping key
                    System.out.println("Choose grouping key (Type, Thema, Country): ");
                    System.out.print("Your choice: ");
                    String groupBy = scanner.nextLine().trim();
                    if (!groupBy.equalsIgnoreCase("Type") &&
                            !groupBy.equalsIgnoreCase("Thema") &&
                            !groupBy.equalsIgnoreCase("Country")) {
                        logger.severe("Invalid grouping key.");
                        return;
                    }
                    // Perform transformation
                    String transformedOutputPath = "src/main/resources/transformed_output.xml";
                    try (InputStream xslStream = xslUrl.openStream();
                         OutputStream transformedOutput = new FileOutputStream(transformedOutputPath)) {
                        XSLTransformer.transformXML(xmlUrl.openStream(), xslStream, transformedOutput, groupBy);
                        logger.info("Transformation completed. Output saved to " + transformedOutputPath);
                    } catch (Exception e) {
                        logger.severe("Error during transformation: " + e.getMessage());
                    }
                }
            } else {
                logger.severe("XML validation failed.");
            }
        } catch (IOException e) {
            logger.severe("Error reading XML/XSD files: " + e.getMessage());
        }
    }
}
