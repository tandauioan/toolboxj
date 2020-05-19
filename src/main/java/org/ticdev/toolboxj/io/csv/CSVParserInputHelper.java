package org.ticdev.toolboxj.io.csv;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface for classes that provide specific ways of handling end-of-line.
 * <p>
 * Since there is no restriction on how many lines are available from a reader,
 * it is possible, although highly unlikely, that the line number type (long)
 * cannot hold the correct line number as it may wrap (may actually be
 * negative). While it's useful to have this value in most cases, it's not
 * guaranteed that there will be no situation where there could be more than
 * {@link Long#MAX_VALUE} lines.
 * </p>
 * <p>
 * It's always better to use a {@link java.io.BufferedReader} with this
 * method as the default {#link {@link java.io.Reader#read()}
 * implementation can add huge overhead (currently, it allocates an array
 * to read one char).
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface CSVParserInputHelper {

  /**
   * End-of-line flag.
   */
  int EOL = -2;

  /**
   * End-of-file flag.
   */
  int EOF = -1;

  /**
   * Reads the next character from the reader.
   *
   * @param reader the reader
   * @return the either EOL for end-of-line, EOF for end-of-file, or the
   *     regular character from the reader.
   * @throws IOException if an exception occurred when reading from
   *                     the reader or interpreting the flags.
   */
  int next(Reader reader) throws IOException;

  /**
   * Returns one character to the input helper. Only one character get
   * be returned before a new read. If the character returned is either
   * EOL or EOF the line count will be decremented.
   *
   * @param character the character to be returned to the input helper
   */
  void unget(int character);

  /**
   * Returns the current line number.
   *
   * @return the current line number.
   */
  long lineNumber();

}
