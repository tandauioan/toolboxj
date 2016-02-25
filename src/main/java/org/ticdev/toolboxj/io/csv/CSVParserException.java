package org.ticdev.toolboxj.io.csv;

/**
 * Parsing exception thrown by a {@link CSVParser}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserException
    extends
    Exception {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Exception detail/explanation
     */
    private final String details;

    /**
     * Exception line number
     */
    private final long lineNumber;

    /**
     * Class constructor.
     * 
     * @param details
     *            exception details
     * @param lineNumber
     *            parser line number for exception
     */
    public CSVParserException(
        String details,
        long lineNumber) {
        super(String.format("Line: %d. Detail: %s", lineNumber, details));
        this.details = details;
        this.lineNumber = lineNumber;
    }

    /**
     * Returns the exception details
     * 
     * @return the exception details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Returns the parser line number
     * 
     * @return the parser line number
     */
    public long getLineNumber() {
        return lineNumber;
    }

}
