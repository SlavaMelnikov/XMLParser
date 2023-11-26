package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.entity.Medicine;
import by.melnikov.medicinesxml.exception.MedicineCustomException;

import java.util.Set;

public abstract class AbstractMedicineBuilder {
    abstract public Set<Medicine> getMedicines();
    abstract public void buildSetMedicines(String fileName) throws MedicineCustomException;
}
