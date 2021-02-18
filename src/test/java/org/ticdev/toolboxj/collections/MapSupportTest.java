package org.ticdev.toolboxj.collections;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test class for {@link MapSupport}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class MapSupportTest {

  /**
   * Key creation modulo 10 for overlapping keys.
   */
  private static final Function<Integer, Integer> KEYCREATOR = i -> i % 10;

  /**
   * Map with duplicated keys, with {@link Pair} entries.
   */
  private static final List<Pair<Integer, Integer>> PAIRLISTWITHDUPLICATEDKEYS =
      IntStream.range(0, 100)
          .mapToObj(i -> TupleSupport.of(KEYCREATOR.apply(i), i))
          .collect(Collectors.toList());

  /**
   * A set with all the values associated to the key, as a set.
   */
  private static final Map<Integer, Set<Integer>> PAIREXPECTEDKEYSWITHVALUESINSET =
      PAIRLISTWITHDUPLICATEDKEYS
          .stream()
          .collect(Collectors.toMap(
              Pair::item1,
              p -> new HashSet<>(Collections.singleton(p.item2())),
              (set1, set2) -> {
                set1.addAll(set2);
                return set1;
              }
          ));

  /**
   * Map with duplicated keys, with {@link java.util.Map.Entry} entries.
   */
  private static final List<Map.Entry<Integer, Integer>> ENTRYLISTWITHDUPLICATEDKEYS =
      IntStream.range(0, 100)
          .mapToObj(i -> MapSupport.entry(KEYCREATOR.apply(i), i))
          .collect(Collectors.toList());

  /**
   * A set with all the values associated to the key, as a set.
   */
  private static final Map<Integer, Set<Integer>> ENTRYEXPECTEDKEYSWITHVALUESINSET =
      ENTRYLISTWITHDUPLICATEDKEYS
          .stream()
          .collect(Collectors.toMap(
              Map.Entry::getKey,
              e -> new HashSet<>(Collections.singleton(e.getValue())),
              (set1, set2) -> {
                set1.addAll(set2);
                return set1;
              }
          ));

  /**
   * For each entry in map, checks that the value associated with the key
   * is a value in the set associated with the same key in mapWithAllValues.
   *
   * @param mapWithAllValues the map containing all the values for a key.
   * @param map              the map to check.
   */
  private static void matchesDuplicatedValues(
      Map<Integer, Set<Integer>> mapWithAllValues,
      Map<Integer, Integer> map
  ) {
    Assert.assertEquals(mapWithAllValues.keySet().size(), map.keySet().size());
    map.entrySet().stream()
        .map(actentry ->
            TupleSupport.of(
                actentry.getValue(),
                mapWithAllValues.get(actentry.getKey())))
        .forEach(pair -> {
          Assert.assertNotNull(pair.item2());
          Assert.assertTrue(pair.item2().contains(pair.item1()));
        });
  }

  @Test
  public void testToMapWithMappersAndIterator() {
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Pair::item1,
            Pair::item2,
            PAIRLISTWITHDUPLICATEDKEYS.iterator()
        ));
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            ENTRYLISTWITHDUPLICATEDKEYS.iterator()
        )
    );
  }

  @Test
  public void testToMapWithMappersAndStream() {
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Pair::item1,
            Pair::item2,
            PAIRLISTWITHDUPLICATEDKEYS.stream()
        ));
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            ENTRYLISTWITHDUPLICATEDKEYS.stream()
        )
    );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testToMapWithMappersAndArray() {
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Pair::item1,
            Pair::item2,
            PAIRLISTWITHDUPLICATEDKEYS.toArray(
                (Pair<Integer, Integer>[]) Array.newInstance(Pair.class, 0))
        ));
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            ENTRYLISTWITHDUPLICATEDKEYS.toArray(
                (Map.Entry<Integer, Integer>[]) Array.newInstance(Map.Entry.class, 0))
        )
    );
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testToMap() {
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.pairsToMap(PAIRLISTWITHDUPLICATEDKEYS.iterator())
    );
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.pairsToMap(PAIRLISTWITHDUPLICATEDKEYS.stream())
    );
    matchesDuplicatedValues(
        PAIREXPECTEDKEYSWITHVALUESINSET,
        MapSupport.pairsToMap(PAIRLISTWITHDUPLICATEDKEYS.toArray(
            (Pair<Integer, Integer>[]) Array.newInstance(Pair.class, 0)
        ))
    );
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.entriesToMap(ENTRYLISTWITHDUPLICATEDKEYS.iterator())
    );
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.entriesToMap(ENTRYLISTWITHDUPLICATEDKEYS.stream())
    );
    matchesDuplicatedValues(
        ENTRYEXPECTEDKEYSWITHVALUESINSET,
        MapSupport.entriesToMap(ENTRYLISTWITHDUPLICATEDKEYS.toArray(
            (Map.Entry<Integer, Integer>[]) Array.newInstance(Map.Entry.class, 0)
        ))
    );
  }

  @Test
  public void testEntryPair() {
    final List<String> values = Arrays.asList("s1", "s2");
    values.forEach(v1 ->
        values.forEach(v2 ->
            Assert.assertEquals(
                TupleSupport.of(v1, v2),
                MapSupport.entryPair(v1, v2)
            )));
  }

  @Test
  public void testEntry() {
    final List<String> values = Arrays.asList("s1", "s2");
    values.forEach(v1 ->
        values.forEach(v2 ->
            Assert.assertEquals(
                new AbstractMap.SimpleImmutableEntry<>(v1, v2),
                MapSupport.entry(v1, v2)
            )));
  }

}