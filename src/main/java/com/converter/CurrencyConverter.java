package com.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyConverter {
  private JTextField amountInput;
  private JComboBox<String> fromInput;
  private JComboBox<String> toInput;
  private JTextField amountOutput;
  private JButton convertButton;
  private JPanel mainPanel;
  private JPanel formPanel;

  public CurrencyConverter() {
    List<String> currencies = getCurrenciesList();
    currencies.forEach(c -> fromInput.addItem(c));
    currencies.forEach(c -> toInput.addItem(c));
    convertButton.addActionListener(e -> amountOutput.setText(Double.toString(convertCurrency(getAmount(), getFrom(), getTo()))));
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("CurrencyConverter");
    frame.setContentPane(new CurrencyConverter().mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public static List<String> getCurrenciesList() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      File file = new File("src/main/resources/currencies.json");
      return objectMapper.readValue(file, new TypeReference<>() {});
    } catch (IOException e) {
      log.error("Failed to read currencies: " + e.getMessage());
    }
    return Collections.emptyList();
  }

  private static Map<String, Double> getCurrenciesValueMap() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      File file = new File("src/main/resources/currencyToOneDollar.json");
      return objectMapper.readValue(file, new TypeReference<>() {});
    } catch (IOException e) {
      log.error("Failed to read currencies values: " + e.getMessage());
    }
    return Collections.emptyMap();
  }

  public static Double convertCurrency(Double amount, String from, String to) {
    Map<String, Double> currenciesMap = getCurrenciesValueMap();
    Double fromValue = currenciesMap.get(from);
    Double toValue = currenciesMap.get(to);
    return (amount / fromValue) * toValue;
  }

  private Double getAmount() {
    //TODO: Add validation for input
    String amountValue = amountInput.getText();
    return Double.valueOf(amountValue);
  }

  private String getFrom() {
    String fromValue = (String) fromInput.getSelectedItem();
    assert fromValue != null;
    return fromValue.substring(0, 3);
  }

  private String getTo() {
    String toValue = (String) toInput.getSelectedItem();
    assert toValue != null;
    return toValue.substring(0, 3);
  }
}
