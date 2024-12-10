package apcsa.finalproject.fairshareproject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private HashMap<String, Double> transactions = new HashMap<>();
    private String currency;
    private String name;
    private String email;
    private String password;
    private ArrayList<String> friends = new ArrayList<>();
    private final SimpleBooleanProperty included = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty specialRatio = new SimpleBooleanProperty(false);
    private final SimpleDoubleProperty portion = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty due = new SimpleDoubleProperty(0);

    public User(String c, String n) {
        currency = c;
        name = n;
    }

    public User(String c, String n, String e, String p) {
        currency = c;
        name = n;
        email = e;
        password = p;
    }

    public double getDue() {
        return due.get();
    }

    public void setDue(double due) {
        this.due.set(due);
    }

    public boolean hasSpecialRatio() {
        return specialRatio.get();
    }

    public void setSpecialRatio(boolean sr) {
        specialRatio.set(sr);
    }

    public boolean isIncluded() {
        return included.get();
    }

    public void setIncluded(boolean included) {
        this.included.set(included);
    }

    public SimpleDoubleProperty dueProperty() {
        return due;
    }

    public double getPortion() {
        return portion.get();
    }

    public void setPortion(double portion) {
        this.portion.set(portion);
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Double> getTransactions() {
        return transactions;
    }

    public String getCurrency() {
        return currency;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setName(String n) {
        name = n;
    }

    public void setCurrency(String c) {
        currency = c;
    }

    public void setEmail(String e) {
        email = e;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTransaction(String s, Double t) {
        transactions.put(s, t);
    }

    public void setTransactions(HashMap<String, Double> t) {
        transactions = t;
    }

    public void setFriends(ArrayList<String> f) {
        friends = f;
    }

    public void addFriends(String f) {
        friends.add(f);
    }
}
