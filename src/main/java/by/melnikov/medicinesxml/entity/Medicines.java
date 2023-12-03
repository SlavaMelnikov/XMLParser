package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class Medicines {
    @XmlElement(name = "medicines")
    private Set<Medicine> medicines = new HashSet<>();

    public Medicines() {
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public boolean add(Medicine medicine) {
        return medicines.add(medicine);
    }


}
