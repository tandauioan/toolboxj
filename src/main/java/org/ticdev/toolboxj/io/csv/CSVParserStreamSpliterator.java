package org.ticdev.toolboxj.io.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CSVParserStreamSpliterator
    implements
    Spliterator<List<String>> {

    private final Reader reader;

    private final CSVParser parser;

    private CSVParserStreamSpliterator(
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

    public static Stream<List<String>>
        stream(final CSVParser parser, final Reader reader) {

        return StreamSupport
            .stream(new CSVParserStreamSpliterator(parser, reader), false)
            .onClose(() -> {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });

    }

}
