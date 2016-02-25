package org.ticdev.toolboxj.io.csv;

/**
 * This exception specifies that a character limit has been reached during
 * parsing of one record.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserLineTooLongException
    extends
    AbstractCSVParserLimitException {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Class constructor.
     * 
     * @param restriction
     *            the character limit.
     * @param lineNumber
     *            the line number where the exception occurred.
     */
    public CSVParserLineTooLongException(
        int restriction,
        long lineNumber) {
        super(restriction, lineNumber);
    }

}
