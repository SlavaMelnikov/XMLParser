package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Set;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Antibiotic", propOrder = {
        "needPrescription"
})
public class Antibiotic extends Medicine {
    @XmlElement(name = "need-prescription")
    private boolean needPrescription;

    public Antibiotic() {
    }

    public Antibiotic(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Medicine.Shape shape, MedicineDosage dosage, MedicineCertificate certification, boolean needPrescription) {
        super(name, medicinePackage, companies, analogs, shape, dosage, certification);
        this.needPrescription = needPrescription;
    }

    public boolean needPrescription() {
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
        return new StringJoiner("\t", Antibiotic.class.getSimpleName() + ":\n", "\n")
                .add(super.toString())
                .add("needPrescription=" + needPrescription)
                .toString();
    }
}
