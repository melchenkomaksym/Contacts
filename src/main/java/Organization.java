import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Organization extends Contact implements Serializable {

    private String address;

    Organization() {}

    public Organization(String name, String phoneNumber, String address) {
        super(name, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        setLastEditTime(LocalDateTime.now());
    }

    public String[] getAllFields() {
        return new String[] {"name", "address", "number"};
    }

    public void setField(String field, String value) {
        switch (field) {
            case "name":
                this.setName(value);
                System.out.println("The record updated!");
                break;
            case "address":
                this.setAddress(value);
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
            case "address":
                value = this.getAddress();
                break;
            case "number":
                value = this.getPhoneNumber();
                break;
            default:
                System.out.println("There is no such field!");
        }
        return value;
    }

    @Override
    public String toString() {
        return "Organization name: " + this.getName() + "\n"
                + "Address: " + this.getAddress() + "\n"
                + "Number: " + this.getPhoneNumber() + "\n"
                + "Time created: " + this.getCreatedTime().truncatedTo(ChronoUnit.MINUTES) + "\n"
                + "Time last edit: " + this.getLastEditTime().truncatedTo(ChronoUnit.MINUTES);
    }

    public boolean matchesSearchCondition(String condition) {
        return (this.getName().toLowerCase().contains(condition) || this.getName().toLowerCase().matches(condition)) ||
                (this.getPhoneNumber().toLowerCase().contains(condition) || this.getPhoneNumber().toLowerCase().matches(condition));
    }

}
