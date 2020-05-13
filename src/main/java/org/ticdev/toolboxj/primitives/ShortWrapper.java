package org.ticdev.toolboxj.primitives;

/**
 * Mutable short primitive holder.
 * <p>
 * Java primitive conversions are used for all numeric types. Boolean values are
 * false if 0 and true if non-zero. Boolean getter will return 0 for false and 1
 * for true.
 * </p>
 * <p>
 * {@link #hashCode()} and {@link #equals(Object)} adjust to the current value
 * of the holder.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class ShortWrapper
    extends Number
    implements
    PrimitiveSetter<ShortWrapper>,
    PrimitiveGetter<ShortWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The short primitive value.
   */
  private short value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public ShortWrapper(
      final short initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default value set by
   * Java
   */
  public ShortWrapper() {
    /*
     * defaults to the JAVA initial value for a short primitive (0,
     * currently)
     */
  }

  @Override
  public ShortWrapper self() {
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
    return value;
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
  public ShortWrapper booleanValue(final boolean booleanValue) {
    this.value = (short) (booleanValue ? 1 : 0);
    return this;
  }

  @Override
  public ShortWrapper byteValue(final byte byteValue) {
    this.value = byteValue;
    return this;
  }

  @Override
  public ShortWrapper shortValue(final short shortValue) {
    this.value = shortValue;
    return this;
  }

  @Override
  public ShortWrapper intValue(final int intValue) {
    this.value = (short) intValue;
    return this;
  }

  @Override
  public ShortWrapper longValue(final long longValue) {
    this.value = (short) longValue;
    return this;
  }

  @Override
  public ShortWrapper floatValue(final float floatValue) {
    this.value = (short) floatValue;
    return this;
  }

  @Override
  public ShortWrapper doubleValue(final double doubleValue) {
    this.value = (short) doubleValue;
    return this;
  }

  @Override
  public ShortWrapper charValue(final char charValue) {
    this.value = (short) charValue;
    return this;
  }

  @Override
  public ShortWrapper copyFrom(final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.shortValue();
    return this;
  }

  /**
   * Creates a new instance of the ShortHolder from the given value.
   *
   * @param shortValue the value
   * @return the new instance
   */
  public static ShortWrapper of(final short shortValue) {
    return new ShortWrapper(shortValue);
  }

  /**
   * Creates a new instance of ShortHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static ShortWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.shortValue());
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
          && value == ((PrimitiveGetter<?>) obj).shortValue());
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
  public ShortWrapper increment() {
    value++;
    return this;
  }

  /**
   * Decrements the value by one.
   *
   * @return this instance
   */
  public ShortWrapper decrement() {
    value--;
    return this;
  }

  /**
   * Adds the given amount to this value.
   *
   * @param amount the amount
   * @return this instance
   */
  public ShortWrapper add(final short amount) {
    value += amount;
    return this;
  }

  /**
   * Returns the current value then increments it.
   *
   * @return the value before incrementing it.
   */
  public short getThenIncrement() {
    short result = value;
    value++;
    return result;
  }

  /**
   * Increments the current value and returns it.
   *
   * @return the incremented value
   */
  public short incrementThenGet() {
    value++;
    return value;
  }

  /**
   * Returns the current value then decrements it.
   *
   * @return the value before decrementing it.
   */
  public short getThenDecrement() {
    short result = value;
    value--;
    return result;
  }

  /**
   * Decrements the current value and returns it.
   *
   * @return the decremented value.
   */
  public short decrementThenGet() {
    value--;
    return value;
  }

  /**
   * Returns the current value then adds the given amount.
   *
   * @param amount the amount to add
   * @return the value before adding the amount
   */
  public short getThenAdd(final short amount) {
    short result = value;
    value += amount;
    return result;
  }

  /**
   * Adds the given amount to the current value and returns it.
   *
   * @param amount the amount to add
   * @return the value after adding the given amount
   */
  public short addThenGet(final short amount) {
    value += amount;
    return value;
  }


}
