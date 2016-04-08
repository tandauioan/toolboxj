package org.ticdev.toolboxj.io;

import java.io.IOException;
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
        
        try (EOFInputStream is = EOFInputStream.getInstance()) {
            Assert.assertEquals(0, is.available());
            Assert.assertEquals(-1, is.read());
            is.close();
            Assert.assertEquals(0, is.available());
            Assert.assertEquals(-1, is.read());
        } catch(IOException ex) {
            Assert.fail(ex.toString());
        }
        
    }

}
