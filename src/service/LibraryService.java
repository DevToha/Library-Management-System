package service;

import model.*;
import exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryService {

    private final List<Item> itemList = new ArrayList<>();
    private final List<Member> memberList = new ArrayList<>();
    private final List<Transaction> transactionList = new ArrayList<>();

    public void addItem(Item newItem) throws DuplicateException {

        boolean exists = itemList.stream()
                .anyMatch(item ->
                        item.getId().equalsIgnoreCase(newItem.getId()));

        if (exists) {
            throw new DuplicateException(
                    "Item ID already exists: " + newItem.getId());
        }

        itemList.add(newItem);
    }

    public void addMember(Member newMember) throws DuplicateException {

        boolean exists = memberList.stream()
                .anyMatch(member ->
                        member.getId().equalsIgnoreCase(newMember.getId()));

        if (exists) {
            throw new DuplicateException(
                    "Member ID already exists: " + newMember.getId());
        }

        memberList.add(newMember);
    }

    public List<Item> getAllItems() {
        return itemList;
    }

    public List<Member> getAllMembers() {
        return memberList;
    }

    public List<Transaction> getAllTransactions() {
        return transactionList;
    }

    public void issueItem(String memberId, String itemId)
            throws NotFoundException, NotAvailableException {

        Member selectedMember = findMember(memberId);
        Item selectedItem = findItem(itemId);

        if (!selectedItem.isAvailable()) {
            throw new NotAvailableException(
                    "This item is not available right now.");
        }

        selectedItem.setAvailable(false);

        String transactionId =
                "TX-" + (transactionList.size() + 1);

        Transaction newTransaction = new Transaction(
                transactionId,
                selectedMember.getId(),
                selectedItem.getId(),
                new Date()
        );

        transactionList.add(newTransaction);
    }

    public void returnItem(String itemId) throws NotFoundException {

        Item selectedItem = findItem(itemId);

        Transaction activeTransaction = transactionList.stream()
                .filter(t -> t.getItemId().equalsIgnoreCase(itemId))
                .filter(t -> t.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException(
                                "No active transaction found."));

        selectedItem.setAvailable(true);
        activeTransaction.setReturnDate(new Date());
    }

    private Member findMember(String id) throws NotFoundException {

        return memberList.stream()
                .filter(member -> member.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Member not found."));
    }

    private Item findItem(String id) throws NotFoundException {

        return itemList.stream()
                .filter(item -> item.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Item not found."));
    }
}