package model;

import java.util.List;

//class for operations
public class Operation {
    private String oper;

    //EFFECT: Constructor
    public Operation() {
        this.oper = "";
    }

    //EFFECT: set oper to given string
    //MODIFIE: this
    public void setOper(String s) {
        this.oper = s;
    }

    //EFFECT: return oper
    public String getOper() {
        return this.oper;
    }

    // REQUIRES: operation have to be one of "+", "-", "*", "/"
    // MODIFIES: this
    // EFFECTS: return the result of the wanted calculation of the two last inputs.
    public double makeStandardOperation(List<Double> inputList, String oper) {
        this.setOper(oper);
        int size = inputList.size();
        double firstNum = inputList.get(size - 2);
        double secondNum = inputList.get(size - 1);
        switch (this.oper) {
            case "+":
                return firstNum + secondNum;
            case "-":
                return firstNum - secondNum;
            case "*":
                return firstNum * secondNum;
            case "/":
                return firstNum / secondNum;
            default:
                return 0;
        }
    }

    // REQUIRES: operation have to be one of "sin", "cos", "tan"
    // MODIFIES: this
    // EFFECTS: return the result of the wanted scientific calculation of the two last inputs.
    public double makeScientificOperation(List<Double> inputList, String oper) {
        this.setOper(oper);
        int size = inputList.size();
        double num = inputList.get(size - 1);
        switch (this.oper) {
            case "sin":
                return Math.sin(num);
            case "cos":
                return Math.cos(num);
            case "tan":
                return Math.tan(num);
            default:
                return 0;
        }
    }
}
