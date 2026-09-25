package gui;

import exception.DuplicateException;

public class BookPanel extends JPanel {

    public BookPanel(LibraryService service) {
        this.service = service;

        setOpaque(false);
        setLayout(new BorderLayout(0, 20));

        JLabel title = new JLabel("Manage Books");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel formCard = new MainFrame.GlassPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        String[] types = {"Book", "Periodical", "DigitalMedia"};
        typeBox = new JComboBox<>(types);
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        idField = createTextField();
        titleField = createTextField();
        categoryField = createTextField();
        extraField1 = createTextField();
        extraField2 = createTextField();

        formCard.add(createField("Type", typeBox));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(createField("Item ID", idField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(createField("Title", titleField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(createField("Category", categoryField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(createField("Author / Publisher / Format", extraField1));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(createField("ISBN / Issue No / File Size", extraField2));
        formCard.add(Box.createVerticalStrut(20));

        JButton addButton = createButton("Add Item");
        JButton updateButton = createButton("Update");
        JButton deleteButton = createButton("Delete");
        JButton clearButton = createButton("Clear");

        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        clearButton.addActionListener(e -> clearFields());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        formCard.add(buttonPanel);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Type", "Title", "Category", "Available"}, 0
        );

        JTable table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane tableScroll = new JScrollPane(table);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(0, 42));

        JButton searchButton = createButton("Search");
        searchButton.addActionListener(e -> searchItems());

        JPanel searchBar = new JPanel(new BorderLayout(12, 0));
        searchBar.setOpaque(false);
        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(searchButton, BorderLayout.EAST);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 15));
        tablePanel.setOpaque(false);
        tablePanel.add(searchBar, BorderLayout.NORTH);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(formCard, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        loadItems();
    }
}
