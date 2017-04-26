package org.ticdev.toolboxj.io.csv;

import java.io.IOException;
import java.io.Reader;

/**
 * Default implementation of {@link CSVParserInputHelper}.
 * <p>
 * <p>
 * The following end-of-line patterns are recognized by this class: \r, \n,
 * \r\n, \n\r. Any other sequence is treated as separate lines.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class DefaultCSVParserInputHelper
        implements
        CSVParserInputHelper {

    /**
     * if next is line-feed, skip it
     */
    private boolean skipLF = false;

    /**
     * if next is carriage-return, skip it
     */
    private boolean skipCR = false;

    /**
     * Current line number.
     */
    private long lineNumber = 1;

    /**
     * the next character is available in unget as it was put back
     */
    private boolean hasUnget = false;

    /**
     * if a character was put back then it will be stored here
     */
    private int unget = EOL;

    @Override
    public int next(Reader reader)
            throws
            IOException {
        if (hasUnget) {
            hasUnget = false;
            if (unget == EOL) {
                lineNumber++;
            }
            return unget;
        }
        while (true) {
            int next = reader.read();
            switch (next) {
                case '\r':
                    if (!skipCR) {
                        lineNumber++;
                        skipLF = true;
                        return EOL;
                    }
                    skipCR = false;
                    break;
                case '\n':
                    if (!skipLF) {
                        lineNumber++;
                        skipCR = true;
                        return EOL;
                    }
                    skipLF = false;
                    break;
                case -1:
                    return EOF;
                default:
                    skipLF = false;
                    skipCR = false;
                    return next;
            }
        }
    }

    @Override
    public void unget(int character)
            throws
            RuntimeException {
        if (hasUnget) {
            throw new RuntimeException(
                    "Cannot unget more than once between two reads.");
        }
        hasUnget = true;
        unget = character;
        if (unget == EOL) {
            lineNumber--;
        }
    }

    @Override
    public long lineNumber() {
        return lineNumber;
    }

}
