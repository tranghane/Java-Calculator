package ui;

import javax.swing.*;

//Class for launching the calculator
public class CalculatorLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrameSetUp main = new MainFrameSetUp();
            }
        });
    }
}
