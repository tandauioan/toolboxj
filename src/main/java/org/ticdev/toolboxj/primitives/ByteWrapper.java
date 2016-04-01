package org.ticdev.toolboxj.primitives;

/**
 * Mutable byte primitive holder.
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
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class ByteWrapper
    extends
    Number
    implements
    PrimitiveSetter<ByteWrapper>,
    PrimitiveGetter<ByteWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the byte primitive value
     */
    private byte value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public ByteWrapper(
        byte initialValue) {
        this.value = initialValue;
    }

    /**
     * Default class constructor. Leaves the value to the default value set by
     * Java
     */
    public ByteWrapper() {
        /*
         * defaults to the JAVA initial value for a byte primitive (0,
         * currently)
         */
    }

    @Override
    public ByteWrapper self() {
        return this;
    }

    @Override
    public boolean booleanValue() {
        return value != 0;
    }

    @Override
    public byte byteValue() {
        return value;
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
    public ByteWrapper booleanValue(boolean value) {
        this.value = (byte) (value ? 1 : 0);
        return this;
    }

    @Override
    public ByteWrapper byteValue(byte value) {
        this.value = value;
        return this;
    }

    @Override
    public ByteWrapper shortValue(short value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper intValue(int value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper longValue(long value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper floatValue(float value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper doubleValue(double value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper charValue(char value) {
        this.value = (byte) value;
        return this;
    }

    @Override
    public ByteWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.byteValue();
        return this;
    }

    /**
     * Creates a new instance of the ByteHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static final ByteWrapper of(byte value) {
        return new ByteWrapper(value);
    }

    /**
     * Creates a new instance of ByteHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static final ByteWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.byteValue());
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
                && value == ((PrimitiveGetter<?>) obj).byteValue());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

}
