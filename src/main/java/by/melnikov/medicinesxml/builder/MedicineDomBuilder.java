package by.melnikov.medicinesxml.builder;

import static by.melnikov.medicinesxml.builder.MedicineXmlNode.*;
import by.melnikov.medicinesxml.entity.*;
import by.melnikov.medicinesxml.exception.MedicineCustomException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

public class MedicineDomBuilder extends AbstractMedicineBuilder {
    private final Set<Medicine> medicines;
    private final DocumentBuilder documentBuilder;

    public MedicineDomBuilder() throws MedicineCustomException{
        medicines = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new MedicineCustomException("parser's configuration error: ", e);
        }
    }

    @Override
    public Set<Medicine> getMedicines() {
        return medicines;
    }

    @Override
    public void buildSetMedicines(String fileName) throws MedicineCustomException {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList antibioticsList = root.getElementsByTagName(ANTIBIOTIC.getTag());
            NodeList vitaminsList = root.getElementsByTagName(VITAMIN.getTag());
            for (int i = 0; i < antibioticsList.getLength(); i++) {
                Element antibioticElement = (Element) antibioticsList.item(i);
                Medicine antibiotic = buildMedicine(antibioticElement);
                medicines.add(antibiotic);
            }
            for (int i = 0; i < vitaminsList.getLength(); i++) {
                Element vitaminElement = (Element) vitaminsList.item(i);
                Medicine vitamin = buildMedicine(vitaminElement);
                medicines.add(vitamin);
            }
        } catch (IOException e) {
            throw new MedicineCustomException("File error or I/O error: ", e);
        } catch (SAXException e) {
            throw new MedicineCustomException("Parsing error: ", e);
        }
    }

    private Medicine buildMedicine(Element medicineElement) {
        Element medicinePackageElement = (Element) medicineElement.getElementsByTagName(PACKAGE.getTag()).item(0);
        Element dosageElement = (Element) medicineElement.getElementsByTagName(DOSAGE.getTag()).item(0);
        Element certificationElement = (Element) medicineElement.getElementsByTagName(CERTIFICATE.getTag()).item(0);

        MedicinePackage medicinePackage = buildMedicinePackage(medicinePackageElement);
        MedicineDosage medicineDosage = buildMedicineDosage(dosageElement);
        MedicineCertificate medicineCertificate = buildMedicineCertification(certificationElement);

        Medicine medicine;
        if (ANTIBIOTIC.getTag().equals(medicineElement.getNodeName())) {
             medicine = new Antibiotic();
            ((Antibiotic) medicine).setNeedPrescription(Boolean.parseBoolean(getElementTextContent(medicineElement, NEED_PRESCRIPTION.getTag())));
        } else {
            medicine = new Vitamin();
            ((Vitamin) medicine).setTarget(Vitamin.Target.valueOf(medicineElement.getAttribute(FOR.getTag()).toUpperCase()));
            ((Vitamin) medicine).setGroup(medicineElement.getAttribute(GROUP.getTag()));
        }
        medicine.setName(getElementTextContent(medicineElement, NAME.getTag()));
        NodeList analogsList = medicineElement.getElementsByTagName(ANALOG.getTag());
        for (int i = 0; i < analogsList.getLength(); i++) {
            Node node = analogsList.item(i);
            medicine.addAnalog(node.getTextContent());
        }
        NodeList companiesList = medicineElement.getElementsByTagName(COMPANY.getTag());
        for (int i = 0; i < companiesList.getLength(); i++) {
            Node node = companiesList.item(i);
            medicine.addCompany(node.getTextContent());
        }
        medicine.addCompany(getElementTextContent(medicineElement, COMPANY.getTag()));
        medicine.setShape(Medicine.Shape.valueOf(getElementTextContent(medicineElement, SHAPE.getTag()).toUpperCase()));
        medicine.setMedicinePackage(medicinePackage);
        medicine.setDosage(medicineDosage);
        medicine.setCertification(medicineCertificate);
        return medicine;
    }

    private MedicinePackage buildMedicinePackage(Element medicinePackageElement) {
        MedicinePackage medicinePackage = new MedicinePackage();
        medicinePackage.setPrice(Integer.parseInt(medicinePackageElement.getAttribute(PRICE.getTag())));
        medicinePackage.setSize(MedicinePackage.Size.valueOf(medicinePackageElement.getAttribute(SIZE.getTag()).toUpperCase()));
        String quantityContent = medicinePackageElement.getAttribute(QUANTITY.getTag());
        String quantity = quantityContent.isEmpty() ? MedicinePackage.DEFAULT_QUANTITY : quantityContent;
        medicinePackage.setQuantity(quantity);
        return medicinePackage;
    }

    private MedicineDosage buildMedicineDosage(Element dosageElement) {
        MedicineDosage medicineDosage = new MedicineDosage();
        medicineDosage.setDose(Integer.parseInt(dosageElement.getAttribute(DOSE.getTag())));
        medicineDosage.setFrequency(dosageElement.getAttribute(FREQUENCY.getTag()));
        return medicineDosage;
    }

    private MedicineCertificate buildMedicineCertification(Element certificationElement) {
        MedicineCertificate medicineCertificate = new MedicineCertificate();
        medicineCertificate.setId(certificationElement.getAttribute(REGISTRATION_NUMBER.getTag()));
        medicineCertificate.setPermissionDate(YearMonth.parse(getElementTextContent(certificationElement, PERMISSION_DATE.getTag())));
        medicineCertificate.setExpiredDate(YearMonth.parse(getElementTextContent(certificationElement, EXPIRED_DATE.getTag())));
        String registryOrganizationContent = certificationElement.getAttribute(REGISTRY_ORGANIZATION.getTag());
        String registryOrganization = registryOrganizationContent.isEmpty() ? MedicineCertificate.DEFAULT_REGISTRY_ORGANIZATION : registryOrganizationContent;
        medicineCertificate.setRegistryOrganization(registryOrganization);
        return medicineCertificate;
    }

    private String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
