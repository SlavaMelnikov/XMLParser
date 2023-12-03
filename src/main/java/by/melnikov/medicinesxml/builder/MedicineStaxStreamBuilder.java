package by.melnikov.medicinesxml.builder;

import static by.melnikov.medicinesxml.builder.MedicineXmlNode.*;
import by.melnikov.medicinesxml.entity.*;
import by.melnikov.medicinesxml.exception.MedicineCustomException;

import static javax.xml.stream.XMLStreamConstants.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MedicineStaxStreamBuilder extends AbstractMedicineBuilder {
    private final Set<Medicine> medicines;
    private Medicine currentMedicine;
    private MedicineXmlNode currentNode;

    public MedicineStaxStreamBuilder() {
         medicines = new HashSet<>();
    }

    @Override
    public Set<Medicine> getMedicines() {
        return medicines;
    }

    @Override
    public void buildSetMedicines(String fileName) throws MedicineCustomException {
        try (FileInputStream input = new FileInputStream(fileName)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(input);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == START_ELEMENT) {
                    currentNode = getCurrentNode(reader);
                    if (currentNode == ANTIBIOTIC || currentNode == VITAMIN) {
                        medicines.add(buildMedicine(reader));
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new MedicineCustomException("StAX parsing error: " + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new MedicineCustomException("File not found: " + fileName, e);
        } catch (IOException e) {
            throw new MedicineCustomException("File reading error", e);
        }

    }

    private Medicine buildMedicine(XMLStreamReader reader) throws XMLStreamException {
        switch (currentNode) {
            case ANTIBIOTIC -> currentMedicine = new Antibiotic();
            case VITAMIN -> {
                Vitamin vitamin = new Vitamin();
                vitamin.setTarget(Vitamin.Target.valueOf(reader.getAttributeValue(null, FOR.getTag()).toUpperCase()));
                vitamin.setGroup(reader.getAttributeValue(null, GROUP.getTag()));
                currentMedicine = vitamin;
            }
        }
        int type;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case START_ELEMENT -> {
                    currentNode = getCurrentNode(reader);
                    switch (currentNode) {
                        case NAME -> currentMedicine.setName(getXMLText(reader));
                        case PACKAGE -> currentMedicine.setMedicinePackage(buildMedicinePackage(reader));
                        case COMPANY -> currentMedicine.addCompany(getXMLText(reader));
                        case ANALOG -> currentMedicine.addAnalog(getXMLText(reader));
                        case SHAPE -> currentMedicine.setShape(Medicine.Shape.valueOf(getXMLText(reader).toUpperCase()));
                        case DOSAGE -> currentMedicine.setDosage(buildMedicineDosage(reader));
                        case CERTIFICATE -> currentMedicine.setCertification(buildMedicineCertification(reader));
                        case NEED_PRESCRIPTION -> ((Antibiotic) currentMedicine).setNeedPrescription(Boolean.parseBoolean(getXMLText(reader)));
                    }
                }
                case END_ELEMENT -> {
                    currentNode = getCurrentNode(reader);
                    if (currentNode == ANTIBIOTIC || currentNode == VITAMIN) {
                        return currentMedicine;
                    }
                }
            }
        }
        throw new XMLStreamException("unknown element");
    }

    private MedicinePackage buildMedicinePackage(XMLStreamReader reader) {
        MedicinePackage medicinePackage = new MedicinePackage();
        medicinePackage.setPrice(Integer.parseInt(reader.getAttributeValue(null, PRICE.getTag())));
        medicinePackage.setSize(MedicinePackage.Size.valueOf(reader.getAttributeValue(null, SIZE.getTag()).toUpperCase()));
        Optional<String> quantity = Optional.ofNullable(reader.getAttributeValue(null, QUANTITY.getTag()));
        medicinePackage.setQuantity(quantity.orElse(MedicinePackage.DEFAULT_QUANTITY));
        return medicinePackage;
    }

    private MedicineDosage buildMedicineDosage(XMLStreamReader reader) {
        MedicineDosage medicineDosage = new MedicineDosage();
        medicineDosage.setDose(Integer.parseInt(reader.getAttributeValue(null, DOSE.getTag())));
        medicineDosage.setFrequency(reader.getAttributeValue(null, FREQUENCY.getTag()));
        return medicineDosage;
    }

    private MedicineCertificate buildMedicineCertification(XMLStreamReader reader) throws XMLStreamException {
        MedicineCertificate medicineCertificate = new MedicineCertificate();
        medicineCertificate.setId(reader.getAttributeValue(null, REGISTRATION_NUMBER.getTag()));
        Optional<String> registryOrganization = Optional.ofNullable(reader.getAttributeValue(null, REGISTRY_ORGANIZATION.getTag()));
        medicineCertificate.setRegistryOrganization(registryOrganization.orElse(MedicineCertificate.DEFAULT_REGISTRY_ORGANIZATION));
        boolean isPermissionDate = false;
        boolean isExpiredDate = false;
        int type;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case START_ELEMENT -> {
                    currentNode = getCurrentNode(reader);
                    switch (currentNode) {
                        case PERMISSION_DATE -> {
                            medicineCertificate.setPermissionDate(YearMonth.parse(getXMLText(reader)));
                            isPermissionDate = true;
                        }
                        case EXPIRED_DATE -> {
                            medicineCertificate.setExpiredDate(YearMonth.parse(getXMLText(reader)));
                            isExpiredDate = true;
                        }
                    }
                }
                case END_ELEMENT -> {
                    if (isPermissionDate && isExpiredDate) return medicineCertificate;
                }
            }
        }
        throw new XMLStreamException("unknown element");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private MedicineXmlNode getCurrentNode(XMLStreamReader reader) {
        return MedicineXmlNode.valueOf(reader.getLocalName().toUpperCase().replaceAll("-", "_"));
    }
}
