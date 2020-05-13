package org.ticdev.toolboxj.primitives;

/**
 * Mutable boolean primitive holder.
 * <p>
 * Non boolean specific integer methods (byte, short, int, long) are accepted
 * with the rule that a value of zero resolved to false, and a value different
 * from zero resolves to true. Getter methods for integer types will return 0
 * for false and 1 for true. All the other methods will throw an unsupported
 * operation exception.
 * </p>
 * <p>
 * {@link #hashCode()} and {@link #equals(Object)} adjust to the current value
 * of the holder.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class BooleanWrapper
    implements
    PrimitiveSetter<BooleanWrapper>,
    PrimitiveGetter<BooleanWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The boolean primitive value.
   */
  private boolean value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public BooleanWrapper(
      final boolean initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default value assigned
   * by Java.
   */
  public BooleanWrapper() {
    /*
     * defaults to the JAVA initial value for a boolean primitive (false,
     * currently)
     */
  }

  @Override
  public BooleanWrapper self() {
    return this;
  }

  @Override
  public boolean booleanValue() {
    return value;
  }

  @Override
  public byte byteValue() {
    return (byte) intValue();
  }

  @Override
  public short shortValue() {
    return (short) intValue();
  }

  @Override
  public int intValue() {
    return value ? 1 : 0;
  }

  @Override
  public long longValue() {
    return intValue();
  }

  @Override
  public float floatValue() {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  @Override
  public double doubleValue() {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  @Override
  public char charValue() {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  @Override
  public BooleanWrapper booleanValue(final boolean booleanValue) {
    this.value = booleanValue;
    return this;
  }

  @Override
  public BooleanWrapper byteValue(final byte byteValue) {
    return intValue(byteValue);
  }

  @Override
  public BooleanWrapper shortValue(final short shortValue) {
    return intValue(shortValue);
  }

  @Override
  public BooleanWrapper intValue(final int intValue) {
    this.value = (intValue != 0);
    return this;
  }

  @Override
  public BooleanWrapper longValue(final long longValue) {
    return intValue((int) longValue);
  }

  @Override
  public BooleanWrapper floatValue(final float floatValue) {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  @Override
  public BooleanWrapper doubleValue(final double doubleValue) {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  @Override
  public BooleanWrapper charValue(final char charValue) {
    throw new UnsupportedOperationException(
        PrimitiveGetter.FORMAT_UNSUPPORTED_EXCEPTION_STRING
            .apply(this));
  }

  /**
   * Sets the value to true.
   *
   * @return this instance
   */
  public BooleanWrapper setTrue() {
    value = true;
    return this;
  }

  /**
   * Returns true if the value is true and false otherwise.
   *
   * @return true if the value is true and false otherwise
   */
  public boolean isTrue() {
    return value;
  }

  /**
   * Returns true if the value is false and false otherwise.
   *
   * @return true if the value is false and false otherwise
   */
  public boolean isFalse() {
    return !value;
  }

  /**
   * Gets the current value then sets it to the given value.
   *
   * @param newValue the new value
   * @return the current value before setting to the new value
   */
  public boolean getThenSet(final boolean newValue) {
    boolean result = value;
    value = newValue;
    return result;
  }

  /**
   * Inverts the value, i.e. if the value is true it becomes false, and
   * vice-versa.
   *
   * @return this instance
   */
  public BooleanWrapper invert() {
    value = !value;
    return this;
  }

  /**
   * Returns the current value then inverts it.
   *
   * @return the current value before inverting it.
   */
  public boolean getThenInvert() {
    boolean result = value;
    value = !value;
    return result;
  }

  /**
   * Inverts the current value and returns it.
   *
   * @return the inverted value
   */
  public boolean invertThenGet() {
    return invert().isTrue();
  }

  /**
   * Sets the value to false.
   *
   * @return this instance
   */
  public BooleanWrapper setFalse() {
    value = false;
    return this;
  }

  /**
   * Creates a new instance of BooleanHolder from the given value.
   *
   * @param value the value
   * @return the new instance
   */
  public static BooleanWrapper of(final boolean value) {
    return new BooleanWrapper(value);
  }

  /**
   * Creates a new instance of BooleanHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static BooleanWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.booleanValue());
  }

  @Override
  public BooleanWrapper copyFrom(
      final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.booleanValue();
    return this;
  }

  @Override
  public int hashCode() {
    return intValue();
  }

  @Override
  public boolean equals(final Object obj) {
    try {
      return this == obj
          || (obj instanceof PrimitiveGetter<?>
          && value == ((PrimitiveGetter<?>) obj).booleanValue());
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public String toString() {
    return value + "";
  }

}
