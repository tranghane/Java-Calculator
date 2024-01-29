package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a calculator having an input list and a list of result history
public class Calculator {
    private List<Double> inputList;
    private ResultHistory resultHistory;
    private Operation operation;
    private List<Operation> operations;

    // EFFECTS: create a new Calculator object with an empty result history list, an empty operation list
    //         and an input list containing 0.0
    public Calculator() {
        this.resultHistory = new ResultHistory();
        this.inputList = new ArrayList<>();
        inputList.add(0.0);
        this.operation = new Operation();
        operations = new ArrayList<>();
    }

    //EFFECTS: add new operation to the list of operations
    //MODIFIES: this
    public void addOperation(String s) {
        Operation o = new Operation();
        o.setOper(s);
        operations.add(o);
        EventLog.getInstance().logEvent(new Event("New operation added to calculator: " + s));
    }

    //EFFECT: remove first operation of the list of operations
    //MODIFIES: this
    public void removeLastOperation() {
        operations.remove(operations.size() - 1);
    }

    //EFFECTS: return list of operations
    public List<Operation> getOperations() {
        return operations;
    }

    //EFFECTS: converting list of operation to list of description
    public List<String> operationToString() {
        List<String> ans = new ArrayList<>();
        for (Operation o : operations) {
            ans.add(o.getOper());
        }
        return ans;
    }

    //EFFECTS: return this operation
    public Operation getOperation() {
        return this.operation;
    }

    //EFFECTS: remove all of operation in operations
    public void removeAllOperation() {
        this.operations = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Remove all operation in operations"));
    }

    // MODIFIES: this
    // EFFECTS: add a new input into inputList
    public void addInput(double num) {
        inputList.add(num);
    }

    // MODIFIES: this
    // EFFECTS: delete all item in inputList
    public void resetInput() {
        inputList = new ArrayList<>();
    }

    // EFFECTS: return the input list
    public List<Double> getListInputList() {
        return inputList;
    }

    //EFFECT write inputList and resultHistory to json file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inputList", inputListToJson());
        json.put("resultHistory", resultHistory.resultHistoryToJSon());
        return json;
    }

    // EFFECTS: returns input list in this calculator as a JSON array
    private JSONArray inputListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (double num : inputList) {
            jsonArray.put(num);
        }
        return jsonArray;
    }

    // EFFECTS: returns resultHistory
    public ResultHistory getHistory() {
        return resultHistory;
    }
}