package org.ticdev.toolboxj.io.csv.impl;

import org.ticdev.toolboxj.io.csv.CSVParser;
import org.ticdev.toolboxj.io.csv.CSVParserConfiguration;
import org.ticdev.toolboxj.io.csv.CSVParserException;
import org.ticdev.toolboxj.io.csv.CSVParserFieldTooLargeException;
import org.ticdev.toolboxj.io.csv.CSVParserInputHelper;
import org.ticdev.toolboxj.io.csv.CSVParserLineTooLongException;
import org.ticdev.toolboxj.io.csv.CSVParserTooManyFieldsException;

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
   * Parser configuration.
   */
  private final CSVParserConfiguration config;

  /**
   * The input helper.
   */
  private final CSVParserInputHelper inputHelper;

  /**
   * The delimiter predicate.
   */
  private final DelimiterPredicate delimiterPredicate;

  /**
   * The text delimiter == -1 if none defined.
   */
  private final int textDelimiter;

  /**
   * The escape character == -1 if none defined.
   */
  private final int escapeCharacter;

  /**
   * Class constructor.
   *
   * @param configRef      the csv parser configurator
   * @param inputHelperRef the input helper
   */
  public DefaultCSVParser(
      final CSVParserConfiguration configRef,
      final CSVParserInputHelper inputHelperRef) {
    this.config = CSVParserConfiguration.of(configRef);
    this.inputHelper = inputHelperRef;
    delimiterPredicate = DelimiterPredicate.of(configRef.delimiters());
    textDelimiter = configRef.textDelimiter() == null
        ? -1
        : configRef.textDelimiter();
    escapeCharacter = configRef.escapeCharacter() == null
        ? -1
        : configRef.escapeCharacter();
  }

  /**
   * Tracker for the current record size in characters.
   */
  private int recordCharSize = 0;

  /**
   * Add char with checks and proper increment.
   *
   * @param c  the character to add.
   * @param sb the string builder destination
   * @throws CSVParserLineTooLongException   if the character makes the
   *                                         line too long
   * @throws CSVParserFieldTooLargeException if the character makes teh
   *                                         field too big
   */
  private void addChar(final char c, final StringBuilder sb)
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
  private void addString(final String s, final StringBuilder sb)
      throws
      CSVParserLineTooLongException,
      CSVParserFieldTooLargeException {
    int sz = s.length();
    int sbz = sb.length();
    if (sz >= config.maxRecordCharSize()
        || config.maxRecordCharSize() - sz < sbz) {
      throw new CSVParserLineTooLongException(
          config.maxRecordCharSize(),
          inputHelper.lineNumber());
    }
    if (sz >= config.maxFieldSize()
        || config.maxFieldSize() - sz < sbz) {
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
  private void parseTextDelimited(
      final Reader reader,
      final StringBuilder stringBuilder)
      throws
      CSVParserLineTooLongException,
      CSVParserFieldTooLargeException,
      CSVParserException,
      IOException {

    boolean pendingExit = false;

    while (true) {
      int token = inputHelper.next(reader);
      currentRecordParsedChars++;
      if (token == CSVParserInputHelper.EOF) {
        if (pendingExit) {
          return;
        }
        throw new CSVParserException("Unexpected end of file.",
            inputHelper.lineNumber());
      } else {
        if (token == textDelimiter) {
          if (pendingExit) {
            addChar((char) textDelimiter,
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
        } else if (token == escapeCharacter) {
          parseEscapedCharacter(reader, stringBuilder);
        } else if (token == CSVParserInputHelper.EOL) {
          addString(config.multiLineEOL(),
              stringBuilder);
        } else {
          addChar((char) token, stringBuilder);
        }
      }

    }

  }

  /**
   * Parses an escaped character.
   *
   * @param reader        the reader
   * @param stringBuilder the partial field
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws CSVParserException              if a parsing exception occurred
   * @throws IOException                     if an {@link IOException} occurred
   */
  private void parseEscapedCharacter(
      final Reader reader,
      final StringBuilder stringBuilder)
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
            addString(config.multiLineEOL(),
                stringBuilder);
            return;
          }
          break;
        default:
          char ctoken = (char) token;
          String expansion =
              config.escapedCharacterExpansion()
                  .get(ctoken);
          if (expansion != null) {
            addString(expansion, stringBuilder);
          } else {
            addChar(ctoken, stringBuilder);
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
  private boolean parseCleanField(
      final Reader reader,
      final StringBuilder stringBuilder)
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
          if (token == textDelimiter) {
            parseTextDelimited(reader, stringBuilder);
          } else if (token == escapeCharacter) {
            parseEscapedCharacter(reader, stringBuilder);
          } else {
            char ctoken = (char) token;
            if (delimiterPredicate.isDelimiter(ctoken)) {
              return false;
            } else {
              addChar(ctoken, stringBuilder);
            }
          }
      }
    }

  }

  /**
   * Holds the partial field.
   */
  private final StringBuilder sBuilder = new StringBuilder();

  /**
   * Number of characters parsed for current record.
   */
  private int currentRecordParsedChars = 0;

  /**
   * End-of-file flag.
   */
  private boolean eof = false;

  @Override
  public List<String> parseRecord(
      final Reader reader, final List<String> destinationRef)
      throws
      CSVParserException,
      CSVParserLineTooLongException,
      CSVParserFieldTooLargeException,
      CSVParserTooManyFieldsException,
      IOException,
      InterruptedException {
    List<String> destination = destinationRef == null
        ? new LinkedList<>()
        : destinationRef;
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
      boolean endrecord = parseCleanField(reader, sBuilder);
      recordCount++;
      if (endrecord) {
        if (eof) {
          if (currentRecordParsedChars > 0
              || !destination.isEmpty()) {
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
      final List<String> fields, final StringBuilder destinationRef) {
    StringBuilder destination = destinationRef == null
        ? new StringBuilder()
        : destinationRef;
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
      for (String s : fields) {
        if (scount > 0) {
          destination.append(delimiter);
        }
        destination.append(s);
        scount++;
      }
    }
    return destination;
  }


}
