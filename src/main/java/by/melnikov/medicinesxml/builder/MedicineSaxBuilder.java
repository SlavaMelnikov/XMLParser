package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.entity.Medicine;
import by.melnikov.medicinesxml.exception.MedicineCustomException;
import by.melnikov.medicinesxml.handler.MedicineErrorHandler;
import by.melnikov.medicinesxml.handler.MedicineHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MedicineSaxBuilder extends AbstractMedicineBuilder {
    private Set<Medicine> medicines;
    private MedicineHandler medicineHandler;
    private MedicineErrorHandler medicineErrorHandler;
    private XMLReader reader;

    public MedicineSaxBuilder() throws MedicineCustomException {
        medicines = new HashSet<>();
        medicineHandler = new MedicineHandler();
        medicineErrorHandler = new MedicineErrorHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            throw new MedicineCustomException("SAX parser creating error", e);
        }
        reader.setErrorHandler(medicineErrorHandler);
        reader.setContentHandler(medicineHandler);
    }

    @Override
    public Set<Medicine> getMedicines() {
        return medicines;
    }

    @Override
    public void buildSetMedicines(String fileName) throws MedicineCustomException {
        try {
            reader.parse(fileName);
        } catch (IOException | SAXException e) {
            throw new MedicineCustomException("parsing file error", e);
        }
        medicines = medicineHandler.getSetOfParsedMedicines();
    }

}
