package org.ticdev.toolboxj.collections;

import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Support methods for maps.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MapSupport {

  /**
   * Converts the elements of an iterator to a  dictionary
   * given key and value extractors from the an element.
   *
   * <p>
   * The following rules apply:
   *   <ul>
   *     <li>If multiple elements resolve to the same key, only one will be retained. It is
   *     not specified which.</li>
   *     <li>Null keys are accepted and mapped.</li>
   *     <li>Null values are accepted and mapped.</li>
   *   </ul>
   * </p>
   *
   * @param iterator       iterator over elements.
   * @param keyExtractor   the function returning the map key from the element.
   * @param valueExtractor the function returning the value from the element.
   * @param <T>            the type of the element.
   * @param <K>            the type of the key.
   * @param <V>            the type of the value.
   * @return the map.
   */
  static <T, K, V> Map<K, V> toMap(
      Function<? super T, ? extends K> keyExtractor,
      Function<? super T, ? extends V> valueExtractor,
      Iterator<T> iterator) {
    final HashMap<K, V> result = new HashMap<>();
    while (iterator.hasNext()) {
      final T element = iterator.next();
      result.put(keyExtractor.apply(element), valueExtractor.apply(element));
    }
    return result;
  }

  /**
   * Like {@link #toMap(Function, Function, Iterator)} with variadic elements.
   *
   * @param keyExtractor   the function returning the map key from the element.
   * @param valueExtractor the function returning the map value from the element.
   * @param elements       the elements.
   * @param <T>            the type of the element.
   * @param <K>            the type of the key.
   * @param <V>            the type of the value.
   * @return the map following the rules in {@link #toMap(Function, Function, Iterator)}.
   */
  @SafeVarargs
  static <T, K, V> Map<K, V> toMap(
      Function<? super T, ? extends K> keyExtractor,
      Function<? super T, ? extends V> valueExtractor,
      T... elements
  ) {
    final HashMap<K, V> result = new HashMap<>();
    for (T element : elements) {
      result.put(keyExtractor.apply(element), valueExtractor.apply(element));
    }
    return result;
  }

  /**
   * Extracts a map from the stream objects using key and value mapping functions.
   *
   * @param keyExtractor   the function extracting the map key from the object.
   * @param valueExtractor the function extracting the map value associated to the key, from
   *                       the object.
   * @param stream         the stream of objects.
   * @param <T>            the type of the objects in the stream.
   * @param <K>            the type of the key in the map.
   * @param <V>            the type of the value in the map.
   * @return the map following the rules in {@link #toMap(Function, Function, Iterator)}.
   */
  static <T, K, V> Map<K, V> toMap(
      Function<? super T, ? extends K> keyExtractor,
      Function<? super T, ? extends V> valueExtractor,
      Stream<T> stream
  ) {
    return stream
        .collect(
            HashMap::new,
            (h, t) -> h.put(keyExtractor.apply(t), valueExtractor.apply(t)),
            HashMap::putAll
        );
  }

  /**
   * Convenience method to create a {@link Map.Entry} pair from the given key and value objects.
   *
   * @param key   the key.
   * @param value the value.
   * @param <K>   the type of the key.
   * @param <V>   the type of the value.
   * @return the new instance of {@link Map.Entry}.
   */
  static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleImmutableEntry<>(key, value);
  }

  /**
   * Convenience method to create a {@link Pair} pair from the given key and value objects.
   *
   * @param key   the key.
   * @param value the value.
   * @param <K>   the type of the key.
   * @param <V>   the type of the value.
   * @return the new instance of {@link Pair}.
   */
  static <K, V> Pair<K, V> entryPair(K key, V value) {
    return TupleSupport.of(key, value);
  }

  /**
   * Like {@link #toMap(Function, Function, Iterator)} using key - value pairs already mapped
   * using {@link #entry(Object, Object)}.
   *
   * @param iterator iterator of entries.
   * @param <K>      the type of the key.
   * @param <V>      the type of the value.
   * @return the new map.
   */
  static <K, V> Map<K, V> entriesToMap(Iterator<Map.Entry<K, V>> iterator) {
    return toMap(Map.Entry::getKey, Map.Entry::getValue, iterator);
  }

  /**
   * Like {@link #toMap(Function, Function, Iterator)} using key - value pairs already mapped
   * using {@link #entryPair(Object, Object)}.
   *
   * @param iterator iterator of pairs.
   * @param <K>      the type of the key.
   * @param <V>      the type of the value.
   * @return the new map.
   */
  static <K, V> Map<K, V> pairsToMap(Iterator<Pair<K, V>> iterator) {
    return toMap(Pair::item1, Pair::item2, iterator);
  }

  /**
   * Like {@link #entriesToMap(Iterator)} using an array instead of an iterator.
   *
   * @param entries the entries.
   * @param <K>     the type of the key.
   * @param <V>     the type of the value.
   * @return the new map.
   */
  @SuppressWarnings("unchecked")
  static <K, V> Map<K, V> entriesToMap(Map.Entry<K, V>... entries) {
    return toMap(Map.Entry::getKey, Map.Entry::getValue, entries);
  }

  /**
   * Like {@link #pairsToMap(Iterator)} using an array instead of an iterator.
   *
   * @param pairs the pairs.
   * @param <K>   the type of the key.
   * @param <V>   the type of the value.
   * @return the new map.
   */
  @SuppressWarnings("unchecked")
  static <K, V> Map<K, V> pairsToMap(Pair<K, V>... pairs) {
    return toMap(Pair::item1, Pair::item2, pairs);
  }

  /**
   * Like {@link #entriesToMap(Iterator)} using a stream instead of an iterator.
   *
   * @param stream the stream.
   * @param <K>    the type of the key.
   * @param <V>    the type of the value.
   * @return the new map.
   */
  static <K, V> Map<K, V> entriesToMap(Stream<Map.Entry<K, V>> stream) {
    return toMap(Map.Entry::getKey, Map.Entry::getValue, stream);
  }

  /**
   * Like {@link #pairsToMap(Iterator)} using a stream instead of an iterator.
   *
   * @param stream the stream.
   * @param <K>    teh type of the key.
   * @param <V>    the type of the value.
   * @return the new map.
   */
  static <K, V> Map<K, V> pairsToMap(Stream<Pair<K, V>> stream) {
    return toMap(Pair::item1, Pair::item2, stream);
  }

}
