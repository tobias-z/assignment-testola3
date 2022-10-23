package dto;

public class Customer {
    private final int id;
    private final String firstname, lastname;
    private String phoneNumber;

    public Customer(int id, String firstname, String lastname, String phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastname() {
        return lastname;
    }
}
