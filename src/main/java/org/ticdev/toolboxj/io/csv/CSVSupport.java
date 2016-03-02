package org.ticdev.toolboxj.io.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Static methods to help with CSV parsing.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVSupport {

    /**
     * Returns a new stream that provides records using the given parser and
     * reader.
     * 
     * @param parser
     *            the parser
     * @param reader
     *            the reader
     * 
     * @return the new stream
     */
    public static Stream<List<String>>
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
     * Reads all the CSV records from a reader. It does not close the reader.
     * 
     * @param reader
     *            the reader
     * @param fieldDelimiter
     *            the field delimiter
     * @param textDelimiter
     *            the text delimiter or null
     * @param multiLineEOL
     *            end-of-line replacement for multi-line parsers
     * @param inputHelper
     *            the input helper
     * @return the list of records
     * @throws Exception
     *             if an exception occurred
     */
    public static List<List<String>> parseReader(
        Reader reader,
        char fieldDelimiter,
        Character textDelimiter,
        String multiLineEOL,
        CSVParserInputHelper inputHelper)
            throws Exception {
        try (Stream<List<String>> stream = newStream(
            CSVParserBuilder.newInstance().addDelimiter(fieldDelimiter)
                .setTextDelimiter(textDelimiter)
                .setMultiLineEOL(multiLineEOL)
                .build(inputHelper),
            reader)) {
            return stream.collect(LinkedList::new, List::add,
                List::addAll);
        }
    }

    /**
     * Like {@link #parseReader(Reader, char, Character, String, CSVParserInputHelper)}
     * , but using the default {@link DefaultCSVParserInputHelper}.
     * 
     * @param reader
     *            the reader
     * @param fieldDelimiter
     *            the field delimiter
     * @param textDelimiter
     *            the text delimiter
     * @param multiLineEOL
     *            end-of-line replacement for multi-line parsers
     * @return the list of records
     * @throws Exception
     *             if an exception occurred
     */
    public static List<List<String>> parseReader(
        Reader reader,
        char fieldDelimiter,
        Character textDelimiter,
        String multiLineEOL)
            throws Exception {
        return parseReader(reader, fieldDelimiter, textDelimiter,
            multiLineEOL,
            new DefaultCSVParserInputHelper());
    }

    /**
     * Parses an entire file and returns a list of all the records found.
     * 
     * @param file
     *            the file
     * @param charset
     *            the character set to use
     * @param inputHelper
     *            the input helper
     * @param fieldDelimiter
     *            the field delimiter.
     * @param textDelimiter
     *            the text delimiter (or null)
     * @param multiLineEOL
     *            end-of-line replacement for multi-line parsers
     * @return the list of all records found in the file
     * @throws Exception
     *             if an exception occurred
     */
    public static List<List<String>> parseFile(
        File file,
        Charset charset,
        CSVParserInputHelper inputHelper,
        char fieldDelimiter,
        Character textDelimiter,
        String multiLineEOL)
            throws Exception {

        try (BufferedReader reader =
            Files.newBufferedReader(file.toPath(), charset);) {

            return parseReader(reader, fieldDelimiter, textDelimiter,
                multiLineEOL, inputHelper);

        } catch (Exception ex) {
            throw ex;
        }

    }

}
