package org.ticdev.toolboxj.primitives;

/**
 * Mutable int primitive holder.
 *
 * <p>
 * Java primitive conversions are used for all numeric types. Boolean values are
 * false if 0 and true if non-zero. Boolean getter will return 0 for false and 1
 * for true.
 * </p>
 *
 * <p>
 * {@link #hashCode()} and {@link #equals(Object)} adjust to the current value
 * of the holder.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class IntWrapper
    extends Number
    implements
    PrimitiveSetter<IntWrapper>,
    PrimitiveGetter<IntWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The int primitive value.
   */
  private int value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public IntWrapper(
      final int initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default value set by
   * Java
   */
  public IntWrapper() {
    /*
     * defaults to the JAVA initial value for a int primitive (0, currently)
     */
  }

  @Override
  public IntWrapper self() {
    return this;
  }

  @Override
  public boolean booleanValue() {
    return value != 0;
  }

  @Override
  public byte byteValue() {
    return (byte) value;
  }

  @Override
  public short shortValue() {
    return (short) value;
  }

  @Override
  public int intValue() {
    return value;
  }

  @Override
  public long longValue() {
    return value;
  }

  @Override
  public float floatValue() {
    return value;
  }

  @Override
  public double doubleValue() {
    return value;
  }

  @Override
  public char charValue() {
    return (char) value;
  }

  @Override
  public IntWrapper booleanValue(final boolean booleanValue) {
    this.value = (booleanValue ? 1 : 0);
    return this;
  }

  @Override
  public IntWrapper byteValue(final byte byteValue) {
    this.value = byteValue;
    return this;
  }

  @Override
  public IntWrapper shortValue(final short shortValue) {
    this.value = shortValue;
    return this;
  }

  @Override
  public IntWrapper intValue(final int intValue) {
    this.value = intValue;
    return this;
  }

  @Override
  public IntWrapper longValue(final long longValue) {
    this.value = (int) longValue;
    return this;
  }

  @Override
  public IntWrapper floatValue(final float floatValue) {
    this.value = (int) floatValue;
    return this;
  }

  @Override
  public IntWrapper doubleValue(final double doubleValue) {
    this.value = (int) doubleValue;
    return this;
  }

  @Override
  public IntWrapper charValue(final char charValue) {
    this.value = charValue;
    return this;
  }

  @Override
  public IntWrapper copyFrom(final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.intValue();
    return this;
  }

  /**
   * Creates a new instance of the IntHolder from the given value.
   *
   * @param value the value
   * @return the new instance
   */
  public static IntWrapper of(final int value) {
    return new IntWrapper(value);
  }

  /**
   * Creates a new instance of IntHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static IntWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.intValue());
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public boolean equals(final Object obj) {
    try {
      return this == obj
          || (obj instanceof PrimitiveGetter<?>
          && value == ((PrimitiveGetter<?>) obj).intValue());
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public String toString() {
    return value + "";
  }

  /**
   * Increments the value by one.
   *
   * @return this instance
   */
  public IntWrapper increment() {
    value++;
    return this;
  }

  /**
   * Decrements the value by one.
   *
   * @return this instance
   */
  public IntWrapper decrement() {
    value--;
    return this;
  }

  /**
   * Adds the given amount to this value.
   *
   * @param amount the amount
   * @return this instance
   */
  public IntWrapper add(final int amount) {
    value += amount;
    return this;
  }

  /**
   * Returns the current value then increments it.
   *
   * @return the value before incrementing it.
   */
  public int getThenIncrement() {
    int result = value;
    value++;
    return result;
  }

  /**
   * Increments the current value and returns it.
   *
   * @return the incremented value
   */
  public int incrementThenGet() {
    value++;
    return value;
  }

  /**
   * Returns the current value then decrements it.
   *
   * @return the value before decrementing it.
   */
  public int getThenDecrement() {
    int result = value;
    value--;
    return result;
  }

  /**
   * Decrements the current value and returns it.
   *
   * @return the decremented value.
   */
  public int decrementThenGet() {
    value--;
    return value;
  }

  /**
   * Returns the current value then adds the given amount.
   *
   * @param amount the amount to add
   * @return the value before adding the amount
   */
  public int getThenAdd(final int amount) {
    int result = value;
    value += amount;
    return result;
  }

  /**
   * Adds the given amount to the current value and returns it.
   *
   * @param amount the amount to add
   * @return the value after adding the given amount
   */
  public int addThenGet(final int amount) {
    value += amount;
    return value;
  }

}
