package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest {
    private Operation testOperation;

    @BeforeEach
    void setUp() {
        testOperation = new Operation();
    }

    @Test
    void testConstructor() {
        assertEquals("", testOperation.getOper());
    }

    @Test
    void testSetOper() {
        testOperation.setOper("+");
        assertEquals("+", testOperation.getOper());

        testOperation.setOper("sin");
        assertEquals("sin", testOperation.getOper());
    }

    @Test
    void testMakeStandardOperation() {
        List<Double> inputList = new ArrayList<>();
        inputList.add(9.0);
        inputList.add(3.0);

        assertEquals(12.0, testOperation.makeStandardOperation(inputList, "+"));
        assertEquals(6.0, testOperation.makeStandardOperation(inputList, "-"));
        assertEquals(27.0, testOperation.makeStandardOperation(inputList, "*"));
        assertEquals(3.0, testOperation.makeStandardOperation(inputList, "/"));
        assertEquals(0.0, testOperation.makeStandardOperation(inputList, "?"));
    }

    @Test
    void testMakeScientificOperation() {
        List<Double> inputList = new ArrayList<>();
        inputList.add(9.0);
        inputList.add(3.0);

        assertEquals(0.1411200080598672, testOperation.makeScientificOperation(inputList, "sin"));
        assertEquals(-0.9899924966004454, testOperation.makeScientificOperation(inputList, "cos"));
        assertEquals(-0.1425465430742778, testOperation.makeScientificOperation(inputList, "tan"));
        assertEquals(0, testOperation.makeScientificOperation(inputList, "?"));
    }
}
