package gui;

import service.LibraryService;


import javax.swing.*;
import java.awt.*;

public class IssueReturnPanel extends JPanel {

    private JTextField memberIdField, itemIdField, returnItemIdField;

    public IssueReturnPanel(LibraryService service) {
        this.service = service;
        setLayout(new GridLayout(2, 1, 10, 10));

        JPanel issuePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        issuePanel.setBorder(BorderFactory.createTitledBorder("Checkout / Issue Item"));

        itemIdField = new JTextField();
        JButton issueButton = new JButton("Issue Item");

        issuePanel.add(new JLabel("Member ID:"));
        issuePanel.add(memberIdField);
        issuePanel.add(new JLabel("Item ID:"));


        JPanel returnPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        returnPanel.setBorder(BorderFactory.createTitledBorder("Return Item"));

        returnItemIdField = new JTextField();
        JButton returnButton = new JButton("Return Item");

        returnPanel.add(new JLabel("Item ID:"));
        returnPanel.add(returnItemIdField);

        add(issuePanel);
        add(returnPanel);

        returnButton.addActionListener(e -> {
            try {
                service.returnItem(returnItemIdField.getText().trim());
                JOptionPane.showMessageDialog(this, "Item returned successfully!");
                returnItemIdField.setText("");
            } catch (NotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Return Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}