package com.example;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.OutputStream;

// Transforms XML using XSLT
public class XSLTransformer {
    // Transforms XML based on XSLT and saves output
    public static void transformXML(InputStream xmlStream, InputStream xslStream, OutputStream outputStream, String groupBy) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(xslStream);
            Transformer transformer = factory.newTransformer(xslt);
            transformer.setParameter("groupBy", groupBy);
            Source xml = new StreamSource(xmlStream);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(xml, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
