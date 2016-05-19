package org.ticdev.toolboxj.collections;

import java.util.Set;

/**
 *
 * Graph interface.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <VERTEX> the structure used to maintain information about a node
 * @param <EDGE> the structure used to maintain information about an edge
 */
public interface Graph<VERTEX, EDGE> {

    /**
     * Returns true if the vertex exists in the graph and false otherwise.
     *
     * @param vertex the vertex
     * @return true if the vertex exists in the graph and false otherwise.
     */
    boolean hasVertex(VERTEX vertex);

    /**
     * Like {@link #hasVertex(java.lang.Object)} but throws an
     * {@link IllegalArgumentException} if the vertex doesn't exist in this
     * graph.
     *
     * @param vertex the vertex
     * @throws IllegalArgumentException if the vertex does not exist in this
     * graph
     */
    void assertHasVertex(VERTEX vertex) throws IllegalArgumentException;

    /**
     * Returns a set of all the vertices in this graph.
     *
     * @return a set of all the vertices in this graph.
     */
    Set<VERTEX> vertices();

    /**
     * Returns the number of vertices in this graph
     *
     * @return the number of vertices in this graph
     */
    int vertexCount();

    /**
     * Returns all the vertices that are connected to this graph via at least
     * one edge, regardless of direction.
     *
     * @param vertex the starting vertex
     * @param excludeSelf if true, the current node is excluded from the set
     * even if there is an edge to itself
     * @return all the vertices connected to this vertex
     */
    Set<VERTEX> connected(VERTEX vertex, boolean excludeSelf);

    /**
     * Returns all the vertices that can reach this vertex via at least one
     * edge. The direction is taken into account, if applicable.
     *
     * @param vertex the vertex that is reached
     * @param excludeSelf true if the vertex itself should be excluded even if
     * there is an edge pointing to itself.
     * @return all the vertices that can reach this vertex via at least one edge
     */
    Set<VERTEX> reaching(VERTEX vertex, boolean excludeSelf);

    /**
     * Returns all the vertices that can be reached from this vertex via at
     * least one edge. The direction is take into account, if applicable.
     *
     * @param vertex the vertex that is reached
     * @param excludeSelf true if the vertex itself should be excluded even if
     * there is an edge pointing to itself.
     * @return all the vertices that can be reached from this vertex via at
     * least one edge.
     */
    Set<VERTEX> reachableFrom(VERTEX vertex, boolean excludeSelf);

    /**
     * Returns true if an edge exists from vertex1 to vertex2.
     *
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @return true if an edge exists from vertex1 to vertex2 and false
     * otherwise.
     */
    boolean edgeExists(VERTEX vertex1, VERTEX vertex2);

    /**
     * Returns all the edges starting in vertex1 and ending in vertex2
     *
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @return all the edges starting in vertex1 and ending in vertex2
     */
    Set<EDGE> edges(VERTEX vertex1, VERTEX vertex2);

    /**
     * Returns one edge starting in vertex1 and ending in vertex2.
     *
     * If there are multiple edges between the two nodes, the implementation can
     * choose what to return.
     *
     * @param vertex1 the starting vertex
     * @param vertex2 the ending vertex
     * @return one edge starting from vertex1 and ending in vertex2. If there is
     * no edge, null is returned.
     */
    EDGE oneEdge(VERTEX vertex1, VERTEX vertex2);

}
