package org.ticdev.toolboxj.io.csv;

/**
 * This exception specifies that there are more fields per record than allowed
 * by a restriction.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class CSVParserTooManyFieldsException
    extends
    AbstractCSVParserLimitException {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Class constructor.
   *
   * @param restriction number of fields per record limit.
   * @param lineNumber  the line number where the violation/exception
   *                    has occurred.
   */
  public CSVParserTooManyFieldsException(
      final int restriction,
      final long lineNumber) {
    super(restriction, lineNumber);
  }

}
