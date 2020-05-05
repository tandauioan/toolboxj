package org.ticdev.toolboxj.primitives;

import java.io.Serializable;

import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.self.SelfConditionalConsumer;
import org.ticdev.toolboxj.self.SelfConsumer;
import org.ticdev.toolboxj.self.SelfFilter;
import org.ticdev.toolboxj.self.SelfMapper;

/**
 * THis interface exposes setter methods and overloaded set method for all
 * primitive types.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the concrete implementation of the {@link PrimitiveSetter}
 */
public interface PrimitiveSetter<T extends PrimitiveSetter<T>>
    extends
    Self<T>,
    Serializable {

    /**
     * Sets the boolean value
     * 
     * @param value
     *            the boolean value
     * @return this instance
     */
    T booleanValue(boolean value);

    /**
     * Sets the byte value
     * 
     * @param value
     *            the byte value
     * @return this instance
     */
    T byteValue(byte value);

    /**
     * Sets the short value
     * 
     * @param value
     *            the short value
     * @return this instance
     */
    T shortValue(short value);

    /**
     * Sets the int value
     * 
     * @param value
     *            the int value
     * @return this instance
     */
    T intValue(int value);

    /**
     * Sets the long value
     * 
     * @param value
     *            the long value
     * @return this instance
     */
    T longValue(long value);

    /**
     * Sets the float value
     * 
     * @param value
     *            the float value
     * @return this instance
     */
    T floatValue(float value);

    /**
     * Sets the double value
     * 
     * @param value
     *            the double value
     * @return this instance
     */
    T doubleValue(double value);

    /**
     * Sets the char value
     * 
     * @param value
     *            the char value
     * @return this instance
     */
    T charValue(char value);

    /**
     * Copies the underlying primitive value from the given primitive getter
     * 
     * @param primitiveGetter
     *            the primitive getter
     * @return this instance
     */
    T copyFrom(PrimitiveGetter<?> primitiveGetter);

    /**
     * Overloaded method to set the boolean value
     * 
     * @param value
     *            the boolean value
     * @return this instance
     */
    default T set(boolean value) {
        return booleanValue(value);
    }

    /**
     * Overloaded method to set the byte value
     * 
     * @param value
     *            the byte value
     * @return this instance
     */
    default T set(byte value) {
        return byteValue(value);
    }

    /**
     * Overloaded method to set the short value
     * 
     * @param value
     *            the short value
     * @return this instance
     */
    default T set(short value) {
        return shortValue(value);
    }

    /**
     * Overloaded method to set the int value
     * 
     * @param value
     *            the int value
     * @return this instance
     */
    default T set(int value) {
        return intValue(value);
    }

    /**
     * Overloaded method to set the long value
     * 
     * @param value
     *            the long value
     * @return this instance
     */
    default T set(long value) {
        return longValue(value);
    }

    /**
     * Overloaded method to set the float value
     * 
     * @param value
     *            the float value
     * @return this instance
     */
    default T set(float value) {
        return floatValue(value);
    }

    /**
     * Overloaded method to set the double value
     * 
     * @param value
     *            the double value
     * @return this instance
     */
    default T set(double value) {
        return doubleValue(value);
    }

    /**
     * Overloaded method to set the char value
     * 
     * @param value
     *            the char value
     * @return this instance
     */
    default T set(char value) {
        return charValue(value);
    }

}
