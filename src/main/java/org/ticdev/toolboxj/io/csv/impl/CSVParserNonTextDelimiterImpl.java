package org.ticdev.toolboxj.io.csv.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ticdev.toolboxj.io.csv.CSVParser;
import org.ticdev.toolboxj.io.csv.CSVParserBuilder;
import org.ticdev.toolboxj.io.csv.CSVParserException;
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
 * If there are multiple field separators they are considered separately and not
 * merged. Two consecutive separators, with no space between them are guarding
 * an empty field, which will not be skipped. A separator immediately followed
 * by end-of-line signals the start of an empty string field, as well.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserNonTextDelimiterImpl
    implements
    CSVParser {

    private final int maxRecordCharSize;

    private final int maxFieldSize;

    private final int maxFieldsPerRecord;

    private final char[] delimiters;

    private final char singleDelimiter;

    private final DelimiterPredicate delimiterPredicate;

    private final CSVParserInputHelper inputHelper;

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
        /* optimize isDelimiter a little bit */
        singleDelimiter = delimiters[0];

        if (delimiters.length == 1) {
            delimiterPredicate = (c) -> c == singleDelimiter;
        } else {
            delimiterPredicate = (c) -> {
                for (int i = 0; i < delimiters.length; i++) {
                    if (delimiters[i] == c) {
                        return true;
                    }
                }
                return false;
            };
        }
    }

    private boolean is_delimiter_(char c) {
        return delimiterPredicate.isDelimiter(c);
    }

    @Override
    public List<String>
        parseRecord(Reader reader, List<String> destination)
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

        /* usable character counter */
        int currentCharCount = 0;

        while (true) {

            int token = inputHelper.next(reader);

            switch (token) {
                case CSVParserInputHelper.EOL:
                    destination.add(stringBuilder.substring(0));
                    stringBuilder.delete(0, stringBuilder.length());
                    return destination;
                case CSVParserInputHelper.EOF:
                    if (!destination.isEmpty()
                        || stringBuilder.length() > 0) {
                        destination.add(stringBuilder.substring(0));
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                    return destination;
                default:
                    char c = (char) token;
                    if (is_delimiter_(c)) {
                        destination.add(stringBuilder.substring(0));
                        stringBuilder.delete(0, stringBuilder.length());
                    } else {
                        stringBuilder.append(c);
                        currentCharCount++;
                    }
            }

            if (currentCharCount > maxRecordCharSize) {
                throw new CSVParserLineTooLongException(maxRecordCharSize,
                    inputHelper.lineNumber());
            }
            if (destination.size() > maxFieldsPerRecord) {
                throw new CSVParserTooManyFieldsException(
                    maxFieldsPerRecord, inputHelper.lineNumber());
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

        }

    }

    @Override
    public StringBuilder
        format(List<String> fields, StringBuilder destination) {
        String delimiter = delimiters.length > 0 ? delimiters[0]+"" : "";
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
