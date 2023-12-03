package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.entity.Medicine;
import by.melnikov.medicinesxml.entity.Medicines;
import by.melnikov.medicinesxml.exception.MedicineCustomException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

public class MedicineJaxbBuilder extends AbstractMedicineBuilder {
    private Medicines medicines = new Medicines();

    @Override
    public Set<Medicine> getMedicines() {
        return medicines.getMedicines();
    }

    @Override
    public void buildSetMedicines(String fileName) throws MedicineCustomException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Medicines.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            FileReader reader = new FileReader(fileName);
            medicines = (Medicines) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new MedicineCustomException("Unmarshalling error", e);
        } catch (FileNotFoundException e) {
            throw new MedicineCustomException("File not found: " + fileName, e);
        }

    }
}
