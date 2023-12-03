package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.*;
import java.util.Objects;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dosage")
public class MedicineDosage {
    @XmlAttribute(name = "dose", required = true)
    @XmlSchemaType(name = "positiveInteger")
    private int dose;
    @XmlAttribute(name = "frequency", required = true)
    private String frequency;

    public MedicineDosage() {
    }

    public MedicineDosage(int dose, String frequency) {
        this.dose = dose;
        this.frequency = frequency;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineDosage that = (MedicineDosage) o;

        if (dose != that.dose) return false;
        return Objects.equals(frequency, that.frequency);
    }

    @Override
    public int hashCode() {
        int result = dose;
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("dose=" + dose)
                .add("frequency='" + frequency + "'")
                .toString();
    }
}
