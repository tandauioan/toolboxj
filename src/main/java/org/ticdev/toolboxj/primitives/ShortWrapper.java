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
     * @param initialValue the initial value of the primitive.
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
     * @param value the value
     * @return the new instance
     */
    public static ShortWrapper of(short value) {
        return new ShortWrapper(value);
    }

    /**
     * Creates a new instance of ShortHolder from the given PrimitiveGetter.
     *
     * @param primitiveGetter the primitive getter
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
    public ShortWrapper add(short amount) {
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
     * Increments the current value and returns it
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
    public short getThenAdd(short amount) {
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
    public short addThenGet(short amount) {
        value += amount;
        return value;
    }


}
