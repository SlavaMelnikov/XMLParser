package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Medicine", propOrder = {
        "name",
        "medicinePackage",
        "companies",
        "analogs",
        "shape",
        "dosage",
        "certification"
})
@XmlSeeAlso({
        Antibiotic.class,
        Vitamin.class
})
public abstract class Medicine {
    public enum Shape {
        TABLETS, CAPSULES, POWDER, AMPOULES
    }
    @XmlElement(required = true)
    private String name;
    @XmlElement(name = "package", required = true)
    private MedicinePackage medicinePackage;
    @XmlElement(required = true)
    private Set<String> companies = new HashSet<>();
    private Set<String> analogs = new HashSet<>();
    @XmlElement(required = true)
    private Shape shape;
    @XmlElement(name = "dosage", required = true)
    private MedicineDosage dosage;
    @XmlElement(name = "certificate", required = true)
    private MedicineCertificate certification;

    public Medicine() {
    }

    public Medicine(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Shape shape, MedicineDosage dosage, MedicineCertificate certification) {
        this.name = name;
        this.medicinePackage = medicinePackage;
        this.companies = companies;
        this.analogs = analogs;
        this.shape = shape;
        this.dosage = dosage;
        this.certification = certification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicinePackage getMedicinePackage() {
        return medicinePackage;
    }

    public void setMedicinePackage(MedicinePackage medicinePackage) {
        this.medicinePackage = medicinePackage;
    }

    public Set<String> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<String> companies) {
        this.companies = companies;
    }
    public void addCompany(String company) {
        this.companies.add(company);
    }

    public Set<String> getAnalogs() {
        return analogs;
    }

    public void setAnalogs(Set<String> analogs) {
        this.analogs = analogs;
    }
    public void addAnalog(String analog) {
        this.analogs.add(analog);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public MedicineDosage getDosage() {
        return dosage;
    }

    public void setDosage(MedicineDosage dosage) {
        this.dosage = dosage;
    }

    public MedicineCertificate getCertification() {
        return certification;
    }

    public void setCertification(MedicineCertificate certification) {
        this.certification = certification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicine medicine = (Medicine) o;

        if (!Objects.equals(name, medicine.name)) return false;
        if (!Objects.equals(medicinePackage, medicine.medicinePackage))
            return false;
        if (!Objects.equals(companies, medicine.companies)) return false;
        if (!Objects.equals(analogs, medicine.analogs)) return false;
        if (shape != medicine.shape) return false;
        if (!Objects.equals(dosage, medicine.dosage)) return false;
        return Objects.equals(certification, medicine.certification);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (medicinePackage != null ? medicinePackage.hashCode() : 0);
        result = 31 * result + (companies != null ? companies.hashCode() : 0);
        result = 31 * result + (analogs != null ? analogs.hashCode() : 0);
        result = 31 * result + (shape != null ? shape.hashCode() : 0);
        result = 31 * result + (dosage != null ? dosage.hashCode() : 0);
        result = 31 * result + (certification != null ? certification.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner("\t")
                .add("\tname='" + name + "'\n")
                .add("medicinePackage=" + medicinePackage + "\n")
                .add("companies=" + companies + "\n")
                .add("analogs=" + analogs + "\n")
                .add("shape=" + shape + "\n")
                .add("dosage=" + dosage + "\n")
                .add("certification=" + certification + "\n")
                .toString();
    }
}
