package org.ticdev.toolboxj.io.csv.impl;

import org.ticdev.toolboxj.io.csv.*;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link CSVParser} implementation that uses a {@link CSVParserConfiguration}
 * to define the parsing rules.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class DefaultCSVParser
        implements CSVParser {

    /**
     * parser configuration
     */
    private final CSVParserConfiguration config;

    /**
     * the input helper
     */
    private final CSVParserInputHelper inputHelper;

    /**
     * the delimiter predicate
     */
    private final DelimiterPredicate delimiterPredicate;

    /**
     * The text delimiter == -1 if none defined
     */
    private final int text_delimiter_;

    /**
     * the escape character == -1 if none defined
     */
    private final int escape_character_;

    /**
     * Class constructor.
     *
     * @param config      the csv parser configurator
     * @param inputHelper the input helper
     */
    public DefaultCSVParser(
            CSVParserConfiguration config,
            CSVParserInputHelper inputHelper) {
        this.config = CSVParserConfiguration.of(config);
        this.inputHelper = inputHelper;
        delimiterPredicate = DelimiterPredicate.of(config.delimiters());
        text_delimiter_ = config.textDelimiter() == null ?
                -1 : config.textDelimiter();
        escape_character_ = config.escapeCharacter() == null ?
                -1 : config.escapeCharacter();
    }

    /**
     * Tracker for the current record size in characters
     */
    private int recordCharSize = 0;

    private void add_char_(char c, StringBuilder sb)
            throws
            CSVParserLineTooLongException,
            CSVParserFieldTooLargeException {
        if (config.maxFieldSize() == sb.length()) {
            throw new CSVParserFieldTooLargeException(
                    config.maxFieldSize(), inputHelper.lineNumber());
        }
        if (config.maxRecordCharSize() <= recordCharSize) {
            throw new CSVParserLineTooLongException(
                    config.maxRecordCharSize(),
                    inputHelper.lineNumber());
        }
        recordCharSize++;
        sb.append(c);
    }

    /**
     * Adds a string to the partial field as long as it stays
     * withing the bounds.
     *
     * @param s  the string to add
     * @param sb the partial field
     * @throws CSVParserLineTooLongException   the line is too long
     * @throws CSVParserFieldTooLargeException the field is too large
     */
    private void add_string_(String s, StringBuilder sb)
            throws
            CSVParserLineTooLongException,
            CSVParserFieldTooLargeException {
        int sz = s.length();
        int sbz = sb.length();
        if (sz >= config.maxRecordCharSize() ||
            config.maxRecordCharSize() - sz < sbz) {
            throw new CSVParserLineTooLongException(
                    config.maxRecordCharSize(),
                    inputHelper.lineNumber());
        }
        if (sz >= config.maxFieldSize() ||
            config.maxFieldSize() - sz < sbz) {
            throw new CSVParserFieldTooLargeException(
                    config.maxFieldSize(), inputHelper.lineNumber());
        }
        recordCharSize += sz;
        sb.append(s);
    }

    /**
     * Parses a text delimited field.
     *
     * @param reader        the reader
     * @param stringBuilder the partial field
     * @throws CSVParserLineTooLongException   if the line is too long
     * @throws CSVParserFieldTooLargeException if the field if too large
     * @throws CSVParserException              if a parsing exception occurred
     * @throws IOException                     if an {@link IOException} occurred
     */
    private void parse_text_delimited_(
            Reader reader,
            StringBuilder stringBuilder)
            throws
            CSVParserLineTooLongException,
            CSVParserFieldTooLargeException,
            CSVParserException,
            IOException {

        boolean pendingExit = false;

        while (true) {
            int token = inputHelper.next(reader);
            currentRecordParsedChars++;
            switch (token) {
                case CSVParserInputHelper.EOF:
                    if (pendingExit) {
                        return;
                    }
                    throw new CSVParserException("Unexpected end of file.",
                                                 inputHelper.lineNumber());

                default:
                    if (token == text_delimiter_) {
                        if (pendingExit) {
                            add_char_((char) text_delimiter_,
                                      stringBuilder);
                            pendingExit = false;
                        } else {
                            if (config.textDelimiterEscapesItself()) {
                                pendingExit = true;
                            } else {
                                return;
                            }

                        }
                    } else if (pendingExit) {
                        inputHelper.unget(token);
                        currentRecordParsedChars--;
                        return;
                    } else if (token == escape_character_) {
                        parse_escaped_character(reader, stringBuilder);
                    } else if (token == CSVParserInputHelper.EOL) {
                        add_string_(config.multiLineEOL(),
                                    stringBuilder);
                    } else {
                        add_char_((char) token, stringBuilder);
                    }
            }

        }

    }

    /**
     * Parses an escaped character
     *
     * @param reader        the reader
     * @param stringBuilder the partial field
     * @throws CSVParserLineTooLongException   if the line is too long
     * @throws CSVParserFieldTooLargeException if the field is too large
     * @throws CSVParserException              if a parsing exception occurred
     * @throws IOException                     if an {@link IOException} occurred
     */
    private void parse_escaped_character(
            Reader reader,
            StringBuilder stringBuilder)
            throws
            CSVParserLineTooLongException,
            CSVParserFieldTooLargeException,
            CSVParserException,
            IOException {

        while (true) {
            int token = inputHelper.next(reader);
            currentRecordParsedChars++;
            switch (token) {
                case CSVParserInputHelper.EOF:
                    throw new CSVParserException("Unexpected end of file",
                                                 inputHelper.lineNumber());
                case CSVParserInputHelper.EOL:
                    if (config.isEOLEscaped()) {
                        add_string_(config.multiLineEOL(),
                                    stringBuilder);
                        return;
                    }
                    break;
                default:
                    Character ctoken = (char) token;
                    String expansion =
                            config.escapedCharacterExpansion()
                                  .get(ctoken);
                    if (expansion != null) {
                        add_string_(expansion, stringBuilder);
                    } else {
                        add_char_(ctoken, stringBuilder);
                    }
                    return;
            }
        }
    }

    /**
     * Parses a field.
     *
     * @param reader        the reader
     * @param stringBuilder the partial field
     * @return true if end-of-record, false otherwise
     * @throws CSVParserFieldTooLargeException the field is too large
     * @throws CSVParserLineTooLongException   the line is too long
     * @throws CSVParserException              if a parser exception occurs
     * @throws IOException                     if an {@link IOException} occurred.
     */
    private boolean parse_clean_field_(
            Reader reader,
            StringBuilder stringBuilder)
            throws
            CSVParserFieldTooLargeException,
            CSVParserLineTooLongException,
            CSVParserException,
            IOException {

        while (true) {
            int token = inputHelper.next(reader);
            currentRecordParsedChars++;
            switch (token) {
                case CSVParserInputHelper.EOF:
                    currentRecordParsedChars--;
                    inputHelper.unget(token);
                    eof = true;
                case CSVParserInputHelper.EOL:
                    return true;
                default:
                    if (token == text_delimiter_) {
                        parse_text_delimited_(reader, stringBuilder);
                    } else if (token == escape_character_) {
                        parse_escaped_character(reader, stringBuilder);
                    } else {
                        char ctoken = (char) token;
                        if (delimiterPredicate.isDelimiter(ctoken)) {
                            return false;
                        } else {
                            add_char_(ctoken, stringBuilder);
                        }
                    }
            }
        }

    }

    /**
     * holds the partial field
     */
    private final StringBuilder sBuilder = new StringBuilder();

    /**
     * number of characters parsed for current record
     */
    private int currentRecordParsedChars = 0;

    /**
     * end-of-file flag
     */
    private boolean eof = false;

    @Override
    public List<String> parseRecord(
            Reader reader, List<String> destination)
            throws
            CSVParserException,
            CSVParserLineTooLongException,
            CSVParserFieldTooLargeException,
            CSVParserTooManyFieldsException,
            IOException,
            InterruptedException {
        if (destination == null) {
            destination = new LinkedList<>();
        }
        recordCharSize = 0;
        int recordCount = 0;
        while (true) {
            currentRecordParsedChars = 0;
            eof = false;
            sBuilder.delete(0, sBuilder.length());
            if (recordCount >= config.maxFieldsPerRecord()) {
                throw new CSVParserTooManyFieldsException(
                        config.maxFieldsPerRecord(),
                        inputHelper.lineNumber());
            }
            boolean endrecord = parse_clean_field_(reader, sBuilder);
            recordCount++;
            if (endrecord) {
                if (eof) {
                    if (currentRecordParsedChars > 0 ||
                        !destination.isEmpty()) {
                        destination.add(sBuilder.substring(0));
                    }
                    return destination.size() == 0 ? null : destination;
                } else {
                    destination.add(sBuilder.substring(0));
                    return destination;
                }
            } else {
                destination.add(sBuilder.substring(0));
            }
        }
    }

    @Override
    public StringBuilder format(
            List<String> fields, StringBuilder destination) {
        if (destination == null) {
            destination = new StringBuilder();
        }
        String delimiter = delimiterPredicate.getFirst();

        if (config.textDelimiter() != null) {
            int scount = 0;
            for (String s : fields) {
                if (scount > 0) {
                    destination.append(delimiter);
                }
                destination.append(config.textDelimiter());
                int sz = s.length();
                if (config.escapeCharacter() != null) {
                    for (int i = 0; i < sz; i++) {
                        char c = s.charAt(i);
                        if (c == config.textDelimiter()) {
                            destination.append(config.escapeCharacter())
                                       .append(c);
                        } else {
                            destination.append(c);
                        }
                    }
                } else if (config.textDelimiterEscapesItself()) {
                    char delim = config.textDelimiter();
                    for (int i = 0; i < sz; i++) {
                        char c = s.charAt(i);
                        if (c == delim) {
                            destination.append(c).append(c);
                        } else {
                            destination.append(c);
                        }
                    }
                } else {
                    destination.append(s);
                }

                destination.append(config.textDelimiter());
                scount++;
            }
        } else {
            int scount = 0;
            for(String s: fields) {
                if(scount > 0) {
                    destination.append(delimiter);
                }
                destination.append(s);
                scount++;
            }
        }
        return destination;
    }


}
