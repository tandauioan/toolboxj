package org.ticdev.toolboxj.io;

import org.junit.Assert;
import org.junit.Test;



/**
 * Test class for {@link EOFInputStream}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class EOFInputStreamTest {

    /**
     * Ensure operations work as expected and {@link EOFInputStream#close()} has
     * no effect on the stream.
     */
    @Test
    public void testOperations() {
        EOFInputStream is = null;
        try {
            is = EOFInputStream.getInstance();
            Assert.assertEquals(0, is.available());
            Assert.assertEquals(-1, is.read());
        } catch(Exception ex) {
            Assert.fail();
        } finally {
            try {
                is.close();
            } catch(Exception ex) {
                Assert.fail();
            }
        }
        Assert.assertNotNull(is);
        try {
            Assert.assertEquals(0, is.available());
            Assert.assertEquals(-1, is.read());
        } catch(Exception ex) {
            Assert.fail();
        }
    }

}
