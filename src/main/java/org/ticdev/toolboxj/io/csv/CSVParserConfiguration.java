package org.ticdev.toolboxj.io.csv;

import java.util.*;

/**
 * CSV parser configuration interface.
 * <p>
 * The end-of-line marker defaults to %n.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface CSVParserConfiguration {

    /**
     * Maximum number of characters allowed in one record
     */
    int MAX_RECORD_CHAR_SIZE = Integer.MAX_VALUE - 1;

    /**
     * Maximum number of characters in one field belonging to one record
     */
    int MAX_FIELD_SIZE = Integer.MAX_VALUE - 1;

    /**
     * Default end-of-line expansion marker when parsing multi-line records.
     */
    String MULTILINE_EOL = "%n";

    /**
     * Maximum number of fields in one record
     */
    int MAX_FIELDS_PER_RECORD = Integer.MAX_VALUE - 1;

    /**
     * Returns the maximum number of characters allowed in one record.
     *
     * @return the maximum number of characters allowed in one record.
     */
    int maxRecordCharSize();

    /**
     * Returns the maximum number of characters in one field belonging
     * to a record.
     *
     * @return the maximum number of characters in one field belonging
     * to a record.
     */
    int maxFieldSize();

    /**
     * Returns the maximum number of fields in one record.
     *
     * @return the maximum number of fields in one record.
     */
    int maxFieldsPerRecord();

    /**
     * Returns the list of field delimiters.
     *
     * @return the list of field delimiter.
     */
    List<Character> delimiters();

    /**
     * Returns the text delimiter for records. If the returned value is
     * null then there is no text delimiter.
     *
     * @return the text delimiter character.
     */
    Character textDelimiter();

    /**
     * True if text delimiter in text delimited field should escape itself,
     * i.e. a double text delimiter acts as one character, e.g. "a "" b "
     * results in <pre>a " b</pre>.
     *
     * @return true if text delimiter escape itself and false otherwise.
     */
    boolean textDelimiterEscapesItself();

    /**
     * Returns the expansion for a multi-line EOL delimiter.
     *
     * @return the expansion for a multi-line EOL delimiter.
     */
    String multiLineEOL();

    /**
     * Returns the escape character that may be in use. If the returned
     * value is null then there is no escape character defined.
     *
     * @return the escape character.
     */
    Character escapeCharacter();

    /**
     * Whether or not the escape character is used to escape EOL.
     * Defaults to true.
     *
     * @return true if EOL is escaped in a field and false otherwise.
     */
    boolean isEOLEscaped();

    /**
     * Returns the defined expansion mapping for escaped characters.
     *
     * @return the string expansion mapping.
     */
    Map<Character, String> escapedCharacterExpansion();

    /**
     * Returns an unmodifiable CSVParserConfiguration object that is
     * a copy of the given configuration.
     *
     * @param source the source configuration
     * @return the unmodifiable version of the source.
     */
    static CSVParserConfiguration of(CSVParserConfiguration source) {
        return new CSVConfig(source);
    }

    class CSVConfig
            implements CSVParserConfiguration {

        private CSVConfig(CSVParserConfiguration config) {
            this.maxRecordCharSize = config.maxRecordCharSize();
            this.maxFieldSize = config.maxFieldSize();
            this.maxFieldsPerRecord = config.maxFieldsPerRecord();
            List<Character> delims = config.delimiters();
            this.delimiters = delims == null ? new LinkedList<>() :
                    Collections.unmodifiableList(delims);
            this.textDelimiter = config.textDelimiter();
            this.textDelimiterEscapesItself =
                    config.textDelimiterEscapesItself();
            this.multiLineEOL = config.multiLineEOL();
            this.escapeCharacter = config.escapeCharacter();
            Map<Character, String> escexpansion =
                    config.escapedCharacterExpansion();
            this.escapedCharacterExpansion =
                    escexpansion == null ? new HashMap<>() :
                            Collections.unmodifiableMap(escexpansion);
            this.isEOLEscaped = config.isEOLEscaped();
        }

        /**
         * max record char size
         */
        private final int maxRecordCharSize;

        @Override
        public int maxRecordCharSize() {
            return maxRecordCharSize;
        }

        /**
         * max field size
         */
        private final int maxFieldSize;

        @Override
        public int maxFieldSize() {
            return maxFieldSize;
        }

        private final int maxFieldsPerRecord;

        @Override
        public int maxFieldsPerRecord() {
            return maxFieldsPerRecord;
        }

        /**
         * delimiters
         */
        private final List<Character> delimiters;

        @Override
        public List<Character> delimiters() {
            return delimiters;
        }

        /**
         * text delimiter
         */
        private final Character textDelimiter;

        @Override
        public Character textDelimiter() {
            return textDelimiter;
        }

        /**
         * text delimiter escapes itself
         */
        private final boolean textDelimiterEscapesItself;


        @Override
        public boolean textDelimiterEscapesItself() {
            return textDelimiterEscapesItself;
        }

        /**
         * multi-line EOL
         */
        private final String multiLineEOL;

        @Override
        public String multiLineEOL() {
            return multiLineEOL;
        }

        /**
         * escape character
         */
        private final Character escapeCharacter;

        @Override
        public Character escapeCharacter() {
            return escapeCharacter;
        }

        @Override
        public boolean isEOLEscaped() {
            return isEOLEscaped;
        }

        private final Map<Character, String> escapedCharacterExpansion;

        @Override
        public Map<Character, String> escapedCharacterExpansion() {
            return this.escapedCharacterExpansion;
        }

        /**
         * escaped EOL
         */
        private final boolean isEOLEscaped;
    }

}
