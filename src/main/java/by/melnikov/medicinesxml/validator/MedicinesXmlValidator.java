package by.melnikov.medicinesxml.validator;

import by.melnikov.medicinesxml.exception.MedicineCustomException;
import by.melnikov.medicinesxml.handler.MedicineErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class MedicinesXmlValidator {
    public static boolean validateXMLFile(String xmlFile, String xsdFile) throws MedicineCustomException{
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaLocation = new File(xsdFile);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlFile);
            validator.setErrorHandler(new MedicineErrorHandler());
            validator.validate(source);
        } catch (SAXException | IOException e) {
            throw new MedicineCustomException(xmlFile + " is not correct or valid");
        }
        return true;
    }
}
