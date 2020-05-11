package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.QuadView;
import org.ticdev.toolboxj.tuples.SingleView;
import org.ticdev.toolboxj.tuples.TripletView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Support for implementing direct access look-up via integer index for
 * tuples.
 *
 * @param <CONTAINER> the type of the container.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
@FunctionalInterface
public interface TupleIndexedLookup<CONTAINER> {

  /**
   * Single indexer.
   */
  TupleIndexedLookup<SingleView<?>> SINGLE =
      TupleIndexedLookup.<SingleView<?>>newBuilder()
          .add(SingleView::item1)
          .build();

  /**
   * The size of single tuples.
   */
  int SINGLE_SIZE = 1;

  /**
   * Pair indexer.
   */
  TupleIndexedLookup<PairView<?, ?>> PAIR =
      TupleIndexedLookup.<PairView<?, ?>>newBuilder()
          .add(PairView::item1)
          .add(PairView::item2)
          .build();

  /**
   * The size of pair tuples.
   */
  int PAIR_SIZE = 2;

  /**
   * Triplet indexer.
   */
  TupleIndexedLookup<TripletView<?, ?, ?>> TRIPLET =
      TupleIndexedLookup.<TripletView<?, ?, ?>>newBuilder()
          .add(TripletView::item1)
          .add(TripletView::item2)
          .add(TripletView::item3)
          .build();

  /**
   * The size of triplet tuples.
   */
  int TRIPLET_SIZE = 3;

  /**
   * Quad indexer.
   */
  TupleIndexedLookup<QuadView<?, ?, ?, ?>> QUAD =
      TupleIndexedLookup.<QuadView<?, ?, ?, ?>>newBuilder()
          .add(QuadView::item1)
          .add(QuadView::item2)
          .add(QuadView::item3)
          .add(QuadView::item4)
          .build();

  /**
   * The sixe of quad tuples.
   */
  int QUAD_SIZE = 4;

  /**
   * Returns the element on the index position in the given container
   * (tuple).
   *
   * @param container the container.
   * @param index     the element index.
   * @return the element at index.
   * @throws IndexOutOfBoundsException if index is out of bounds.
   */
  Object get(CONTAINER container, int index) throws IndexOutOfBoundsException;

  /**
   * Look-up builder for a container of a given class.
   *
   * @param <CONTAINER> the class of the container.
   */
  class Builder<CONTAINER> {

    /**
     * Retriever function lookup.
     */
    private final List<Function<CONTAINER, Object>> retriever =
        new LinkedList<>();

    /**
     * Add lookup function to the container.
     *
     * @param function the lookup function.
     * @return the container builder.
     */
    Builder<CONTAINER> add(final Function<CONTAINER, Object> function) {
      retriever.add(function);
      return this;
    }

    /**
     * Creates and returns a new {@link TupleIndexedLookup} instance.
     *
     * @return a new lookup instance.
     */
    TupleIndexedLookup<CONTAINER> build() {
      final ArrayList<Function<CONTAINER, Object>> lookUp =
          new ArrayList<>(retriever);
      return (container, index) -> lookUp.get(index).apply(container);
    }

  }

  /**
   * Creates a new indexed lookup builder instance.
   *
   * @param <C> the type of container.
   * @return the new instance of a builder.
   */
  static <C> Builder<C> newBuilder() {
    return new Builder<>();
  }

}
