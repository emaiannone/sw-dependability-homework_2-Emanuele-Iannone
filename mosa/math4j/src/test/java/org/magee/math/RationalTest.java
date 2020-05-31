package org.magee.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.junit.runner.RunWith;
import org.magee.math.Rational;

public class RationalTest {

    /**
     * Test for {@link Rational#subtract(long)} and {@link Rational#negate()} )}
     * It computes: - (-1/-1 - 1) = - [(-1-1)/-1 ] = - (-2/-1) = -2
     * {@link Rational#subtract(long)} has a bug when converting the long into a Rational, as it uses -1 as a denominator instead of 1
     */
  @Test
  public void test01()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.subtract((-1L));
      Rational rational2 = rational1.negate();
      assertEquals(-2.0, rational2.doubleValue(), 0.01);  // FIXED
  }

    /**
     * Test for {@link Rational#abs()} and {@link Rational#multiply(Rational)}
     * It computes: |-1/-1| * (-1/-1) = 1/1 * (-1/-1) = -1/-1 = 1
     * The tested methods have various bugs:
     * 1. {@link Rational#abs()} does not correctly apply the absolute value to the denominator
     * 2. {@link Rational#multiply(Rational)} has a bug: it divides the numerators instead of multiplying them.
     */
  @Test
  public void test02()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.abs();
      Rational rational2 = rational1.multiply(rational0);
      assertEquals((-1L), rational2.numerator);
      assertEquals((byte) 1, rational1.byteValue());  //FIXED
  }

    /**
     * Test for {@link Rational#negate()} and {@link Rational#inverse()}
     * It computes: [- (-2685/-2685)]^-1 = (2685/-2685)^-1 = -2685/2685 = -1
     */
  @Test
  public void test03()  throws Throwable  {
      Rational rational0 = new Rational((-2685L), (-2685L));
      Rational rational1 = rational0.negate();
      Rational rational2 = rational1.inverse();
      assertEquals(2685L, rational2.denominator);
      assertEquals((-2685L), rational0.numerator);
      assertEquals((short) (-1), rational2.shortValue());
  }

    /**
     * Test for {@link Rational#multiply(long)}
     * It (tries to) computes: -1432/0 * 1, but it's illegal
     */
  @Test
  public void test04()  throws Throwable  {
      Rational rational0 = new Rational((-1432L), 24840256L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.multiply(1L);
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * Test for {@link Rational#divide(Rational)} and {@link Rational#floatValue()}
     * It computes: (667/1415) / (667/1415) = (667/1415) * (1415/667) = 943805/943805 = 1
     * {@link Rational#divide(Rational)} is fine, but it calls the buggy {@link Rational#multiply(Rational)} discovered in {@link RationalTest#test02()}
     */
  @Test
  public void test05()  throws Throwable  {
      Rational rational0 = new Rational(667L, 1415L);
      Rational rational1 = rational0.divide(rational0);
      float float0 = rational1.floatValue();
      assertEquals(1.0F, float0, 0.01F); // FIXED
      assertEquals(0.4713781F, rational0.floatValue(), 0.01F);
      assertEquals(943805L, rational1.denominator);
  }

    /**
     * Test for {@link Rational#divide(Rational)}
     * It computes: (-1/-1) / (-1/-1) = (-1/-1) * (-1/-1) = 1/1 = 1
     */
  @Test
  public void test06()  throws Throwable  {
      Rational rational0 = new Rational((-1L), (-1L));
      Rational rational1 = rational0.divide(rational0);
      assertEquals((-1L), rational0.denominator);
      assertEquals(1L, rational1.longValue());
  }

    /**
     * Test for {@link Rational#pow(int)}
     * It computes: (2/2)^0 = 2^0/2^0 = 1/1 = 1
     */
  @Test
  public void test07()  throws Throwable  {
      Rational rational0 = new Rational(2L, 2L);
      Rational rational1 = rational0.pow(0);
      assertEquals(1L, rational1.numerator);
      assertEquals((short)1, rational0.shortValue());
      assertEquals(1.0, rational1.doubleValue(), 0.01);
  }

    /**
     * Test for {@link Rational#add(long)}
     * It computes: 1439/1439 + 0 = 1
     * {@link Rational#add(long)} has a bug when converting the long into a Rational, as it uses 0 as a denominator, raising NumberFormatException in any case!
     */
  @Test
  public void test08()  throws Throwable  {
      Rational rational0 = new Rational(1493L, 1493L);
      Rational result = rational0.add(0L);
      assertEquals(1L, result.longValue());
  }

    /**
     * Test for {@link Rational#subtract(long)}
     * It (tries to) computes: 0/2503 = 0/0 - (-4002) = 1
     * Note that the bug of {@link Rational#subtract(long)} does not manifest itself due to the (correctly) raised NumberFormatException
     */
  @Test
  public void test09()  throws Throwable  {
      Rational rational0 = new Rational(0L, 2503L);
      rational0.denominator = 0L;
      // Undeclared exception!
      try { 
        rational0.subtract((-4002L));
        fail("Expecting exception: NumberFormatException");
      
      } catch(NumberFormatException e) {
         //
         // Cannot create a Rational object with zero as the denominator
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * Test for {@link Rational#add(Rational)}
     * It (tries to) computes: 1/1 + null
     * Note that the bug of {@link Rational#subtract(long)} does not manifest itself due to the (correctly) raised NumberFormatException
     */
  @Test
  public void test10()  throws Throwable  {
      Rational rational0 = new Rational(1L, 1L);
      // Undeclared exception!
      try { 
        rational0.add((Rational) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("org.magee.math.Rational", e);
      }
  }

    /**
     * Additional test for {@link Rational#divide(long)}
     */
  @Test
    public void divideScalar() {
      Rational rational0 = new Rational(2L, 6L);
      Rational result = rational0.divide(2L);
      assertEquals(1D/6D, result.doubleValue(), 0.01D);
  }

    /**
     * Additional test for {@link Rational#abs()}
     */
  @Test
    public void absTwoPositives() {
      Rational rational0 = new Rational(5L, 9L);
      assertEquals(5F/9F, rational0.abs().doubleValue(), 0.01F);
  }
    /**
     * Additional test for {@link Rational#abs()}
     * This further highlights the fact that {@link Rational#abs()} does not correctly apply the absolute value to the denominator
     */
    @Test
    public void absNumPositive() {
        Rational rational0 = new Rational(5L, -9L);
        assertEquals(5F/9F, rational0.abs().doubleValue(), 0.01F);
    }

    /**
     * Additional test for {@link Rational#abs()}
     */
    @Test
    public void absDenPositive() {
        Rational rational0 = new Rational(-5L, 9L);
        assertEquals(5F/9F, rational0.abs().doubleValue(), 0.01F);
    }
}
