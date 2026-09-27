package gui;

import exception.DuplicateException;
import exception.InvalidException;
import exception.NotFoundException;
import model.Book;
import model.Item;
import service.LibraryService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookPanel extends JPanel {

    private LibraryService service;

    private JTextField idField;
    private JTextField titleField;
    private JTextField categoryField;
    private JTextField authorField;
    private JTextField isbnField;

    private DefaultTableModel tableModel;
    private JTextField searchField;

    private static final Color HEADER_BG = new Color(37, 49, 89);
    private static final Color BORDER_COLOR = new Color(218, 225, 236);
    private static final Color FIELD_BORDER = new Color(205, 212, 224);

    public BookPanel(LibraryService service) {
        this.service = service;

        setBackground(new Color(243, 245, 249));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel title = new JLabel("Manage Books");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(HEADER_BG);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel mainPanel = new JPanel(new BorderLayout(18, 0));
        mainPanel.setOpaque(false);

        mainPanel.add(createFormPanel(), BorderLayout.WEST);
        mainPanel.add(createListPanel(), BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        loadItems();
    }

    private JPanel createFormPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(340, 0));
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                "Book Details",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                HEADER_BG
        ));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setOpaque(false);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        idField = createTextField();
        titleField = createTextField();
        categoryField = createTextField();
        authorField = createTextField();
        isbnField = createTextField();

        fieldsPanel.add(createField("Book ID", idField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("Title", titleField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("Category", categoryField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("Author", authorField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("ISBN", isbnField));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 16, 16, 16));

        JButton addButton = createButton("Add", new Color(40, 140, 80));
        JButton updateButton = createButton("Update", new Color(44, 102, 191));
        JButton deleteButton = createButton("Delete", new Color(200, 60, 60));
        JButton clearButton = createButton("Clear", new Color(120, 120, 120));

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        card.add(fieldsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createListPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                "Book List",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                HEADER_BG
        ));

        JPanel inner = new JPanel(new BorderLayout(0, 14));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton searchButton = createButton("Search", new Color(44, 102, 191));
        JButton showAllButton = createButton("Show All", new Color(120, 120, 120));

        searchButton.addActionListener(e -> searchItems());
        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadItems();
        });

        JPanel searchBar = new JPanel(new BorderLayout(8, 0));
        searchBar.setOpaque(false);
        searchBar.add(searchField, BorderLayout.CENTER);

        JPanel searchButtonPanel = new JPanel(new GridLayout(1, 2, 6, 0));
        searchButtonPanel.setOpaque(false);
        searchButtonPanel.add(searchButton);
        searchButtonPanel.add(showAllButton);
        searchBar.add(searchButtonPanel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Title", "Category", "Author", "ISBN", "Available"}, 0
        );

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(232, 236, 242));
        table.setSelectionBackground(new Color(220, 230, 246));

        JScrollPane tableScroll = new JScrollPane(table);
        table.setBackground(Color.WHITE);
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));

        inner.add(searchBar, BorderLayout.NORTH);
        inner.add(tableScroll, BorderLayout.CENTER);

        card.add(inner, BorderLayout.CENTER);

        return card;
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(70, 80, 100));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setPreferredSize(new Dimension(0, 36));
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        return field;
    }

    private JButton createButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void addBook() {
        try {
            Book book = new Book(
                    idField.getText(),
                    titleField.getText(),
                    categoryField.getText(),
                    authorField.getText(),
                    isbnField.getText()
            );

            service.addItem(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearFields();
            loadItems();

        } catch (InvalidException | DuplicateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateBook() {
        try {
            service.updateItem(idField.getText(), titleField.getText(), categoryField.getText());
            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            loadItems();
        } catch (NotFoundException | InvalidException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteBook() {
        try {
            service.deleteItem(idField.getText());
            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            clearFields();
            loadItems();
        } catch (NotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void searchItems() {
        String keyword = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);

        for (Item item : service.getAllItems()) {
            if (item instanceof Book && item.getTitle().toLowerCase().contains(keyword)) {
                Book book = (Book) item;
                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getTitle(),
                        book.getCategory(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.isAvailable() ? "Yes" : "No"
                });
            }
        }
    }

    private void loadItems() {
        tableModel.setRowCount(0);

        for (Item item : service.getAllItems()) {
            if (item instanceof Book) {
                Book book = (Book) item;
                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getTitle(),
                        book.getCategory(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.isAvailable() ? "Yes" : "No"
                });
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        categoryField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }
}