package by.melnikov.medicinesxml.main;

import by.melnikov.medicinesxml.builder.AbstractMedicinesBuilder;
import by.melnikov.medicinesxml.builder.MedicinesBuilderFactory;
import by.melnikov.medicinesxml.validator.MedicinesXMLValidator;

public class Main {
    public static void main(String[] args) {
        String xmlFile = "src//main//resources//medicines.xml"; //FIXME
        String xsdFile = "src//main//resources//medicine.xsd";  //FIXME
        MedicinesXMLValidator.validateXMLFile(xmlFile, xsdFile);

        MedicinesBuilderFactory factory = MedicinesBuilderFactory.getInstance();
        String[] parsers = {"Dom", "SAX", "stax event", "stax-stream", "JAXB"};
        for (String parser : parsers) {
            AbstractMedicinesBuilder builder = factory.createMedicinesBuilder(parser);
            builder.buildSetMedicines(xmlFile);
            System.out.println(builder.getMedicines());
            System.out.println("=======================================================");
            System.out.println();
        }
    }
}
