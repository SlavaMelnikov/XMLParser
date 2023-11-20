package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.entity.Medicine;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMedicinesBuilder {
    protected Set<Medicine> medicines;

    public AbstractMedicinesBuilder() {
        medicines = new HashSet<>();
    }

    public AbstractMedicinesBuilder(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    abstract public void buildSetMedicines(String fileName);
}
