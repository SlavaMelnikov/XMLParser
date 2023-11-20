package by.melnikov.medicinesxml.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public abstract class Medicine {
    public enum Shape {
        TABLETS, CAPSULES, POWDER, AMPOULES
    }
    private String name;
    private MedicinePackage medicinePackage;
    private Set<String> companies = new HashSet<>();
    private Set<String> analogs = new HashSet<>();
    private Shape shape;
    private MedicineDosage dosage;
    private MedicineCertification certification;

    public Medicine() {
    }

    public Medicine(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Shape shape, MedicineDosage dosage, MedicineCertification certification) {
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

    public MedicineCertification getCertification() {
        return certification;
    }

    public void setCertification(MedicineCertification certification) {
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
        return new StringJoiner(", ")
                .add("name='" + name + "'")
                .add("medicinePackage=" + medicinePackage)
                .add("companies=" + companies)
                .add("analogs=" + analogs)
                .add("shape=" + shape)
                .add("dosage=" + dosage)
                .add("certification=" + certification)
                .toString();
    }
}
