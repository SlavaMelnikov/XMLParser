package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vitamin")
public class Vitamin extends Medicine {

    public enum Target {
        ANY, MEN, WOMEN, CHILDREN
    }
    @XmlAttribute(name = "for", required = true)
    private Target target;
    @XmlAttribute(name = "group", required = true)
    private String group;

    public Vitamin() {
    }

    public Vitamin(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Shape shape, MedicineDosage dosage, MedicineCertificate certification, Target target, String group) {
        super(name, medicinePackage, companies, analogs, shape, dosage, certification);
        this.target = target;
        this.group = group;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Vitamin vitamins = (Vitamin) o;

        if (target != vitamins.target) return false;
        return Objects.equals(group, vitamins.group);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner("\t", Vitamin.class.getSimpleName() + ":\n", "\n")
                .add(super.toString())
                .add("targetPeople=" + target + "\n")
                .add("group=" + group)
                .toString();
    }
}
