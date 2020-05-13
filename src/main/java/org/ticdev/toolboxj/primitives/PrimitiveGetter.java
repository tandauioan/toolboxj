package org.ticdev.toolboxj.primitives;

import org.ticdev.toolboxj.self.Self;

import java.io.Serializable;
import java.util.function.Function;

/**
 * This interface exposes getter methods for all primitive types.
 *
 * @param <T> the concrete implementation of the {@link PrimitiveGetter}
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface PrimitiveGetter<T extends PrimitiveGetter<T>>
    extends Self<T>, Serializable {

  /**
   * A standard function that creates an unsupported operation message for the
   * given object.
   */
  Function<Object, String> FORMAT_UNSUPPORTED_EXCEPTION_STRING =
      (o) -> "Operation unsupported for "
          + o.getClass().getCanonicalName();

  /**
   * Standard message used for unsupported operation exceptions.
   */
  String UNSUPPORTED_OPERATION_MESSAGE =
      "Operation incompatible with this instance";

  /**
   * Returns the primitive value.
   *
   * @return the primitive value
   */
  boolean booleanValue();

  /**
   * Returns the byte value.
   *
   * @return the byte value
   */
  byte byteValue();

  /**
   * Returns the short value.
   *
   * @return the short value
   */
  short shortValue();

  /**
   * Returns the int value.
   *
   * @return the int value
   */
  int intValue();

  /**
   * Returns the long value.
   *
   * @return the long value
   */
  long longValue();

  /**
   * Returns the float value.
   *
   * @return the float value
   */
  float floatValue();

  /**
   * Returns the double value.
   *
   * @return the double value
   */
  double doubleValue();

  /**
   * Returns the char value.
   *
   * @return the char value
   */
  char charValue();

}
