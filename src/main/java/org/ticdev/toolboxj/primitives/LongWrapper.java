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
 *
 */
public class LongWrapper
    extends
    Number
    implements
    PrimitiveSetter<LongWrapper>,
    PrimitiveGetter<LongWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the long primitive value
     */
    private long value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public LongWrapper(
        long initialValue) {
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
    public LongWrapper booleanValue(boolean value) {
        this.value = (value ? 1 : 0);
        return this;
    }

    @Override
    public LongWrapper byteValue(byte value) {
        this.value = value;
        return this;
    }

    @Override
    public LongWrapper shortValue(short value) {
        this.value = value;
        return this;
    }

    @Override
    public LongWrapper intValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public LongWrapper longValue(long value) {
        this.value = value;
        return this;
    }

    @Override
    public LongWrapper floatValue(float value) {
        this.value = (long) value;
        return this;
    }

    @Override
    public LongWrapper doubleValue(double value) {
        this.value = (long) value;
        return this;
    }

    @Override
    public LongWrapper charValue(char value) {
        this.value = value;
        return this;
    }

    @Override
    public LongWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.longValue();
        return this;
    }

    /**
     * Creates a new instance of the LongHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static final LongWrapper of(long value) {
        return new LongWrapper(value);
    }

    /**
     * Creates a new instance of LongHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static final LongWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.longValue());
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
                && value == ((PrimitiveGetter<?>) obj).longValue());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

}
