package gui;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private LibraryService service;
    private DashboardPanel dashboardPanel;
    private BookPanel bookPanel;
    private PeriodicalPanel periodicalPanel;
    private MemberPanel memberPanel;
    private IssueReturnPanel issueReturnPanel;

    public MainFrame() {
        service = new LibraryService();
        setTitle("Library Management System");
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        dashboardPanel = new DashboardPanel(service);
        bookPanel = new BookPanel(service);
        periodicalPanel = new PeriodicalPanel(service);
        memberPanel = new MemberPanel(service);
        issueReturnPanel = new IssueReturnPanel(service);

        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Periodicals", periodicalPanel);
        tabbedPane.addTab("Members", memberPanel);
        tabbedPane.addTab("Issue / Return", issueReturnPanel);

        tabbedPane.addChangeListener(e -> {
            dashboardPanel.refresh();
            bookPanel.refreshTable();
            periodicalPanel.refreshTable();
            memberPanel.refreshTable();
        });

        add(tabbedPane, BorderLayout.CENTER);
    }
}