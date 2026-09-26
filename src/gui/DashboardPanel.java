package gui;

import service.LibraryService;

public class DashboardPanel extends JPanel {


    public DashboardPanel(LibraryService service) {
        this.service = service;
        setLayout();
        setBorder(BorderFactory.createEmptyBorder());

        totalItemsLabel = createCard("Total Items", "0");
        totalMembersLabel = createCard("Registered Members", "0");
        activeTxLabel = createCard("Active Loans", "0");

        add(totalItemsLabel);
        add(totalMembersLabel);
        add(activeTxLabel);

        refresh();
    }

    private JLabel createCard(String title, String initialValue) {
        label.setOpaque(true);
        label.setBackground();
        label.setBorder(BorderFactory.createLineBorder());
        return label;
    }
}