package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MedicinesDOMBuilder extends AbstractMedicinesBuilder {
    private Set<Medicine> medicines;
    private DocumentBuilder documentBuilder;

    public MedicinesDOMBuilder() {
        medicines = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("parser's configuration error: " + e);
        }
    }

    public MedicinesDOMBuilder(Set<Medicine> medicines) {
        super(medicines);
    }

    @Override
    public Set<Medicine> getMedicines() {
        return medicines;
    }

    @Override
    public void buildSetMedicines(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList medicinesList = root.getElementsByTagName("medicine");
            for (int i = 0; i < medicinesList.getLength(); i++) {
                Element medicineElement = (Element) medicinesList.item(i);
                Medicine medicine = buildMedicine(medicineElement);
                medicines.add(medicine);
            }
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        } catch (SAXException e) {
            System.err.println("Parsing failure: " + e);
        }
    }

    private Medicine buildMedicine(Element medicineElement) {
        if (medicineElement == null) {
            throw new NullPointerException(); //FIXME
        }

        Medicine medicine = null;
        if ("Antibiotic".equals(medicineElement.getAttribute("xsi:type"))) {
            medicine = new Antibiotic();
        } else if ("Vitamins".equals(medicineElement.getAttribute("xsi:type"))) {
            medicine = new Vitamins();
        }

        Element packageElement = (Element) medicineElement.getElementsByTagName("package").item(0);
        Element dosageElement = (Element) medicineElement.getElementsByTagName("dosage").item(0);
        Element certificationElement = (Element) medicineElement.getElementsByTagName("certification").item(0);
        MedicinePackage medicinePackage = new MedicinePackage();
        MedicineDosage medicineDosage = new MedicineDosage();
        MedicineCertification medicineCertification = new MedicineCertification();

        medicine.setName(medicineElement.getAttribute("name"));
        medicine.addAnalog(getElementTextContent(medicineElement, "analog"));
        medicine.addCompany(getElementTextContent(medicineElement, "company"));
        medicine.setShape(Medicine.Shape.valueOf(medicineElement.getAttribute("shape")));
        medicinePackage.setPrice(Integer.parseInt(getElementTextContent(packageElement,"price")));

        //FIXME
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
