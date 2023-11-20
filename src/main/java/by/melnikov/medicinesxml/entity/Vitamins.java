package by.melnikov.medicinesxml.entity;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class Vitamins extends Medicine {

    private enum TargetPeople {
        ANY, MEN, WOMEN, CHILDREN
    }
    private TargetPeople targetPeople;
    private String group;

    public Vitamins() {
    }

    public Vitamins(String name, MedicinePackage medicinePackage, Set<String> companies, Set<String> analogs, Shape shape, MedicineDosage dosage, MedicineCertification certification, TargetPeople targetPeople, String group) {
        super(name, medicinePackage, companies, analogs, shape, dosage, certification);
        this.targetPeople = targetPeople;
        this.group = group;
    }

    public TargetPeople getTargetPeople() {
        return targetPeople;
    }

    public void setTargetPeople(TargetPeople targetPeople) {
        this.targetPeople = targetPeople;
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

        Vitamins vitamins = (Vitamins) o;

        if (targetPeople != vitamins.targetPeople) return false;
        return Objects.equals(group, vitamins.group);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetPeople != null ? targetPeople.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vitamins.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("targetPeople=" + targetPeople)
                .add("group=" + group)
                .toString();
    }
}
