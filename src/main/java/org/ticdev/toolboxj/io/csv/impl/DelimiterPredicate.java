package org.ticdev.toolboxj.io.csv.impl;

/**
 * A strategy/predicate that decides whether or not the given character is a
 * delimiter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public interface DelimiterPredicate {

    /**
     * Returns true if the character is a delimiter and false otherwise.
     * @param c the character
     * @return true if the character is a delimiter and false otherwise.
     */
    boolean isDelimiter(char c);

}
