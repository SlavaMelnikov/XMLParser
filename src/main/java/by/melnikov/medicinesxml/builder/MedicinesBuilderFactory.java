package by.melnikov.medicinesxml.builder;

public class MedicinesBuilderFactory {
    public static MedicinesBuilderFactory instance;

    private MedicinesBuilderFactory() {
    }

    public static MedicinesBuilderFactory getInstance() {
        if (instance == null) {
            instance = new MedicinesBuilderFactory();
        }
        return instance;
    }
    private enum TypeParser{
        DOM, SAX, STAX_STREAM, STAX_EVENT, JAXB
    }

    public AbstractMedicinesBuilder createMedicinesBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.trim().toUpperCase().replaceFirst("[\\s-]+", "_"));
        switch (type) {
            case DOM -> {
                return new MedicinesDOMBuilder();
            }
            case SAX -> {
                return new MedicinesSAXBuilder();
            }
            case STAX_STREAM -> {
                return new MedicinesStAXStreamBuilder();
            }
            case STAX_EVENT -> {
                return new MedicinesStAXEventBuilder();
            }
            case JAXB -> {
                return new MedicinesJAXBBuilder();
            }
            default -> throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }

}
