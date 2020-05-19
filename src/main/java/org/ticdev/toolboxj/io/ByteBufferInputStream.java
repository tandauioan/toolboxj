package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * An {@link InputStream} implementation that's backed by a ByteBuffer.
 *
 * <p>
 * Closing the input stream makes the underlying {@link ByteBuffer}'s position
 * equal to the limit so further reading is not possible. The {@link #close()}
 * method makes sure that future operations return EOF, but no exceptions will
 * be thrown.
 * </p>
 *
 * <p>
 * Mark related operations are not currently supported, but this may change in
 * the future.
 * </p>
 *
 * <p>
 * The constructor will throw a {@link NullPointerException}
 * if the backing byte buffer is null.
 * If the {@link #newInstance(ByteBuffer)} method is used, if
 * the backing byte buffer is null or empty,
 * then an {@link InputStream} will be
 * returned that always returns EOF (see {@link EOFInputStream})
 * </p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class ByteBufferInputStream extends InputStream {

  /**
   * Mask to extract a positive int out of the byte value.
   */
  private static final int POSITIVE_MASK = 0xff;

  /**
   * The byte buffer backing this input stream.
   */
  private final ByteBuffer byteBuffer;

  /**
   * Class constructor.
   *
   * @param byteBufferRef the {@link ByteBuffer} backing
   *                      this {@link InputStream}. The buffer must
   *                      be non-null.
   * @throws NullPointerException if the buffer is null
   */
  public ByteBufferInputStream(final ByteBuffer byteBufferRef)
      throws NullPointerException {
    if (byteBufferRef == null) {
      throw new NullPointerException(
          "Backing ByteBuffer cannot be null.");
    }
    this.byteBuffer = byteBufferRef;
  }

  @Override
  public int read()
      throws IOException {
    return byteBuffer.hasRemaining()
        ? POSITIVE_MASK & byteBuffer.get()
        : -1;
  }

  @Override
  public int read(
      final byte[] b,
      final int off,
      final int len
  ) throws IOException {
    if (off < 0 || len < 0 || b.length - len < 0
        || b.length - len < off) {
      throw new IndexOutOfBoundsException();
    }
    if (len == 0) {
      return 0;
    }
    int length = len;
    if (byteBuffer.hasRemaining()) {
      int remaining = byteBuffer.remaining();
      if (remaining < length) {
        length = remaining;
      }
      byteBuffer.get(b, off, length);
      return length;
    }
    return -1;
  }

  @Override
  public int read(final byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  @Override
  public long skip(final long n)
      throws IOException {
    if (n <= 0 || !byteBuffer.hasRemaining()) {
      return 0;
    }

    int remaining = byteBuffer.remaining();

    if (n > Integer.MAX_VALUE) {
      byteBuffer.position(byteBuffer.limit());
      return remaining;
    } else {
      remaining = (int) n;
    }
    byteBuffer.position(byteBuffer.position() + remaining);
    return remaining;

  }

  @Override
  public int available()
      throws IOException {
    return byteBuffer.remaining();
  }

  @Override
  public void close()
      throws IOException {
    byteBuffer.position(byteBuffer.limit());
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  /**
   * Returns an instance of an input stream backed by the given buffer if it's
   * not null and has available elements, otherwise it returns an input stream
   * that reached EOF.
   *
   * @param byteBuffer the byte buffer
   * @return an instance of {@link ByteBufferInputStream} if the byteBuffer is
   *     non-null and not empty, and a {@link InputStream} that reached
   *     EOF, otherwise.
   */
  public static InputStream newInstance(final ByteBuffer byteBuffer) {
    if (byteBuffer == null || !byteBuffer.hasRemaining()) {
      return EOFInputStream.getInstance();
    }
    return new ByteBufferInputStream(byteBuffer);
  }

}
