package ui;

import model.Calculator;
import model.Operation;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

//Class for setting the actionListener for all the JComponents
public class ActionListenerSetUp {

    private Calculator calculator;
    private String operation;
    private boolean needReset;
    private boolean operationAdded;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/calculator.json";

    //Constructor for class
    public ActionListenerSetUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        calculator = new Calculator();
        needReset = false;
        operationAdded = true;
        operation = "";
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener to all the buttons from 0 to 9
    public void setUpNumberButton(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!needReset) {
                    String buttonText = textDisplay.getText() + b.getText();
                    textDisplay.setText(buttonText);
                } else {
                    textDisplay.setText(b.getText());
                    needReset = false;
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for dot button
    public void setUpDotButton(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!needReset) {
                    if (textDisplay.getText().isEmpty()) {
                        textDisplay.setText("0.");
                    } else {
                        String buttonText = textDisplay.getText() + b.getText();
                        textDisplay.setText(buttonText);
                    }
                } else {
                    textDisplay.setText("0.");
                    needReset = false;
                }

            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for scientific operation buttons
    public void setUpScientificOperation(JButton b, JTextField textField, JTextField operationDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                String operationUsed = operationDisplay.getText();
                calculator.addInput(Double.parseDouble(input));
                double ans = calculator.getOperation().makeScientificOperation(calculator.getListInputList(),
                                                                               b.getText());
                calculator.getHistory().addResult(ans);
                calculator.addInput(ans);
                calculator.addOperation(b.getText());
                operationAdded = true;
                textField.setText(Double.toString(ans));
                operationDisplay.setText(operationUsed
                        + " "
                        + calculator.operationToString().get(calculator.operationToString().size() - 1));
                needReset = true;
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for equal button
    public void setUpEqual(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!operationAdded) {
                    calculator.addOperation(operationTranslator(operation));
                    operationAdded = true;
                }
                Double secondNum = Double.parseDouble(textDisplay.getText());
                calculator.addInput(secondNum);
                if (operation != "") {
                    double ans = calculator.getOperation().makeStandardOperation(calculator.getListInputList(),
                            operation);
                    calculator.getHistory().addResult(ans);
                    calculator.addInput(ans);
                    textDisplay.setText(Double.toString(ans));
                    needReset = true;
                    operation = "";
                } else {
                    textDisplay.setText("");
                    needReset = true;
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for simple operation buttons
    public void setUpSimpleOperation(JButton b, JTextField textField, JTextField operationDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(operation, "")) {
                    Double firstNum = Double.parseDouble(textField.getText());
                    calculator.addInput(firstNum);
                    assigningOperation(b.getText());
                    textField.setText("");
                    changeOperationDisplace(b, operationDisplay);
                } else if (!Objects.equals(textField.getText(), "")) {
                    continueTheOperation(b, textField, operationDisplay);
                } else if (Objects.equals(textField.getText(), "")) {
                    assigningOperation(b.getText());
                    textField.setText("");
                    String operationUsed = operationDisplay.getText();
                    operationDisplay.setText(replaceString(operationUsed, b.getText()));
                    operationAdded = false;
                    calculator.removeLastOperation();
                }
            }
        });
    }

    //EFFECT: following the old operation with a new operation without pressing =
    private void continueTheOperation(JButton b, JTextField textField, JTextField operationDisplay) {
        double secondNum = Double.parseDouble(textField.getText());
        calculator.addInput(secondNum);
        double ans = calculator.getOperation().makeStandardOperation(calculator.getListInputList(),
                operation);
        calculator.getHistory().addResult(ans);
        calculator.addInput(ans);
        textField.setText(Double.toString(ans));
        assigningOperation(b.getText());
        changeOperationDisplace(b, operationDisplay);
        needReset = true;
    }

    //EFFECT: replace the last character of s with a
    private String replaceString(String s, String a) {
        if (s != null && !s.isEmpty()) {
            return s.substring(0, s.length() - 1) + a;
        }
        return s;
    }

    //EFFECT: change operationDisplace
    //MODIFIES: this
    private void changeOperationDisplace(JButton b, JTextField operationDisplay) {
        calculator.addOperation(operationTranslator(b.getText()));
        operationAdded = true;
        String operationUsed = operationDisplay.getText();
        operationDisplay.setText(operationUsed
                + " "
                + calculator.operationToString().get(calculator.operationToString().size() - 1));
    }

    //EFFECT: change a string into proper operation symbol
    private String operationTranslator(String s) {
        if (Objects.equals(s, "x")) {
            return  "*";
        } else if (Objects.equals(s, ":")) {
            return "/";
        } else {
            return s;
        }
    }

    //MODIFIES: this
    //EFFECT: assigning the corresponding operation for each symbol on ui
    private void assigningOperation(String s) {
        if (s == "x") {
            operation = "*";
        } else if (s == ":") {
            operation = "/";
        } else {
            operation = s;
        }
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for AC button
    public void setUpAC(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculator.addInput(0);
                textDisplay.setText("");
                operation = "";
                needReset = false;
                operationAdded = true;
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up Actionlistener for history button
    public void setUpHistoryButton(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String historyToDisplay = "";
                List<Double> numList = calculator.getHistory().getListResultHistory();
                for (int i = 0; i < numList.size(); i++) {
                    if (i != numList.size() - 1) {
                        historyToDisplay += Double.toString(numList.get(i)) + ", ";
                    } else {
                        historyToDisplay += Double.toString(numList.get(i));
                    }
                }
                operation = "";
                textDisplay.setText(historyToDisplay);
                needReset = true;
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up Action listener for reset button
    public void setUpResetButton(JButton b, JTextField textDisplay, JTextField operationDisplace) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculator.getHistory().resetResult();
                calculator.removeAllOperation();
                textDisplay.setText("History, Operations is reset");
                operationDisplace.setText("Operation used: ");
                needReset = true;
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up ActionListener for save button
    public void setUpSaveButton(JButton b, JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(calculator);
                    jsonWriter.close();
                    textDisplay.setText("Saved to " + JSON_STORE);
                    needReset = true;
                } catch (FileNotFoundException exception) {
                    textDisplay.setText("Unable to write to file: " + JSON_STORE);
                    needReset = true;
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECT: set up actionListener for load button
    public void setUpLoadButton(JButton b,JTextField textDisplay) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calculator = jsonReader.read();
                    textDisplay.setText("Loaded from " + JSON_STORE);
                    needReset = true;
                } catch (IOException exception) {
                    textDisplay.setText("Unable to read from file: " + JSON_STORE);
                    needReset = true;
                }
            }
        });
    }

}
