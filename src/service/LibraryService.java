package service;

import exception.DuplicateException;
import exception.InvalidException;
import exception.NotAvailableException;
import exception.NotFoundException;
import model.Book;
import model.DigitalMedia;
import model.Item;
import model.Member;
import model.Periodical;
import model.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LibraryService {

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    private final String ITEM_FILE = "data/items.txt";
    private final String MEMBER_FILE = "data/members.txt";
    private final String TRANSACTION_FILE = "data/transactions.txt";
    private final String LOG_FILE = "data/activity_log.txt";

    public LibraryService() {
        createDataFolder();
        loadItems();
        loadMembers();
        loadTransactions();
    }

    private void createDataFolder() {
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void logActivity(String action) {
        try {
            FileWriter writer = new FileWriter(LOG_FILE, true);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("[" + time + "] " + action + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }

    public void addItem(Item newItem) throws InvalidException, DuplicateException {

        if (newItem.getId() == null || newItem.getId().trim().isEmpty()) {
            throw new InvalidException("Item ID cannot be empty.");
        }

        if (newItem.getTitle() == null || newItem.getTitle().trim().isEmpty()) {
            throw new InvalidException("Item title cannot be empty.");
        }

        for (Item item : items) {
            if (item.getId().equals(newItem.getId())) {
                throw new DuplicateException("Item ID already exists: " + newItem.getId());
            }
        }

        items.add(newItem);
        saveItems();
        logActivity("Added Item: " + newItem.getId() + " - " + newItem.getTitle());
    }

    public ArrayList<Item> getAllItems() {
        return items;
    }

    public Item findItemById(String id) throws NotFoundException {

        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        throw new NotFoundException("Item not found with ID: " + id);
    }

    public void updateItem(String id, String title, String category)
            throws NotFoundException, InvalidException {

        Item item = findItemById(id);

        if (title == null || title.trim().isEmpty()) {
            throw new InvalidException("Title cannot be empty.");
        }

        item.setTitle(title);
        item.setCategory(category);
        saveItems();
        logActivity("Updated Item: " + id);
    }

    public boolean deleteItem(String id) throws NotFoundException {

        Item item = findItemById(id);
        items.remove(item);
        saveItems();
        logActivity("Deleted Item: " + id);
        return true;
    }

    public void addMember(Member newMember) throws InvalidException, DuplicateException {

        if (newMember.getId() == null || newMember.getId().trim().isEmpty()) {
            throw new InvalidException("Member ID cannot be empty.");
        }

        if (newMember.getName() == null || newMember.getName().trim().isEmpty()) {
            throw new InvalidException("Member name cannot be empty.");
        }

        if (newMember.getEmail() == null || !newMember.getEmail().contains("@")) {
            throw new InvalidException("Email is not valid.");
        }

        for (Member member : members) {
            if (member.getId().equals(newMember.getId())) {
                throw new DuplicateException("Member ID already exists: " + newMember.getId());
            }
        }

        members.add(newMember);
        saveMembers();
        logActivity("Added Member: " + newMember.getId() + " - " + newMember.getName());
    }

    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public Member findMemberById(String id) throws NotFoundException {

        for (Member member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }

        throw new NotFoundException("Member not found with ID: " + id);
    }

    public void updateMember(String id, String name, String email, String phone)
            throws NotFoundException, InvalidException {

        Member member = findMemberById(id);

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidException("Name cannot be empty.");
        }

        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);
        saveMembers();
        logActivity("Updated Member: " + id);
    }

    public boolean deleteMember(String id) throws NotFoundException {

        Member member = findMemberById(id);
        members.remove(member);
        saveMembers();
        logActivity("Deleted Member: " + id);
        return true;
    }

    public void issueItem(String itemId, String memberId)
            throws NotFoundException, NotAvailableException {

        Item item = findItemById(itemId);
        Member member = findMemberById(memberId);

        if (!item.isAvailable()) {
            throw new NotAvailableException("Item is already borrowed.");
        }

        if (member.getBorrowCount() >= 3) {
            throw new NotAvailableException("Member has reached the borrow limit of 3.");
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String txnId = "T" + System.currentTimeMillis();

        Transaction txn = new Transaction(txnId, memberId, itemId, date, "-", "Issued");
        transactions.add(txn);

        item.setAvailable(false);
        member.setBorrowCount(member.getBorrowCount() + 1);

        saveItems();
        saveMembers();
        saveTransactions();
        logActivity("Issued Item: " + itemId + " to Member: " + memberId);
    }

    public void returnItem(String itemId)
            throws NotFoundException, NotAvailableException {

        Item item = findItemById(itemId);

        if (item.isAvailable()) {
            throw new NotAvailableException("This item is not currently borrowed.");
        }

        Transaction txn = null;
        for (Transaction t : transactions) {
            if (t.getItemId().equals(itemId) && t.getStatus().equals("Issued")) {
                txn = t;
                break;
            }
        }

        if (txn == null) {
            throw new NotFoundException("No active transaction found for this item.");
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txn.setReturnDate(date);
        txn.setStatus("Returned");

        item.setAvailable(true);

        for (Member member : members) {
            if (member.getId().equals(txn.getMemberId())) {
                member.setBorrowCount(member.getBorrowCount() - 1);
                break;
            }
        }

        saveItems();
        saveMembers();
        saveTransactions();
        logActivity("Returned Item: " + itemId);
    }

    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    public ArrayList<Item> searchItems(String keyword) {

        ArrayList<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(item);
            }
        }

        return result;
    }

    private void saveItems() {
        try {
            FileWriter writer = new FileWriter(ITEM_FILE);

            for (Item item : items) {
                if (item instanceof Book) {
                    Book book = (Book) item;
                    writer.write("Book," + book.getId() + "," + book.getTitle() + ","
                            + book.getCategory() + "," + book.getAuthor() + "," + book.getIsbn()
                            + "," + book.isAvailable() + "\n");
                } else if (item instanceof Periodical) {
                    Periodical p = (Periodical) item;
                    writer.write("Periodical," + p.getId() + "," + p.getTitle() + ","
                            + p.getCategory() + "," + p.getPublisher() + "," + p.getIssueNumber()
                            + "," + p.isAvailable() + "\n");
                } else if (item instanceof DigitalMedia) {
                    DigitalMedia d = (DigitalMedia) item;
                    writer.write("DigitalMedia," + d.getId() + "," + d.getTitle() + ","
                            + d.getCategory() + "," + d.getFormat() + "," + d.getFileSize()
                            + "," + d.isAvailable() + "\n");
                }
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving items: " + e.getMessage());
        }
    }

    private void loadItems() {
        File file = new File(ITEM_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ITEM_FILE));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 2) {
                    continue;
                }

                if (data[0].equals("Book") && data.length == 7) {
                    Book book = new Book(data[1], data[2], data[3], data[4], data[5]);
                    book.setAvailable(Boolean.parseBoolean(data[6]));
                    items.add(book);
                } else if (data[0].equals("Periodical") && data.length == 7) {
                    Periodical p = new Periodical(data[1], data[2], data[3], data[4], Integer.parseInt(data[5]));
                    p.setAvailable(Boolean.parseBoolean(data[6]));
                    items.add(p);
                } else if (data[0].equals("DigitalMedia") && data.length == 7) {
                    DigitalMedia d = new DigitalMedia(data[1], data[2], data[3], data[4], Double.parseDouble(data[5]));
                    d.setAvailable(Boolean.parseBoolean(data[6]));
                    items.add(d);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
    }

    private void saveMembers() {
        try {
            FileWriter writer = new FileWriter(MEMBER_FILE);

            for (Member member : members) {
                writer.write(member.getId() + "," + member.getName() + ","
                        + member.getEmail() + "," + member.getPhone() + ","
                        + member.getBorrowCount() + "\n");
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    private void loadMembers() {
        File file = new File(MEMBER_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(MEMBER_FILE));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 5) {
                    Member member = new Member(data[0], data[1], data[2], data[3]);
                    member.setBorrowCount(Integer.parseInt(data[4]));
                    members.add(member);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
    }

    private void saveTransactions() {
        try {
            FileWriter writer = new FileWriter(TRANSACTION_FILE);

            for (Transaction txn : transactions) {
                writer.write(txn.getTransactionId() + "," + txn.getMemberId() + ","
                        + txn.getItemId() + "," + txn.getIssueDate() + ","
                        + txn.getReturnDate() + "," + txn.getStatus() + "\n");
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 6) {
                    Transaction txn = new Transaction(data[0], data[1], data[2], data[3], data[4], data[5]);
                    transactions.add(txn);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
}