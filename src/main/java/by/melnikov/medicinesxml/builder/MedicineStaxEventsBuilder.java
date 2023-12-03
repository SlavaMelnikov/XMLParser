package by.melnikov.medicinesxml.builder;

import static by.melnikov.medicinesxml.builder.MedicineXmlNode.*;
import by.melnikov.medicinesxml.entity.*;
import by.melnikov.medicinesxml.exception.MedicineCustomException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MedicineStaxEventsBuilder extends AbstractMedicineBuilder {
    private final Set<Medicine> medicines;
    private Medicine currentMedicine;
    private MedicineXmlNode currentNode;
    private XMLEventReader reader;
    private XMLEvent event;

    public MedicineStaxEventsBuilder() {
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
            reader = factory.createXMLEventReader(input);
            while (reader.hasNext()) {
                event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    currentNode = getCurrentNode(startElement);
                    if (currentNode == ANTIBIOTIC || currentNode == VITAMIN) {
                        medicines.add(buildMedicine(startElement));
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

    private Medicine buildMedicine(StartElement startElement) throws XMLStreamException {
        switch (currentNode) {
            case ANTIBIOTIC -> currentMedicine = new Antibiotic();
            case VITAMIN -> {
                Vitamin vitamin = new Vitamin();
                vitamin.setTarget(Vitamin.Target.valueOf(startElement.getAttributeByName(new QName(FOR.getTag())).getValue().toUpperCase()));
                vitamin.setGroup(startElement.getAttributeByName(new QName(GROUP.getTag())).getValue());
                currentMedicine = vitamin;
            }
        }
        while (reader.hasNext()) {
            event = reader.nextEvent();
            if (event.isStartElement()) {
                startElement = event.asStartElement();
                currentNode = getCurrentNode(startElement);
                switch (currentNode) {
                    case NAME -> currentMedicine.setName(getXMLText(reader));
                    case PACKAGE -> currentMedicine.setMedicinePackage(buildMedicinePackage(startElement));
                    case COMPANY -> currentMedicine.addCompany(getXMLText(reader));
                    case ANALOG -> currentMedicine.addAnalog(getXMLText(reader));
                    case SHAPE -> currentMedicine.setShape(Medicine.Shape.valueOf(getXMLText(reader).toUpperCase()));
                    case DOSAGE -> currentMedicine.setDosage(buildMedicineDosage(startElement));
                    case CERTIFICATE -> currentMedicine.setCertification(buildMedicineCertification(startElement));
                    case NEED_PRESCRIPTION -> ((Antibiotic) currentMedicine).setNeedPrescription(Boolean.parseBoolean(getXMLText(reader)));
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                currentNode = MedicineXmlNode.valueOf(endElement.getName().getLocalPart().toUpperCase().replaceAll("-", "_"));
                if (currentNode == ANTIBIOTIC || currentNode == VITAMIN) {
                    return currentMedicine;
                }
            }
        }
        throw new XMLStreamException("unknown element");
    }

    private MedicinePackage buildMedicinePackage(StartElement startElement) {
        MedicinePackage medicinePackage = new MedicinePackage();
        medicinePackage.setPrice(Integer.parseInt(startElement.getAttributeByName(new QName(PRICE.getTag())).getValue()));
        medicinePackage.setSize(MedicinePackage.Size.valueOf(startElement.getAttributeByName(new QName(SIZE.getTag())).getValue().toUpperCase()));
        Optional<Attribute> quantityOptional = Optional.ofNullable(startElement.getAttributeByName(new QName(QUANTITY.getTag())));
        String quantity = quantityOptional.isPresent() ? quantityOptional.get().getValue() : MedicinePackage.DEFAULT_QUANTITY;
        medicinePackage.setQuantity(quantity);
        return medicinePackage;
    }

    private MedicineDosage buildMedicineDosage(StartElement startElement) {
        MedicineDosage medicineDosage = new MedicineDosage();
        medicineDosage.setDose(Integer.parseInt(startElement.getAttributeByName(new QName(DOSE.getTag())).getValue()));
        medicineDosage.setFrequency(startElement.getAttributeByName(new QName(FREQUENCY.getTag())).getValue());
        return medicineDosage;
    }

    private MedicineCertificate buildMedicineCertification(StartElement startElement) throws XMLStreamException {
        MedicineCertificate medicineCertificate = new MedicineCertificate();
        medicineCertificate.setId(startElement.getAttributeByName(new QName(REGISTRATION_NUMBER.getTag())).getValue());
        Optional<Attribute> registryOrganizationOptional = Optional.ofNullable(startElement.getAttributeByName(new QName(REGISTRY_ORGANIZATION.getTag())));
        String registryOrganization = registryOrganizationOptional.isPresent() ? registryOrganizationOptional.get().getValue() : MedicineCertificate.DEFAULT_REGISTRY_ORGANIZATION;
        medicineCertificate.setRegistryOrganization(registryOrganization);
        boolean isPermissionDate = false;
        boolean isExpiredDate = false;
        while (reader.hasNext()) {
            event = reader.nextEvent();
            if (event.isStartElement()) {
                startElement = event.asStartElement();
                currentNode = getCurrentNode(startElement);
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
            if (event.isEndElement()) {
                if (isPermissionDate && isExpiredDate) {
                    return medicineCertificate;
                }
            }

        }
        throw new XMLStreamException("unknown element");
    }

    private String getXMLText(XMLEventReader reader) throws XMLStreamException {
        return reader.nextEvent().asCharacters().getData();
    }

    private MedicineXmlNode getCurrentNode(StartElement startElement) {
        return MedicineXmlNode.valueOf(startElement.getName().getLocalPart().toUpperCase().replaceAll("-", "_"));
    }
}
