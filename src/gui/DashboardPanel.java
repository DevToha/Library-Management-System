package gui;

import model.Book;
import model.DigitalMedia;
import model.Item;
import model.Periodical;
import service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private LibraryService service;

    private static final Color CARD_BORDER = new Color(218, 225, 236);
    private static final Color TITLE_COLOR = new Color(37, 49, 89);
    private static final Color LABEL_COLOR = new Color(110, 120, 140);

    public DashboardPanel(LibraryService service) {
        this.service = service;

        setBackground(new Color(243, 245, 249));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(TITLE_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        int books = 0;
        int periodicals = 0;
        int digital = 0;
        int issued = 0;

        for (Item item : service.getAllItems()) {
            if (item instanceof Book) {
                books++;
            } else if (item instanceof Periodical) {
                periodicals++;
            } else if (item instanceof DigitalMedia) {
                digital++;
            }

            if (!item.isAvailable()) {
                issued++;
            }
        }

        int total = books + periodicals + + digital;
        int available = total - issued;

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 16));
        grid.setOpaque(false);

        grid.add(infoCard("Books", String.valueOf(books), new Color(44, 102, 191)));
        grid.add(infoCard("Periodicals", String.valueOf(periodicals), new Color(40, 145, 80)));
        grid.add(infoCard("Digital Media", String.valueOf(digital), new Color(128, 90, 210)));
        grid.add(infoCard("Total Members", String.valueOf(service.getAllMembers().size()), new Color(20, 150, 160)));
        grid.add(infoCard("Issued", String.valueOf(issued), new Color(200, 70, 70)));
        grid.add(infoCard("Available", String.valueOf(available), new Color(240, 150, 40)));

        centerPanel.add(grid, BorderLayout.NORTH);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel infoCard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(CARD_BORDER, 1),
                        BorderFactory.createEmptyBorder(22, 20, 22, 20)
                )
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(LABEL_COLOR);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 34));
        valueLabel.setForeground(TITLE_COLOR);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}