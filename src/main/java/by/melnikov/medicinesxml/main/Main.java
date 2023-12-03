package by.melnikov.medicinesxml.main;

import by.melnikov.medicinesxml.builder.AbstractMedicineBuilder;
import by.melnikov.medicinesxml.builder.MedicineBuilderFactory;
import by.melnikov.medicinesxml.entity.Medicine;
import by.melnikov.medicinesxml.exception.MedicineCustomException;
import by.melnikov.medicinesxml.validator.MedicinesXmlValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class Main {
    public static final String XML_FILE = "src//main//resources//medicines.xml"; //FIXME
    public static final String XSD_FILE = "src//main//resources//medicine.xsd"; //FIXME
    public static final String PARSING_RESULT = "src//main//resources//result.txt"; //FIXME

    public static void main(String[] args) throws MedicineCustomException {
        MedicinesXmlValidator.validateXMLFile(XML_FILE, XSD_FILE);
        MedicineBuilderFactory factory = MedicineBuilderFactory.getInstance();
        String[] parsers = {"DOM", "sax", "stax stream", "Stax-events", "JAXB"};
        for (String parser : parsers) {
            AbstractMedicineBuilder builder = factory.createMedicineBuilder(parser);
            builder.buildSetMedicines(XML_FILE);
            writeResultToFile(builder.getMedicines());
        }
    }

    public static void writeResultToFile(Set<Medicine> medicines) throws MedicineCustomException {
        try {
            Path path = Path.of(PARSING_RESULT);
            for (Medicine medicine : medicines) {
                Files.write(path, medicine.toString().getBytes(), StandardOpenOption.APPEND);
                Files.writeString(path, "\n", StandardOpenOption.APPEND);
            }
            Files.writeString(path, "==============================================================================================================\n\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new MedicineCustomException("writing to file error", e);
        }
    }
}
