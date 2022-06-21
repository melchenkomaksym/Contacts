import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static final String DEFAULT_FILE_NAME = "phoneBook.db";
    static Scanner scanner = new Scanner(System.in);
    static ContactBook phoneBook;

    public static void main(String[] args) {

        String fileName = args.length == 0 ? DEFAULT_FILE_NAME : args[0];

        phoneBook = readPhonebook(fileName);

        String action;
        do {
            action = askForAction();
            switch (action) {
                case "add":
                    System.out.println("[menu] Enter the type (person, organization):");
                    String type = scanner.nextLine().toLowerCase();
                    switch (type) {
                        case "person":
                            addPerson();
                            break;
                        case "organization":
                            addOrganization();
                            break;
                        default:
                            System.out.println("Wrong type!");
                    }
                    break;
                case "list":
                    list(phoneBook.getContacts());
                    System.out.println();

                    System.out.println("[list] Enter action ([number], back):");
                    String listItemIndex = scanner.nextLine();

                    if (isNumeric(listItemIndex)) {
                        modifyRecord(phoneBook, listItemIndex);
                    }

                    break;
                case "search":
                    String searchAction;
                    do {
                        System.out.println("Enter search query:");
                        String searchQuery = scanner.nextLine();
                        ContactBook searchResults = phoneBook.getSearchResults(searchQuery);
                        System.out.printf("Found %d results:%n", searchResults.size());
                        list(searchResults.getContacts());

                        System.out.println("[search] Enter action ([number], back, again):");
                        searchAction = scanner.nextLine();
                        if (isNumeric(searchAction)) {
                            modifyRecord(searchResults, searchAction);
                        }
                    } while (searchAction.equals("again"));
                    break;
                case "count":
                    phoneBook.count();
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Such option is not supported.");
            }
            System.out.println();
        } while (!action.equals("exit"));
        writePhonebook(phoneBook, fileName);
    }

    public static String askForAction() {
        System.out.println("[menu] Enter action (add, list, search, count, exit):");
        return scanner.nextLine().toLowerCase();
    }

    private static void addPerson() {
        Person person = new Person();
        System.out.println("Enter the name:");
        String name = scanner.nextLine();
        person.setName(name);
        System.out.println("Enter the surname:");
        String surname = scanner.nextLine();
        person.setSurname(surname);
        System.out.println("Enter the birth date:");
        String birthDate = scanner.nextLine();
        person.setBirthDate(birthDate);
        System.out.println("Enter the gender (M, F):");
        String gender = scanner.nextLine();
        person.setGender(gender);
        System.out.println("Enter the number:");
        String number = scanner.nextLine();
        person.setPhoneNumber(number);
        phoneBook.addContact(person);
    }

    private static void addOrganization() {
        Organization organization = new Organization();
        System.out.println("Enter the organization name:");
        String name = scanner.nextLine();
        organization.setName(name);
        System.out.println("Enter the address:");
        String address = scanner.nextLine();
        organization.setAddress(address);
        System.out.println("Enter the number:");
        String number = scanner.nextLine();
        organization.setPhoneNumber(number);
        phoneBook.addContact(organization);
    }

    private static ContactBook readPhonebook(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream = null;
        ContactBook phonebook = null;

        if (file.length() == 0) {
            return new ContactBook();
        } else {
            try {
                fileInputStream = new FileInputStream(fileName);
                objectInputStream = new ObjectInputStream(fileInputStream);
                phonebook = (ContactBook) objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Objects.requireNonNull(objectInputStream).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return phonebook;
        }
    }

    private static void writePhonebook(ContactBook phonebook, String fileName) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(phonebook);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(objectOutputStream).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isNumeric(String number) {
        if (number == null) {
            return false;
        }

        try {
            Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    private static void list(List<Contact> contacts) {
        int counter = 1;
        for (Contact contact : contacts) {
            System.out.printf("%d. %s%n", counter, contact.toShortString());
            counter++;
        }
    }

    private static void modifyRecord(ContactBook book, String countNumber) {
        Contact chosenContact = book.getContact(Integer.parseInt(countNumber) - 1);
        System.out.println(chosenContact.toString());
        System.out.println();

        System.out.println("[record] Enter action (edit, delete, menu):");
        String recordAction = scanner.nextLine();

        switch (recordAction.toLowerCase()) {
            case "edit":
                System.out.printf("Select a field (%s):%n",
                        Arrays.toString(chosenContact.getAllFields()));
                String field = scanner.nextLine();
                System.out.printf("Enter %s:%n", field);
                String value = scanner.nextLine();
                phoneBook.editContact(chosenContact, field, value);
                break;
            case "delete":
                phoneBook.removeContact(chosenContact);
                break;
        }
    }

}
