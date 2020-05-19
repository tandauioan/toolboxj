package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * This is a {@link Reader} implementation that's backed by a
 * {@link CharSequence}.
 *
 * <p>
 * Closing the reader makes the position equals to its limit so further reading
 * is not possible. The {@link #close()} method makes sure future operations
 * return EOF, but no exceptions will be thrown.
 * </p>
 *
 * <p>
 * Mark related operations are not supported. But this may change in the future.
 * </p>
 *
 * <p>
 * The constructor will throw a {@link NullPointerException} if the backing char
 * sequence is null. If the {@link #newInstance(CharSequence)} method is used,
 * if the backing char sequence is null, then an {@link EOFReader} will be
 * returned.
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class CharSequenceReader extends Reader {

  /**
   * The char sequence backing this reader.
   */
  private final CharSequence charSequence;

  /**
   * The current position index.
   */
  private int position = 0;

  /**
   * Class constructor.
   *
   * @param charSequenceRef the {@link CharSequence} backing this
   *                        {@link Reader}. The character sequence
   *                        must be non-null.
   * @throws NullPointerException if the character sequence is null
   */
  public CharSequenceReader(
      final CharSequence charSequenceRef
  ) throws NullPointerException {
    if (charSequenceRef == null) {
      throw new NullPointerException(
          "Backing CharSequence cannot be null.");
    }
    this.charSequence = charSequenceRef;
  }

  /**
   * Returns true if there are remaining characters in the sequence, and false
   * otherwise.
   *
   * @return true if there are remaining characters in the sequence and false
   *     otherwise.
   */
  private boolean hasRemaining() {
    return charSequence.length() - position > 0;
  }

  /**
   * Returns the number of remaining characters in the sequence.
   *
   * @return the number of remaining characters in the sequence.
   */
  private int remaining() {
    return charSequence.length() - position;
  }

  @Override
  public int read()
      throws IOException {
    return hasRemaining()
        ? charSequence.charAt(position++)
        : -1;
  }

  @Override
  public int read(
      final char[] cbuf, final int off, final int len
  ) throws IOException {
    if (off < 0 || len < 0 || cbuf.length - len < 0
        || cbuf.length - len < off) {
      throw new IndexOutOfBoundsException();
    }
    if (len == 0) {
      return 0;
    }
    int length = len;
    int offset = off;
    if (hasRemaining()) {
      int remaining = remaining();
      if (remaining < length) {
        length = remaining;
      }
      int count = length;
      while (count-- > 0) {
        cbuf[offset++] = charSequence.charAt(position++);
      }
      return length;
    }

    return -1;
  }

  @Override
  public int read(final char[] cbuf) throws IOException {
    return read(cbuf, 0, cbuf.length);
  }

  @Override
  public boolean ready() throws IOException {
    return true;
  }

  @Override
  public int read(final CharBuffer target) throws IOException {
    if (hasRemaining()) {
      int len = 0;
      if (hasRemaining() && target.hasRemaining()) {
        target.put(charSequence.charAt(position++));
      }
      return len;
    }
    return -1;
  }

  @Override
  public void close() throws IOException {
    position = charSequence.length();
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public long skip(final long n) throws IOException {
    if (n <= 0 || !hasRemaining()) {
      return 0;
    }
    int remaining = remaining();
    /*
     * if n larger than int representation then skip all and return what was
     * remaining
     */
    if (n > Integer.MAX_VALUE) {
      position = charSequence.length();
      return remaining;
    }
    if (n < remaining) {
      remaining = (int) n;
    }
    position += remaining;
    return remaining;
  }

  /**
   * Returns an instance of a reader backed by the given char sequence if it's
   * not null, and reached EOF if null or empty.
   *
   * @param charSequence the char sequence
   * @return an instance of CharSequenceReader if the charSequence is not null
   *     and not empty and a {@link Reader} that reached EOF otherwise.
   */
  public static Reader newInstance(final CharSequence charSequence) {
    if (charSequence == null || charSequence.length() == 0) {
      return EOFReader.getInstance();
    }
    return new CharSequenceReader(charSequence);
  }

}
