package ui.console;

import model.Calculator;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//Calculator application
public class CalculatorApp {
    private Calculator calculator;
    private Scanner input;
    private final List<String> standardOperations = Arrays.asList("+", "-", "*", "/");
    private final List<String> scientificOperations = Arrays.asList("sin", "cos", "tan");
    private boolean operationInput = false;
    private String oper = "";

    private static final String JSON_STORE = "./data/calculator.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the calculator application
    public CalculatorApp() {
        calculator = new Calculator();
        displayInstruction();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runCalculator();
    }

    // EFFECT: allowing a user to perform change to a calculator
    private void runCalculator() {
        for (; ; ) {
            this.input = new Scanner(System.in);
            String inputReader = input.nextLine();
            if (inputReader.equals("quit")) {
                break;
            } else {
                handle(inputReader);
            }
        }
        System.out.println("done");
    }

    // EFFECT: call loadCalculator() and print the last item in inputList
    private void loadAndPrint() {
        loadCalculator();
        printLastItem(calculator.getListInputList());
    }

    // EFFECT: handle the inputReader with each different case
    private void handle(String inputReader) {
        if (isStandardOperation(inputReader)) {
            operationInput = true;
            oper = inputReader;
            //handleStandardOperation(oper, inputReader, operationInput);
        } else if (isStandardScientific(inputReader)) {
            scientificCalculate(inputReader);
        } else if (inputReader.equals("history")) {
            System.out.println(calculator.getHistory().getListResultHistory());
        } else if (inputReader.equals("reset")) {
            deleteHistory();
        } else if (inputReader.equals("s")) {
            saveCalculator();
        } else if (inputReader.equals("l")) {
            loadAndPrint();
        } else {
            double number = Double.parseDouble(inputReader);
            calculator.addInput(number);
            if (operationInput) {
                standardCalculate(oper);
                operationInput = false;
            }
        }
    }

    // EFFECT: print out the last items of the list
    private void printLastItem(List<Double> list) {
        if (!list.isEmpty()) {
            System.out.println(list.get(list.size() - 1));
        }
    }

    //EFFECTS: Displace instruction to user
    private void displayInstruction() {
        System.out.println("Enter the math, type history to see history");
        System.out.println("type reset to reset the result history, or type quit to exit");
        System.out.println("type history to see history");
        System.out.println("type s to save");
        System.out.println("type l to load");
    }

    // MODIFIES: this
    // EFFECTS: perform a standard calculation and display the answer
    private void standardCalculate(String oper) {
        double ans = calculator.getOperation().makeStandardOperation(calculator.getListInputList(), oper);
        System.out.println(ans);
        this.calculator.getHistory().addResult(ans);
        this.calculator.addInput(ans);
    }

    // MODIFIES: this
    // EFFECTS: perform a scientific calculation and display the answer
    private void scientificCalculate(String input) {
        double ans = calculator.getOperation().makeScientificOperation(calculator.getListInputList(), input);
        System.out.println(ans);
        calculator.getHistory().addResult(ans);
        calculator.addInput(ans);
    }

    // MODIFIES: this
    // EFFECTS: delete all item in result history and display notification
    private void deleteHistory() {
        calculator.getHistory().resetResult();
        System.out.println("History is reset");
    }

    // EFFECTS: return true if reader is one of the standard operation
    private boolean isStandardOperation(String reader) {
        return standardOperations.contains(reader);
    }

    // EFFECTS: return true if reader is one of the scientific operation
    private boolean isStandardScientific(String reader) {
        return scientificOperations.contains(reader);
    }

    // EFFECTS: saves the workroom to file
    private void saveCalculator() {
        try {
            jsonWriter.open();
            jsonWriter.write(calculator);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCalculator() {
        try {
            calculator = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
