package org.ticdev.toolboxj.primitives;

/**
 * Mutable char primitive holder.
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
public final class CharWrapper
    extends Number
    implements
    PrimitiveSetter<CharWrapper>,
    PrimitiveGetter<CharWrapper> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The char primitive value.
   */
  private char value;

  /**
   * Class constructor. Initializes the value to the initial value.
   *
   * @param initialValue the initial value of the primitive.
   */
  public CharWrapper(
      final char initialValue) {
    this.value = initialValue;
  }

  /**
   * Default class constructor. Leaves the value to the default value set by
   * Java
   */
  public CharWrapper() {
    /*
     * defaults to the JAVA initial value for a char primitive (0,
     * currently)
     */
  }

  @Override
  public CharWrapper self() {
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
    return value;
  }

  @Override
  public CharWrapper booleanValue(final boolean booleanValue) {
    this.value = (char) (booleanValue ? 1 : 0);
    return this;
  }

  @Override
  public CharWrapper byteValue(final byte byteValue) {
    this.value = (char) byteValue;
    return this;
  }

  @Override
  public CharWrapper shortValue(final short shortValue) {
    this.value = (char) shortValue;
    return this;
  }

  @Override
  public CharWrapper intValue(final int intValue) {
    this.value = (char) intValue;
    return this;
  }

  @Override
  public CharWrapper longValue(final long longValue) {
    this.value = (char) longValue;
    return this;
  }

  @Override
  public CharWrapper floatValue(final float floatValue) {
    this.value = (char) floatValue;
    return this;
  }

  @Override
  public CharWrapper doubleValue(final double doubleValue) {
    this.value = (char) doubleValue;
    return this;
  }

  @Override
  public CharWrapper charValue(final char charValue) {
    this.value = charValue;
    return this;
  }

  @Override
  public CharWrapper copyFrom(final PrimitiveGetter<?> primitiveGetter) {
    this.value = primitiveGetter.charValue();
    return this;
  }

  /**
   * Creates a new instance of the CharHolder from the given value.
   *
   * @param value the value
   * @return the new instance
   */
  public static CharWrapper of(final char value) {
    return new CharWrapper(value);
  }

  /**
   * Creates a new instance of CharHolder from the given PrimitiveGetter.
   *
   * @param primitiveGetter the primitive getter
   * @return the new instance
   */
  public static CharWrapper
  of(final PrimitiveGetter<?> primitiveGetter) {
    return of(primitiveGetter.charValue());
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
          && value == ((PrimitiveGetter<?>) obj).charValue());
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public String toString() {
    return value + "";
  }

}
