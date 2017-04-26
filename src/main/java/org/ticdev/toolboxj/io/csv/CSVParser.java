package org.ticdev.toolboxj.io.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Interface for a CSV parser.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public interface CSVParser {

    /**
     * Reads the next record from the given reader and places the fields of that
     * record in the given destination list.
     * 
     * <p>
     * A new list is created, used, and returned if the destination list is
     * null.
     * </p>
     * 
     * <p>
     * null is returned if the end-of-file has been reached on the reader.
     * </p>
     * 
     * @param reader
     *            the reader used to read the fields for the next record.
     * @param destination
     *            the destination list for the fields of the record. If null, a
     *            new list will be created.
     * 
     * @return null if there are no more records to read and the list of fields
     *         for the currently read record otherwise.
     * 
     * @throws CSVParserException
     *             if a parser exception occurred
     * @throws CSVParserLineTooLongException
     *             if a restriction on the line length has occurred
     * @throws CSVParserTooManyFieldsException
     *             if a restriction on the number of fields in a record has
     *             occurred
     * @throws IOException
     *             if an IO exception has occurred while reading the record
     * @throws InterruptedException
     *             if the thread is interrupted during parsing
     */
    List<String>
        parseRecord(Reader reader, List<String> destination)
            throws CSVParserException,
            CSVParserLineTooLongException,
            CSVParserTooManyFieldsException,
            IOException,
            InterruptedException,
            CSVParserFieldTooLargeException;

    /**
     * Given a list of fields in a CSV record, this method will build the string
     * object following the configuration of this parser.
     * 
     * @param fields
     *            all the fields of the record
     * 
     * @param destination
     *            the destination {@link StringBuilder} to which the string is
     *            appended (without clearing the previous content).
     * @return the destination {@link StringBuilder} if it's not null and a
     *         newly created instance containing the formatted line, otherwise.
     */
    StringBuilder format(List<String> fields, StringBuilder destination);


}
