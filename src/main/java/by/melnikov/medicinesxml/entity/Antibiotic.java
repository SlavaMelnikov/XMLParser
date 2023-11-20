package by.melnikov.medicinesxml.entity;

import java.util.Set;
import java.util.StringJoiner;

public class Antibiotic extends Medicine {
    private boolean needPrescription;

    public Antibiotic() {
    }

    public Antibiotic(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Medicine.Shape shape, MedicineDosage dosage, MedicineCertification certification, boolean needPrescription) {
        super(name, medicinePackage, companies, analogs, shape, dosage, certification);
        this.needPrescription = needPrescription;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Antibiotic that = (Antibiotic) o;

        return needPrescription == that.needPrescription;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (needPrescription ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Antibiotic.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("needPrescription=" + needPrescription)
                .toString();
    }
}
