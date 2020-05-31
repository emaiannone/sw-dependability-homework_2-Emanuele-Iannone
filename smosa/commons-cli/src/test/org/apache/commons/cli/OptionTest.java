package org.apache.commons.cli;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {

    /**
     * Test for {@link Option#hasArgName()}
     * {@link Option#hasArgName()} returns true when it has an empty arg (""), and this seems wrong (but, of course, it depends on its invariant)
     */
    @Test
    public void test01() throws Throwable {
        Option option0 = new Option("", "");
        option0.setArgName("");
        boolean boolean0 = option0.hasArgName();
        assertFalse(boolean0);  // FIXED
    }

    /**
     * Test for {@link Option#getId()}
     * {@link Option#getId()} incorrectly returns the second character (index 1) instead of the first one (index 0)
     */
    @Test
    public void test02() throws Throwable {
        Option option0 = new Option("arg", "arg");
        int int0 = option0.getId();
        assertEquals("It should have default arg name", "arg", option0.getArgName());
        assertFalse("It should not have a long name", option0.hasLongOpt());
        assertFalse("It should not accept any arg value", option0.hasArgs());
        assertEquals("ID should be letter 'a'", 'a', int0);  // FIXED
    }

    /**
     * Additional test for {@link Option#getId()}
     * This further highlights the fact that {@link Option#getId()} is buggy, in fact it break (unhandled StringIndexOutOfBoundsException) when an option has a single character short name.
     */
    @Test
    public void getIdTestSingleCharacter() {
        Option option = new Option("c", "");
        int id = option.getId();
        assertEquals('c', id);
    }

    /**
     * Additional test for {@link Option#getId()}
     * This further highlights the fact that {@link Option#getId()} is buggy.
     */
    @Test
    public void getIdTestTwoCharacters() {
        Option option = new Option("ca", "");
        int id = option.getId();
        assertEquals('c', id);
    }

    /**
     * Test (that seems) for {@link Option#getKey()}
     * It does not check anything on {@link Option#getKey()}, so a null check assertion was added
     */
    @Test
    public void test03() throws Throwable {
        Option option0 = new Option((String) null, (String) null);
        String key = option0.getKey();
        assertEquals("It should have default arg name", "arg", option0.getArgName());
        assertFalse("It should not accept any arg value", option0.hasArgs());
        assertNull("It should be null", key);
    }

    /**
     * Test for {@link Option#hasArgs()}
     */
    @Test
    public void test04() throws Throwable {
        Option option0 = new Option("NXb", true, "NXb");
        boolean boolean0 = option0.hasArgs();
        assertEquals("It should have default arg name", "arg", option0.getArgName());
        assertFalse("It should not have a long name", option0.hasLongOpt());
        assertTrue("It should accept at least one arg value", boolean0);
        assertEquals("It should accept exactly one arg value", 1, option0.getArgs());
    }

    /**
     * Additional test for {@link Option#hasArgs()}
     * {@link Option#hasArgs()} returns true even when the number of args is 0 (so flagged as valid), when it should not
     */
    @Test
    public void hasArgsNumber0() {
        Option option = new Option("c", "");
        option.setArgs(0);
        assertFalse(option.hasArgs());
    }

    /**
     * Test for {@link Option#equals(Object)}
     * {@link Option#equals(Object)} returns true for two instances when their longOpt are null and "" (or vice versa), when it should not
     */
    @Test
    public void test05() throws Throwable {
        Option option0 = new Option("", "");
        Option option1 = new Option("", "", false, "");
        boolean boolean0 = option0.equals(option1);
        assertEquals("2nd option should have default arg name", "arg", option1.getArgName());
        assertFalse("1st option should not have a long name", option0.hasLongOpt());
        assertFalse("2nd option should not accept any arg value", option1.hasArgs());
        assertFalse("The two options should be not equals", boolean0);  // FIXED
    }

    /**
     * Test for {@link Option#equals(Object)}
     * {@link Option#equals(Object)} returns true for two instances when their longOpt are null and "" (or vice versa), when it should not
     */
    @Test
    public void test06() throws Throwable {
        Option option0 = new Option((String) null, "", false, "");
        Option option1 = new Option((String) null, "");
        boolean boolean0 = option0.equals(option1);
        assertTrue("1st option should have a long name", option0.hasLongOpt());
        assertFalse("The two options should be not equals", boolean0);  // FIXED
        assertEquals("2nd option should have default arg name", "arg", option1.getArgName());
        assertFalse("2nd option should not have a long name", option1.hasLongOpt());
        assertFalse("2nd option should not accept any arg value", option1.hasArgs());
    }

    /**
     * Test for {@link Option#equals(Object)}
     */
    @Test
    public void test07() throws Throwable {
        Option option0 = new Option("", "Pz");
        Option option1 = new Option("Pz", "");
        boolean boolean0 = option0.equals(option1);
        assertFalse("The two options should be not equals", boolean0);
        assertEquals("2nd option should accept no arg values", (-1), option1.getArgs());
        assertFalse("2nd option should not have a long name", option1.hasLongOpt());
        assertEquals("2nd option shot have empty description", "", option1.getDescription());
        assertEquals("1st option should be \"Pz\"", "Pz", option1.getOpt());
        assertEquals("2nd option should have default arg name", "arg", option1.getArgName());
    }

    /**
     * Additional test for {@link Option#equals(Object)}
     * Even worse than {@link OptionTest#test05()} and {@link OptionTest#test06()}, {@link Option#equals(Object)} does not correctly check longOpt when they are not equals (so, line 613 should return false)
     */
    @Test
    public void equalsDifferentLongOpt() {
        Option option0 = new Option("v", "verbose", false, "");
        Option option1 = new Option("v", "not_verbose", false, "");
        assertNotEquals("The two options should be not equals", option0, option1);
    }

    /**
     * Additional test for {@link Option#equals(Object)}
     */
    @Test
    public void equalsWithItself() {
        Option option0 = new Option("v", "verbose", false, "");
        assertEquals("The option should be equal to itself", option0, option0);
    }

    /**
     * Additional test for {@link Option#equals(Object)}
     */
    @Test
    public void equalsWithNull() {
        Option option0 = new Option("v", "verbose", false, "");
        assertNotEquals("The option should be not equal to null", option0, null);
    }

    /**
     * Test mainly for {@link Option#hasArg()}
     */
    @Test
    public void test08() throws Throwable {
        Option option0 = new Option((String) null, (String) null);
        boolean boolean0 = option0.hasArg();
        assertFalse("It should not accept any arg value", boolean0);
        assertEquals("It should accept no arg values", (-1), option0.getArgs());
        assertEquals("It should have default arg name", "arg", option0.getArgName());
    }

    /**
     * Test mainly for {@link Option#clone()}
     */
    @Test
    public void test09() throws Throwable {
        Option option0 = new Option("", "", true, "");
        Option option1 = (Option) option0.clone();
        boolean boolean0 = option0.equals(option1);
        assertEquals("2nd option should have default arg name", "arg", option1.getArgName());
        assertTrue("The two clones should be equal", boolean0);
        assertTrue("2nd option should accept at least one arg value", option1.hasArgs());
        assertNotSame("The two clones should be not the very same object", option1, option0);
    }

    /**
     * Test that seems for {@link Option#getOpt()}
     * It does not check anything on {@link Option#getOpt()}, so an equality check assertion was added
     */
    @Test
    public void test10() throws Throwable {
        Option option0 = new Option("NO_ARGS_ALLOWED", "NO_ARGS_ALLOWED");
        String opt = option0.getOpt();
        assertEquals("It should have default arg name", "arg", option0.getArgName());
        assertFalse("It should not have a long name", option0.hasLongOpt());
        assertFalse("It should not accept any arg value", option0.hasArgs());
        assertEquals("It should be the one passed on construction", "NO_ARGS_ALLOWED", opt);
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     */
    @Test
    public void addValueForProcessingUninitializedArgs() {
        Option option = new Option("", "");
        assertThrows("It should throw RuntimeException because no args are accepted", RuntimeException.class, () -> option.addValueForProcessing(""));
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     */
    @Test
    public void addValueForProcessingOneValueOneArg() {
        Option option = new Option("", "");
        option.setArgs(1);
        option.addValueForProcessing("value");
        assertTrue("It should successfully parse a single value, storing it into the list of values", option.getValues().length > 0);
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     * {@link Option#addValueForProcessing(String)} is not buggy per se, but it (indirectly) calls the buggy {@link Option#add(String)}
     * that accepts any values when numberOfArgs is 0, when it should not (in fact, -2 is the real responsible of the "unlimitedness")
     */
    @Test
    public void addValueForProcessingOneValueZeroArgs() {
        Option option = new Option("", "");
        option.setArgs(0);
        assertThrows("It should throw RuntimeException", RuntimeException.class, () -> option.addValueForProcessing("value"));
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     * Similarly to {@link OptionTest#addValueForProcessingOneValueZeroArgs()}, {@link Option#addValueForProcessing(String)} calls the buggy {@link Option#add(String)}
     */
    @Test
    public void addValueForProcessingTwoValuesZeroArgs() {
        Option option = new Option("", "");
        option.setArgs(0);
        option.addValueForProcessing("value1");
        assertThrows("It should throw RuntimeException", RuntimeException.class, () -> option.addValueForProcessing("value2"));
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     * Similarly to {@link OptionTest#addValueForProcessingOneValueZeroArgs()}, {@link Option#addValueForProcessing(String)} calls the buggy {@link Option#add(String)}
     */
    @Test
    public void addValueForProcessingThreeValuesOneArg() {
        Option option = new Option("", "");
        option.setArgs(1);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        assertThrows("It should throw RuntimeException", RuntimeException.class, () -> option.addValueForProcessing("value3"));
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     * As seen in {@link OptionTest#addValueForProcessingOneValueZeroArgs()}, the buggy {@link Option#add(String)} accept up to 2 more values than the intended limit.
     */
    @Test
    public void addValueForProcessingFourValuesOneArg() {
        Option option = new Option("", "");
        option.setArgs(1);
        option.addValueForProcessing("value1");
        option.addValueForProcessing("value2");
        option.addValueForProcessing("value3");
        assertThrows("It should throw RuntimeException", RuntimeException.class, () -> option.addValueForProcessing("value4"));
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     */
    @Test
    public void addValueForProcessingThreeCSVsUnlimitedArgs() {
        Option option = new Option("", "");
        option.setArgs(Option.UNLIMITED_VALUES);
        option.setValueSeparator(',');
        option.addValueForProcessing("1,2,3");
        assertEquals("It should have three values", 3, option.getValues().length);
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     */
    @Test
    public void addValueForProcessingParseThreeCSVsTwoArgs() {
        Option option = new Option("", "");
        option.setArgs(2);
        option.setValueSeparator(',');
        option.addValueForProcessing("1,2,3");
        assertEquals("It should have two values", 2, option.getValues().length);
    }

    /**
     * Additional test for {@link Option#addValueForProcessing(String)}
     */
    @Test
    public void clearValues() {
        Option option = new Option("", "");
        option.clearValues();
        assertNull("It should be null", option.getValues());
    }
}
