import controller.ContactBook;
import entity.Organization;
import entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {

    ContactBook contactBook = new ContactBook();

    Person person1 = new Person("Andrew", "Drake", "111", "2000-01-10", "M");
    Person person2 = new Person("Drew", "Lama", "222", "1990-07-08", "F");

    Organization organization1 = new Organization("IBM", "555-55", "144 Morrison Street");
    Organization organization2 = new Organization("SpaceX", "888-88", "1 Rocket Road");

    @BeforeEach
    public void refreshContactBook() {
        contactBook.addContact(person1);
        contactBook.addContact(person2);
        contactBook.addContact(organization1);
        contactBook.addContact(organization2);
    }

    @Test
    public void addContactTest() {

        Person person = new Person("John", "Doe", "000", "1000-01-01", "M");
        contactBook.addContact(person);

        assertEquals(contactBook.size(), 5);
    }

    @Test
    public void editContactTest() {

        String updatedPersonName = "Andrey";
        String updateOrganizationPhone = "999-99";

        contactBook.editContact(person1, "name", updatedPersonName);
        contactBook.editContact(organization2, "number", updateOrganizationPhone);

        assertEquals(person1.getName(), updatedPersonName);
        assertEquals(organization2.getPhoneNumber(), updateOrganizationPhone);
    }

    @Test
    public void deleteContactTest() {

        contactBook.removeContact(person1);

        assertEquals(contactBook.size(), 3);
        assertEquals(contactBook.getSearchResults(person1.getName()).size(), 0);
    }

    @Test
    public void searchForContactTest() {

        ContactBook searchResults = contactBook.getSearchResults(person1.getName());
        assertEquals(searchResults.size(), 1);

        Person searchedContact = (Person) searchResults.getContact(0);
        assertEquals(searchedContact.getName(), person1.getName());
        assertEquals(searchedContact.getSurname(), person1.getSurname());
        assertEquals(searchedContact.getPhoneNumber(), person1.getPhoneNumber());

        searchResults = contactBook.getSearchResults("ew");
        assertEquals(searchResults.size(), 2);
    }

}
