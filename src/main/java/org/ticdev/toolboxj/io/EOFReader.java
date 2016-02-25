package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.Reader;

/**
 * This is a {@link Reader} that has reached the end-of-file. The
 * {@link EOFReader#ready()} will always return true.
 * 
 * <p>
 * This class is implemented as a singleton and is also thread safe as it
 * doesn't maintain any internal states. The {@link #close()} method has no
 * effect on the singleton instance.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class EOFReader
    extends
    Reader {

    /**
     * Singleton instance
     */
    private final static EOFReader INSTANCE =
        new EOFReader();

    /**
     * Private constructor
     */
    private EOFReader() {
    }

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance.
     */
    public final static EOFReader getInstance() {
        return INSTANCE;
    }

    @Override
    public int read()
        throws IOException {
        return -1;
    }

    @Override
    public int read(char[] cbuf, int off, int len)
        throws IOException {
        if (off < 0 || len < 0 || cbuf.length - len < 0
            || cbuf.length - len < off) {
            throw new IndexOutOfBoundsException();
        }
        return -1;
    }

    @Override
    public void close()
        throws IOException {
        /* do nothing */
    }

    @Override
    public boolean ready()
        throws IOException {
        return true;
    }

}
