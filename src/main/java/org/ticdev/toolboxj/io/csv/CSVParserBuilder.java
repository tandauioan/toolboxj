package org.ticdev.toolboxj.io.csv;

import java.util.LinkedList;
import java.util.List;

import org.ticdev.toolboxj.io.csv.impl.CSVParserNonTextDelimiterImpl;
import org.ticdev.toolboxj.io.csv.impl.CSVParserTextDelimiterImpl;

/**
 * Builder class for {@link CSVParser}.
 * 
 * <p>
 * The end-of-line marker that can be set using {@link #setMultiLineEOL(String)}
 * defaults to %n. Any String or {@link String#format(String, Object...)}
 * non-argument pattern is accepted.
 * </p>
 * 
 * <p>
 * Delimiters are stored in a list in the order they were provided, without
 * removing duplicates. This allows for implementations that impose a specific
 * order and number of delimiters when parsing records.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserBuilder {

    /**
     * Maximum number of characters to parse for one record
     */
    public static final int MAX_RECORD_CHAR_SIZE = Integer.MAX_VALUE - 1;

    /**
     * Maximum number of characters in one field belonging to a record
     */
    public static final int MAX_FIELD_SIZE = Integer.MAX_VALUE - 1;

    /**
     * Default end-of-line marker when parsing multi-line records.
     */
    public static final String DEFAULT_MULTILINE_EOL = "%n";

    /**
     * Maximum number of fields in one record
     */
    public static final int MAX_FIELDS_PER_RECORD = Integer.MAX_VALUE - 1;

    /**
     * The maximum number of characters allowed in one record for this builder
     * instance. Defaults to {@link #MAX_RECORD_CHAR_SIZE}.
     */
    private int maxRecordCharSize = MAX_RECORD_CHAR_SIZE;

    /**
     * The maximum number of characters in one field belonging to a record for
     * this builder instance. Defaults to {@link #MAX_FIELD_SIZE}.
     */
    private int maxFieldSize = MAX_FIELD_SIZE;

    /**
     * The maximum number of fields in one record for this builder instance.
     * Defaults to {@link #MAX_FIELDS_PER_RECORD}.
     */
    private int maxFieldsPerRecord = MAX_FIELDS_PER_RECORD;

    /**
     * The end-of-line marker when parsing multi-line records
     */
    private String multiLineEOL = DEFAULT_MULTILINE_EOL;

    /**
     * Field delimiters defined for a parser.
     */
    private final List<Character> delimiters = new LinkedList<>();

    /**
     * Text delimiter defaults to null.
     */
    private Character textDelimiter = null;

    /**
     * Default class constructor.
     */
    public CSVParserBuilder() {
    }

    /**
     * Builder instance factory method.
     * 
     * @return a new builder.
     */
    public static CSVParserBuilder newInstance() {
        return new CSVParserBuilder();
    }

    /**
     * Sets the maximum number of characters allowed in one record for this
     * builder instance. The default value is {@link #MAX_RECORD_CHAR_SIZE}.
     * 
     * @param maxRecordCharSize
     *            the maximum number of characters allowed in one record for
     *            this builder instance.
     * @return this instance
     */
    public CSVParserBuilder
        setMaxRecordCharSize(int maxRecordCharSize) {
        this.maxRecordCharSize = maxRecordCharSize;
        return this;
    }

    /**
     * Returns the maximum number of characters allowed in one record for this
     * builder instance.
     * 
     * @return the maximum number of characters allowed in one record for this
     *         builder instance.
     */
    public int getMaxRecordCharSize() {
        return maxRecordCharSize;
    }

    /**
     * Sets the maximum number of characters in one field belonging to a record
     * for this builder instance. The default value is {@link #MAX_FIELD_SIZE}.
     * 
     * @param maxFieldSize
     *            the maximum number of characters in one field belonging to a
     *            record for this builder instance
     * @return this instance
     */
    public CSVParserBuilder setMaxFieldSize(int maxFieldSize) {
        this.maxFieldSize = maxFieldSize;
        return this;
    }

    /**
     * Returns the maximum number of characters in one field belonging to a
     * record for this builder instance.
     * 
     * @return the maximum number of characters in one field belonging to a
     *         record for this builder instance.
     */
    public int getMaxFieldSize() {
        return maxFieldSize;
    }

    /**
     * Sets the maximum number of fields in one record for this builder
     * instance. The default value is {@link #MAX_FIELDS_PER_RECORD}.
     * 
     * @param maxFieldsPerRecord
     *            the maximum number of fields in one record for this builder
     *            instance.
     * @return this instance
     */
    public CSVParserBuilder
        setMaxFieldsPerRecord(int maxFieldsPerRecord) {
        this.maxFieldsPerRecord = maxFieldsPerRecord;
        return this;
    }

    /**
     * Returns the maximum number of fields in one record for this builder
     * instance.
     * 
     * @return the maximum number of fields in one record for this builder
     *         instance.
     */
    public int getMaxFieldsPerRecord() {
        return maxFieldsPerRecord;
    }

    /**
     * Adds the delimiter to the list of delimiters if it doesn't already exist.
     * 
     * @param delimiter
     *            the delimiter to add
     * @return this instance
     */
    public CSVParserBuilder addDelimiter(Character delimiter) {
        delimiters.add(delimiter);
        return this;
    }

    /**
     * Returns a copy of the list of delimiters in this builder.
     * 
     * @return a copy of the list of delimiters in this builder.
     */
    public List<Character> getDelimiters() {
        return new LinkedList<>(delimiters);
    }

    /**
     * Sets the text field delimiter character. If null then no text delimiter
     * is considered.
     * 
     * @param textDelimiter
     *            the text delimiter character.
     * @return this instance
     */
    public CSVParserBuilder setTextDelimiter(Character textDelimiter) {
        this.textDelimiter = textDelimiter;
        return this;
    }

    /**
     * Returns the text field delimiter. If none is set then null is returned.
     * 
     * @return the text field delimiter or null if none set.
     */
    public Character getTextDelimiter() {
        return textDelimiter;
    }

    /**
     * Sets the end-of-line marker when parsing multi-line records. By default
     * it is set to {@link #DEFAULT_MULTILINE_EOL}.
     * 
     * @param multiLineEOL
     *            the marker
     * @return this instance
     */
    public CSVParserBuilder setMultiLineEOL(String multiLineEOL) {
        this.multiLineEOL = multiLineEOL;
        return this;
    }

    /**
     * Returns the end-of-line marker when parsing multi-line records.
     * 
     * @return the marker
     */
    public String getMultiLineEOL() {
        return multiLineEOL;
    }

    /**
     * Returns a parser matching the configuration of this builder.
     * 
     * @param inputHelper
     *            the reader input helper
     * @return a new parser with this builder's characteristics.
     */
    public CSVParser build(CSVParserInputHelper inputHelper) {
        return getTextDelimiter() == null
            ? new CSVParserNonTextDelimiterImpl(this, inputHelper)
            : new CSVParserTextDelimiterImpl(this, inputHelper);
    }

    /**
     * Returns a parser matching the configuration of this builder and an
     * instance of {@link DefaultCSVParserInputHelper}.
     * 
     * @return a parser with this builder's characteristics and a default input
     *         helper instance.
     */
    public CSVParser build() {
        return build(new DefaultCSVParserInputHelper());
    }

    /**
     * Quickly create a new {@link CSVParser} using maximal values for character
     * and field count restrictions.
     * 
     * @param delimiter
     *            the field delimiter
     * @param textDelimiter
     *            the text delimiter, or null if a text delimiter should not be
     *            considered
     * @param inputHelper
     *            an input helper
     * @return a new {@link CSVParser}
     */
    public static CSVParser newParser(
        char delimiter,
        Character textDelimiter,
        CSVParserInputHelper inputHelper) {
        return CSVParserBuilder.newInstance().addDelimiter(delimiter)
            .setTextDelimiter(textDelimiter).build(inputHelper);
    }

    /**
     * Quickly create a new {@link CSVParser} using maximal values for character
     * and field count restrictions and the default CSV input helper.
     * 
     * @param delimiter
     *            the field delimiter
     * @param textDelimiter
     *            the text delimiter, or null if a text delimiter should not be
     *            considered
     * @return a new {@link CSVParser}
     */
    public static CSVParser
        newParser(char delimiter, Character textDelimiter) {
        return newParser(delimiter, textDelimiter,
            new DefaultCSVParserInputHelper());
    }

}
