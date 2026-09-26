package gui;

import service.LibraryService;
import model.Member;
import exception.DuplicateException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberPanel extends JPanel {
    private LibraryService service;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, emailField;
    private JComboBox<String> typeBox;

    public MemberPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Register Member"));

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        typeBox = new JComboBox<>(new String[]{"Student", "Faculty"});

        formPanel.add(new JLabel("Member ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeBox);

        JButton addButton = new JButton("Register");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Type", "Max Limit"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                Member m = new Member(idField.getText(), nameField.getText(), emailField.getText(), (String) typeBox.getSelectedItem());
                service.addMember(m);
                refreshTable();
                clearFields();
            } catch (DuplicateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Member m : service.getAllMembers()) {
            tableModel.addRow(new Object[]{m.getId(), m.getName(), m.getEmail(), m.getMemberType(), m.getMaxAllowedItems()});
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
    }
}