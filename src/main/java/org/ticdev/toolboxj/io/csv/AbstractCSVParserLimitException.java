package org.ticdev.toolboxj.io.csv;

/**
 * Abstract exception class used for implementations that have an integer based
 * restriction.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
class AbstractCSVParserLimitException
    extends
    Exception {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * The line number where the exception occurred.
     */
    private final long lineNumber;

    /**
     * The restriction limit.
     */
    private final int restriction;

    /**
     * Class constructor.
     * 
     * @param restriction
     *            the restriction limit.
     * @param lineNumber
     *            the line number where the violation/exception occurred.
     */
    protected AbstractCSVParserLimitException(
        int restriction,
        long lineNumber) {
        super(
            String.format("Line: %d. Limit: %d", lineNumber, restriction));
        this.restriction = restriction;
        this.lineNumber = lineNumber;
    }

    /**
     * Returns the restriction limit.
     * 
     * @return the restriction limit.
     */
    int getRestriction() {
        return restriction;
    }

    /**
     * Returns the line number.
     * 
     * @return the line number.
     */
    long getLineNumber() {
        return lineNumber;
    }

}
