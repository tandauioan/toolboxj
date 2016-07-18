package org.ticdev.toolboxj.primitives;

/**
 * Mutable double primitive holder.
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
     * @param initialValue the initial value of the primitive.
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
     * @param value the value
     * @return the new instance
     */
    public static DoubleWrapper of(double value) {
        return new DoubleWrapper(value);
    }

    /**
     * Creates a new instance of DoubleHolder from the given PrimitiveGetter.
     *
     * @param primitiveGetter the primitive getter
     * @return the new instance
     */
    public static DoubleWrapper
    of(PrimitiveGetter<?> primitiveGetter) {
        return of(primitiveGetter.doubleValue());
    }

    @Override
    public int hashCode() {
        return (int) value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PrimitiveGetter<?>
                && Double.compare(value, ((PrimitiveGetter<?>) obj)
                .doubleValue()) == 0);
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
    public DoubleWrapper increment() {
        value++;
        return this;
    }

    /**
     * Decrements the value by one.
     *
     * @return this instance
     */
    public DoubleWrapper decrement() {
        value--;
        return this;
    }

    /**
     * Adds the given amount to this value.
     *
     * @param amount the amount
     * @return this instance
     */
    public DoubleWrapper add(double amount) {
        value += amount;
        return this;
    }

    /**
     * Returns the current value then increments it.
     *
     * @return the value before incrementing it.
     */
    public double getThenIncrement() {
        double result = value;
        value++;
        return result;
    }

    /**
     * Increments the current value and returns it
     *
     * @return the incremented value
     */
    public double incrementThenGet() {
        value++;
        return value;
    }

    /**
     * Returns the current value then decrements it.
     *
     * @return the value before decrementing it.
     */
    public double getThenDecrement() {
        double result = value;
        value--;
        return result;
    }

    /**
     * Decrements the current value and returns it.
     *
     * @return the decremented value.
     */
    public double decrementThenGet() {
        value--;
        return value;
    }

    /**
     * Returns the current value then adds the given amount.
     *
     * @param amount the amount to add
     * @return the value before adding the amount
     */
    public double getThenAdd(double amount) {
        double result = value;
        value += amount;
        return result;
    }

    /**
     * Adds the given amount to the current value and returns it.
     *
     * @param amount the amount to add
     * @return the value after adding the given amount
     */
    public double addThenGet(double amount) {
        value += amount;
        return value;
    }


}
