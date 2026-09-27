# Library Management System

A Java Swing desktop application for managing a university or institutional library — cataloging items, registering members, and handling issue/return workflows with full data persistence.

---

## Overview

The Library Management System (LMS) centralizes day-to-day library operations into a single desktop app. Librarians can catalog resources, manage members, issue and return items with automatic availability tracking, and generate real-time reports — all backed by simple, human-readable file storage that survives application restarts.

## Objectives

- Centralize management of library resources, members, and transactions
- Apply core OOP principles — Encapsulation, Abstraction, Inheritance, and Polymorphism
- Deliver a clean, responsive GUI built with Java Swing
- Validate input and handle errors gracefully with custom exceptions
- Give librarians full control over records, workflows, and reporting
- Persist data reliably across sessions using file-based storage

## 🧩 Key Features

| Module | Description |
|---|---|
| **Dashboard** | Real-time summary cards for total books, periodicals, digital media, members, and issued/available items |
| **Books / Periodicals / Digital Media** | Add, update, delete, and clear catalog records for each item type |
| **Members** | Register, update, and remove library members |
| **Issue / Return** | Issue items with availability and borrow-limit checks; process returns |
| **Reports** | View and export a full summary of library activity |

## Architecture

Built around a clean object model with clear separation of concerns:

- **Frontend** — Java Swing, with sidebar navigation powered by `CardLayout`
- **Backend** — `LibraryService` handles data operations, validation, and file persistence
- **Exception Handling** — Custom exceptions (`DuplicateException`, `InvalidException`, `NotAvailableException`, `NotFoundException`) provide robust, user-friendly error reporting via `JOptionPane`

### Object Model

| Class | Description |
|---|---|
| `App` | Application entry point; launches the GUI and sets the look-and-feel |
| `Item` *(abstract)* | Base class for shared item fields (id, title, category, availability) |
| `Book`, `Periodical`, `DigitalMedia` | Item subtypes, each with type-specific fields |
| `Person` *(abstract)* | Base class for shared person fields (id, name, email, phone) |
| `Member`, `Librarian` | Person subtypes with role-specific fields (`borrowCount`, `salary`) |
| `Transaction` | Records each issue/return event with dates and status |
| `LibraryService` | Core service layer for data operations and persistence |
| `MainFrame` + panels | Swing GUI: dashboard, catalog panels, issue/return, and reports |

**OOP in practice:** each item subclass overrides `getType()` and `calculateLateFee()`; `Member` and `Librarian` override `getRole()` — demonstrating polymorphism throughout the object model.

## ⚠️ Exception Handling

| Exception | Triggered when… |
|---|---|
| `DuplicateException` | An item or member is added with an ID that already exists |
| `InvalidException` | A form field is empty or contains an invalid value |
| `NotAvailableException` | An item is already borrowed, or a member has reached the borrow limit of 3 |
| `NotFoundException` | A searched item or member ID does not exist |

## Data Persistence

Data is stored as plain, comma-delimited text files in the `data/` folder (created automatically on first run):

```
data/
├── items.txt          # Books, periodicals, and digital media
├── members.txt        # Library members
├── transactions.txt   # Issue and return records
├── activity_log.txt   # Timestamped activity log
└── report.txt          # Exported report from the Reports panel
```

Sample data is included, so the app launches with items, members, and transaction history already loaded.

## Getting Started

### Requirements

- JDK 11 or higher
- IntelliJ IDEA (Community or Ultimate) — recommended

### Run in IntelliJ IDEA

1. Extract/clone the project.
2. Open IntelliJ IDEA → **File → Open** → select the project folder.
3. Wait for IntelliJ to index the project.
4. Select a JDK 11+ installation if prompted.
5. Click the green run arrow next to `App`, or go to **Run → Run 'App'**.

### Run from the Command Line

```bash
javac -d out -cp src $(find src -name "*.java")
java -cp out main.App
```

## Project Structure

```
LibraryManagementSystem/
|
|-- src/
|   |-- model/                 
|   |   |-- Person.java        
|   |   |-- Member.java        
|   |   |-- Librarian.java     
|   |   |-- Item.java          
|   |   |-- Book.java          
|   |   |-- Periodical.java    
|   |   |-- DigitalMedia.java  
|   |   |-- Transaction.java   
|   |
|   |-- exception/             
|   |   |-- InvalidException.java
|   |   |-- DuplicateException.java
|   |   |-- NotFoundException.java
|   |   |-- NotAvailableException.java
|   |
|   |-- service/
|   |   |-- LibraryService.java  
|   |
|   |-- gui/                   
|   |   |-- MainFrame.java
|   |   |-- DashboardPanel.java
|   |   |-- BookPanel.java
|   |   |-- PeriodicalPanel.java
|   |   |-- DigitalMediaPanel.java
|   |   |-- MemberPanel.java
|   |   |-- IssueReturnPanel.java
|   |   |-- ReportPanel.java
|   |
|   |-- main/
|       |-- App.java           
|
|-- data/                      
|   |-- items.txt
|   |-- members.txt
|   |-- transactions.txt
|   |-- activity_log.txt
|   |-- report.txt
|
|-- README.md
```

## Future Enhancements

- Migrate storage to MySQL or SQLite for a persistent database backend
- Email notifications for due dates and overdue items
- Image upload for book covers and item verification
- Web or mobile companion platform
- Member self-service login portal for browsing and borrowing history

---

## 👥 Group Members

| SL | Student Name | ID |
|---|---|---|
| 1 | Tiash Khan Toha | 2024200000134 |
| 2 | Sudipto Halder Rahul | 2024200000593 |
| 3 | Arafat Rahman | 2024200000378 |

---

## 📝 Conclusion

This project demonstrates a complete, practical application of object-oriented design — clear class hierarchies, encapsulated services, and robust exception handling — combined with a functional Swing GUI and file-based persistence. It offers an organized, scalable foundation for managing library resources, members, and transactions.