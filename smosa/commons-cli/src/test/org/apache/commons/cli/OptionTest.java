package org.apache.commons.cli;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {

    /**
     * Test mainly for {@link Option#setArgName(String)}
     * @throws Throwable
     */
  @Test
  public void test01()  throws Throwable  {
      Option option0 = new Option("", "");
      option0.setArgName("");
      boolean boolean0 = option0.hasArgName();
      assertFalse(boolean0);  // an empty arg ("") should make setArgName() return false instead of true because it does not make sense (it depends on its invariant)
  }

    /**
     * Test mainly for {@link Option#getId()}
     * @throws Throwable
     */
  @Test
  public void test02()  throws Throwable  {
      Option option0 = new Option("arg", "arg");
      int int0 = option0.getId();
      assertEquals("It should have default arg name", "arg", option0.getArgName());
      assertFalse("It should not have a long name", option0.hasLongOpt());
      assertFalse("It should not accept any arg value", option0.hasArgs());
      assertEquals("ID should be letter 'a'", 'a', int0);  // Letter 'r' --> 'a'
  }

    //////// The following two tests for getId() show the fact that charAt(1) should be replaced with charAt(0) ///////
    /**
     * Test for {@link Option#getId()}
     */
    @Test
    public void getIdTestSingleCharacter() {
        Option option = new Option("c", "");
        int id = option.getId();
        assertEquals('c', id);
    }

    /**
     * Test for {@link Option#getId()}
     */
    @Test
    public void getIdTestTwoCharacters() {
        Option option = new Option("ca", "");
        int id = option.getId();
        assertEquals('c', id);
    }

    /**
     * Test that seems for {@link Option#getKey()}, but it does not check anything on it.
     * @throws Throwable
     */
  @Test
  public void test03()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      option0.getKey();
      assertEquals("It should have default arg name", "arg", option0.getArgName());
      assertFalse("It should not accept any arg value", option0.hasArgs());
  }

    /**
     * Test mainly for {@link Option#hasArgs()}
     * @throws Throwable
     */
  @Test
  public void test04()  throws Throwable  {
      Option option0 = new Option("NXb", true, "NXb");
      boolean boolean0 = option0.hasArgs();
      assertEquals("It should have default arg name", "arg", option0.getArgName());
      assertFalse("It should not have a long name", option0.hasLongOpt());
      assertTrue("It should accept at least one arg value", boolean0);
      assertEquals("It should accept exactly one arg value", 1, option0.getArgs());
  }

    //////// The following test for hasArgs() show the fact that 0 is a valid numberOfArgs, when it should not ///////
    /**
     * Test for {@link Option#hasArgs()}
     */
    @Test
    public void hasArgsNumber0() {
        Option option = new Option("c", "");
        option.setArgs(0);
        assertFalse(option.hasArgs());
    }

    /**
     * Test mainly for {@link Option#equals(Object)}
     * @throws Throwable
     */
  @Test
  public void test05()  throws Throwable  {
      Option option0 = new Option("", "");
      Option option1 = new Option("", "", false, "");
      boolean boolean0 = option0.equals(option1);
      assertEquals("arg", option1.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option1.hasArgs());
      assertTrue(boolean0);
  }

    /**
     * Test mainly for {@link Option#equals(Object)}
     * @throws Throwable
     */
  @Test
  public void test06()  throws Throwable  {
      Option option0 = new Option((String) null, "", false, "");
      Option option1 = new Option((String) null, "");
      boolean boolean0 = option0.equals(option1);
      assertTrue(option0.hasLongOpt());
      assertTrue(boolean0);
      assertEquals("arg", option1.getArgName());
      assertFalse(option1.hasLongOpt());
      assertFalse(option1.hasArgs());
  }

    /**
     * Test mainly for {@link Option#equals(Object)}
     * @throws Throwable
     */
  @Test
  public void test07()  throws Throwable  {
      Option option0 = new Option("", "Pz");
      Option option1 = new Option("Pz", "");
      boolean boolean0 = option0.equals(option1);
      assertFalse(boolean0);
      assertEquals((-1), option1.getArgs());
      assertFalse(option1.hasLongOpt());
      assertEquals("", option1.getDescription());
      assertEquals("Pz", option1.getOpt());
      assertEquals("arg", option1.getArgName());
  }

    /**
     * Test mainly for {@link Option#hasArg()}
     * @throws Throwable
     */
  @Test
  public void test08()  throws Throwable  {
      Option option0 = new Option((String) null, (String) null);
      boolean boolean0 = option0.hasArg();
      assertFalse(boolean0);
      assertEquals((-1), option0.getArgs());
      assertEquals("arg", option0.getArgName());
  }

    /**
     * Test mainly for {@link Option#clone()}
     * @throws Throwable
     */
  @Test
  public void test09()  throws Throwable  {
      Option option0 = new Option("", "", true, "");
      Option option1 = (Option)option0.clone();
      boolean boolean0 = option0.equals(option1);
      assertEquals("arg", option1.getArgName());
      assertTrue(boolean0);
      assertTrue(option1.hasArg());
      assertNotSame(option1, option0);
  }

    /**
     * Test mainly for {@link Option#getOpt()}
     * @throws Throwable
     */
  @Test
  public void test10()  throws Throwable  {
      Option option0 = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
      option0.getOpt();
      assertEquals("arg", option0.getArgName());
      assertFalse(option0.hasLongOpt());
      assertFalse(option0.hasArgs());
  }




}
