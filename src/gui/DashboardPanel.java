package gui;

import service.LibraryService;

<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private LibraryService service;
    private JLabel totalItemsLabel;
    private JLabel totalMembersLabel;
    private JLabel activeTxLabel;

    public DashboardPanel(LibraryService service) {
        this.service = service;
        setLayout(new GridLayout(1, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
=======
public class DashboardPanel extends JPanel {


    public DashboardPanel(LibraryService service) {
        this.service = service;
        setLayout();
        setBorder(BorderFactory.createEmptyBorder());
>>>>>>> 5f849a3b675f3bde74d71c76ff11baab3d6ceff0

        totalItemsLabel = createCard("Total Items", "0");
        totalMembersLabel = createCard("Registered Members", "0");
        activeTxLabel = createCard("Active Loans", "0");

        add(totalItemsLabel);
        add(totalMembersLabel);
        add(activeTxLabel);

<<<<<<< HEAD
    }

    private JLabel createCard(String title, String initialValue) {
        JLabel label = new JLabel("<html><center><h3>" + title + "</h3><h1>" + initialValue + "</h1></center></html>", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(230, 235, 245));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }


=======
        refresh();
    }

    private JLabel createCard(String title, String initialValue) {
        label.setOpaque(true);
        label.setBackground();
        label.setBorder(BorderFactory.createLineBorder());
        return label;
    }
>>>>>>> 5f849a3b675f3bde74d71c76ff11baab3d6ceff0
}