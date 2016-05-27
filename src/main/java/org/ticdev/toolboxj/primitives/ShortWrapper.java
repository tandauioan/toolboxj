package org.ticdev.toolboxj.primitives;

/**
 * Mutable short primitive holder.
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
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ShortWrapper
    extends
    Number
    implements
    PrimitiveSetter<ShortWrapper>,
    PrimitiveGetter<ShortWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the short primitive value
     */
    private short value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public ShortWrapper(
        short initialValue) {
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
    public ShortWrapper booleanValue(boolean value) {
        this.value = (short) (value ? 1 : 0);
        return this;
    }

    @Override
    public ShortWrapper byteValue(byte value) {
        this.value = value;
        return this;
    }

    @Override
    public ShortWrapper shortValue(short value) {
        this.value = value;
        return this;
    }

    @Override
    public ShortWrapper intValue(int value) {
        this.value = (short) value;
        return this;
    }

    @Override
    public ShortWrapper longValue(long value) {
        this.value = (short) value;
        return this;
    }

    @Override
    public ShortWrapper floatValue(float value) {
        this.value = (short) value;
        return this;
    }

    @Override
    public ShortWrapper doubleValue(double value) {
        this.value = (short) value;
        return this;
    }

    @Override
    public ShortWrapper charValue(char value) {
        this.value = (short) value;
        return this;
    }

    @Override
    public ShortWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.shortValue();
        return this;
    }

    /**
     * Creates a new instance of the ShortHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static ShortWrapper of(short value) {
        return new ShortWrapper(value);
    }

    /**
     * Creates a new instance of ShortHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static ShortWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.shortValue());
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
                && value == ((PrimitiveGetter<?>) obj).shortValue());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

}
