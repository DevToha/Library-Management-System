package gui;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class MemberPanel extends JPanel {
    private LibraryService service;
    private JTextField idField, nameField, emailField;
    private JComboBox<String> typeBox;

    public MemberPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Register Member"));

        emailField = new JTextField();
        typeBox = new JComboBox<>(new String[]{"Student", "Faculty"});

        formPanel.add(new JLabel("Member ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeBox);

        JButton addButton = new JButton("Register");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Type", "Max Limit"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

    }

}