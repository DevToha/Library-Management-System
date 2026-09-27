package gui;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private LibraryService service;
    private DashboardPanel dashboardPanel;
    private BookPanel bookPanel;
    private MemberPanel memberPanel;

    public MainFrame() {
        service = new LibraryService();
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        dashboardPanel = new DashboardPanel(service);
        bookPanel = new BookPanel(service);
        memberPanel = new MemberPanel(service);

        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);

        tabbedPane.addChangeListener(e -> {
            dashboardPanel.refresh();
            bookPanel.refreshTable();
            memberPanel.refreshTable();
        });

        add(tabbedPane, BorderLayout.CENTER);
    }
}