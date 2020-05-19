package org.ticdev.toolboxj.io.csv;

/**
 * Abstract exception class used for implementations that have an
 * integer based restriction.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
class AbstractCSVParserLimitException extends Exception {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The line number where the exception occurred.
   */
  private final long lineNumber;

  /**
   * The restriction limit.
   */
  private final int restriction;

  /**
   * Class constructor.
   *
   * @param restrictionValue the restriction limit.
   * @param lineNumberValue  the line number where the
   *                         violation/exception occurred.
   */
  protected AbstractCSVParserLimitException(
      final int restrictionValue,
      final long lineNumberValue) {
    super(
        String.format("Line: %d. Limit: %d",
            lineNumberValue, restrictionValue));
    this.restriction = restrictionValue;
    this.lineNumber = lineNumberValue;
  }

  /**
   * Returns the restriction limit.
   *
   * @return the restriction limit.
   */
  int getRestriction() {
    return restriction;
  }

  /**
   * Returns the line number.
   *
   * @return the line number.
   */
  long getLineNumber() {
    return lineNumber;
  }

}
