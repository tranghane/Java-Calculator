package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// A test class for calculator
public class CalculatorTest {
    private Calculator testCalculator;

    @BeforeEach
    public void setUp() {
        testCalculator = new Calculator();
    }

    @Test
    public void testConstructor() {
        testCalculator.addInput(1);

        // deletable
        testCalculator.getHistory().addResult(2);
        testCalculator.getHistory().addResult(3);

        List<Double> inputs = testCalculator.getListInputList();
        List<Double> results = testCalculator.getHistory().getListResultHistory();
        List<Operation> operations = testCalculator.getOperations();

        assertEquals(0, operations.size());

        assertEquals(2, inputs.size());
        assertEquals(2, results.size());

        assertEquals(0.0, inputs.get(0));
        assertEquals(1.0, inputs.get(1));

        assertEquals(2.0, results.get(0));
        assertEquals(3.0, results.get(1));
    }

    @Test
    public void testMakeStandardOperationOnce() {
        //deletable
        testCalculator.addInput(1);
        testCalculator.addInput(2);

        String invalidInput = "?";

        assertEquals(3.0, testCalculator.getOperation().makeStandardOperation(testCalculator.getListInputList(), "+"));
        assertEquals(-1.0, testCalculator.getOperation().makeStandardOperation(testCalculator.getListInputList(), "-"));
        assertEquals(0.5, testCalculator.getOperation().makeStandardOperation(testCalculator.getListInputList(), "/"));
        assertEquals(2.0, testCalculator.getOperation().makeStandardOperation(testCalculator.getListInputList(), "*"));
        assertEquals(0.0, testCalculator.getOperation().makeStandardOperation(testCalculator.getListInputList(), invalidInput));
    }

    @Test
    public void testMakeScientificOperationOnce() {
        testCalculator.addInput(50);

        String invalidInput = "?";
        //deletable

        assertEquals(-0.26237485370392877, testCalculator.getOperation().makeScientificOperation(testCalculator.getListInputList(), "sin"));
        assertEquals(0.9649660284921133, testCalculator.getOperation().makeScientificOperation(testCalculator.getListInputList(), "cos"));
        assertEquals(-0.27190061199763077, testCalculator.getOperation().makeScientificOperation(testCalculator.getListInputList(), "tan"));
        assertEquals(0.0, testCalculator.getOperation().makeScientificOperation(testCalculator.getListInputList(), invalidInput));
    }

    @Test
    public void testResetInput() {
        testCalculator.addInput(50);
        testCalculator.addInput(90);
        List<Double> inputs = testCalculator.getListInputList();
        assertEquals(3, inputs.size());
        testCalculator.resetInput();
        List<Double> newInputs = testCalculator.getListInputList();
        assertTrue(newInputs.isEmpty());

    }

    @Test
    void testGetResultHistory() {
        ResultHistory result = testCalculator.getHistory();
        List<Double> results = result.getListResultHistory();
        assertEquals(0, results.size());
    }

    @Test
    void testGetOperation() {
        Operation operation = testCalculator.getOperation();
        String oper = operation.getOper();
        assertEquals("", oper);
    }

    @Test
    void testAddCalculator() {
        testCalculator.addOperation("+");

        testCalculator.addOperation("-");

        testCalculator.addOperation("*");

        testCalculator.addOperation("/");

        List<Operation> operations = testCalculator.getOperations();

        Operation o = new Operation();
        o.setOper("+");
        assertEquals(o.getOper(), operations.get(0).getOper());

        o.setOper("-");
        assertEquals(o.getOper(), operations.get(1).getOper());

        o.setOper("*");
        assertEquals(o.getOper(), operations.get(2).getOper());

        o.setOper("/");
        assertEquals(o.getOper(), operations.get(3).getOper());
    }

    @Test
    void testOperationToString() {
        testCalculator.addInput(2.0);
        testCalculator.addInput(1.0);
        testCalculator.addOperation("+");
        testCalculator.addOperation("-");
        testCalculator.addOperation("*");
        testCalculator.addOperation("/");
        testCalculator.addOperation("sin");
        testCalculator.addOperation("cos");
        testCalculator.addOperation("tan");

        List<String> strings = testCalculator.operationToString();
        assertEquals(7, strings.size());
        assertEquals("+", strings.get(0));
        assertEquals("-", strings.get(1));
        assertEquals("*", strings.get(2));
        assertEquals("/", strings.get(3));
        assertEquals("sin", strings.get(4));
        assertEquals("cos", strings.get(5));
        assertEquals("tan", strings.get(6));
    }

    @Test
    void testRemoveLastOperation() {
        testCalculator.addOperation("+");
        testCalculator.addOperation("-");
        testCalculator.addOperation("*");
        testCalculator.removeLastOperation();

        List<Operation> operations = testCalculator.getOperations();
        assertEquals(2, operations.size());
        assertEquals("+", operations.get(0).getOper());
        assertEquals("-", operations.get(1).getOper());
    }

    @Test
    void testRemoveAllOperation() {
        testCalculator.addOperation("+");
        testCalculator.addOperation("-");
        testCalculator.addOperation("*");

        List<Operation> operations = testCalculator.getOperations();
        assertEquals(3, operations.size());

        testCalculator.removeAllOperation();
        operations = testCalculator.getOperations();
        assertEquals(0, operations.size());
    }
}
