package by.melnikov.medicinesxml.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MedicineErrorHandler implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger();
    private String getLineColumnNumber(SAXParseException e) {
        return e.getLineNumber() + " : " + e.getColumnNumber();
    }
    @Override
    public void warning(SAXParseException e) throws SAXException {
        logger.warn(getLineColumnNumber(e) + " - " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        logger.error(getLineColumnNumber(e) + " - " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        logger.fatal(getLineColumnNumber(e) + " - " + e.getMessage());
    }
}
