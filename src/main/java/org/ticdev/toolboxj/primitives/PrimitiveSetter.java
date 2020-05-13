package org.ticdev.toolboxj.primitives;

import org.ticdev.toolboxj.self.Self;

import java.io.Serializable;

/**
 * THis interface exposes setter methods and overloaded set method for all
 * primitive types.
 *
 * @param <T> the concrete implementation of the {@link PrimitiveSetter}
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface PrimitiveSetter<T extends PrimitiveSetter<T>>
    extends Self<T>, Serializable {

  /**
   * Sets the boolean value.
   *
   * @param booleanValue the boolean value
   * @return this instance
   */
  T booleanValue(boolean booleanValue);

  /**
   * Sets the byte value.
   *
   * @param byteValue the byte value
   * @return this instance
   */
  T byteValue(byte byteValue);

  /**
   * Sets the short value.
   *
   * @param shortValue the short value
   * @return this instance
   */
  T shortValue(short shortValue);

  /**
   * Sets the int value.
   *
   * @param intValue the int value
   * @return this instance
   */
  T intValue(int intValue);

  /**
   * Sets the long value.
   *
   * @param longValue the long value
   * @return this instance
   */
  T longValue(long longValue);

  /**
   * Sets the float value.
   *
   * @param floatValue the float value
   * @return this instance
   */
  T floatValue(float floatValue);

  /**
   * Sets the double value.
   *
   * @param doubleValue the double value
   * @return this instance
   */
  T doubleValue(double doubleValue);

  /**
   * Sets the char value.
   *
   * @param charValue the char value
   * @return this instance
   */
  T charValue(char charValue);

  /**
   * Copies the underlying primitive value from the given primitive getter.
   *
   * @param primitiveGetter the primitive getter
   * @return this instance
   */
  T copyFrom(PrimitiveGetter<?> primitiveGetter);

  /**
   * Overloaded method to set the boolean value.
   *
   * @param booleanValue the boolean value
   * @return this instance
   */
  default T set(final boolean booleanValue) {
    return booleanValue(booleanValue);
  }

  /**
   * Overloaded method to set the byte value.
   *
   * @param value the byte value
   * @return this instance
   */
  default T set(byte value) {
    return byteValue(value);
  }

  /**
   * Overloaded method to set the short value.
   *
   * @param shortValue the short value
   * @return this instance
   */
  default T set(final short shortValue) {
    return shortValue(shortValue);
  }

  /**
   * Overloaded method to set the int value.
   *
   * @param intValue the int value
   * @return this instance
   */
  default T set(final int intValue) {
    return intValue(intValue);
  }

  /**
   * Overloaded method to set the long value.
   *
   * @param longValue the long value
   * @return this instance
   */
  default T set(final long longValue) {
    return longValue(longValue);
  }

  /**
   * Overloaded method to set the float value.
   *
   * @param floatValue the float value
   * @return this instance
   */
  default T set(final float floatValue) {
    return floatValue(floatValue);
  }

  /**
   * Overloaded method to set the double value.
   *
   * @param doubleValue the double value
   * @return this instance
   */
  default T set(final double doubleValue) {
    return doubleValue(doubleValue);
  }

  /**
   * Overloaded method to set the char value.
   *
   * @param charValue the char value
   * @return this instance
   */
  default T set(final char charValue) {
    return charValue(charValue);
  }

}
