package org.ticdev.toolboxj.io.csv;

/**
 * Parsing exception thrown by a {@link CSVParser}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class CSVParserException extends Exception {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Exception detail/explanation.
   */
  private final String details;

  /**
   * Exception line number.
   */
  private final long lineNumber;

  /**
   * Class constructor.
   *
   * @param detailsValue    exception details
   * @param lineNumberValue parser line number for exception
   */
  public CSVParserException(
      final String detailsValue,
      final long lineNumberValue) {
    super(String.format("Line: %d. Detail: %s",
        lineNumberValue, detailsValue));
    this.details = detailsValue;
    this.lineNumber = lineNumberValue;
  }

  /**
   * Returns the exception details.
   *
   * @return the exception details
   */
  public String getDetails() {
    return details;
  }

  /**
   * Returns the parser line number.
   *
   * @return the parser line number
   */
  public long getLineNumber() {
    return lineNumber;
  }

}
