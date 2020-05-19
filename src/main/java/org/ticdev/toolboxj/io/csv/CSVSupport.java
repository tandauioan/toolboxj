package org.ticdev.toolboxj.io.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Static methods to help with CSV parsing.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface CSVSupport {

  /**
   * Returns a new stream that provides records using the given parser and
   * reader.
   *
   * @param parser the parser
   * @param reader the reader
   * @return the new stream
   */
  static Stream<List<String>>
  newStream(
      final CSVParser parser,
      final Reader reader) {

    CSVParserStreamSpliterator spliterator =
        new CSVParserStreamSpliterator(parser, reader);

    return StreamSupport.stream(spliterator, false).onClose(() -> {
      try {
        spliterator.close();
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      } catch (Exception ex) {
        throw new UncheckedIOException(
            new IOException(ex.toString()));
      }
    });

  }

  /**
   * Read all the records from the given reader.
   *
   * @param reader        the reader
   * @param configuration the parser configuration
   * @param inputHelper   the input helper
   * @return a list of all the records
   * @throws InterruptedException            if the process was interrupted
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws IOException                     if an {@link IOException} occurred
   * @throws CSVParserTooManyFieldsException if there are too many fields
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserException              if a parsing exception occurred
   */
  static List<List<String>> parseReader(
      Reader reader, CSVParserConfiguration configuration,
      CSVParserInputHelper inputHelper)
      throws
      InterruptedException,
      CSVParserFieldTooLargeException,
      IOException,
      CSVParserTooManyFieldsException,
      CSVParserLineTooLongException,
      CSVParserException {
    CSVParser parser =
        CSVParserBuilder.createParser(configuration, inputHelper);
    List<List<String>> result = new LinkedList<>();
    List<String> record;
    while ((record = parser.parseRecord(reader, null)) != null) {
      result.add(record);
    }
    return result;
  }

  /**
   * Like {@link #parseReader(Reader, CSVParserConfiguration,
   * CSVParserInputHelper)}
   * but uses a default input helper.
   *
   * @param reader        the reader
   * @param configuration the parser configuration
   * @return a list of all the records
   * @throws InterruptedException            if the process was interrupted
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws CSVParserTooManyFieldsException if there are too many fields
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserException              if a parsing exception occurred
   * @throws IOException                     if an {@link IOException} occurred
   */
  static List<List<String>> parseReader(
      Reader reader, CSVParserConfiguration configuration)
      throws
      InterruptedException,
      CSVParserFieldTooLargeException,
      CSVParserTooManyFieldsException,
      CSVParserLineTooLongException,
      CSVParserException,
      IOException {
    return parseReader(reader, configuration,
        new DefaultCSVParserInputHelper());
  }

  /**
   * Utility function to read all the records from a reader.
   *
   * @param reader         the reader
   * @param fieldDelimiter the field delimiter
   * @param textDelimiter  the text delimiter, if any
   * @param multiLineEOL   the multi-line record EOL expansion
   * @param inputHelper    the input helper
   * @return all the records from the given reader
   * @throws InterruptedException            if the process was interrupted
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws CSVParserTooManyFieldsException if there are too many fields
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserException              if a parser exception occurred
   * @throws IOException                     if an {@link IOException} occurred
   */
  static List<List<String>> parseReader(
      Reader reader, char fieldDelimiter, Character textDelimiter,
      String multiLineEOL, CSVParserInputHelper inputHelper)
      throws
      InterruptedException,
      CSVParserFieldTooLargeException,
      CSVParserTooManyFieldsException,
      CSVParserLineTooLongException,
      CSVParserException,
      IOException {
    return parseReader(reader, CSVParserConfiguration
            .of(CSVParserBuilder.newInstance()
                .addDelimiter(
                    fieldDelimiter)
                .textDelimiter(
                    textDelimiter)
                .multiLineEOL(
                    multiLineEOL)
                .escapeCharacter(null)
                .textDelimiterEscapesItself(
                    true)),
        inputHelper);
  }

  /**
   * Like {@link #parseReader(Reader, char, Character, String,
   * CSVParserInputHelper)}
   * but uses a default input helper.
   *
   * @param reader         the reader
   * @param fieldDelimiter the field delimiter
   * @param textDelimiter  the text delimiter
   * @param multiLineEOL   the multi-line record EOL expansion
   * @return all the records from the given reader.
   * @throws InterruptedException            if the process was interrupted
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws CSVParserTooManyFieldsException if there are too many fields
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserException              if a parser exception occurred
   * @throws IOException                     if an {@link IOException} occurred
   */
  static List<List<String>> parseReader(
      Reader reader, char fieldDelimiter, Character textDelimiter,
      String multiLineEOL)
      throws
      InterruptedException,
      CSVParserFieldTooLargeException,
      CSVParserTooManyFieldsException,
      CSVParserLineTooLongException,
      CSVParserException,
      IOException {
    return parseReader(reader, fieldDelimiter, textDelimiter,
        multiLineEOL,
        new DefaultCSVParserInputHelper());
  }

  /**
   * Like {@link #parseReader(Reader, char, Character, String,
   * CSVParserInputHelper)}
   * but reading from a file.
   *
   * @param file           the file
   * @param charset        the character set
   * @param inputHelper    the input helper
   * @param fieldDelimiter the field delimiter
   * @param textDelimiter  the text delimiter
   * @param multiLineEOL   the multi-line record EOL expansion
   * @return all the records in the file
   * @throws InterruptedException            if the process was interrupted
   * @throws CSVParserFieldTooLargeException if the field is too large
   * @throws CSVParserTooManyFieldsException if there are too many fields
   * @throws CSVParserLineTooLongException   if the line is too long
   * @throws CSVParserException              if a parser exception occurred
   * @throws IOException                     if an {@link IOException} occurred
   */
  static List<List<String>> parseFile(
      Path file, Charset charset, CSVParserInputHelper inputHelper,
      char fieldDelimiter, Character textDelimiter,
      String multiLineEOL)
      throws
      InterruptedException,
      CSVParserFieldTooLargeException,
      CSVParserTooManyFieldsException,
      CSVParserLineTooLongException,
      CSVParserException,
      IOException {
    try (BufferedReader reader = Files
        .newBufferedReader(file, charset)) {
      return parseReader(reader, fieldDelimiter, textDelimiter,
          multiLineEOL, inputHelper);
    }
  }

  /**
   * Parses an entire file and returns a list of all the records found.
   *
   * @param file           the file
   * @param charset        the character set to use
   * @param inputHelper    the input helper
   * @param fieldDelimiter the field delimiter.
   * @param textDelimiter  the text delimiter (or null)
   * @param multiLineEOL   end-of-line replacement for multi-line parsers
   * @return the list of all records found in the file
   * @throws Exception if an exception occurred
   */
  static List<List<String>> parseFile(
      File file,
      Charset charset,
      CSVParserInputHelper inputHelper,
      char fieldDelimiter,
      Character textDelimiter,
      String multiLineEOL)
      throws
      Exception {

    return parseFile(file.toPath(), charset, inputHelper,
        fieldDelimiter, textDelimiter, multiLineEOL);

  }

}
