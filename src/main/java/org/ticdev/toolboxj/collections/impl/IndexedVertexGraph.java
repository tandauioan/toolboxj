package org.ticdev.toolboxj.collections.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.ticdev.toolboxj.collections.Graph;

/**
 *
 * A graph overlay that indexes the vertices of the given graph and allows
 * indexed access to the vertices.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <VERTEX> the structure used to maintain information about a node
 * @param <EDGE> the structure used to maintain information about an edge
 */
public class IndexedVertexGraph<VERTEX, EDGE> implements Graph<VERTEX, EDGE> {

    /**
     * wrapped graph
     */
    private final Graph<VERTEX, EDGE> graph;

    /**
     * mapping from the index to the associated vertex
     */
    private final ArrayList<VERTEX> indexToVertex;

    /**
     * mapping from the vertex to its associated index
     */
    private final Map<VERTEX, Integer> vertexToIndex = new HashMap<>();

    public IndexedVertexGraph(Graph<VERTEX, EDGE> graph) throws
            NullPointerException {
        if (graph == null) {
            throw new NullPointerException();
        }
        this.graph = graph;
        indexToVertex = new ArrayList<>(graph.vertexCount());
        graph.vertices().forEach(v -> {
            indexToVertex.add(v);
            vertexToIndex.put(v, indexToVertex.size() - 1);
        });
    }

    public VERTEX atIndex(int index) {
        return indexToVertex.get(index);
    }
    
    public int indexOf(VERTEX vertex) {
        assertHasVertex(vertex);
        return vertexToIndex.get(vertex);
    }
    
    public Integer indexOfAsInteger(VERTEX vertex) {
        assertHasVertex(vertex);
        return vertexToIndex.get(vertex);
    }

    @Override
    public boolean hasVertex(VERTEX vertex) {
        return graph.hasVertex(vertex);
    }

    @Override
    public void assertHasVertex(VERTEX vertex) throws IllegalArgumentException {
        graph.assertHasVertex(vertex);
    }

    @Override
    public Set<VERTEX> vertices() {
        return graph.vertices();
    }

    @Override
    public int vertexCount() {
        return graph.vertexCount();
    }

    @Override
    public Set<VERTEX> connected(VERTEX vertex, boolean excludeSelf) {
        return graph.connected(vertex, excludeSelf);
    }

    @Override
    public Set<VERTEX> reaching(VERTEX vertex, boolean excludeSelf) {
        return graph.reaching(vertex, excludeSelf);
    }

    @Override
    public Set<VERTEX> reachableFrom(VERTEX vertex, boolean excludeSelf) {
        return graph.reachableFrom(vertex, excludeSelf);
    }

    @Override
    public boolean edgeExists(VERTEX vertex1, VERTEX vertex2) {
        return graph.edgeExists(vertex1, vertex2);
    }

    @Override
    public Set<EDGE> edges(VERTEX vertex1, VERTEX vertex2) {
        return graph.edges(vertex1, vertex2);
    }

    @Override
    public EDGE oneEdge(VERTEX vertex1, VERTEX vertex2) {
        return graph.oneEdge(vertex1, vertex2);
    }

}
