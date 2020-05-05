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
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
@FunctionalInterface
public interface TupleIndexedLookup<CONTAINER> {

  TupleIndexedLookup<SingleView<?>> SINGLE =
      TupleIndexedLookup.<SingleView<?>>newBuilder()
          .add(SingleView::item1)
          .build();

  int SINGLE_SIZE = 1;

  TupleIndexedLookup<PairView<?, ?>> PAIR =
      TupleIndexedLookup.<PairView<?, ?>>newBuilder()
          .add(PairView::item1)
          .add(PairView::item2)
          .build();

  int PAIR_SIZE = 2;

  TupleIndexedLookup<TripletView<?, ?, ?>> TRIPLET =
      TupleIndexedLookup.<TripletView<?, ?, ?>>newBuilder()
          .add(TripletView::item1)
          .add(TripletView::item2)
          .add(TripletView::item3)
          .build();

  int TRIPLET_SIZE = 3;

  TupleIndexedLookup<QuadView<?, ?, ?, ?>> QUAD =
      TupleIndexedLookup.<QuadView<?, ?, ?, ?>>newBuilder()
          .add(QuadView::item1)
          .add(QuadView::item2)
          .add(QuadView::item3)
          .add(QuadView::item4)
          .build();

  int QUAD_SIZE = 4;

  Object get(CONTAINER container, int index) throws IndexOutOfBoundsException;

  class Builder<CONTAINER> {

    final List<Function<CONTAINER, Object>> retriever = new LinkedList<>();

    Builder<CONTAINER> add(Function<CONTAINER, Object> function) {
      retriever.add(function);
      return this;
    }

    TupleIndexedLookup<CONTAINER> build() {
      final ArrayList<Function<CONTAINER, Object>> lookUp =
          new ArrayList<>(retriever);
      return (container, index) -> lookUp.get(index).apply(container);
    }

  }

  static <C> Builder<C> newBuilder() {
    return new Builder<>();
  }

}
