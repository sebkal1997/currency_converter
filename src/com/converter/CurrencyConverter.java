package com.converter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class CurrencyConverter {
  private JTextField amountInput;
  private JComboBox fromInput;
  private JComboBox toInput;
  private JTextField amountOutput;
  private JButton convertButton;
  private JPanel mainPanel;
  private JPanel formPanel;

  public CurrencyConverter() {
    convertButton.addActionListener(e -> amountOutput.setText(amountInput.getText()));
    amountInput.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        amountInput.setEditable(e.getKeyChar() >= '0' && e.getKeyChar() <= '9');
      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("CurrencyConverter");
    frame.setContentPane(new CurrencyConverter().mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
