package org.ticdev.toolboxj.io.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Interface for classes that provide specific ways of handling end-of-line.
 * 
 * <p>
 * Since there is no restriction on how many lines are available from a reader,
 * it is possible, although highly unlikely, that the line number type (long)
 * cannot hold the correct line number as it may wrap (may actually be
 * negative). While it's useful to have this value in most cases, it's not
 * guaranteed that there will be no situation where there could be more than
 * {@link Long#MAX_VALUE} lines.
 * </p>
 * 
 * <p>
 * It's always better to use a {@link BufferedReader} with this method as the
 * default {#link {@link Reader#read()} implementation can add huge overhead
 * (currently, it allocates an array to read one char).
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public interface CSVParserInputHelper {

    /**
     * end-of-line flag
     */
    public static final int EOL = -2;

    /**
     * end-of-file flag
     */
    public static final int EOF = -1;

    /**
     * Reads the next character from the reader.
     * 
     * @param reader
     *            the reader
     * @return the either EOL for end-of-line, EOF for end-of-file, or the
     *         regular character from the reader.
     * @throws IOException
     *             if an exception occurred when reading from the reader or
     *             interpreting the flags.
     */
    int next(Reader reader)
        throws IOException;

    /**
     * Returns the current line number.
     * 
     * @return the current line number.
     */
    long lineNumber();

}
