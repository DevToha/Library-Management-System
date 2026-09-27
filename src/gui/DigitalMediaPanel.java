package gui;

import service.LibraryService;
import model.DigitalMedia;
import model.Item;
import exception.DuplicateException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DigitalMediaPanel extends JPanel {
    private LibraryService service;
    private DefaultTableModel tableModel;
    private JTextField idField, titleField, formatField, sizeField;

    public DigitalMediaPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Digital Media"));

        idField = new JTextField();
        titleField = new JTextField();
        formatField = new JTextField();
        sizeField = new JTextField();
        formPanel.add(new JLabel("Media ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Format (MP4/PDF/ISO):"));
        formPanel.add(formatField);
        formPanel.add(new JLabel("File Size (MB):"));
        formPanel.add(sizeField);

        JButton addButton = new JButton("Add Digital Media");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Format", "Size (MB)", "Status"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                double size = Double.parseDouble(sizeField.getText().trim());
                DigitalMedia dm = new DigitalMedia(idField.getText().trim(), titleField.getText().trim(), formatField.getText().trim(), size);
                service.addItem(dm);
                refreshTable();
                idField.setText(""); titleField.setText("");
                formatField.setText(""); sizeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric size in MB.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (DuplicateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshTable();
