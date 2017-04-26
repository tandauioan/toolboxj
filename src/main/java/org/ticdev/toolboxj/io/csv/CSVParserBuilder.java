package org.ticdev.toolboxj.io.csv;

import org.ticdev.toolboxj.io.csv.impl.DefaultCSVParser;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * CSV configuration builder.
 * <p>
 * Delimiters are stored in a list on the order they were provided,
 * without removing duplicates. This allows for implementations that
 * impose a specific order and number of delimiters when parsing
 * records.
 * </p>
 * <p>
 * By default the text delimiter escapes itself.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class CSVParserBuilder
        implements CSVParserConfiguration {

    /**
     * maximum number of characters per record
     */
    private int maxRecordCharSize =
            CSVParserConfiguration.MAX_RECORD_CHAR_SIZE;

    /**
     * maximum number of characters per record
     */
    private int maxFieldSize = CSVParserConfiguration.MAX_FIELD_SIZE;

    /**
     * maximum number of fields in a record
     */
    private int maxFieldsPerRecord =
            CSVParserConfiguration.MAX_FIELDS_PER_RECORD;

    /**
     * multi-line EOL expansion
     */
    private String multiLineEOL = CSVParserConfiguration.MULTILINE_EOL;

    /**
     * list of delimiters
     */
    private final List<Character> delimiters = new LinkedList<>();

    /**
     * text delimiter
     */
    private Character textDelimiter = null;

    /**
     * text delimiter escapes itself
     */
    private boolean textDelimiterEscapesItself = true;

    /**
     * escape character, if any
     */
    private Character escapeCharacter = null;

    /**
     * escape character expansion
     */
    private Map<Character, String> escapeCharacterExpansion =
            new HashMap<>();

    /**
     * is EOL escaped
     */
    private boolean isEOLEscaped = true;

    /**
     * Default constructor
     */
    public CSVParserBuilder() {
    }

    /**
     * Returns a new builder instance.
     *
     * @return a new builder instance.
     */
    public static CSVParserBuilder newInstance() {
        return new CSVParserBuilder();
    }

    /**
     * Sets the maximum number of characters allowed in one record.
     *
     * @param maxRecordCharSize the new value
     * @return this instance
     */
    public CSVParserBuilder maxRecordCharSize(
            int maxRecordCharSize) {
        this.maxRecordCharSize = maxRecordCharSize;
        return this;
    }

    @Override
    public int maxRecordCharSize() {
        return maxRecordCharSize;
    }

    /**
     * Sets the maximum number of characters allowed in one field of one
     * record.
     *
     * @param maxFieldSize the new value
     * @return this instance
     */
    public CSVParserBuilder maxFieldSize(int maxFieldSize) {
        this.maxFieldSize = maxFieldSize;
        return this;
    }

    @Override
    public int maxFieldSize() {
        return maxFieldSize;
    }

    /**
     * Sets the maximum number of fields allowed in a record
     *
     * @param maxFieldsPerRecord the new value
     * @return this instance
     */
    public CSVParserBuilder maxFieldsPerRecord(
            int maxFieldsPerRecord) {
        this.maxFieldsPerRecord = maxFieldsPerRecord;
        return this;
    }

    @Override
    public int maxFieldsPerRecord() {
        return maxFieldsPerRecord;
    }

    /**
     * Adds a new delimiter to the delimiters list.
     *
     * @param delimiter the delimiter
     * @return this instance
     */
    public CSVParserBuilder addDelimiter(Character delimiter) {
        this.delimiters.add(delimiter);
        return this;
    }

    @Override
    public List<Character> delimiters() {
        return delimiters;
    }

    /**
     * Sets the text delimiter. If the argument is null it clears the text
     * delimiter.
     *
     * @param textDelimiter the text delimiter
     * @return this instance
     */
    public CSVParserBuilder textDelimiter(Character textDelimiter) {
        this.textDelimiter = textDelimiter;
        return this;
    }

    @Override
    public Character textDelimiter() {
        return textDelimiter;
    }

    /**
     * Set to true if the text delimiter should escape itself and to
     * false otherwise.
     *
     * @param textDelimiterEscapesItself the new value
     * @return this instance
     */
    public CSVParserBuilder textDelimiterEscapesItself(
            boolean textDelimiterEscapesItself) {
        this.textDelimiterEscapesItself = textDelimiterEscapesItself;
        return this;
    }

    @Override
    public boolean textDelimiterEscapesItself() {
        return textDelimiterEscapesItself;
    }

    /**
     * Sets the multi-line EOL expansion
     *
     * @param multiLineEOL the expansion
     * @return this instance
     */
    public CSVParserBuilder multiLineEOL(String multiLineEOL) {
        this.multiLineEOL = multiLineEOL;
        return this;
    }

    @Override
    public String multiLineEOL() {
        return multiLineEOL;
    }

    /**
     * Sets the escape character. If the argument is null it clears the
     * escape character.
     *
     * @param escapeCharacter the escape character
     * @return this instance
     */
    public CSVParserBuilder escapeCharacter(
            Character escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
        return this;
    }

    @Override
    public Character escapeCharacter() {
        return escapeCharacter;
    }

    /**
     * Set to true if EOL is expected to be escaped and false otherwise.
     *
     * @param isEOLEscaped the new value
     * @return this instance
     */
    public CSVParserBuilder eolEscaped(boolean isEOLEscaped) {
        this.isEOLEscaped = isEOLEscaped;
        return this;
    }

    @Override
    public boolean isEOLEscaped() {
        return isEOLEscaped;
    }


    /**
     * Adds a new escape character expansion mapping.
     *
     * @param escapedCharacter the escaped character
     * @param expansion        the expansion
     * @return this instance
     */
    public CSVParserBuilder addEscapeCharacterExpansionMapping(
            Character escapedCharacter, String expansion) {
        escapeCharacterExpansion.put(escapedCharacter, expansion);
        return this;
    }

    @Override
    public Map<Character, String> escapedCharacterExpansion() {
        return escapeCharacterExpansion;
    }

    /**
     * Returns a new parser matching the configuration of this builder and
     * using the given input helper to read characters.
     *
     * @param inputHelper the input helper
     * @return the new parser
     */
    public CSVParser build(CSVParserInputHelper inputHelper) {
        return new DefaultCSVParser(CSVParserConfiguration.of(this),
                                    inputHelper);
    }

    /**
     * Returns a parser matching the configuration of this builder and an
     * instance of {@link DefaultCSVParserInputHelper}.
     *
     * @return a parser with this builder's characteristics and a default input
     * helper instance.
     */
    public CSVParser build() {
        return build(new DefaultCSVParserInputHelper());
    }

    /**
     * Returns a new parser matching the given configuration and an
     * instance of {@link CSVParserInputHelper}.
     *
     * @param configuration the parser configuration
     * @param inputHelper   the input helper
     * @return a parser with the given configuration characteristics
     * and the given input helper instance
     */
    public static CSVParser createParser(
            CSVParserConfiguration configuration,
            CSVParserInputHelper inputHelper) {
        return new DefaultCSVParser(configuration, inputHelper);
    }

    /**
     * Returns a new parser matching the given configuration, and using an
     * instance of {@link DefaultCSVParserInputHelper}.
     *
     * @param configuration the parser configuration
     * @return a parser with the given configuration characteristics and
     * the default input helper.
     */
    public static CSVParser createParser(
            CSVParserConfiguration configuration) {
        return new DefaultCSVParser(configuration,
                                    new DefaultCSVParserInputHelper());
    }

    public static void main(String[] args)
            throws
            Exception {

        CSVParser parser =
                CSVParserBuilder.newInstance().escapeCharacter('\\')
                                .eolEscaped(true).addDelimiter(',')
                                .textDelimiter('"')
                                .textDelimiterEscapesItself(false).build();

        try (
                BufferedReader br = Files.newBufferedReader(Paths.get("" +
                                                                      "/Users/nelutu/a.csv"))

        ) {

            int min = Integer.MAX_VALUE;
            int max = 0;
            long record = 0;

            ArrayList<String> fields = new ArrayList<>(100);
            while (parser.parseRecord(br, fields) != null) {
                record++;
                int sz = fields.size();
                if (min > sz) {
                    min = sz;
                    System.out
                            .printf("Min: %d. Record: %d%n", min, record);
                }
                if (max < sz) {
                    max = sz;
                    System.out
                            .printf("Max: %d. Record: %d%n", max, record);
                }
                fields.clear();
                if (record % 1000000 == 0) {
                    System.out.println(record + "");
                }
            }

        }

    }

}
