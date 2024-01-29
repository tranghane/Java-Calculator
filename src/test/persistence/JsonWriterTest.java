package persistence;

import model.Calculator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import static org.junit.jupiter.api.Assertions.*;
public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterStartingCalculator() {
        try {
            Calculator c = new Calculator();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalculator.json");

            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalculator.json");
            c = reader.read();

            List<Double> inputList = c.getListInputList();
            List<Double> resultHistory = c.getHistory().getListResultHistory();

            assertEquals(0, resultHistory.size());
            assertEquals(1, inputList.size());

            assertEquals(0.0, inputList.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalCalculator() {
        try {
            Calculator c = new Calculator();
            JsonWriter writer = new JsonWriter("./data/testWriterNormalCalculator.json");
            c.getHistory().addResult(1);
            c.getHistory().addResult(2);

            c.addInput(1);
            c.addInput(2);

            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalCalculator.json");
            c = reader.read();

            List<Double> inputList = c.getListInputList();
            List<Double> resultHistory = c.getHistory().getListResultHistory();

            assertEquals(3, inputList.size());
            assertEquals(2, resultHistory.size());

            assertEquals(0.0, inputList.get(0));
            assertEquals(1.0, inputList.get(1));
            assertEquals(2.0, inputList.get(2));

            assertEquals(1.0, resultHistory.get(0));
            assertEquals(2.0, resultHistory.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
