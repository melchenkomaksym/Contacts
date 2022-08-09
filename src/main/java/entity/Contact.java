package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class contains common fields and methods for Person and Organization.
 */

public abstract class Contact implements Serializable {

    private String name;
    private String phoneNumber = "";
    private final LocalDateTime createdTime;
    private LocalDateTime lastEditTime;

    Contact() {
        this.createdTime = LocalDateTime.now();
    }

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdTime = LocalDateTime.now();
        this.lastEditTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isPhoneNumberValid(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "[no number]";
            System.out.println("Wrong number format!");
        }
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(LocalDateTime lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String toShortString() {
        return this.getName();
    }

    public abstract String[] getAllFields();

    public abstract void setField(String field, String value);

    public abstract boolean matchesSearchCondition(String condition);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.name) && Objects.equals(phoneNumber, contact.phoneNumber) && Objects.equals(createdTime, contact.createdTime) && Objects.equals(lastEditTime, contact.lastEditTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, createdTime, lastEditTime);
    }

    protected String getFormattedDate(String localDateTime) {
        return LocalDate.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
    }

    /**
     * Method verifies that phone number matches the pattern.
     *
     * @param phoneNumber the phone number
     * @return verification result
     */

    private boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*");
        return pattern.matcher(phoneNumber).matches();
    }

}
