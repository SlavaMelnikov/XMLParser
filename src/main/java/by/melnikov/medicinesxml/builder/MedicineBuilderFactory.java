package by.melnikov.medicinesxml.builder;

import by.melnikov.medicinesxml.exception.MedicineCustomException;

public class MedicineBuilderFactory {
    public static MedicineBuilderFactory instance;

    private MedicineBuilderFactory() {
    }

    public static MedicineBuilderFactory getInstance() {
        if (instance == null) {
            instance = new MedicineBuilderFactory();
        }
        return instance;
    }
    private enum TypeParser{
        DOM, SAX, STAX_STREAM, STAX_EVENTS, JAXB
    }

    public AbstractMedicineBuilder createMedicineBuilder(String typeParser) throws MedicineCustomException {
        TypeParser type = TypeParser.valueOf(typeParser.trim().toUpperCase().replaceAll("[\\s-]+", "_"));
        switch (type) {
            case DOM -> {
                return new MedicineDomBuilder();
            }
            case SAX -> {
                return new MedicineSaxBuilder();
            }
            case STAX_STREAM -> {
                return new MedicineStaxStreamBuilder();
            }
            case STAX_EVENTS -> {
                return new MedicineStaxEventsBuilder();
            }
//            case JAXB -> {
//                return new MedicineJaxbBuilder();
//            }
            default -> throw new MedicineCustomException("not found such a parser: " + type.name());
        }
    }

}
