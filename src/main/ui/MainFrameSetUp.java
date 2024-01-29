package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;


//Class for setting up the JPanel and Jframe
public class MainFrameSetUp {
    private JFrame window;
    private final ActionListenerSetUp listener = new ActionListenerSetUp();

    private final int width = 500;
    private final int height = 500;

    private final JButton buttonZero = new JButton("0");
    private final JButton buttonOne = new JButton("1");
    private final JButton buttonTwo = new JButton("2");
    private final JButton buttonThree = new JButton("3");
    private final JButton buttonFour = new JButton("4");
    private final JButton buttonFive = new JButton("5");
    private final JButton buttonSix = new JButton("6");
    private final JButton buttonSeven = new JButton("7");
    private final JButton buttonEight = new JButton("8");
    private final JButton buttonNine = new JButton("9");

    private final JButton buttonAdd = new JButton("+");
    private final JButton buttonMinus = new JButton("-");
    private final JButton buttonMultiply = new JButton("x");
    private final JButton buttonDivide = new JButton(":");

    private final JButton buttonSin = new JButton("sin");
    private final JButton buttonCos = new JButton("cos");
    private final JButton buttonTan = new JButton("tan");

    private final JButton buttonEqual = new JButton("=");
    private final JButton buttonAC = new JButton("AC");
    private final JButton buttonDot = new JButton(".");

    private final JButton buttonHistory = new JButton("history");
    private final JButton buttonReset = new JButton("reset");
    private final JButton buttonSave = new JButton("save");
    private final JButton buttonLoad = new JButton("load");

    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JButton buttonTest = new JButton("test");

    private JTextField textField;
    private JTextField operationDisplay;

    private JLabel loaderLabel;

    private JFrame splash;

    //EFFECT: call the set-up method the main window
    public MainFrameSetUp() {
        setUp();
    }

    //EFFECT: set up window and splash
    private void setUp() {
        setUpSplash();
        setUpWindow();
        displayLoader();
        setUpEventLog();

    }

