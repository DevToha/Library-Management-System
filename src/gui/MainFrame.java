package gui;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private LibraryService service;
    private JButton activeButton;
    private Map<String, JButton> menuButtons = new HashMap<String, JButton>();

    private static final Color SIDEBAR_BG = new Color(37, 49, 89);
    private static final Color SIDEBAR_ACTIVE = new Color(64, 81, 138);
    private static final Color SIDEBAR_ITEM_BG = new Color(47, 61, 107);
    private static final Color SIDEBAR_ITEM_BORDER = new Color(70, 85, 140);
    private static final Color CONTENT_BG = new Color(243, 245, 249);

    public MainFrame() {
        setTitle("Library Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new LibraryService();

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(CONTENT_BG);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        contentPanel.add(new DashboardPanel(service), "dashboard");
        contentPanel.add(new BookPanel(service), "books");
        contentPanel.add(new PeriodicalPanel(service), "periodicals");
        contentPanel.add(new DigitalMediaPanel(service), "digital");
        contentPanel.add(new MemberPanel(service), "members");
        contentPanel.add(new IssueReturnPanel(service), "issue");
        contentPanel.add(new ReportPanel(service), "reports");

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        add(root);
        setVisible(true);
    }

    private JPanel createMainArea() {
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(CONTENT_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 225, 236)),
                BorderFactory.createEmptyBorder(16, 28, 16, 28)
        ));

        JLabel headerTitle = new JLabel("Library Management System");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerTitle.setForeground(new Color(37, 49, 89));

        header.add(headerTitle, BorderLayout.WEST);

        mainArea.add(header, BorderLayout.NORTH);
        mainArea.add(contentPanel, BorderLayout.CENTER);

        return mainArea;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 18, 25, 18));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(29, 40, 75));
        titleBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 85, 140), 1),
                BorderFactory.createEmptyBorder(16, 14, 16, 14)
        ));
        titleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.setMaximumSize(new Dimension(214, 80));

        JLabel logo = new JLabel("Library");
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.add(logo);

        JLabel logo2 = new JLabel("Management System");
        logo2.setFont(new Font("Arial", Font.BOLD, 15));
        logo2.setForeground(new Color(200, 210, 240));
        logo2.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleBox.add(logo2);

        sidebar.add(titleBox);
        sidebar.add(Box.createVerticalStrut(25));

        sidebar.add(menuButton("Dashboard", "dashboard", true));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Books", "books", false));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Periodicals", "periodicals", false));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Digital Media", "digital", false));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Members", "members", false));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Issue / Return", "issue", false));
        sidebar.add(Box.createVerticalStrut(14));
        sidebar.add(menuButton("Reports", "reports", false));

        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JButton menuButton(String text, String page, boolean isActive) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(214, 60));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SIDEBAR_ITEM_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 18, 0, 0)
        ));

        if (isActive) {
            button.setBackground(SIDEBAR_ACTIVE);
            activeButton = button;
        } else {
            button.setBackground(SIDEBAR_ITEM_BG);
        }

        menuButtons.put(page, button);

        button.addActionListener(e -> {
            if (activeButton != null) {
                activeButton.setBackground(SIDEBAR_ITEM_BG);
            }
            activeButton = button;
            button.setBackground(SIDEBAR_ACTIVE);

            cardLayout.show(contentPanel, page);
        });

        return button;
    }

    public void showPage(String page) {
        for (JButton btn : menuButtons.values()) {
            btn.setBackground(SIDEBAR_ITEM_BG);
        }

        JButton btn = menuButtons.get(page);
        if (btn != null) {
            btn.setBackground(SIDEBAR_ACTIVE);
            activeButton = btn;
        }

        cardLayout.show(contentPanel, page);
    }
}