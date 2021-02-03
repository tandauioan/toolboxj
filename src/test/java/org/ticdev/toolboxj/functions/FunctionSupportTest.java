package org.ticdev.toolboxj.functions;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.ticdev.toolboxj.functions.FunctionSupport.*;

/**
 * Test class for {@link FunctionSupport}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class FunctionSupportTest {

  @Test
  public void testRemapOnThrowable() {
    final String expectedSuccess = "expected return";
    final String expectedFailure = "expected failure";
    final Callable<String> success = () -> expectedSuccess;
    final Callable<String> failure = () -> {
      throw new Exception();
    };
    final String actualSuccess = remapOnThrowable(success, t -> expectedFailure);
    Assert.assertEquals(expectedSuccess, actualSuccess);
    final String actualFailure = remapOnThrowable(failure, t -> expectedFailure);
    Assert.assertEquals(expectedFailure, actualFailure);
  }

  @Test
  public void testToRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final RuntimeException rteCheckedException = toRte(checkedException);
    Assert.assertNotSame(checkedException, rteCheckedException);
    Assert.assertSame(checkedException, rteCheckedException.getCause());
    final RuntimeException rteRuntimeException = toRte(runtimeException);
    Assert.assertSame(runtimeException, rteRuntimeException);
  }

  @Test
  public void testToNewRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final RuntimeException rteCheckedException = toNewRte(checkedException);
    Assert.assertNotSame(checkedException, rteCheckedException);
    Assert.assertSame(checkedException, rteCheckedException.getCause());
    final RuntimeException rteRuntimeException = toNewRte(runtimeException);
    Assert.assertNotSame(runtimeException, rteRuntimeException);
    Assert.assertSame(runtimeException, rteRuntimeException.getCause());
  }

  @Test
  public void testAlwaysRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      Assert.fail(alwaysRte(checkedException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      Assert.fail(alwaysRte(runtimeException));
    } catch (Throwable throwable) {
      Assert.assertSame(runtimeException, throwable);
    }
  }

  @Test
  public void testAlwaysNewRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      Assert.fail(alwaysNewRte(checkedException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      Assert.fail(alwaysNewRte(runtimeException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(runtimeException, throwable);
      Assert.assertSame(runtimeException, throwable.getCause());
    }
  }

  @Test
  public void testDoAlwaysRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      doAlwaysRte(checkedException);
      Assert.fail();
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      doAlwaysRte(runtimeException);
      Assert.fail();
    } catch (Throwable throwable) {
      Assert.assertSame(runtimeException, throwable);
    }
  }

  @Test
  public void testDoAlwaysNewRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      doAlwaysNewRte(checkedException);
      Assert.fail();
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      doAlwaysNewRte(runtimeException);
      Assert.fail();
    } catch (Throwable throwable) {
      Assert.assertNotSame(runtimeException, throwable);
      Assert.assertSame(runtimeException, throwable.getCause());
    }
  }

  @Test
  public void testWithRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final String message = "message";
    final Callable<String> callableCheckedException = () -> {
      throw checkedException;
    };
    final Callable<String> callableRuntimeException = () -> {
      throw runtimeException;
    };
    final Callable<String> callableWithMessage = () -> message;
    try {
      Assert.fail(withRte(callableCheckedException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      Assert.fail(withRte(callableRuntimeException));
    } catch (Throwable throwable) {
      Assert.assertSame(runtimeException, throwable);
    }
    try {
      Assert.assertSame(message, withRte(callableWithMessage));
    } catch (Throwable throwable) {
      Assert.fail(throwable.getMessage());
    }
  }

  @Test
  public void testWithNewRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final String message = "message";
    final Callable<String> callableCheckedException = () -> {
      throw checkedException;
    };
    final Callable<String> callableRuntimeException = () -> {
      throw runtimeException;
    };
    final Callable<String> callableWithMessage = () -> message;
    try {
      Assert.fail(withNewRte(callableCheckedException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(checkedException, throwable);
      Assert.assertSame(checkedException, throwable.getCause());
    }
    try {
      Assert.fail(withNewRte(callableRuntimeException));
    } catch (Throwable throwable) {
      Assert.assertNotSame(runtimeException, throwable);
      Assert.assertSame(runtimeException, throwable.getCause());
    }
    try {
      Assert.assertSame(message, withNewRte(callableWithMessage));
    } catch (Throwable throwable) {
      Assert.fail(throwable.getMessage());
    }
  }

  @Test
  public void testToRteSupplier() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final Supplier<String> checkedExceptionSupplier = toRteSupplier(() -> {
      throw checkedException;
    });
    final Supplier<String> runtimeExceptionSupplier = toRteSupplier(() -> {
      throw runtimeException;
    });
    final String successExpected = "success";
    final Supplier<String> successSupplier = toRteSupplier(() -> successExpected);
    try {
      Assert.fail(checkedExceptionSupplier.get());
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      Assert.fail(runtimeExceptionSupplier.get());
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex);
    }
    Assert.assertSame(successExpected, successSupplier.get());
  }

  @Test
  public void testToNewRteSupplier() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    final Supplier<String> checkedExceptionSupplier = toNewRteSupplier(() -> {
      throw checkedException;
    });
    final Supplier<String> runtimeExceptionSupplier = toNewRteSupplier(() -> {
      throw runtimeException;
    });
    final String successExpected = "success";
    final Supplier<String> successSupplier = toNewRteSupplier(() -> successExpected);
    try {
      Assert.fail(checkedExceptionSupplier.get());
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      Assert.fail(runtimeExceptionSupplier.get());
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex.getCause());
    }
    Assert.assertSame(successExpected, successSupplier.get());
  }

  @Test
  public void testDoWithRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      doWithRte(() -> {
        throw checkedException;
      });
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      doWithRte(() -> {
        throw runtimeException;
      });
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex);
    }
    try {
      doWithRte(() -> 1);
    } catch (RuntimeException ex) {
      Assert.fail(ex.toString());
    }
  }

  @Test
  public void testDoWithNewRte() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      doWithNewRte(() -> {
        throw checkedException;
      });
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      doWithNewRte(() -> {
        throw runtimeException;
      });
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex.getCause());
    }
    try {
      doWithNewRte(() -> 1);
    } catch (RuntimeException ex) {
      Assert.fail(ex.toString());
    }
  }

  @Test
  public void testToRteRunnable() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      toRteRunnable(() -> {
        throw checkedException;
      }).run();
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      toRteRunnable(() -> {
        throw runtimeException;
      }).run();
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex);
    }
    try {
      toRteRunnable(() -> 1).run();
    } catch (RuntimeException ex) {
      Assert.fail(ex.toString());
    }
  }

  @Test
  public void testTonewRteRunnable() {
    final Exception checkedException = new Exception();
    final RuntimeException runtimeException = new RuntimeException();
    try {
      toNewRteRunnable(() -> {
        throw checkedException;
      }).run();
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(checkedException, ex.getCause());
    }
    try {
      toNewRteRunnable(() -> {
        throw runtimeException;
      }).run();
      Assert.fail();
    } catch (RuntimeException ex) {
      Assert.assertSame(runtimeException, ex.getCause());
    }
    try {
      toNewRteRunnable(() -> 1).run();
    } catch (RuntimeException ex) {
      Assert.fail(ex.toString());
    }
  }

}