    //EFFECT: set up EventLog that print all the events to console when exit the program
    private void setUpEventLog() {
        EventLog el = EventLog.getInstance();
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event event : el) {
                    System.out.println(event);
                    System.out.println(" ");
                }
                System.exit(0);
            }
        });
    }

    //EFFECT: set up window JFrame
    //MODIFIES: this
    private void setUpWindow() {
        window = new JFrame();
        window.setTitle("Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }

    //EFFECT: setup splash Jframe
    //MODIFIES: this
    private void setUpSplash() {
        splash = new JFrame("Calculator");
        splash.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        splash.setSize(300, 300);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        setUpLoader(splash);
    }

    //EFFECT: display splash Jframe for 3 seconds, then remove splash and display window Jframe
    //MODIFIES: this
    private void displayLoader() {
        ActionListener splashListen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splash.setVisible(false);
                window.setVisible(true);
                setUpJPanel(window);
                setUpActionListener();
            }
        };
        Timer timer = new Timer(3000, splashListen);
        timer.setRepeats(false);
        timer.start();
    }

    //EFFECT: set up action listener for all the button
    private void setUpActionListener() {
        setUpNumberButton();
        listener.setUpDotButton(buttonDot, textField);

        listener.setUpScientificOperation(buttonSin, textField, operationDisplay);
        listener.setUpScientificOperation(buttonCos, textField, operationDisplay);
        listener.setUpScientificOperation(buttonTan, textField, operationDisplay);

        listener.setUpSimpleOperation(buttonAdd, textField, operationDisplay);
        listener.setUpSimpleOperation(buttonMinus, textField, operationDisplay);
        listener.setUpSimpleOperation(buttonDivide, textField, operationDisplay);
        listener.setUpSimpleOperation(buttonMultiply, textField, operationDisplay);


        listener.setUpEqual(buttonEqual, textField);
        listener.setUpAC(buttonAC, textField);
        listener.setUpHistoryButton(buttonHistory, textField);
        listener.setUpResetButton(buttonReset, textField, operationDisplay);
        listener.setUpSaveButton(buttonSave, textField);
        listener.setUpLoadButton(buttonLoad, textField);
    }

    //EFFECT set up number buttons
    private void setUpNumberButton() {
        listener.setUpNumberButton(buttonZero, textField);
        listener.setUpNumberButton(buttonOne, textField);
        listener.setUpNumberButton(buttonTwo, textField);
        listener.setUpNumberButton(buttonThree, textField);
        listener.setUpNumberButton(buttonFour, textField);
        listener.setUpNumberButton(buttonFive, textField);
        listener.setUpNumberButton(buttonSix, textField);
        listener.setUpNumberButton(buttonSeven, textField);
        listener.setUpNumberButton(buttonEight, textField);
        listener.setUpNumberButton(buttonNine, textField);
    }

    //EFFECT: set up the JPanel and fill each row of the grid
    private void setUpJPanel(JFrame window) {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        fillRows(buttonArea);
        window.add(buttonArea, BorderLayout.CENTER);
    }

    //EFFECT: rows filler for grid layout of the panel
    private void fillRows(JPanel buttonArea) {
        addRowZero(buttonArea);
        addRowOne(buttonArea);
        addRowTwo(buttonArea);
        addRowThree(buttonArea);
        addRowFour(buttonArea);
        addRowFive(buttonArea);
        addRowSix(buttonArea);
    }

    //EFFECT: First row filler
    private void addRowZero(JPanel buttonArea) {
        operationDisplay = new JTextField();
        operationDisplay.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        buttonArea.add(operationDisplay, gbc);
        gbc.gridwidth = 1;
        operationDisplay.setText("Operation used:");
        operationDisplay.setEditable(false);
    }

    //EFFECT: Row one filler
    private void addRowOne(JPanel buttonArea) {
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 5;
        buttonArea.add(textField, gbc);
        gbc.gridwidth = 1;
        textField.setEditable(false);
    }

    //EFFECT: Row two filler
    private void addRowTwo(JPanel buttonArea) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        buttonArea.add(buttonTan, gbc);

        gbc.gridx = 1;
        buttonArea.add(buttonCos, gbc);

        gbc.gridx = 2;
        buttonArea.add(buttonSin, gbc);

        gbc.gridx = 3;
        buttonArea.add(buttonAC, gbc);

        gbc.gridx = 4;
        buttonArea.add(buttonDivide, gbc);

    }

    //EFFECT: Row three filler
    private void addRowThree(JPanel buttonArea) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        buttonArea.add(buttonHistory, gbc);

        gbc.gridx = 1;
        buttonArea.add(buttonSeven, gbc);

        gbc.gridx = 2;
        buttonArea.add(buttonEight, gbc);

        gbc.gridx = 3;
        buttonArea.add(buttonNine, gbc);

        gbc.gridx = 4;
        buttonArea.add(buttonMultiply, gbc);
    }

    //EFFECT: Row four filler
    private void addRowFour(JPanel buttonArea) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        buttonArea.add(buttonReset, gbc);

        gbc.gridx = 1;
        buttonArea.add(buttonFour, gbc);

        gbc.gridx = 2;
        buttonArea.add(buttonFive, gbc);

        gbc.gridx = 3;
        buttonArea.add(buttonSix, gbc);

        gbc.gridx = 4;
        buttonArea.add(buttonMinus, gbc);
    }

    //EFFECT: Row five filler
    private void addRowFive(JPanel buttonArea) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        buttonArea.add(buttonSave, gbc);

        gbc.gridx = 1;
        buttonArea.add(buttonOne, gbc);

        gbc.gridx = 2;
        buttonArea.add(buttonTwo, gbc);

        gbc.gridx = 3;
        buttonArea.add(buttonThree, gbc);

        gbc.gridx = 4;
        buttonArea.add(buttonAdd, gbc);
    }

    //EFFECT: Row six filler
    private void addRowSix(JPanel buttonArea) {
        gbc.gridx = 0;
        gbc.gridy += 1;
        buttonArea.add(buttonLoad, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        buttonArea.add(buttonZero, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        buttonArea.add(buttonDot, gbc);

        gbc.gridx = 4;
        buttonArea.add(buttonEqual, gbc);
    }

    //EFFECT: add calculator.png to a JLable and add that label to given frame
    private void setUpLoader(JFrame frame) {
        loaderLabel = new JLabel();
        URL iconURL = getClass().getResource("calculator.png"); //taken from icon8.com
        assert iconURL != null;
        ImageIcon icon = new ImageIcon(iconURL);

        loaderLabel.setIcon(icon);
        loaderLabel.setBackground(Color.white);
        loaderLabel.setOpaque(true);
        loaderLabel.setVerticalAlignment(JLabel.CENTER);
        loaderLabel.setHorizontalAlignment(JLabel.CENTER);

        frame.add(loaderLabel);
    }


}
