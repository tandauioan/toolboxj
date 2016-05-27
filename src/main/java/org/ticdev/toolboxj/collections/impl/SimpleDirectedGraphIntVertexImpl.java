package org.ticdev.toolboxj.collections.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.ticdev.toolboxj.collections.Graph;

/**
 *
 * Concrete implementation of a simple directed graph implementation.
 *
 * <p>
 * The graph has no loops. There can be only one directed edge from one node to
 * another. E.g. (node1, node2, edge12), (node2, node1, edge21).</p>
 *
 * <p>
 * The implementation uses an adjacency matrix. The meaning of source node and
 * destination node is strict. New nodes can be added to the graph, but nodes
 * cannot be removed. Each vertex is represented as an {@link Integer} that
 * represents its position in the adjacency matrix. A null value for an edge
 * from one node to another means that there is no direct path from the source
 * node to the destination node.</p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <EDGE> the edge type
 */
public class SimpleDirectedGraphIntVertexImpl<EDGE> implements Graph<Integer, EDGE> {

    /**
     * Adjacency matrix
     */
    private final ArrayList<ArrayList<EDGE>> adjacency = new ArrayList<>();

    /**
     * all the vertices for quick lookup and avoid auto-boxing
     */
    private final ArrayList<Integer> vertices = new ArrayList<>();

    /**
     * Default constructor.
     */
    public SimpleDirectedGraphIntVertexImpl() {

    }

    /**
     * Adds a new vertex and resizes the adjacency matrix accordingly
     *
     * @return the new vertex
     */
    public Integer addVertex() {
        int newVertex = vertices.size();
        ArrayList<EDGE> edges = new ArrayList<>();
        for (int i = 0; i <= newVertex; i++) {
            edges.add(null);
        }
        adjacency.stream().forEach(e -> e.add(null));
        adjacency.add(edges);
        Integer result = newVertex;
        vertices.add(result);
        return result;
    }

    /**
     * Sets and edge from the source vertex to the destination vertex. If the
     * edge is null, then there will be no edge between the source and the
     * destination (existing edge, if any, will be removed).
     *
     * @param source the source vertex
     * @param destination the destination vertex
     * @param edge the edge
     * @return true if the new edge was registered and false if the vertices are
     * the same instance.
     * @throws IllegalArgumentException if the vertices are invalid for the
     * graph.
     */
    public boolean setEdge(Integer source, Integer destination, EDGE edge) throws
            IllegalArgumentException {
        assertHasVertex(source);
        if (!source.equals(destination)) {
            assertHasVertex(source);
            adjacency.get(source).set(destination, edge);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasVertex(Integer vertex) {
        return vertex != null && vertex >= 0 && vertex < vertices.size();
    }

    @Override
    public void assertHasVertex(Integer vertex) throws IllegalArgumentException {
        if (!hasVertex(vertex)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<Integer> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public Set<Integer> connected(Integer vertex, boolean excludeSelf) {
        assertHasVertex(vertex);
        final Set<Integer> result = new HashSet<>();
        ArrayList<EDGE> current = adjacency.get(vertex);
        for (int v = 0; v < vertex; v++) {
            if (current.get(v) != null || adjacency.get(v).get(vertex) != null) {
                result.add(vertices.get(v));
            }
        }
        for (int v = vertex + 1; v < vertices.size(); v++) {
            if (current.get(v) != null || adjacency.get(v).get(vertex) != null) {
                result.add(vertices.get(v));
            }
        }
        return result;
    }

    @Override
    public Set<Integer> reaching(Integer vertex, boolean excludeSelf) {
        assertHasVertex(vertex);
        final Set<Integer> result = new HashSet<>();
        for (int v = 0; v < vertex; v++) {
            if (adjacency.get(v).get(vertex) != null) {
                result.add(vertices.get(v));
            }
        }
        for (int v = vertex + 1; v < vertices.size(); v++) {
            if (adjacency.get(v).get(vertex) != null) {
                result.add(vertices.get(v));
            }
        }
        return result;
    }

    @Override
    public Set<Integer> reachableFrom(Integer vertex, boolean excludeSelf) {
        assertHasVertex(vertex);
        final Set<Integer> result = new HashSet<>();
        final ArrayList<EDGE> current = adjacency.get(vertex);
        for (int v = 0; v < vertex; v++) {
            if (current.get(v) != null) {
                result.add(vertices.get(v));
            }
        }
        for (int v = vertex + 1; v < vertices.size(); v++) {
            if (current.get(v) != null) {
                result.add(vertices.get(v));
            }
        }
        return result;
    }

    @Override
    public boolean edgeExists(Integer vertex1, Integer vertex2) {
        assertHasVertex(vertex1);
        if (vertex1.equals(vertex2)) {
            return false;
        }
        assertHasVertex(vertex2);
        return adjacency.get(vertex1).get(vertex2) != null;
    }

    @Override
    public Set<EDGE> edges(Integer vertex1, Integer vertex2) {
        EDGE result = oneEdge(vertex1, vertex2);
        return Collections.singleton(oneEdge(vertex1, vertex2));
    }

    @Override
    public EDGE oneEdge(Integer vertex1, Integer vertex2) {
        assertHasVertex(vertex1);
        if (vertex1.equals(vertex2)) {
            return null;
        }
        assertHasVertex(vertex2);
        return adjacency.get(vertex1).get(vertex2);
    }

}
