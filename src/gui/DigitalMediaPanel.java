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
