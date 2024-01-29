package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// A test class for result history
public class ResultHistoryTest {
    private ResultHistory testHistory;

    @BeforeEach
    public void setUp() {
        testHistory = new ResultHistory();
    }

    @Test
    void constructorTest() {
        assertEquals(0, testHistory.getListResultHistory().size());
    }

    @Test
    void addResultTest() {
        testHistory.addResult(0);
        List<Double> results = testHistory.getListResultHistory();
        assertEquals(1, results.size());
        assertEquals(0, results.get(0));

        testHistory.addResult(3);
        List<Double> resultsNew = testHistory.getListResultHistory();
        assertEquals(2, resultsNew.size());
        assertEquals(0, resultsNew.get(0));
        assertEquals(3, resultsNew.get(1));
    }

    @Test
    void resetResultTest() {
        testHistory.addResult(0);
        testHistory.addResult(1);
        testHistory.addResult(2);

        List<Double> results = testHistory.getListResultHistory();
        assertEquals(3, results.size());
        testHistory.resetResult();
        List<Double> resultsNew = testHistory.getListResultHistory();
        assertEquals(0, resultsNew.size());
    }
}
