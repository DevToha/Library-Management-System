# Topic: - LIBRARY MANAGEMENT SYSTEM

Group Member ID |


## Project Overview

The Library Management System (LMS) is a Java desktop application designed to streamline the process of cataloging, borrowing, and tracking library resources within a university or institutional centralizes all library operations, allowing users to:

- Add, update, and delete library items (Books, Periodicals, Digital Media) via a and manage library members.
- Issue and return items with automatic availability tracking and borrow limits.
- View a real-time dashboard summary and generate reports.
- file-based record of all items, members, and transactions.

## Project Objectives

- Create a centralized system for managing library resources and members.
  -OP principles (Encapsulation, Abstraction, Inheritance, Polymorphism).
- Provide a smooth graphical user interface using Java Swing.
- Design a secure and validates user input and manages errors gracefully.
- Ensure librarians have full control over stored records, issue/return workflows, and reporting.
- Maintain data persistence using records survive across application restarts.

## System Theory

In most institutions, managing library resources is handled manually through registers or spreadsheets. This leads to mismatched borrowing records, and difficulty tracking overdue items. LMS overcomes these issues through:

- **Object-Oriented Architecture:** clear class relationships, Member, Librarian, Transaction).
- **Structured Data Flow:** from user input → validation → file storage → dashboard reporting.
- **Encapsulated Modules a defined responsibility to reduce coupling.

| PRINCIPLES | IMPLEMENTATION |
|------------|----------------|
| Encapsulation | Private fields with getters/set Overridden `getType()` and `calculateLateFee()` in each item subclass; overridden `getRole()` in `Member` and `Librarian` |

## Methodology

### Requirement Analysis

Understand the problem of library resource tracking within the university. Define user roles: Librarian and Member. Specify functional requirements (catalog items, register members, issue, return, report).

### System Design

- **Frontend:** Built using Java Swing with a sidebar navigation (`CardLayout`) separating the main functions.
- **Backend Service:** `LibraryService` handles data operations, validation, and file persistence.
- **Exception Handling:** Custom exceptions (`DuplicateException`, `InvalidException`, `NotAvailableException`, `NotFoundException`) ensure robust error reporting.

### Object Model

| CLASS | DESCRIPTION |
|-------|-------------|
| App | The main driver class that launches the application and sets the look-and-feel. |
| Item (abstract) | Base class defining shared item fields (id, title, category, available). |
| Book | Extends Item with `author` and `isbn`. |
| Periodical | Extends Item with `publisher` and `issueNumber`. |
| DigitalMedia | Extends Item with `format` and `fileSize`. |
| Person (abstract) | Base class defining shared person fields (id, name, email, phone). |
| Member | Inherits from Person with a `borrowCount` for tracking active loans. |
| Librarian | Inherits from Person with a `salary` field and librarian role. |
| Transaction | Records each issue/return event (member, item, dates, status). |
| LibraryService | Handles all data operations, input validation, and file persistence. |
| MainFrame | Main window with sidebar navigation and CardLayout content switching. |
| DashboardPanel | Displays summary cards with total counts of items, members, and transactions. |
| BookPanel | Manage the book catalog (add / update / delete / clear). |
| PeriodicalPanel | Manage the periodical catalog. |
| DigitalMediaPanel | Manage the digital media catalog. |
| MemberPanel | Register and manage library members. |
| IssueReturnPanel | Issue items to members and process returns with validation. |
| ReportPanel | View and export library reports. |
| DuplicateException | Thrown when adding an item or member with an existing ID. |
| InvalidException | Thrown when form fields are empty or invalid. |
| NotAvailableException | Thrown when an item is already borrowed or borrow limit is reached. |
| NotFoundException | Thrown when a searched item or member ID does not exist. |

### Implementation

Developed in Java SE using OOP design. Uses try-catch blocks and custom exceptions for input safety. GUI forms mapped to backend services ensuring smooth communication. Data is stored in comma-delimited text files inside a `data/` folder, loaded on startup and rewritten on every change.

### Testing & Validation

GUI event functions validated for both success and error scenarios. Issue/return workflow tested with edge cases (duplicate items, borrowing unavailable items, exceeding the borrow limit of 3, returning non-borrowed items).

## Functional Modules

- **Dashboard** — summary cards showing total books, periodicals, digital media, members, issued items, and available items.
- **Books** — add, update, delete, and clear book records.
- **Periodicals** — add, update, delete, and clear periodical records.
- **Digital Media** — add, update, delete, and clear digital media records.
- **Members** — register, update, and delete library members.
- **Issue / Return** — issue items to members and process returns with availability and borrow-limit checks.
- **Reports** — view and export a summary report of all library activity.

## Exception Handling

Custom exceptions ensure data validity and user feedback:

- `DuplicateException` → when adding an item or member with an ID that already exists.
- `InvalidException` → when form fields are empty or contain invalid values.
- `NotAvailableException` → when an item is already borrowed or a member has reached the borrow limit of 3.
- `NotFoundException` → when a searched item or member ID does not exist.

Friendly user-facing messages shown via `JOptionPane` dialogs.

## Data Persistence

The application stores data in plain text files inside the `data/` folder (created automatically on first run):

- `items.txt` — books, periodicals, and digital media.
- `members.txt` — library members.
- `transactions.txt` — issue and return records.
- `activity_log.txt` — timestamped activity log.
- `report.txt` — exported report (generated from the Reports panel).

Sample data is included so the application opens with books, members, and a transaction already loaded.

## Requirements

- JDK 11 or higher
- IntelliJ IDEA Community or Ultimate edition (recommended)

## How to Run in IntelliJ IDEA

1. Extract `LibraryManagementSystem.zip`.
2. Open IntelliJ IDEA.
3. Click **File → Open** and select the `LibraryManagementSystem` folder.
4. Wait for IntelliJ to load the project.
5. If IntelliJ asks for a JDK, select any JDK 11 or higher installed on your machine.
6. Click the green arrow next to `App` at the top toolbar, or go to **Run → Run 'App'**.

The `.idea/` folder and run configuration are already included, so the project should run immediately after selecting a JDK.

## How to Run from Command Line

Open a terminal inside the `LibraryManagementSystem` folder:

```bash
javac -d out -cp src $(find src -name "*.java")
java -cp out main.App
```

## How to Run Tests

In IntelliJ: right-click `src/main/TestHarness.java` and select **Run 'TestHarness.main()'**.

Or from command line:

```bash
java -cp out main.TestHarness
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
|       |-- TestHarness.java   
|       |-- ScreenshotCapture.java
|
|-- data/                      
|   |-- items.txt
|   |-- members.txt
|   |-- transactions.txt
|   |-- activity_log.txt
|   |-- report.txt
|
|-- report/                    
|-- screenshots/               
|-- README.md
```

## Conclusion

The Library Management System successfully demonstrates all essential OOP and Java programming principles while solving a real-world problem in a university library. It provides an organized, scalable, and user-centered solution for managing library resources, members, and borrowing transactions efficiently.

This project serves as a model for structured software design, effectively integrating object-oriented programming with GUI development and robust exception management.

## Future Enhancements

- Integration with MySQL or SQLite for persistent database storage.
- Add email notifications for due dates and overdue items.
- Implement image upload for book covers and item verification.
- Extend the system to a web or mobile platform for cross-device accessibility.
- Add a member login portal for self-service browsing and borrowing history.