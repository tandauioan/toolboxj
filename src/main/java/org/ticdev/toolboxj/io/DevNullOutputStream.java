package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a concrete extension of an output stream whose write methods do
 * nothing. This class is implemented as a singleton. The
 * {@link OutputStream#close()} method has no effect on the single instance of
 * this class.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class DevNullOutputStream extends OutputStream {

  /**
   * Singleton instance.
   */
  private static final DevNullOutputStream INSTANCE =
      new DevNullOutputStream();

  /**
   * Default singleton constructor.
   */
  private DevNullOutputStream() {
  }

  /**
   * Returns the singleton instance.
   *
   * @return the singleton instance.
   */
  public static DevNullOutputStream getInstance() {
    return INSTANCE;
  }

  @Override
  public void write(final int b)
      throws IOException {
    /* do nothing */
  }

  @Override
  public void write(final byte[] b, final int off, final int len)
      throws IOException {
    if (off < 0 || len < 0 || b.length - len < 0 || b.length - len < off) {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public void write(final byte[] b)
      throws IOException {
    /* do nothing */
  }

}
