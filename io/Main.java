package io;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import io.math.Matrix;
import io.graphics.DisplayPanel;

public class Main {
    public static void main(String[] args) throws Exception {
        DisplayPanel displayPanel = new DisplayPanel();
        
        JButton button = new JButton("Translate by 100 / 250");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        
        button.addActionListener(actionEvt -> {
            try {
                displayPanel.translate(100, 250);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
            displayPanel.repaint();
        });
    
        JFrame frame = new JFrame();
        frame.setSize(710, 510);
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(displayPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
