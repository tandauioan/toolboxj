package org.ticdev.toolboxj.primitives;

/**
 * Mutable double primitive holder.
 * 
 * <p>
 * Java primitive conversions are used for all numeric types.
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
public class DoubleWrapper
    extends
    Number
    implements
    PrimitiveSetter<DoubleWrapper>,
    PrimitiveGetter<DoubleWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the double primitive value
     */
    private double value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public DoubleWrapper(
        double initialValue) {
        this.value = initialValue;
    }

    /**
     * Default class constructor. Leaves the value to the default value set by
     * Java
     */
    public DoubleWrapper() {
        /*
         * defaults to the JAVA initial value for a double primitive (0,
         * currently)
         */
    }

    @Override
    public DoubleWrapper self() {
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
        return (float) value;
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
    public DoubleWrapper booleanValue(boolean value) {
        this.value = (value ? 1 : 0);
        return this;
    }

    @Override
    public DoubleWrapper byteValue(byte value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper shortValue(short value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper intValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper longValue(long value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper floatValue(float value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper doubleValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper charValue(char value) {
        this.value = value;
        return this;
    }

    @Override
    public DoubleWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.doubleValue();
        return this;
    }

    /**
     * Creates a new instance of the DoubleHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static final DoubleWrapper of(double value) {
        return new DoubleWrapper(value);
    }

    /**
     * Creates a new instance of DoubleHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static final DoubleWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.doubleValue());
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
                && value == ((PrimitiveGetter<?>) obj).doubleValue());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }
    
}
