package org.ticdev.toolboxj.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.Iterator;

/**
 * Concrete implementation of an iterator that iterates over all the objects
 * from an input stream.
 * 
 * <p>
 * The {@link #next()} method can throw a {@link RuntimeException} if an error
 * was encountered while reading from the stream.
 * </p>
 * 
 * <p>
 * The iterator must be closed using the {@link #close()} method.
 * </p>
 * 
 * <p>
 * The {@link #remove()} method is not supported by this iterator.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the type of the object read from the stream.
 */
public class ObjectStreamIterator<T>
    implements
    Iterator<T>,
    AutoCloseable {

    /**
     * The object input stream.
     */
    private final ObjectInputStream inputStream;

    /**
     * The class of the objects read from the input stream.
     */
    private final Class<T> clazz;

    /**
     * The next cached object
     */
    private T nextObject = null;

    /**
     * Class constructor.
     * 
     * @param inputStream
     *            the input stream
     * @param clazz
     *            the class of objects that are read from the stream
     * @throws Exception
     *             if an exception occurs
     */
    public ObjectStreamIterator(
        InputStream inputStream,
        Class<T> clazz)
            throws Exception {
        this.inputStream = new ObjectInputStream(inputStream);
        this.clazz = clazz;
        try {
            cache_next_();
        } catch (Exception ex) {
            try {
                inputStream.close();
            } catch (Exception ex1) {
                ex.addSuppressed(ex1);
            }
            throw ex;
        }
    }

    /**
     * Caches the next record.
     * 
     * @throws Exception
     *             if an exception occurs
     */
    private void cache_next_()
        throws Exception {
        try {
            nextObject = clazz.cast(inputStream.readObject());
        } catch (EOFException ex) {
            nextObject = null;
        } catch (OptionalDataException ex) {
            if (ex.eof) {
                nextObject = null;
            } else {
                throw ex;
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw ex;
        }
    }

    @Override
    public void close()
        throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Override
    public boolean hasNext() {
        return nextObject != null;
    }

    @Override
    public T next() {
        T result = nextObject;
        try {
            if (nextObject != null) {
                cache_next_();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

}
