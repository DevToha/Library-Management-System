package gui;

import exception.*;
import model.Member;
import service.LibraryService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberPanel extends JPanel {

    private final LibraryService service;
    private final JTextField idField = createTextField(), nameField = createTextField(),
            emailField = createTextField(), phoneField = createTextField(),
            searchField = createTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Email", "Phone", "Borrowed"}, 0);

    private static final Color HEADER_BG = new Color(37, 49, 89);
    private static final Color BORDER_COLOR = new Color(218, 225, 236);
    private static final Color FIELD_BORDER = new Color(205, 212, 224);
    private static final Font FONT_BOLD_14 = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_PLAIN_13 = new Font("Arial", Font.PLAIN, 13);

    public MemberPanel(LibraryService service) {
        this.service = service;
        setBackground(new Color(243, 245, 249));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel title = new JLabel("Manage Members");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(HEADER_BG);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel mainPanel = new JPanel(new BorderLayout(18, 0));
        mainPanel.setOpaque(false);
        mainPanel.add(createFormPanel(), BorderLayout.WEST);
        mainPanel.add(createListPanel(), BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        loadMembers();
    }

    private JPanel createFormPanel() {
        JPanel card = createCard("Member Details");
        card.setPreferredSize(new Dimension(340, 0));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setOpaque(false);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JTextField[] fields = {idField, nameField, emailField, phoneField};
        String[] labels = {"Member ID", "Name", "Email", "Phone"};

        for (int i = 0; i < fields.length; i++) {
            if (i > 0) fieldsPanel.add(Box.createVerticalStrut(14));
            fieldsPanel.add(createField(labels[i], fields[i]));
        }

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 16, 16, 16));

        buttonPanel.add(createButton("Add", new Color(40, 140, 80), e -> addMember()));
        buttonPanel.add(createButton("Update", new Color(44, 102, 191), e -> updateMember()));
        buttonPanel.add(createButton("Delete", new Color(200, 60, 60), e -> deleteMember()));
        buttonPanel.add(createButton("Clear", new Color(120, 120, 120), e -> clearFields()));

        card.add(fieldsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createListPanel() {
        JPanel card = createCard("Member List");

        JPanel inner = new JPanel(new BorderLayout(0, 14));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));

        JPanel searchBtnPanel = new JPanel(new GridLayout(1, 2, 6, 0));
        searchBtnPanel.setOpaque(false);
        searchBtnPanel.add(createButton("Search", new Color(44, 102, 191), e -> searchMembers()));
        searchBtnPanel.add(createButton("Show All", new Color(120, 120, 120), e -> {
            searchField.setText("");
            loadMembers();
        }));

        JPanel searchBar = new JPanel(new BorderLayout(8, 0));
        searchBar.setOpaque(false);
        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(searchBtnPanel, BorderLayout.EAST);

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(FONT_PLAIN_13);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(232, 236, 242));
        table.setSelectionBackground(new Color(220, 230, 246));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));

        inner.add(searchBar, BorderLayout.NORTH);
        inner.add(tableScroll, BorderLayout.CENTER);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    private JPanel createCard(String title) {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, FONT_BOLD_14, HEADER_BG
        ));
        return card;
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));

        JLabel label = new JLabel(labelText);
        label.setFont(FONT_PLAIN_13);
        label.setForeground(new Color(70, 80, 100));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setPreferredSize(new Dimension(0, 36));
        field.setFont(FONT_PLAIN_13);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    private JButton createButton(String text, Color bg, ActionListener action) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }

    private void addMember() {
        executeAction(() -> {
            Member member = new Member(idField.getText(), nameField.getText(),
                    emailField.getText(), phoneField.getText());
            service.addMember(member);
            JOptionPane.showMessageDialog(this, "Member added successfully!");
            clearFields();
        });
    }

    private void updateMember() {
        executeAction(() -> {
            service.updateMember(idField.getText(), nameField.getText(),
                    emailField.getText(), phoneField.getText());
            JOptionPane.showMessageDialog(this, "Member updated successfully!");
        });
    }

    private void deleteMember() {
        executeAction(() -> {
            service.deleteMember(idField.getText());
            JOptionPane.showMessageDialog(this, "Member deleted successfully!");
            clearFields();
        });
    }

    private void executeAction(ActionRunnable action) {
        try {
            action.run();
            loadMembers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void searchMembers() {
        fillTable(searchField.getText().toLowerCase());
    }

    private void loadMembers() {
        fillTable(null);
    }

    private void fillTable(String keyword) {
        tableModel.setRowCount(0);
        for (Member m : service.getAllMembers()) {
            if (keyword == null || m.getName().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{m.getId(), m.getName(), m.getEmail(), m.getPhone(), m.getBorrowCount()});
            }
        }
    }

    private void clearFields() {
        for (JTextField field : new JTextField[]{idField, nameField, emailField, phoneField}) {
            field.setText("");
        }
    }

    @FunctionalInterface
    private interface ActionRunnable {
        void run() throws Exception;
    }
}