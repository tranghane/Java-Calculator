package persistence;

import model.Calculator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

//source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import static org.junit.jupiter.api.Assertions.*;
public class JsonReaderTest {
    private Calculator c;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noneSuchFile.json");
        try {
            c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalculator() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalculator.json");
        try {
            c = reader.read();

            List<Double> inputList = c.getListInputList();
            List<Double> resultHistory = c.getHistory().getListResultHistory();

            assertEquals(0, resultHistory.size());
            assertEquals(1, inputList.size());

            assertEquals(0.0, inputList.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNormalCalculator() {
        JsonReader reader = new JsonReader("./data/testReaderNormalCalculator.json");
        try {
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
            fail("Couldn't read from file");
        }
    }


}
