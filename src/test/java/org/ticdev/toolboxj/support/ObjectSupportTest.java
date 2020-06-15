package org.ticdev.toolboxj.support;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.ticdev.toolboxj.support.ObjectSupport.allEqual;
import static org.ticdev.toolboxj.support.ObjectSupport.allEqualHashes;
import static org.ticdev.toolboxj.support.ObjectSupport.allNonNull;
import static org.ticdev.toolboxj.support.ObjectSupport.allNull;
import static org.ticdev.toolboxj.support.ObjectSupport.nonEqual;
import static org.ticdev.toolboxj.support.ObjectSupport.requireNonNull;

/**
 * Test class for {@link ObjectSupport}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ObjectSupportTest {

  private final int rangeMax = 100;

  /* nulls */
  private final Object[] nullObject = {null};
  private final Object[] nullObjects = {null, null};
  private final Object[] batchNullObjects = IntStream.range(1, rangeMax)
      .mapToObj(i -> null).toArray();

  /* non nulls */
  private final Object[] nonNullObject = {new Object()};
  private final Object[] nonNullObjects = {new Object(), new Object()};
  private final Object[] batchNonNullObjects = IntStream.range(1, rangeMax)
      .mapToObj(i -> new Object()).toArray();

  /* mixed */
  private final Object[] mixed1 = {null, new Object()};
  private final Object[] mixed2 = {new Object(), null};
  private final Object[] mixed3 = {null, new Object(), null};
  private final Object[] mixed4 = {new Object(), null, new Object()};
  private final Object[] batchMixed = IntStream.range(1, rangeMax)
      .mapToObj(i -> i % 2 == 0 ? null : new Object()).toArray();


  @Test
  public void testAllNull() {
    Assert.assertTrue(allNull());
    Assert.assertTrue(allNull(nullObject));
    Assert.assertTrue(allNull(nullObjects));
    Assert.assertTrue(allNull(batchNullObjects));
    Assert.assertFalse(allNull(nonNullObject));
    Assert.assertFalse(allNull(nonNullObjects));
    Assert.assertFalse(allNull(batchNonNullObjects));
    Assert.assertFalse(allNull(mixed1));
    Assert.assertFalse(allNull(mixed2));
    Assert.assertFalse(allNull(mixed3));
    Assert.assertFalse(allNull(mixed4));
    Assert.assertFalse(allNull(batchMixed));
  }

  @Test
  public void testAllNonNull() {
    Assert.assertTrue(allNonNull());
    Assert.assertFalse(allNonNull(nullObject));
    Assert.assertFalse(allNonNull(nullObjects));
    Assert.assertFalse(allNonNull(batchNullObjects));
    Assert.assertTrue(allNonNull(nonNullObject));
    Assert.assertTrue(allNonNull(nonNullObjects));
    Assert.assertTrue(allNonNull(batchNonNullObjects));
    Assert.assertFalse(allNonNull(mixed1));
    Assert.assertFalse(allNonNull(mixed2));
    Assert.assertFalse(allNonNull(mixed3));
    Assert.assertFalse(allNonNull(mixed4));
    Assert.assertFalse(allNonNull(batchMixed));
  }

  @Test
  public void testRequireNonNull() {
    for (Object[] oArr
        : Arrays.asList(
        nullObject,
        nullObjects,
        batchNullObjects,
        mixed1,
        mixed2,
        mixed3,
        mixed4,
        batchMixed)) {
      Assert.assertThrows(
          NullPointerException.class,
          () -> requireNonNull(oArr)
      );
    }

    for (Object[] oArr
        : Arrays.asList(
        nonNullObject, nonNullObjects, batchNonNullObjects
    )) {
      try {
        requireNonNull(oArr);
      } catch (NullPointerException ex) {
        Assert.fail();
      }
    }

  }

  @Test
  public void testAllEqualHashes() {
    final String s1 = "s1";
    final String s2 = new String(s1.toCharArray());
    final String s3 = "s2";
    Assert.assertTrue(allEqualHashes());
    Assert.assertTrue(allEqualHashes(s1));
    Assert.assertTrue(allEqualHashes(s1, s1));
    Assert.assertTrue(allEqualHashes(s1, s2));
    Assert.assertTrue(allEqualHashes(s1, s2, s1));
    Assert.assertFalse(allEqualHashes(s1, s3, s2));
    Assert.assertTrue(allEqualHashes(null, null));
  }

  @Test
  public void testAllEqual() {
    final String s1 = "s1";
    final String s2 = new String(s1.toCharArray());
    final String s3 = "s2";
    Assert.assertTrue(allEqual());
    Assert.assertTrue(allEqual(s1));
    Assert.assertTrue(allEqual(null, null));
    Assert.assertTrue(allEqual(s1, s2, s2));
    Assert.assertFalse(allEqual(s1, s2, s3));
    Assert.assertFalse(allEqual(s1, null, s2));
  }

  @Test
  public void testNonEqual() {
    final String s1 = "s1";
    final String s2 = new String(s1.toCharArray());
    final String s3 = "s2";
    Assert.assertTrue(nonEqual());
    Assert.assertTrue(nonEqual(s1));
    Assert.assertTrue(nonEqual(s1, s3));
    Assert.assertFalse(nonEqual(null, null));
    Assert.assertFalse(nonEqual(s1, s2, s2));
    Assert.assertFalse(nonEqual(s1, s2, s3));
    Assert.assertFalse(nonEqual(s1, null, s2));
    Assert.assertTrue(nonEqual(s1, null, s3));
  }

}