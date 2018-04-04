package com.rpn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class CalculatorTest {

    private Calculator calculator = new Calculator();

    @Before
    public void setUp() throws Exception {
        calculator.parseAndCalculateString("clear");
    }

    @Test
    public void test1() {
        calculator.parseAndCalculateString("5 2");
        Assert.assertEquals("stack: 5 2", calculator.printStack());
    }

    @Test
    public void test2() {
        calculator.parseAndCalculateString("4 sqrt");
        Assert.assertEquals("stack: 2", calculator.printStack());
    }

    @Test
    public void test3() {
        calculator.parseAndCalculateString("5 2 -");
        Assert.assertEquals("stack: 3", calculator.printStack());
        calculator.parseAndCalculateString("3 -");
        Assert.assertEquals("stack: 0", calculator.printStack());
    }

    @Test
    public void test4() {
        calculator.parseAndCalculateString("5 4 3 2");
        Assert.assertEquals("stack: 5 4 3 2", calculator.printStack());
        calculator.parseAndCalculateString("undo undo *");
        Assert.assertEquals("stack: 20", calculator.printStack());
        calculator.parseAndCalculateString("5 *");
        Assert.assertEquals("stack: 100", calculator.printStack());
        calculator.parseAndCalculateString("undo");
        Assert.assertEquals("stack: 20 5", calculator.printStack());
    }

    @Test
    public void test5() {
        calculator.parseAndCalculateString("7 12 2 /");
        Assert.assertEquals("stack: 7 6", calculator.printStack());
        calculator.parseAndCalculateString("*");
        Assert.assertEquals("stack: 42", calculator.printStack());
        calculator.parseAndCalculateString("4 /");
        Assert.assertEquals("stack: 10.5", calculator.printStack());
    }

    @Test
    public void test6() {
        calculator.parseAndCalculateString("1 2 3 4 5");
        Assert.assertEquals("stack: 1 2 3 4 5", calculator.printStack());
        calculator.parseAndCalculateString("*");
        Assert.assertEquals("stack: 1 2 3 20", calculator.printStack());
        calculator.parseAndCalculateString("clear 3 4 -");
        Assert.assertEquals("stack: -1", calculator.printStack());
    }

    @Test
    public void test7() {
        calculator.parseAndCalculateString("1 2 3 4 5");
        Assert.assertEquals("stack: 1 2 3 4 5", calculator.printStack());
        calculator.parseAndCalculateString("* * * *");
        Assert.assertEquals("stack: 120", calculator.printStack());
    }

    @Test
    public void test8() {
        calculator.parseAndCalculateString("1 2 3 * 5 + * * 6 5");
        Assert.assertEquals("stack: 11", calculator.printStack());
    }

    @Test
    public void test9() {
        calculator.parseAndCalculateString("1 2 3 * 5 +");
        Assert.assertEquals("stack: 1 11", calculator.printStack());
        calculator.parseAndCalculateString("undo");
        Assert.assertEquals("stack: 1 6 5", calculator.printStack());
        calculator.parseAndCalculateString("undo");
        Assert.assertEquals("stack: 1 6", calculator.printStack());
        calculator.parseAndCalculateString("undo");
        Assert.assertEquals("stack: 1 2 3", calculator.printStack());
        calculator.parseAndCalculateString("undo undo");
        Assert.assertEquals("stack: 1", calculator.printStack());
        calculator.parseAndCalculateString("undo undo undo undo");
        Assert.assertEquals("stack:", calculator.printStack());
    }

    @Test
    public void test10() {
        calculator.parseAndCalculateString("1 2 3 * 0 /");
        Assert.assertEquals("stack: 1 6 0", calculator.printStack());
        calculator.parseAndCalculateString("undo");
        Assert.assertEquals("stack: 1 6", calculator.printStack());
        calculator.parseAndCalculateString("-8 sqrt");
        Assert.assertEquals("stack: 1 6 -8", calculator.printStack());
    }

    @Test
    public void test11() {
        calculator.parseAndCalculateString("  1   2 3 * 0 /");
        Assert.assertEquals("stack:", calculator.printStack());
        calculator.parseAndCalculateString("1   2 3 * 0 /");
        Assert.assertEquals("stack: 1", calculator.printStack());
        calculator.parseAndCalculateString("clear 1 yusk234 3 * 0 /");
        Assert.assertEquals("stack: 1", calculator.printStack());
    }

}
