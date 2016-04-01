package org.ticdev.toolboxj.primitives;

/**
 * Mutable boolean primitive holder.
 * 
 * <p>
 * Non boolean specific integer methods (byte, short, int, long) are accepted
 * with the rule that a value of zero resolved to false, and a value different
 * from zero resolves to true. Getter methods for integer types will return 0
 * for false and 1 for true. All the other methods will throw an unsupported
 * operation exception.
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
public class BooleanWrapper
    implements
    PrimitiveSetter<BooleanWrapper>,
    PrimitiveGetter<BooleanWrapper> {

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the boolean primitive value
     */
    private boolean value;

    /**
     * Class constructor. Initializes the value to the initial value.
     * 
     * @param initialValue
     *            the initial value of the primitive.
     */
    public BooleanWrapper(
        boolean initialValue) {
        this.value = initialValue;
    }

    /**
     * Default class constructor. Leaves the value to the default value assigned
     * by Java.
     */
    public BooleanWrapper() {
        /*
         * defaults to the JAVA initial value for a boolean primitive (false,
         * currently)
         */
    }

    @Override
    public BooleanWrapper self() {
        return this;
    }

    @Override
    public boolean booleanValue() {
        return value;
    }

    @Override
    public byte byteValue() {
        return (byte) intValue();
    }

    @Override
    public short shortValue() {
        return (short) intValue();
    }

    @Override
    public int intValue() {
        return value ? 1 : 0;
    }

    @Override
    public long longValue() {
        return intValue();
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    @Override
    public char charValue() {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    @Override
    public BooleanWrapper booleanValue(boolean value) {
        this.value = value;
        return this;
    }

    @Override
    public BooleanWrapper byteValue(byte value) {
        return intValue(value);
    }

    @Override
    public BooleanWrapper shortValue(short value) {
        return intValue(value);
    }

    @Override
    public BooleanWrapper intValue(int value) {
        this.value = (value == 0 ? false : true);
        return this;
    }

    @Override
    public BooleanWrapper longValue(long value) {
        return intValue((int) value);
    }

    @Override
    public BooleanWrapper floatValue(float value) {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    @Override
    public BooleanWrapper doubleValue(double value) {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    @Override
    public BooleanWrapper charValue(char value) {
        throw new UnsupportedOperationException(
            PrimitiveGetter.formatUnsupportedExceptionString.apply(this));
    }

    /**
     * Creates a new instance of BooleanHolder from the given value.
     * 
     * @param value
     *            the value
     * @return the new instance
     */
    public static final BooleanWrapper of(boolean value) {
        return new BooleanWrapper(value);
    }

    /**
     * Creates a new instance of BooleanHolder from the given PrimitiveGetter.
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return the new instance
     */
    public static final BooleanWrapper
        of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.booleanValue());
    }

    @Override
    public BooleanWrapper copyFrom(PrimitiveGetter<?> primitiveGetter) {
        this.value = primitiveGetter.booleanValue();
        return this;
    }

    @Override
    public int hashCode() {
        return intValue();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return (obj instanceof PrimitiveGetter<?>
                && value == ((PrimitiveGetter<?>) obj).booleanValue());
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value + "";
    }

}
