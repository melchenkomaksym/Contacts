package contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class ContactBook implements Serializable {
    private final ArrayList<Contact> listOfContacts = new ArrayList<>();

    public ArrayList<Contact> getContacts() {
        return this.listOfContacts;
    }

    public int size() {
        return listOfContacts.size();
    }

    public void addContact(Contact contact) {
        listOfContacts.add(contact);
    }

    public void editContact(Contact contact, String field, String value) {
        for (Contact item : listOfContacts) {
            if (item.equals(contact)) {
                item.setField(field, value);
            }
        }
    }

    public void removeContact(Contact contact) {
        for (Contact item : listOfContacts) {
            if (item.equals(contact)) {
                listOfContacts.remove(contact);
            }
        }
    }

    public void count() {
        System.out.printf("The Phone Book has %d records.%n", listOfContacts.size());
    }

    public Contact getContact(int index) {
        return listOfContacts.get(index);
    }

    public ContactBook getSearchResults(String searchQuery) {
        ContactBook searchResults = new ContactBook();
        for (Contact contact : listOfContacts) {
            if (contact.matchesSearchCondition(searchQuery)) {
                searchResults.addContact(contact);
            }
        }
        return searchResults;
    }
}