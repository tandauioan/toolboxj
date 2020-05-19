package org.ticdev.toolboxj.io.csv;

import org.ticdev.toolboxj.io.csv.impl.DefaultCSVParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public final class CSVParserBuilder implements CSVParserConfiguration {

  /**
   * Maximum number of characters per record.
   */
  private int maxRecordCharSize =
      CSVParserConfiguration.MAX_RECORD_CHAR_SIZE;

  /**
   * Maximum number of characters per record.
   */
  private int maxFieldSize = CSVParserConfiguration.MAX_FIELD_SIZE;

  /**
   * Maximum number of fields in a record.
   */
  private int maxFieldsPerRecord =
      CSVParserConfiguration.MAX_FIELDS_PER_RECORD;

  /**
   * Multi-line EOL expansion.
   */
  private String multiLineEOL = CSVParserConfiguration.MULTILINE_EOL;

  /**
   * List of delimiters.
   */
  private final List<Character> delimiters = new LinkedList<>();

  /**
   * Text delimiter.
   */
  private Character textDelimiter = null;

  /**
   * Text delimiter escapes itself.
   */
  private boolean textDelimiterEscapesItself = true;

  /**
   * Escape character, if any.
   */
  private Character escapeCharacter = null;

  /**
   * Escape character expansion.
   */
  private final Map<Character, String> escapeCharacterExpansion =
      new HashMap<>();

  /**
   * Is EOL escaped.
   */
  private boolean isEOLEscaped = true;

  /**
   * Default constructor.
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
   * @param maxRecordCharSizeValue the new value
   * @return this instance
   */
  public CSVParserBuilder maxRecordCharSize(
      final int maxRecordCharSizeValue) {
    this.maxRecordCharSize = maxRecordCharSizeValue;
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
   * @param maxFieldSizeValue the new value
   * @return this instance
   */
  public CSVParserBuilder maxFieldSize(final int maxFieldSizeValue) {
    this.maxFieldSize = maxFieldSizeValue;
    return this;
  }

  @Override
  public int maxFieldSize() {
    return maxFieldSize;
  }

  /**
   * Sets the maximum number of fields allowed in a record.
   *
   * @param maxFieldsPerRecordValue the new value
   * @return this instance
   */
  public CSVParserBuilder maxFieldsPerRecord(
      final int maxFieldsPerRecordValue) {
    this.maxFieldsPerRecord = maxFieldsPerRecordValue;
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
  public CSVParserBuilder addDelimiter(final Character delimiter) {
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
   * @param textDelimiterValue the text delimiter
   * @return this instance
   */
  public CSVParserBuilder textDelimiter(
      final Character textDelimiterValue) {
    this.textDelimiter = textDelimiterValue;
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
   * @param textDelimiterEscapesItselfValue the new value
   * @return this instance
   */
  public CSVParserBuilder textDelimiterEscapesItself(
      final boolean textDelimiterEscapesItselfValue) {
    this.textDelimiterEscapesItself = textDelimiterEscapesItselfValue;
    return this;
  }

  @Override
  public boolean textDelimiterEscapesItself() {
    return textDelimiterEscapesItself;
  }

  /**
   * Sets the multi-line EOL expansion.
   *
   * @param multiLineEOLValue the expansion
   * @return this instance
   */
  public CSVParserBuilder multiLineEOL(final String multiLineEOLValue) {
    this.multiLineEOL = multiLineEOLValue;
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
   * @param escapeCharacterValue the escape character
   * @return this instance
   */
  public CSVParserBuilder escapeCharacter(
      final Character escapeCharacterValue) {
    this.escapeCharacter = escapeCharacterValue;
    return this;
  }

  @Override
  public Character escapeCharacter() {
    return escapeCharacter;
  }

  /**
   * Set to true if EOL is expected to be escaped and false otherwise.
   *
   * @param isEOLEscapedValue the new value
   * @return this instance
   */
  public CSVParserBuilder eolEscaped(final boolean isEOLEscapedValue) {
    this.isEOLEscaped = isEOLEscapedValue;
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
      final Character escapedCharacter, final String expansion) {
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
  public CSVParser build(final CSVParserInputHelper inputHelper) {
    return new DefaultCSVParser(CSVParserConfiguration.of(this),
        inputHelper);
  }

  /**
   * Returns a parser matching the configuration of this builder and an
   * instance of {@link DefaultCSVParserInputHelper}.
   *
   * @return a parser with this builder's characteristics and a default input
   *     helper instance.
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
   *     and the given input helper instance
   */
  public static CSVParser createParser(
      final CSVParserConfiguration configuration,
      final CSVParserInputHelper inputHelper) {
    return new DefaultCSVParser(configuration, inputHelper);
  }

  /**
   * Returns a new parser matching the given configuration, and using an
   * instance of {@link DefaultCSVParserInputHelper}.
   *
   * @param configuration the parser configuration
   * @return a parser with the given configuration characteristics and
   *     the default input helper.
   */
  public static CSVParser createParser(
      final CSVParserConfiguration configuration) {
    return new DefaultCSVParser(configuration,
        new DefaultCSVParserInputHelper());
  }

}
