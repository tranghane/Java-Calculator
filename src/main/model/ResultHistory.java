package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// A class for result history
public class ResultHistory {
    private List<Double> resultHistory;

    // MODIFIES: this
    // EFFECTS: constructor for ResultHistory
    public ResultHistory() {
        this.resultHistory = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECT: add number to resultHistory
    public void addResult(double num) {
        resultHistory.add(num);
        EventLog.getInstance().logEvent(new Event("Add new result to history: " + Double.toString(num)));
    }

    // MODIFIES: this
    // EFFECTS: delete all item in resultHistory
    public void resetResult() {
        resultHistory = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Remove all result in history"));
    }

    // EFFECTS: return the result history
    public List<Double> getListResultHistory() {
        return resultHistory;
    }

    // EFFECTS: returns result list in this calculator as a JSON array
    public JSONArray resultHistoryToJSon() {
        JSONArray jsonArray = new JSONArray();
        for (double num : resultHistory) {
            jsonArray.put(num);
        }
        return jsonArray;
    }
}
