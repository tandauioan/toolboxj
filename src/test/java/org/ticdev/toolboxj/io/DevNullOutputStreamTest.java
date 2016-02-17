package org.ticdev.toolboxj.io;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link DevNullOutputStream}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class DevNullOutputStreamTest {

    /**
     * Byte array that supplies the test bytes and the test array itself
     */
    private final static byte[] byteArrayTest =
        { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    /**
     * Test correct write behavior before and after calling close, which should
     * have no effect on the stream.
     */
    @Test
    public void testWriteAndWriteAfterClose() {
        OutputStream os = DevNullOutputStream.getInstance();
        try {
            os.write(byteArrayTest[0]);
        } catch(Exception ex) {
            Assert.fail(ex.toString());
        } finally {
            try {
                os.close();
            } catch(Exception ex) {
                Assert.fail(ex.toString());
            }
        }
        try {
            os.write(byteArrayTest[1]);
        } catch(Exception ex) {
            Assert.fail(ex.toString());
        }
    }
    
    /**
     * Test all write methods 
     */
    @Test
    public void testAllWriteMethods() {
        try (OutputStream os = DevNullOutputStream.getInstance()) {
            os.write(byteArrayTest[0]);
            os.write(byteArrayTest, 0, byteArrayTest.length);
            os.write(byteArrayTest);
        } catch(Exception ex) {
            Assert.fail(ex.toString());
        }
    }
    
    /**
     * Force {@link IndexOutOfBoundsException} for invalid offset, length and sum.
     */
    @Test
    public void forceIndexOutOfBoundsExceptions() {
        
        /* negative offset */
        try (OutputStream os = DevNullOutputStream.getInstance()) {
            os.write(byteArrayTest, -1, 2);
            Assert.fail();
        } catch(IndexOutOfBoundsException ex) {
            Assert.assertTrue(ex != null);
        } catch(IOException ex) {
            Assert.fail(ex.toString());
        }
        
        /* negative length */
        try (OutputStream os = DevNullOutputStream.getInstance()) {
            os.write(byteArrayTest, 0, -1);
            Assert.fail();
        } catch(IndexOutOfBoundsException ex) {
            Assert.assertTrue(ex != null);
        } catch(IOException ex) {
            Assert.fail(ex.toString());
        }
        
        /* invalid offset and length combination */
        try (OutputStream os = DevNullOutputStream.getInstance()) {
            os.write(byteArrayTest, byteArrayTest.length , 1);
            Assert.fail();
        } catch(IndexOutOfBoundsException ex) {
            Assert.assertTrue(ex != null);
        } catch(IOException ex) {
            Assert.fail(ex.toString());
        }
        
        try (OutputStream os = DevNullOutputStream.getInstance()) {
            os.write(byteArrayTest, 1 , byteArrayTest.length);
            Assert.fail();
        } catch(IndexOutOfBoundsException ex) {
            Assert.assertTrue(ex != null);
        } catch(IOException ex) {
            Assert.fail(ex.toString());
        }
    }

}
