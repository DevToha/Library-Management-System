package gui;

import service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private LibraryService service;
    private DashboardPanel dashboardPanel;


    public MainFrame() {
        service = new LibraryService();
        setTitle("Library Management System");

        JTabbedPane tabbedPane = new JTabbedPane();

        dashboardPanel = new DashboardPanel(service);
        bookPanel = new BookPanel(service);
        memberPanel = new MemberPanel(service);

        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Members", memberPanel);



        add(tabbedPane, BorderLayout.CENTER);
    }
}