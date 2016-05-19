package org.ticdev.toolboxj.collections.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.ticdev.toolboxj.collections.Graph;

/**
 * Concrete implementation of a simple graph.
 *
 * <p>
 * The graph is undirected and there are no loops. The node exclusion parameter
 * is ignored.</p>
 *
 * <p>
 * The implementation uses an adjacency triangle (half a matrix). New nodes can
 * be added to the graph, but nodes cannot be removed. Each vertex is
 * represented as an{@link Integer} that represents its position in the
 * adjacency matrix. Two vertices are connected if a non-null edge exists
 * between them. To remove an edge between two vertices set if to null.</p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <EDGE> the edge type
 */
public class SimpleGraphIntVertexImpl<EDGE> implements Graph<Integer, EDGE> { 

    /**
     * Adjacency triangle.
     */
    private final ArrayList<ArrayList<EDGE>> adjacency = new ArrayList<>();

    /**
     * all the vertices for quick lookup and avoid auto-boxing
     */
    private final ArrayList<Integer> vertices = new ArrayList<>();

    /**
     * Default constructor.
     */
    public SimpleGraphIntVertexImpl() {
        
    }
    
    /**
     * Add a new vertex.
     *
     * @return the id of the new vertex that was added to the graph
     */
    public Integer addVertex() {
        int newVertex = vertices.size();
        ArrayList<EDGE> edges = new ArrayList<>();
        for (int i = 0; i < newVertex; i++) {
            edges.add(null);
        }
        adjacency.add(edges);
        Integer result = newVertex;
        vertices.add(result);
        return result;
    }

    /**
     * Sets an edge between two vertices. If the edge is null, the vertices are
     * considered not connected.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param edge the edge
     * @return true if the new edge was registered and false if the vertices are
     * the same instance
     * @throws IllegalArgumentException if the vertices are invalid for the
     * graph.
     */
    public boolean setEdge(Integer vertex1, Integer vertex2, EDGE edge) throws
            IllegalArgumentException {
        assertHasVertex(vertex1);
        if (!vertex1.equals(vertex2)) {
            assertHasVertex(vertex2);
            if (vertex1 < vertex2) {
                adjacency.get(vertex2).set(vertex1, edge);
                return true;
            } else {
                adjacency.get(vertex1).set(vertex2, edge);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasVertex(Integer vertex) {
        return vertex == null ? false : vertex >= 0 && vertex < vertices.size();
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
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < vertex; i++) {
            if (adjacency.get(vertex).get(i) != null) {
                result.add(vertices.get(i));
            }
        }
        int sz = vertices.size();
        for (int i = vertex + 1; i < sz; i++) {
            if (adjacency.get(i).get(vertex) != null) {
                result.add(vertices.get(i));
            }
        }
        return result;
    }

    @Override
    public Set<Integer> reaching(Integer vertex, boolean excludeSelf) {
        return connected(vertex, excludeSelf);
    }

    @Override
    public Set<Integer> reachableFrom(Integer vertex, boolean excludeSelf) {
        return connected(vertex, excludeSelf);
    }

    @Override
    public boolean edgeExists(Integer vertex1, Integer vertex2) {
        assertHasVertex(vertex1);
        if (vertex1.equals(vertex2)) {
            assertHasVertex(vertex2);
            if (vertex1 > vertex2) {
                return adjacency.get(vertex1).get(vertex2) != null;
            } else {
                return adjacency.get(vertex2).get(vertex1) != null;
            }
        }
        return false;
    }

    @Override
    public Set<EDGE> edges(Integer vertex1, Integer vertex2) {
        Set<EDGE> result = new HashSet<>();
        assertHasVertex(vertex1);
        if (vertex1.equals(vertex2)) {
            assertHasVertex(vertex2);
            EDGE edge;
            if (vertex1 > vertex2) {
                edge = adjacency.get(vertex1).get(vertex2);
            } else {
                edge = adjacency.get(vertex2).get(vertex1);
            }
            if (edge != null) {
                result.add(edge);
            }
        }
        return result;
    }

    @Override
    public EDGE oneEdge(Integer vertex1, Integer vertex2) {
        assertHasVertex(vertex1);
        if (vertex1.equals(vertex2)) {
            assertHasVertex(vertex2);
            EDGE edge;
            if (vertex1 > vertex2) {
                return adjacency.get(vertex1).get(vertex2);
            } else {
                return adjacency.get(vertex2).get(vertex1);
            }
        }
        return null;
    }

}
