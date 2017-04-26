package org.ticdev.toolboxj.io.csv.impl;

import java.util.List;

/**
 * A strategy/predicate that decides whether or not the given character is a
 * delimiter.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface DelimiterPredicate {

    /**
     * Returns true if the character is a delimiter and false otherwise.
     *
     * @param c the character
     * @return true if the character is a delimiter and false otherwise.
     */
    boolean isDelimiter(char c);

    /**
     * Returns the first delimiter as a string, so it can accommodate empty
     * strings when a delimiter is not defined.
     *
     * @return the first delimiter as a string
     */
    String getFirst();

    /**
     * Default delimiter predicate when no delimiters are defined.
     */
    DelimiterPredicate NO_DELIMITERS =
            new DelimiterPredicate() {
                @Override
                public boolean isDelimiter(char c) {
                    return false;
                }

                @Override
                public String getFirst() {
                    return "";
                }
            };

    /**
     * Returns default, appropriate delimiter predicate based on the
     * provided delimiters list.
     *
     * @param delimiters the list of delimiters
     * @return default predicate appropriate for the given list
     */
    static DelimiterPredicate of(List<Character> delimiters) {

        if (delimiters == null) {
            return NO_DELIMITERS;
        }

        int sz = delimiters.size();

        if (sz == 0) {
            return NO_DELIMITERS;
        } else if (sz == 1) {
            return new DelimiterPredicate() {

                final char delimiter = delimiters.get(0);

                final String sdelimiter = "" + delimiter;

                @Override
                public boolean isDelimiter(char c) {
                    return c == delimiter;
                }

                @Override
                public String getFirst() {
                    return sdelimiter;
                }
            };
        } else {
            Character[] charObjects =
                    delimiters.toArray(new Character[sz]);
            final char[] chars = new char[charObjects.length];
            for (int i = 0; i < charObjects.length; i++) {
                chars[i] = charObjects[i];
            }
            return new DelimiterPredicate() {

                final char[] delimiters = chars;

                final String sdelimiter = delimiters[0] + "";

                @Override
                public boolean isDelimiter(char c) {
                    for (char delimiter : delimiters) {
                        if (c == delimiter) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public String getFirst() {
                    return sdelimiter;
                }
            };
        }

    }

}
