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
 * Concrete implementation of a csv parser that uses a text delimiter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class CSVParserTextDelimiterImpl
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
     * The text delimiter
     */
    private final char textDelimiter;

    private final String multiLineEOL;

    private final int multiLineEOLLength;

    /**
     * Reader input helper
     */
    private final CSVParserInputHelper inputHelper;

    /**
     * Class constructor.
     *
     * @param parserBuilder the parser builder used to configure this
     *                      parser.
     * @param inputHelper   the input helper to use with a reader.
     */
    public CSVParserTextDelimiterImpl(
        CSVParserBuilder parserBuilder,
        CSVParserInputHelper inputHelper) {
        maxRecordCharSize = parserBuilder.getMaxRecordCharSize();
        maxFieldSize = parserBuilder.getMaxFieldSize();
        maxFieldsPerRecord = parserBuilder.getMaxFieldsPerRecord();
        textDelimiter = parserBuilder.getTextDelimiter();
        multiLineEOL = String.format(parserBuilder.getMultiLineEOL());
        multiLineEOLLength = multiLineEOL.length();
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
        parseRecord(Reader reader, List<String> destination)
            throws CSVParserException,
            CSVParserLineTooLongException,
            CSVParserTooManyFieldsException,
            IOException,
            InterruptedException,
            Exception {

        if (destination == null) {
            destination = new LinkedList<>();
        }

        StringBuilder stringBuilder = new StringBuilder();

        int currentRecordCharCount = 0;

        boolean quoteString = false;

        boolean pendingQuoteExit = false;

        /*
         * this is set to true when a quote starts to catch empty fields that
         * are not counted toward useful characters, but that still represent a
         * valid record
         */
        boolean anyOtherActivityInRecord = false;

        int fields = 0;

        while (true) {

            int token = inputHelper.next(reader);

            switch (token) {
                case CSVParserInputHelper.EOL:

                    if (!quoteString) {
                        /* not quoted - we have a complete record */
                        if (fields == maxFieldsPerRecord) {
                            throw new CSVParserTooManyFieldsException(
                                maxFieldsPerRecord,
                                inputHelper.lineNumber());
                        }
                        destination.add(stringBuilder.substring(0));
                        return destination;
                    } else {
                        /* quoted */
                        if (pendingQuoteExit) {
                            /*
                             * the pending text delimiter just closed the quote
                             */
                            if (fields == maxFieldsPerRecord) {
                                throw new CSVParserTooManyFieldsException(
                                    maxFieldsPerRecord,
                                    inputHelper.lineNumber());
                            }
                            destination.add(stringBuilder.substring(0));
                            return destination;
                        } else {
                            /*
                             * we are in a text delimiter, we just add EOL
                             * marker and continue parsing the field (on the
                             * next line)
                             */
                            if (currentRecordCharCount++ == maxRecordCharSize) {
                                throw new CSVParserLineTooLongException(
                                    maxRecordCharSize,
                                    inputHelper.lineNumber());
                            }
                            if (maxFieldSize - stringBuilder
                                .length() < multiLineEOLLength) {
                                throw new CSVParserFieldTooLargeException(
                                    maxFieldSize,
                                    inputHelper.lineNumber());
                            }
                            stringBuilder.append(multiLineEOL);
                        }

                    }
                    break;

                case CSVParserInputHelper.EOF:
                    /* end of file was encountered */
                    if (!quoteString || pendingQuoteExit) {
                        /* if not quoted or if close quote already read */

                        if (fields > 0 || stringBuilder.length() != 0
                            || anyOtherActivityInRecord) {
                            if (fields == maxFieldsPerRecord) {
                                throw new CSVParserTooManyFieldsException(
                                    maxFieldsPerRecord,
                                    inputHelper.lineNumber());
                            }
                            fields++;
                            destination.add(stringBuilder.substring(0));
                        }

                        if (fields != 0) {
                            return destination;
                        }
                        return null;

                    } else {
                        throw new CSVParserException(
                            "End-of-file reached while reading quoted string",
                            inputHelper.lineNumber());
                    }
                    /* no break because we return */

                default:

                    char c = (char) token;

                    if (c == textDelimiter) {
                        /* text delimiter character */

                        if (!quoteString) {
                            quoteString = true;
                            anyOtherActivityInRecord = true;
                        } else {

                            if (!pendingQuoteExit) {
                                /* this may be the closing quote */
                                pendingQuoteExit = true;
                            } else {
                                /*
                                 * doubling a text delimiter inside the text
                                 * delimited area
                                 */
                                if (stringBuilder
                                    .length() == maxFieldSize) {
                                    throw new CSVParserFieldTooLargeException(
                                        maxFieldSize,
                                        inputHelper.lineNumber());
                                }
                                stringBuilder.append(textDelimiter);
                                if (currentRecordCharCount++ == maxRecordCharSize) {
                                    throw new CSVParserLineTooLongException(
                                        maxRecordCharSize,
                                        inputHelper.lineNumber());
                                }
                                pendingQuoteExit = false;
                            }
                        }
                    } else {

                        if (is_delimiter_(c)) {

                            if (!quoteString) {

                                if (fields++ == maxFieldsPerRecord) {
                                    throw new CSVParserTooManyFieldsException(
                                        maxFieldsPerRecord,
                                        inputHelper.lineNumber());
                                }
                                destination
                                    .add(stringBuilder.substring(0));
                                stringBuilder.delete(0,
                                    stringBuilder.length());

                            } else {

                                if (pendingQuoteExit) {
                                    pendingQuoteExit = false;
                                    quoteString = false;
                                    if (fields++ == maxFieldsPerRecord) {
                                        throw new CSVParserTooManyFieldsException(
                                            maxFieldsPerRecord,
                                            inputHelper.lineNumber());
                                    }
                                    destination
                                        .add(stringBuilder.substring(0));
                                    stringBuilder.delete(0,
                                        stringBuilder.length());
                                } else {
                                    if (stringBuilder
                                        .length() == maxFieldSize) {
                                        throw new CSVParserFieldTooLargeException(
                                            maxFieldSize,
                                            inputHelper.lineNumber());
                                    }
                                    stringBuilder.append(c);
                                    if (currentRecordCharCount++ == maxRecordCharSize) {
                                        throw new CSVParserLineTooLongException(
                                            maxRecordCharSize,
                                            inputHelper.lineNumber());
                                    }
                                }

                            }

                        } else {
                            /* regular character */
                            if (pendingQuoteExit) {
                                pendingQuoteExit = false;
                                quoteString = false;
                            }
                            if (stringBuilder.length() == maxFieldSize) {
                                throw new CSVParserFieldTooLargeException(
                                    maxFieldSize,
                                    inputHelper.lineNumber());
                            }
                            stringBuilder.append(c);
                            if (currentRecordCharCount++ == maxRecordCharSize) {
                                throw new CSVParserLineTooLongException(
                                    maxRecordCharSize,
                                    inputHelper.lineNumber());
                            }
                        }

                    }

            }

            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }

    }

    /**
     * Appends the field to destination, guarded by text delimiter, and each
     * text delimiter character found in the field is doubled.
     * 
     * @param field_
     *            the field
     * @param text_delimiter_
     *            the text delimiter
     * @param destination
     *            the destination
     * @return the destination
     */
    private static StringBuilder escape_string_text_delimiter_(
        String field_,
        char text_delimiter_,
        StringBuilder destination) {
        destination.append(text_delimiter_);
        int sz = field_.length();
        for (int i = 0; i < sz; i++) {
            char c = field_.charAt(i);
            if (c == text_delimiter_) {
                destination.append(text_delimiter_)
                    .append(text_delimiter_);
            } else {
                destination.append(c);
            }
        }
        return destination.append(text_delimiter_);
    }

    @Override
    public StringBuilder
        format(List<String> fields, StringBuilder destination) {
        if (destination == null) {
            destination = new StringBuilder();
        }
        String field_delimiter_ =
            delimiters.length == 0 ? "" : "" + delimiters[0];
        if (fields.size() > 0) {
            Iterator<String> it = fields.iterator();
            if (it.hasNext()) {
                escape_string_text_delimiter_(it.next(), textDelimiter,
                    destination);
                while (it.hasNext()) {
                    escape_string_text_delimiter_(it.next(), textDelimiter,
                        destination.append(field_delimiter_));
                }
            }
        }
        return destination;
    }

}
