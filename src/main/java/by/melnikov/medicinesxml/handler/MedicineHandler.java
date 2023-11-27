package by.melnikov.medicinesxml.handler;

import by.melnikov.medicinesxml.builder.MedicineXmlNode;
import by.melnikov.medicinesxml.entity.*;

import static by.melnikov.medicinesxml.builder.MedicineXmlNode.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;


public class MedicineHandler extends DefaultHandler {
    private Set<Medicine> medicines;
    private Medicine currentMedicine;
    private MedicineXmlNode currentXmlNode;
    private MedicinePackage medicinePackage;
    private MedicineDosage medicineDosage;
    private MedicineCertification medicineCertification;

    public MedicineHandler() {
        medicines = new HashSet<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        currentXmlNode = MedicineXmlNode.valueOf(qName.replaceAll("-", "_").toUpperCase());
        switch (currentXmlNode) {
            case ANTIBIOTIC -> {
                currentMedicine = new Antibiotic();
            }
            case VITAMIN -> {
                Vitamin vitamin = new Vitamin();
                for (int i = 0; i < atts.getLength(); i++) {
                    String att = atts.getValue(i);
                    if (FOR.getTag().equals(atts.getQName(i))) {
                        vitamin.setTarget(Vitamin.Target.valueOf(att.toUpperCase()));
                    } else if (GROUP.getTag().equals(atts.getQName(i))) {
                        vitamin.setGroup(att);
                    }
                }
                currentMedicine = vitamin;
            }
            case PACKAGE -> {
                boolean isQuantity = false;
                medicinePackage = new MedicinePackage();
                for (int i = 0; i < atts.getLength(); i++) {
                    String att = atts.getValue(i);
                    if (PRICE.getTag().equals(atts.getQName(i))) {
                        medicinePackage.setPrice(Integer.parseInt(att));
                    } else if (SIZE.getTag().equals(atts.getQName(i))) {
                        medicinePackage.setSize(MedicinePackage.Size.valueOf(att.toUpperCase()));
                    } else if (QUANTITY.getTag().equals(atts.getQName(i))) {
                        medicinePackage.setQuantity(att);
                        isQuantity = true;
                    }
                }
                if (!isQuantity) {
                    medicinePackage.setQuantity(MedicinePackage.DEFAULT_QUANTITY);
                }
                currentMedicine.setMedicinePackage(medicinePackage);
            }
            case DOSAGE -> {
                medicineDosage = new MedicineDosage();
                for (int i = 0; i < atts.getLength(); i++) {
                    String att = atts.getValue(i);
                    if (DOSE.getTag().equals(atts.getQName(i))) {
                        medicineDosage.setDose(Integer.parseInt(att));
                    } else if (FREQUENCY.getTag().equals(atts.getQName(i))) {
                        medicineDosage.setFrequency(att);
                    }
                }
                currentMedicine.setDosage(medicineDosage);
            }
            case CERTIFICATE -> {
                boolean isRegistryOrganization = false;
                medicineCertification = new MedicineCertification();
                for (int i = 0; i < atts.getLength(); i++) {
                    String att = atts.getValue(i);
                    if (REGISTRATION_NUMBER.getTag().equals(atts.getQName(i))) {
                        medicineCertification.setId(att);
                    } else if (REGISTRY_ORGANIZATION.getTag().equals(atts.getQName(i))) {
                        medicineCertification.setRegistryOrganization(att);
                        isRegistryOrganization = true;
                    }
                }
                if (!isRegistryOrganization) {
                    medicineCertification.setRegistryOrganization(MedicineCertification.DEFAULT_REGISTRY_ORGANIZATION);
                }
                currentMedicine.setCertification(medicineCertification);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (ANTIBIOTIC.getTag().equals(qName) || VITAMIN.getTag().equals(qName)) {
            medicines.add(currentMedicine);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        if (currentXmlNode != null) {
            switch (currentXmlNode) {
                case NAME -> currentMedicine.setName(data);
                case COMPANY -> currentMedicine.addCompany(data);
                case ANALOG -> currentMedicine.addAnalog(data);
                case SHAPE -> currentMedicine.setShape(Medicine.Shape.valueOf(data.toUpperCase()));
                case PERMISSION_DATE -> medicineCertification.setPermissionDate(YearMonth.parse(data));
                case EXPIRED_DATE -> medicineCertification.setExpiredDate(YearMonth.parse(data));
                case NEED_PRESCRIPTION -> ((Antibiotic) currentMedicine).setNeedPrescription(Boolean.parseBoolean(data));
            }
        }
        currentXmlNode = null;
    }

    public Set<Medicine> getSetOfParsedMedicines() {
        return medicines;
    }
}
