package org.ticdev.toolboxj.io.csv.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ticdev.toolboxj.io.csv.CSVParser;
import org.ticdev.toolboxj.io.csv.CSVParserBuilder;
import org.ticdev.toolboxj.io.csv.CSVParserException;
import org.ticdev.toolboxj.io.csv.CSVParserFieldTooLargeException;
import org.ticdev.toolboxj.io.csv.CSVParserInputHelper;
import org.ticdev.toolboxj.io.csv.CSVParserLineTooLongException;
import org.ticdev.toolboxj.io.csv.CSVParserTooManyFieldsException;

/**
 * {@link CSVParser} implementation that does not take a text delimiter into
 * consideration when parsing.
 * 
 * <p>
 * E.g. for a comma delimited string: a,b,,"cde" the record will contain 3
 * fields: 1. a, 2. b, 3. , 4. "cde". The last field will contain the double
 * quotes.
 * </p>
 * 
 * <p>
 * If there are multiple field separators they are not merged. Two consecutive
 * separators, with no space between them are guarding an empty field, which
 * will not be skipped. A separator immediately followed by end-of-line signals
 * the start of an empty string field, as well.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserNonTextDelimiterImpl
    implements
    CSVParser {

    /**
     * maximum size of a record
     */
    private final int maxRecordCharSize;

    /**
     * maximum size of a field
     */
    private final int maxFieldSize;

    /**
     * maximum number of fields in a record
     */
    private final int maxFieldsPerRecord;

    /**
     * field delimiters
     */
    private final char[] delimiters;

    /**
     * if only one delimiter is used
     */
    private final char singleDelimiter;

    /**
     * Delimiter predicate function
     */
    private final DelimiterPredicate delimiterPredicate;

    /**
     * Reader input helper
     */
    private final CSVParserInputHelper inputHelper;

    /**
     * Class constructor
     * 
     * @param parserBuilder
     *            the parser builder
     * @param inputHelper
     *            the reader input helper
     */
    public CSVParserNonTextDelimiterImpl(
        CSVParserBuilder parserBuilder,
        CSVParserInputHelper inputHelper) {
        maxRecordCharSize = parserBuilder.getMaxRecordCharSize();
        maxFieldSize = parserBuilder.getMaxFieldSize();
        maxFieldsPerRecord = parserBuilder.getMaxFieldsPerRecord();
        this.inputHelper = inputHelper;
        List<Character> delimitersList = parserBuilder.getDelimiters();
        delimiters = new char[delimitersList.size()];
        int delimitersIndex = 0;
        for (Character c : delimitersList) {
            delimiters[delimitersIndex++] = c;
        }

        if (delimiters.length == 0) {
            singleDelimiter = ' ';
            delimiterPredicate = (c) -> false;
        } else {
            /* optimize isDelimiter a little bit */
            singleDelimiter = delimiters[0];

            if (delimiters.length == 1) {
                delimiterPredicate = (c) -> c == singleDelimiter;
            } else {
                delimiterPredicate = (c) -> {
                    for (char delimiter : delimiters) {
                        if (delimiter == c) {
                            return true;
                        }
                    }
                    return false;
                };
            }
        }
    }

    /**
     * Returns true if the character is a field delimiter and false otherwise
     * 
     * @param c
     *            the character
     * @return true if the character is a field delimiter and false otherwise
     */
    private boolean is_delimiter_(char c) {
        return delimiterPredicate.isDelimiter(c);
    }

    @Override
    public List<String>
        parseRecord(Reader reader,List<String> destination)
            throws CSVParserException,
            CSVParserLineTooLongException,
            CSVParserTooManyFieldsException,
            IOException,
            InterruptedException,
            Exception {
        /* accumulator */
        StringBuilder stringBuilder = new StringBuilder();

        if (destination == null) {
            destination = new LinkedList<>();
        }

        int fields = 0;

        /* usable character counter */
        int currentCharCount = 0;

        while (true) {

            int token = inputHelper.next(reader);

            switch (token) {
                case CSVParserInputHelper.EOL:
                    if(fields == maxFieldsPerRecord) {
                        throw new CSVParserTooManyFieldsException(
                            maxFieldsPerRecord, inputHelper.lineNumber());
                    }
                    destination.add(stringBuilder.substring(0));
                    return destination;
                case CSVParserInputHelper.EOF:
                    if (fields > 0
                        || stringBuilder.length() > 0) {
                        if(fields == maxFieldsPerRecord) {
                            throw new CSVParserTooManyFieldsException(
                                maxFieldsPerRecord, inputHelper.lineNumber());
                        }
                        destination.add(stringBuilder.substring(0));
                        return destination;
                    }
                    return null;
                default:
                    char c = (char) token;
                    if (is_delimiter_(c)) {
                        if(fields++ == maxFieldsPerRecord) {
                            throw new CSVParserTooManyFieldsException(
                                maxFieldsPerRecord, inputHelper.lineNumber());
                        }
                        destination.add(stringBuilder.substring(0));
                        stringBuilder.delete(0, stringBuilder.length());
                    } else {
                        if (stringBuilder.length() == maxFieldSize) {
                            throw new CSVParserFieldTooLargeException(
                                maxFieldSize, inputHelper.lineNumber());
                        }
                        stringBuilder.append(c);
                        if(currentCharCount++ == maxRecordCharSize) {
                            throw new CSVParserLineTooLongException(
                                maxRecordCharSize,
                                inputHelper.lineNumber());
                        }
                    }
            }

            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

        }

    }

    @Override
    public StringBuilder
        format(List<String> fields, StringBuilder destination) {
        String delimiter = delimiters.length > 0 ? delimiters[0] + "" : "";
        Iterator<String> fieldsIterator = fields.iterator();
        if (fieldsIterator.hasNext()) {
            destination.append(fieldsIterator.next());
        }
        while (fieldsIterator.hasNext()) {
            destination.append(delimiter).append(fieldsIterator.next());
        }
        return destination;
    }

}
