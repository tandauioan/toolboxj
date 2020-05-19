package org.ticdev.toolboxj.collections.impl;

import org.ticdev.toolboxj.collections.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A graph overlay that indexes the vertices of the given graph and allows
 * indexed access to the vertices.
 *
 * @param <VERTEX> the structure used to maintain information about a node
 * @param <EDGE>   the structure used to maintain information about an edge
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class IndexedVertexGraph<VERTEX, EDGE>
    implements Graph<VERTEX, EDGE> {

  /**
   * Wrapped graph.
   */
  private final Graph<VERTEX, EDGE> graph;

  /**
   * Mapping from the index to the associated vertex.
   */
  private final ArrayList<VERTEX> indexToVertex;

  /**
   * Mapping from the vertex to its associated index.
   */
  private final Map<VERTEX, Integer> vertexToIndex = new HashMap<>();

  /**
   * Class constructor.
   *
   * @param graphRef the graph to wrap.
   * @throws NullPointerException if the argument is null.
   */
  public IndexedVertexGraph(final Graph<VERTEX, EDGE> graphRef) throws
      NullPointerException {
    this.graph = Objects.requireNonNull(graphRef);
    indexToVertex = new ArrayList<>(graphRef.vertexCount());
    graphRef.vertices().forEach(v -> {
      indexToVertex.add(v);
      vertexToIndex.put(v, indexToVertex.size() - 1);
    });
  }

  /**
   * Returns the vertex at the given index.
   *
   * @param index the index.
   * @return the vertex.
   */
  public VERTEX atIndex(final int index) {
    return indexToVertex.get(index);
  }

  /**
   * Returns the index of the given vertex.
   *
   * @param vertex the vertex.
   * @return the index.
   */
  public int indexOf(final VERTEX vertex) {
    assertHasVertex(vertex);
    return vertexToIndex.get(vertex);
  }

  /**
   * Returns the index of the vertex as {@link Integer}.
   *
   * @param vertex the vertex.
   * @return the index.
   */
  public Integer indexOfAsInteger(final VERTEX vertex) {
    assertHasVertex(vertex);
    return vertexToIndex.get(vertex);
  }

  @Override
  public boolean hasVertex(final VERTEX vertex) {
    return graph.hasVertex(vertex);
  }

  @Override
  public void assertHasVertex(final VERTEX vertex)
      throws IllegalArgumentException {
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
  public Set<VERTEX> connected(
      final VERTEX vertex,
      final boolean excludeSelf) {
    return graph.connected(vertex, excludeSelf);
  }

  @Override
  public Set<VERTEX> reaching(
      final VERTEX vertex,
      final boolean excludeSelf) {
    return graph.reaching(vertex, excludeSelf);
  }

  @Override
  public Set<VERTEX> reachableFrom(
      final VERTEX vertex,
      final boolean excludeSelf
  ) {
    return graph.reachableFrom(vertex, excludeSelf);
  }

  @Override
  public boolean edgeExists(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return graph.edgeExists(vertex1, vertex2);
  }

  @Override
  public Set<EDGE> edges(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return graph.edges(vertex1, vertex2);
  }

  @Override
  public EDGE oneEdge(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return graph.oneEdge(vertex1, vertex2);
  }

}
