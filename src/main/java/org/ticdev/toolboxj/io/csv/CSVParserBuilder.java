package org.ticdev.toolboxj.io.csv;

import java.util.LinkedList;
import java.util.List;

/**
 * Builder class for {@link CSVParser}.
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
     * Field delimiters defined for a parser.
     */
    private List<Character> delimiters = new LinkedList<>();

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
        if (delimiters.stream().anyMatch(c -> c == delimiter)) {
            return this;
        }
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

    
    
}
