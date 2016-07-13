package org.ticdev.toolboxj.collections.impl;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.ticdev.toolboxj.collections.Graph;
import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * A trivial graph implementation, containing a single vertex and no edges.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <VERTEX> the type of the vertex
 */
public class TrivialGraphImpl<VERTEX> implements Single<VERTEX>, Graph<VERTEX, Void> {

    /**
     * The vertex holder single
     */
    private final Single<VERTEX> vertexHolder;

    /**
     * Class constructor.
     *
     * @param vertex the vertex
     */
    public TrivialGraphImpl(VERTEX vertex) {
        vertexHolder = TupleSupport.of(vertex);
    }

    @Override
    public boolean hasVertex(VERTEX vertex) {
        return Objects.equals(vertexHolder.item1(), vertex);
    }

    @Override
    public void assertHasVertex(VERTEX vertex) throws IllegalArgumentException {
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<VERTEX> vertices() {
        return Collections.singleton(vertexHolder.item1());
    }

    @Override
    public int vertexCount() {
        return 1;
    }

    @Override
    public Set<VERTEX> connected(VERTEX vertex, boolean excludeSelf) {
        return Collections.emptySet();
    }

    @Override
    public Set<VERTEX> reaching(VERTEX vertex, boolean excludeSelf) {
        return Collections.emptySet();
    }

    @Override
    public Set<VERTEX> reachableFrom(VERTEX vertex, boolean excludeSelf) {
        return Collections.emptySet();
    }

    @Override
    public boolean edgeExists(VERTEX vertex1, VERTEX vertex2) {
        return false;
    }

    @Override
    public Set<Void> edges(VERTEX vertex1, VERTEX vertex2) {
        return Collections.emptySet();
    }

    @Override
    public Void oneEdge(VERTEX vertex1, VERTEX vertex2) {
        return null;
    }

    @Override
    public VERTEX item1() {
        return vertexHolder.item1();
    }


}
