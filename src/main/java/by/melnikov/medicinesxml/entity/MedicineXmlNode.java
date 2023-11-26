package by.melnikov.medicinesxml.entity;

public enum MedicineXmlNode {
    MEDICINES,
    ANTIBIOTIC, NEED_PRESCRIPTION,
    VITAMIN, FOR, GROUP,
    NAME,
    PACKAGE, QUANTITY, PRICE, SIZE,
    COMPANY,
    ANALOG,
    SHAPE,
    DOSAGE, DOSE, FREQUENCY,
    CERTIFICATE, PERMISSION_DATE, EXPIRED_DATE, REGISTRATION_NUMBER, REGISTRY_ORGANIZATION;

    public String getTag() {
        return name().toLowerCase().replaceAll("_", "-");
    }
}
