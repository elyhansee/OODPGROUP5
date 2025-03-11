package model;

public abstract class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String role; // "Customer", "Seller", or "Administrator"
    protected String contact;
    protected String address;
    protected boolean firstLogin = true;

    public User(String userID, String name, String email, String password, String role, String contact, String address) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.contact = contact;
        this.address = address;
    }

    // Getters and Setters

    public String getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public String getContact() {
        return contact;
    }
    public String getAddress() {
        return address;
    }
    public boolean isFirstLogin() {
        return firstLogin;
    }
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Each subclass may override a method to display their menu
    public abstract void displayMenu();
}
