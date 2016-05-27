package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a concrete extension of an {@link InputStream} whose read methods
 * always return EOF (-1). This class is implemented as a singleton. The
 * {@link InputStream#close()} method has no effect on the single instance of
 * this class. The {@link InputStream#available()} method always returns 0.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class EOFInputStream
    extends
    InputStream {

    /**
     * Singleton instance
     */
    private static final EOFInputStream INSTANCE =
        new EOFInputStream();

    /**
     * Default singleton constructor.
     */
    private EOFInputStream() {
    }

    /**
     * Returns the singleton instance.
     * 
     * @return the singleton instance.
     */
    public static EOFInputStream getInstance() {
        return INSTANCE;
    }
    
    @Override
    public int read()
        throws IOException {
        return -1;
    }
    
    @Override
    public int available()
        throws IOException {
        return 0;
    }

}
