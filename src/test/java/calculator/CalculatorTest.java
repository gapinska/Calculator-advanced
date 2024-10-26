package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private Calculator sut;

    @BeforeEach
    void setUp() {
        sut = new Calculator();
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, sut.getState(), "Initial state should equal 0");
    }

    @Test
    void testAddOne() {
        sut.add(1);
        assertEquals(1.0, sut.getState(), "State should be equal to 1 after adding 1");
    }

    @Test
    public void testMultiplyOneByTwo() {
        sut.setState(1);
        sut.multiply(2);
        assertEquals(2.0, sut.getState(), "1 * 2 should equal 2");
    }

    @Test
    public void testSubtractThreeMinusOne() {
        sut.setState(3);
        sut.subtract(1);
        assertEquals(2.0, sut.getState(), "3 - 1 should equal 2");
    }

    @Test
    public void testDivideTwoByTwo() {
        sut.setState(2);
        sut.divide(2);
        assertEquals(1.0, sut.getState(), "2 / 2 should equal 1");
    }

    @Test
    public void testDivideByZero() {
        sut.setState(10);
        sut.divide(0);

        assertAll(
                "Testing division by zero",
                () -> assertEquals(10.0, sut.getState(), "Dividing by zero should not change the state"),
                () -> assertTrue(sut.isError(), "Error flag should be set when dividing by zero")
        );
    }

    @Test
    public void testReset() {
        sut.setState(100);
        sut.reset();
        assertEquals(0.0, sut.getState(), "State should be reset to 0");
    }

    @Test
    public void testSubtractNegativeResult() {
        sut.setState(5);
        sut.subtract(10);
        assertEquals(-5.0, sut.getState(), "5 - 10 should equal -5");
    }

    @Test
    public void testMultiplyByZero() {
        sut.setState(10);
        sut.multiply(0);
        assertEquals(0.0, sut.getState(), "10 * 0 should equal 0");
    }

    @Test
    public void testSubtractToZero() {
        sut.setState(5);
        sut.subtract(5);
        assertEquals(0.0, sut.getState(), "5 - 5 should equal 0");
    }

    @Test
    public void testAddNegativeValue() {
        sut.setState(10);
        sut.add(-5);
        assertEquals(5.0, sut.getState(), "10 + (-5) should equal 5");
    }

    @Test
    public void testStoreInMemory() {
        sut.setState(10);
        sut.storeInMemory();
        assertEquals(10.0, sut.getMemory(), "Memory should store the value 10");
    }

    @Test
    public void testGetValFromMemory() {
        sut.setState(10);
        sut.storeInMemory();
        sut.add(5);
        sut.getValFromMemory();
        assertEquals(10.0, sut.getState(), "State should be set to the value from memory");
    }

    @Test
    public void testAddMemoryToState() {
        sut.setState(10);
        sut.storeInMemory();
        sut.addMemoryToState();
        assertEquals(20.0, sut.getState(), "State should be 20 after adding memory to state");
    }

    @Test
    public void testSubtractMemoryFromState() {
        sut.setState(10);
        sut.storeInMemory();
        sut.add(10);
        sut.subtractMemoryFromState();
        assertEquals(10.0, sut.getState(), "State should be 10 after subtracting memory from state");
    }

    @Test
    public void testResetMemory() {
        sut.setState(10);
        sut.storeInMemory();
        sut.resetMemory();
        assertEquals(0.0, sut.getMemory(), "Memory should be reset to 0");
    }

    @Test
    public void testMultiplyMaxValueByTwoWithOverflowCheck() {
        sut.setState(Double.MAX_VALUE);
        sut.multiplyWithOverflowCheck(2);
        assertAll(
                "Testing multiplication overflow",
                () -> assertTrue(sut.isError(), "Error flag should be set due to overflow"),
                () -> assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow")
        );
    }

    @Test
    public void testSubtractBeyondMinValueWithOverflowCheck() {
        sut.setState(-Double.MAX_VALUE);
        sut.subtractWithOverflowCheck(Double.MAX_VALUE);
        assertAll(
                "Testing subtraction underflow",
                () -> assertTrue(sut.isError(), "Error flag should be set due to underflow"),
                () -> assertEquals(-Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to underflow")
        );
    }

    @Test
    public void testAdditionNoOverflow() {
        sut.addWithOverflowCheck(10);
        assertFalse(sut.isError(), "Error flag should not be set for valid addition");
        assertEquals(10.0, sut.getState(), "State should be 10 after addition");
    }

    @Test
    public void testAdditionOverflow() {
        sut.setState(Double.MAX_VALUE);
        sut.addWithOverflowCheck(1e308);
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testSubtractNoOverflow() {
        sut.subtractWithOverflowCheck(10);
        assertFalse(sut.isError(), "Error flag should not be set for valid subtraction");
        assertEquals(-10.0, sut.getState(), "State should be -10 after subtraction");
    }

    @Test
    public void testSubtractOverflow() {
        sut.setState(-Double.MAX_VALUE);
        sut.subtractWithOverflowCheck(1e308);
        assertTrue(sut.isError(), "Error flag should be set due to underflow");
        assertEquals(-Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to underflow");
    }

    @Test
    public void testMultiplicationOverflow() {
        sut.setState(Double.MAX_VALUE);
        sut.multiplyWithOverflowCheck(2);
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testMultiplicationNoOverflow() {
        sut.setState(1000);
        sut.multiplyWithOverflowCheck(2);
        assertAll(
                "Testing multiplication without overflow",
                () -> assertFalse(sut.isError(), "Error flag should not be set"),
                () -> assertEquals(2000.0, sut.getState(), "1000 * 2 should equal 2000")
        );
    }

    @Test
    public void testSetStateWithValidString() {
        sut.setState("123.45");
        assertEquals(123.45, sut.getState(), "State should be set to 123.45");
    }

    @Test
    public void testSetStateWithInvalidString() {
        sut.setState("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(0.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testSetStateWithNullString() {
        sut.setState((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(0.0, sut.getState(), "State should not change for null input");
    }

    // Tests for add(String value)
    @Test
    public void testAddWithValidString() {
        sut.setState(10);
        sut.add("5.5");
        assertEquals(15.5, sut.getState(), "10 + 5.5 should equal 15.5");
    }

    @Test
    public void testAddWithInvalidString() {
        sut.setState(10);
        sut.add("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testAddWithNullString() {
        sut.setState(10);
        sut.add((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    // Tests for subtract(String value)
    @Test
    public void testSubtractWithValidString() {
        sut.setState(10);
        sut.subtract("5.5");
        assertEquals(4.5, sut.getState(), "10 - 5.5 should equal 4.5");
    }

    @Test
    public void testSubtractWithInvalidString() {
        sut.setState(10);
        sut.subtract("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testSubtractWithNullString() {
        sut.setState(10);
        sut.subtract((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    // Tests for multiply(String value)
    @Test
    public void testMultiplyWithValidString() {
        sut.setState(10);
        sut.multiply("5.5");
        assertEquals(55.0, sut.getState(), "10 * 5.5 should equal 55");
    }

    @Test
    public void testMultiplyWithInvalidString() {
        sut.setState(10);
        sut.multiply("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testMultiplyWithNullString() {
        sut.setState(10);
        sut.multiply((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    // Tests for divide(String value)
    @Test
    public void testDivideWithValidString() {
        sut.setState(10);
        sut.divide("2.0");
        assertEquals(5.0, sut.getState(), "10 / 2.0 should equal 5");
    }

    @Test
    public void testDivideWithInvalidString() {
        sut.setState(10);
        sut.divide("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testDivideWithNullString() {
        sut.setState(10);
        sut.divide((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    @Test
    public void testDivideByZeroWithString() {
        sut.setState(10);
        sut.divide("0");
        assertTrue(sut.isError(), "Error flag should be set for division by zero");
        assertEquals(10.0, sut.getState(), "State should not change when dividing by zero");
    }

    @Test
    public void testAddWithOverflowCheckValidString() {
        sut.setState(10);
        sut.addWithOverflowCheck("5.5");
        assertFalse(sut.isError(), "Error flag should not be set for valid addition");
        assertEquals(15.5, sut.getState(), "10 + 5.5 should equal 15.5");
    }

    @Test
    public void testAddWithOverflowCheckInvalidString() {
        sut.setState(10);
        sut.addWithOverflowCheck("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testAddWithOverflowCheckNullString() {
        sut.setState(10);
        sut.addWithOverflowCheck((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    @Test
    public void testAddWithOverflowCheckOverflowWithString() {
        sut.setState(Double.MAX_VALUE);
        sut.addWithOverflowCheck("1e308");
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testSubtractWithOverflowCheckValidString() {
        sut.setState(10);
        sut.subtractWithOverflowCheck("5.5");
        assertFalse(sut.isError(), "Error flag should not be set for valid subtraction");
        assertEquals(4.5, sut.getState(), "10 - 5.5 should equal 4.5");
    }

    @Test
    public void testSubtractWithOverflowCheckInvalidString() {
        sut.setState(10);
        sut.subtractWithOverflowCheck("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testSubtractWithOverflowCheckNullString() {
        sut.setState(10);
        sut.subtractWithOverflowCheck((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    @Test
    public void testSubtractWithOverflowCheckUnderflowWithString() {
        sut.setState(-Double.MAX_VALUE);
        sut.subtractWithOverflowCheck("1e308");
        assertTrue(sut.isError(), "Error flag should be set due to underflow");
        assertEquals(-Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to underflow");
    }

    @Test
    public void testMultiplyWithOverflowCheckValidString() {
        sut.setState(10);
        sut.multiplyWithOverflowCheck("5.5");
        assertFalse(sut.isError(), "Error flag should not be set for valid multiplication");
        assertEquals(55.0, sut.getState(), "10 * 5.5 should equal 55");
    }

    @Test
    public void testMultiplyWithOverflowCheckInvalidString() {
        sut.setState(10);
        sut.multiplyWithOverflowCheck("abc");
        assertTrue(sut.isError(), "Error flag should be set for invalid input");
        assertEquals(10.0, sut.getState(), "State should not change for invalid input");
    }

    @Test
    public void testMultiplyWithOverflowCheckNullString() {
        sut.setState(10);
        sut.multiplyWithOverflowCheck((String) null);
        assertTrue(sut.isError(), "Error flag should be set for null input");
        assertEquals(10.0, sut.getState(), "State should not change for null input");
    }

    @Test
    public void testMultiplyWithOverflowCheckOverflowWithString() {
        sut.setState(Double.MAX_VALUE);
        sut.multiplyWithOverflowCheck("2");
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testResetClearsErrorFlag() {
        sut.setState(10);
        sut.divide(0); // Causes an error
        assertTrue(sut.isError(), "Error flag should be set after division by zero");
        sut.reset();
        assertFalse(sut.isError(), "Error flag should be cleared after reset");
        assertEquals(0.0, sut.getState(), "State should be reset to 0");
    }

    @Test
    public void testErrorStatusRemainsAfterValidOperation() {
        sut.setState(10);
        sut.divide(0); // Causes error
        assertTrue(sut.isError(), "Error flag should be set after division by zero");
        sut.add(5); // Perform a valid operation
        assertTrue(sut.isError(), "Error flag should remain set until reset");
        assertEquals(15.0, sut.getState(), "State should change even when error flag is set");
    }

    @Test
    public void testAddResultingInInfinity() {
        sut.setState(Double.MAX_VALUE);
        sut.addWithOverflowCheck(Double.MAX_VALUE);
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testSubtractResultingInNegativeInfinity() {
        sut.setState(-Double.MAX_VALUE);
        sut.subtractWithOverflowCheck(Double.MAX_VALUE);
        assertTrue(sut.isError(), "Error flag should be set due to underflow");
        assertEquals(-Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to underflow");
    }

    @Test
    public void testMultiplyResultingInInfinity() {
        sut.setState(Double.MAX_VALUE);
        sut.multiplyWithOverflowCheck(2);
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testDivideResultingInInfinity() {
        sut.setState(Double.MAX_VALUE);
        sut.divide(0.1);
        assertTrue(sut.isError(), "Error flag should be set due to overflow");
        assertEquals(Double.MAX_VALUE, sut.getState(), "State should remain unchanged due to overflow");
    }

    @Test
    public void testDivideZeroByZero() {
        sut.setState(0.0);
        sut.divide(0.0);
        assertTrue(sut.isError(), "Error flag should be set when dividing zero by zero");
        assertEquals(0.0, sut.getState(), "State should remain unchanged when dividing zero by zero");
    }

    @Test
    public void testStoreInMemoryDoesNotStoreWhenError() {
        sut.setState(10);
        sut.divide(0); // Causes error
        sut.storeInMemory();
        assertEquals(0.0, sut.getMemory(), "Memory should not be updated when there is an error");
    }

    @Test
    public void testGetValFromMemoryDoesNotGetWhenError() {
        sut.setState(10);
        sut.storeInMemory();
        sut.divide(0); // Causes error
        sut.getValFromMemory();
        assertEquals(10.0, sut.getState(), "State should not change when there is an error");
    }

    @Test
    public void testResetMemoryWhileError() {
        sut.setState(10);
        sut.storeInMemory();
        sut.divide(0); // Causes error
        sut.resetMemory();
        assertEquals(0.0, sut.getMemory(), "Memory should be reset even when there is an error");
    }

    @Test
    public void testSetStateWithNaN() {
        sut.setState(Double.NaN);
        assertTrue(Double.isNaN(sut.getState()), "State should be NaN when set to NaN");
    }

    @Test
    public void testAddWithNaN() {
        sut.setState(10);
        sut.add(Double.NaN);
        assertTrue(Double.isNaN(sut.getState()), "State should be NaN after adding NaN");
    }

    @Test
    public void testMultiplyWithInfinity() {
        sut.setState(10);
        sut.multiply(Double.POSITIVE_INFINITY);
        assertTrue(Double.isInfinite(sut.getState()), "State should be Infinite after multiplying by Infinity");
    }

    @Test
    public void testDivideWithInfinity() {
        sut.setState(10);
        sut.divide(Double.POSITIVE_INFINITY);
        assertEquals(0.0, sut.getState(), "10 divided by Infinity should equal 0");
    }
}
