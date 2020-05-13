package org.ticdev.toolboxj.primitives;

/**
 * Mutable float primitive holder.
 * <p>
 * Java primitive conversions are used for all numeric types.
 * </p>
 * <p>
 * {@link #hashCode()} and {@link #equals(Object)} adjust to the current value
 * of the holder.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class FloatWrapper
    extends Number
    implements
    PrimitiveSetter<FloatWrapper>,
    PrimitiveGetter<FloatWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The float primitive value.
   */
  private float value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public FloatWrapper(
      final float initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default
   * value set by Java.
   */
  public FloatWrapper() {
    /*
     * defaults to the JAVA initial value for a float primitive (0,
     * currently)
     */
  }

  @Override
  public FloatWrapper self() {
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
    return (long) value;
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
  public FloatWrapper booleanValue(final boolean booleanValue) {
    this.value = (booleanValue ? 1 : 0);
    return this;
  }

  @Override
  public FloatWrapper byteValue(final byte byteValue) {
    this.value = byteValue;
    return this;
  }

  @Override
  public FloatWrapper shortValue(final short shortValue) {
    this.value = shortValue;
    return this;
  }

  @Override
  public FloatWrapper intValue(final int intValue) {
    this.value = intValue;
    return this;
  }

  @Override
  public FloatWrapper longValue(final long longValue) {
    this.value = longValue;
    return this;
  }

  @Override
  public FloatWrapper floatValue(final float floatValue) {
    this.value = floatValue;
    return this;
  }

  @Override
  public FloatWrapper doubleValue(final double doubleValue) {
    this.value = (float) doubleValue;
    return this;
  }

  @Override
  public FloatWrapper charValue(final char charValue) {
    this.value = charValue;
    return this;
  }

  @Override
  public FloatWrapper copyFrom(
      final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.floatValue();
    return this;
  }

  /**
   * Creates a new instance of the FloatHolder from the given value.
   *
   * @param value the value
   * @return the new instance
   */
  public static FloatWrapper of(final float value) {
    return new FloatWrapper(value);
  }

  /**
   * Creates a new instance of FloatHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static FloatWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.floatValue());
  }

  @Override
  public int hashCode() {
    return (int) value;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || (obj instanceof PrimitiveGetter<?>
        && Float
        .compare(value, ((PrimitiveGetter<?>) obj).floatValue()) == 0);
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
  public FloatWrapper increment() {
    value++;
    return this;
  }

  /**
   * Decrements the value by one.
   *
   * @return this instance
   */
  public FloatWrapper decrement() {
    value--;
    return this;
  }

  /**
   * Adds the given amount to this value.
   *
   * @param amount the amount
   * @return this instance
   */
  public FloatWrapper add(final float amount) {
    value += amount;
    return this;
  }

  /**
   * Returns the current value then increments it.
   *
   * @return the value before incrementing it.
   */
  public float getThenIncrement() {
    float result = value;
    value++;
    return result;
  }

  /**
   * Increments the current value and returns it.
   *
   * @return the incremented value
   */
  public float incrementThenGet() {
    value++;
    return value;
  }

  /**
   * Returns the current value then decrements it.
   *
   * @return the value before decrementing it.
   */
  public float getThenDecrement() {
    float result = value;
    value--;
    return result;
  }

  /**
   * Decrements the current value and returns it.
   *
   * @return the decremented value.
   */
  public float decrementThenGet() {
    value--;
    return value;
  }

  /**
   * Returns the current value then adds the given amount.
   *
   * @param amount the amount to add
   * @return the value before adding the amount
   */
  public float getThenAdd(final float amount) {
    float result = value;
    value += amount;
    return result;
  }

  /**
   * Adds the given amount to the current value and returns it.
   *
   * @param amount the amount to add
   * @return the value after adding the given amount
   */
  public float addThenGet(final float amount) {
    value += amount;
    return value;
  }

}
