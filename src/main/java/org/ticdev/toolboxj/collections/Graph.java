package org.ticdev.toolboxj.collections;

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
     * Returns an {@link Iterable} instance that allows iteration over all the
     * nodes that can be reached from this node via an edge.
     *
     * @param node the node
     * @return {@link Iterable} over the neighbors of this node
     * @throws IllegalArgumentException if the node is not recognized as a graph
     * vertex
     */
    public Iterable<VERTEX> getNeighbors(VERTEX node) throws
            IllegalArgumentException;

    /**
     * Returns an {@link Iterable} instance that allows iteration over all the
     * vertices from node1 to node2.
     *
     * @param node1 the first node
     * @param node2 the second node
     * @return {@link Iterable} over the edges
     * @throws IllegalArgumentException if the nodes are not recognized as graph
     * vertices
     */
    public Iterable<EDGE> getEdges(VERTEX node1, VERTEX node2) throws
            IllegalArgumentException;

    /**
     * Returns a single edge from node1 to node2. The choice of edge in a set of
     * edges is implementation defined.
     *
     * @param node1 the first node
     * @param node2 the second node
     * @return an edge, or null if there are no edges from node1 to node2
     * @throws IllegalArgumentException if the nodes are not recognized as graph
     * vertices
     */
    public EDGE getOneEdge(VERTEX node1, VERTEX node2) throws
            IllegalArgumentException;

    /**
     * Returns all the nodes/vertices in this graph.
     *
     * @return all the nodes in this graph
     */
    public Iterable<VERTEX> getVertices();

}
