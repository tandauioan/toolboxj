package org.ticdev.toolboxj.primitives;

/**
 * Mutable long primitive holder.
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
public final class LongWrapper
    extends Number
    implements
    PrimitiveSetter<LongWrapper>,
    PrimitiveGetter<LongWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The long primitive value.
   */
  private long value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public LongWrapper(
      final long initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default value set by
   * Java
   */
  public LongWrapper() {
    /*
     * defaults to the JAVA initial value for a long primitive (0,
     * currently)
     */
  }

  @Override
  public LongWrapper self() {
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
    return (int) value;
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
  public LongWrapper booleanValue(final boolean booleanValue) {
    this.value = (booleanValue ? 1 : 0);
    return this;
  }

  @Override
  public LongWrapper byteValue(final byte byteValue) {
    this.value = byteValue;
    return this;
  }

  @Override
  public LongWrapper shortValue(final short shortValue) {
    this.value = shortValue;
    return this;
  }

  @Override
  public LongWrapper intValue(final int intValue) {
    this.value = intValue;
    return this;
  }

  @Override
  public LongWrapper longValue(final long longValue) {
    this.value = longValue;
    return this;
  }

  @Override
  public LongWrapper floatValue(final float floatValue) {
    this.value = (long) floatValue;
    return this;
  }

  @Override
  public LongWrapper doubleValue(final double doubleValue) {
    this.value = (long) doubleValue;
    return this;
  }

  @Override
  public LongWrapper charValue(final char charValue) {
    this.value = charValue;
    return this;
  }

  @Override
  public LongWrapper copyFrom(final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.longValue();
    return this;
  }

  /**
   * Creates a new instance of the LongHolder from the given value.
   *
   * @param value the value
   * @return the new instance
   */
  public static LongWrapper of(final long value) {
    return new LongWrapper(value);
  }

  /**
   * Creates a new instance of LongHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static LongWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.longValue());
  }

  @Override
  public int hashCode() {
    return (int) value;
  }

  @Override
  public boolean equals(final Object obj) {
    try {
      return this == obj
          || (obj instanceof PrimitiveGetter<?>
          && value == ((PrimitiveGetter<?>) obj).longValue());
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
  public LongWrapper increment() {
    value++;
    return this;
  }

  /**
   * Decrements the value by one.
   *
   * @return this instance
   */
  public LongWrapper decrement() {
    value--;
    return this;
  }

  /**
   * Adds the given amount to this value.
   *
   * @param amount the amount
   * @return this instance
   */
  public LongWrapper add(final long amount) {
    value += amount;
    return this;
  }

  /**
   * Returns the current value then increments it.
   *
   * @return the value before incrementing it.
   */
  public long getThenIncrement() {
    long result = value;
    value++;
    return result;
  }

  /**
   * Increments the current value and returns it.
   *
   * @return the incremented value
   */
  public long incrementThenGet() {
    value++;
    return value;
  }

  /**
   * Returns the current value then decrements it.
   *
   * @return the value before decrementing it.
   */
  public long getThenDecrement() {
    long result = value;
    value--;
    return result;
  }

  /**
   * Decrements the current value and returns it.
   *
   * @return the decremented value.
   */
  public long decrementThenGet() {
    value--;
    return value;
  }

  /**
   * Returns the current value then adds the given amount.
   *
   * @param amount the amount to add
   * @return the value before adding the amount
   */
  public long getThenAdd(final long amount) {
    long result = value;
    value += amount;
    return result;
  }

  /**
   * Adds the given amount to the current value and returns it.
   *
   * @param amount the amount to add
   * @return the value after adding the given amount
   */
  public long addThenGet(final long amount) {
    value += amount;
    return value;
  }


}
