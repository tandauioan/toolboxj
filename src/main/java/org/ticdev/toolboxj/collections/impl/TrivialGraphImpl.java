package org.ticdev.toolboxj.collections.impl;

import org.ticdev.toolboxj.collections.Graph;
import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.SingleView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * A trivial graph implementation, containing a single vertex and no edges.
 *
 * @param <VERTEX> the type of the vertex
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class TrivialGraphImpl<VERTEX>
    implements SingleView<VERTEX>, Graph<VERTEX, Void> {

  /**
   * The vertex holder single.
   */
  private final Single<VERTEX> vertexHolder;

  /**
   * Class constructor.
   *
   * @param vertex the vertex
   */
  public TrivialGraphImpl(final VERTEX vertex) {
    vertexHolder = TupleSupport.of(vertex);
  }

  @Override
  public boolean hasVertex(final VERTEX vertex) {
    return Objects.equals(vertexHolder.item1(), vertex);
  }

  @Override
  public void assertHasVertex(final VERTEX vertex)
      throws IllegalArgumentException {
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
  public Set<VERTEX> connected(
      final VERTEX vertex,
      final boolean excludeSelf
  ) {
    return Collections.emptySet();
  }

  @Override
  public Set<VERTEX> reaching(
      final VERTEX vertex,
      final boolean excludeSelf
  ) {
    return Collections.emptySet();
  }

  @Override
  public Set<VERTEX> reachableFrom(
      final VERTEX vertex,
      final boolean excludeSelf
  ) {
    return Collections.emptySet();
  }

  @Override
  public boolean edgeExists(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return false;
  }

  @Override
  public Set<Void> edges(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return Collections.emptySet();
  }

  @Override
  public Void oneEdge(
      final VERTEX vertex1,
      final VERTEX vertex2
  ) {
    return null;
  }

  @Override
  public VERTEX item1() {
    return vertexHolder.item1();
  }

}
