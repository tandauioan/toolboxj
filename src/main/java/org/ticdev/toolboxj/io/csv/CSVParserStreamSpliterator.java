package org.ticdev.toolboxj.io.csv;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Spliterator to be used with a stream that provides records from a CSV parser.
 * 
 * <p>
 * The spliterator provides the {@link #close()} method that can be registered
 * with {@link Stream#onClose(Runnable)} method to close the reader
 * automatically when the stream is closed.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class CSVParserStreamSpliterator
    implements
    Spliterator<List<String>>,
    AutoCloseable {

    /**
     * The reader
     */
    private final Reader reader;

    /**
     * The parser
     */
    private final CSVParser parser;

    /**
     * Class constructor.
     * 
     * @param parser
     *            the parser
     * @param reader
     *            the reader
     */
    public CSVParserStreamSpliterator(
        CSVParser parser,
        Reader reader) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public boolean tryAdvance(Consumer<? super List<String>> action) {
        try {
            List<String> record =
                parser.parseRecord(reader, new LinkedList<>());
            if (record == null) {
                return false;
            }
            action.accept(record);
            return true;
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public Spliterator<List<String>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.IMMUTABLE | Spliterator.NONNULL
            | Spliterator.ORDERED;
    }

    @Override
    public void close()
        throws Exception {
        reader.close();
    }

}
