package by.melnikov.medicinesxml.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedicineCustomException extends Exception {
    private static final Logger logger = LogManager.getLogger();
    public MedicineCustomException() {
        super();
    }

    public MedicineCustomException(String message) {
        super(message);
        logger.error(message);
    }

    public MedicineCustomException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message + cause.getMessage());
    }

    public MedicineCustomException(Throwable cause) {
        super(cause);
        logger.error(cause.getMessage());
    }
}
