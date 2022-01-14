package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Person extends Contact implements Serializable {

    private String surname;
    private String birthDate;
    private String gender;

    Person() {}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        setLastEditTime(LocalDateTime.now());
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        try {
            this.birthDate = this.getFormattedDate(birthDate);
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            this.birthDate = "[no data]";
        }
        setLastEditTime(LocalDateTime.now());
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        boolean isMatch = gender.matches("[MF]");
        if (isMatch) {
            this.gender = gender;
        } else {
            System.out.println("Bad gender!");
            this.gender = "[no data]";
        }
        setLastEditTime(LocalDateTime.now());
    }

    public String[] getAllFields() {
        return new String[] {"name", "surname", "birth", "gender", "number"};
    }

    public void setField(String field, String value) {
        switch (field) {
            case "name":
                this.setName(value);
                System.out.println("The record updated!");
                break;
            case "surname":
                this.setSurname(value);
                System.out.println("The record updated!");
                break;
            case "birth":
                this.setBirthDate(value);
                System.out.println("The record updated!");
                break;
            case "gender":
                this.setGender(value);
                System.out.println("The record updated!");
                break;
            case "number":
                this.setPhoneNumber(value);
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("There is no such field!");
        }
    }

    public String getValue(String field) {
        String value = "";
        switch (field) {
            case "name":
                value = this.getName();
                break;
            case "surname":
                value = this.getSurname();
                break;
            case "birth":
                value = this.getBirthDate();
                break;
            case "gender":
                value = this.getGender();
                break;
            case "number":
                value = this.getPhoneNumber();
                break;
            default:
                System.out.println("There is no such field!");
        }
        return value;
    }

    public String toShortString() {
        return this.getName() + " " + this.getSurname();
    }

    public String toString() {
        return "Name: " + this.getName() + "\n"
            + "Surname: " + this.getSurname() + "\n"
            + "Birth date: " + this.getBirthDate() + "\n"
            + "Gender: " + this.getGender() + "\n"
            + "Number: " + this.getPhoneNumber() + "\n"
            + "Time created: " + this.getCreatedTime().truncatedTo(ChronoUnit.MINUTES) + "\n"
            + "Time last edit: " + this.getLastEditTime().truncatedTo(ChronoUnit.MINUTES);
    }

    public boolean matchesSearchCondition(String condition) {
        return (this.getName().toLowerCase().contains(condition) || this.getName().toLowerCase().matches(condition)) ||
                (this.getSurname().toLowerCase().contains(condition) || this.getSurname().toLowerCase().matches(condition)) ||
                (this.getPhoneNumber().toLowerCase().contains(condition) || this.getPhoneNumber().toLowerCase().matches(condition));
    }
}
