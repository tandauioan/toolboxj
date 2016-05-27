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
 *
 */
public class CharWrapper
    extends
    Number
    implements
    PrimitiveSetter<CharWrapper>,
    PrimitiveGetter<CharWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the char primitive value
     */
    private char value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public CharWrapper(
        char initialValue) {
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
    public CharWrapper booleanValue(boolean value) {
        this.value = (char) (value ? 1 : 0);
        return this;
    }

    @Override
    public CharWrapper byteValue(byte value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper shortValue(short value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper intValue(int value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper longValue(long value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper floatValue(float value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper doubleValue(double value) {
        this.value = (char) value;
        return this;
    }

    @Override
    public CharWrapper charValue(char value) {
        this.value = value;
        return this;
    }

    @Override
    public CharWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.charValue();
        return this;
    }

    /**
     * Creates a new instance of the CharHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static  CharWrapper of(char value) {
        return new CharWrapper(value);
    }

    /**
     * Creates a new instance of CharHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static  CharWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.charValue());
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
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
