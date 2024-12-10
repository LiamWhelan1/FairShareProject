package apcsa.finalproject.fairshareproject;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import java.lang.Math;

public class BillSplitter {
    private double total;
    private HashMap<String, Double> items = new HashMap<>();
    // Item select: {Item1={User1=T/F, User2=T/F}, Item2={User1=T/F, User2=T/F}}
    private HashMap<String, HashMap<String, Boolean>> itemSelect = new HashMap<>();
    private HashMap<String, User> peopleList = new HashMap<>();
    private HashMap<String, Double> ratios = new HashMap<>();
    private String currency;

    public BillSplitter(double t, HashMap<String, User> people, String c) {
        total = t;
        peopleList = people;
        currency = c;
        for (String s: peopleList.keySet()) {
            ratios.put(s, 1./peopleList.size());
        }
    }

    public BillSplitter(double t, HashMap<String, User> people, HashMap<String, Double> r, String c) {
        total = t;
        peopleList = people;
        ratios = r;
        currency = c;
    }

    public BillSplitter(HashMap<String, Double> items, HashMap<String, User> people, HashMap<String, HashMap<String, Boolean>> itemSelect, String c) {
        this.items = items;
        peopleList = people;
        this.itemSelect = itemSelect;
        currency = c;
    }

    public double getTotal() {
        return total;
    }

    public HashMap<String, User> getPeople() {
        return peopleList;
    }

    public HashMap<String, Double> getRatios() {
        return ratios;
    }

    public void setTotal(double t) {
        total = t;
    }

    public void addPerson(User person) {
        peopleList.put(person.getName(), person);
        ratios.clear();
        for (String s: peopleList.keySet()) {
            ratios.put(s, 1.0/peopleList.size());
        }
    }

    public void setRatios(HashMap<String, Double> r) {
        ratios = r;
    }

    public void setItems(HashMap<String, Double> items) {
        this.items = items;
    }

    public void addItems(HashMap<String, Double> items) {
        for (String item: items.keySet()) {
            this.items.put(item, items.get(item));
        }
    }

    public HashMap<String, Double> splitTotal(String n) {
        HashMap<String, Double> owed = new HashMap<>();
        for (String s: peopleList.keySet()) {
            double due = ratios.get(s)*total;
            if (!peopleList.get(s).getCurrency().equals(currency)) {
                try {
                    due = CurrencyConverter.convertCurrency(due, currency, peopleList.get(s).getCurrency());
                } catch (IOException | JSONException e) {
                    System.out.println("Error fetching exchange rates: " + e.getMessage());
                }
            }
            due = Math.round(due*100.)/100.;
            owed.put(s, due);
            if (due != 0.)
                peopleList.get(s).addTransaction(n, due);
        }
        return owed;
    }
    // HashMap in form {Item1={User1=Due, User2=Due}, Item2={User1=Due, User2=Due}}
    public HashMap<String, HashMap<String, Double>> splitItems(String n) {
        HashMap<String, HashMap<String, Double>> owed = new HashMap<>();
        for (String item: itemSelect.keySet()) {
            int count = 0;
            for (String user: itemSelect.get(item).keySet()) {
                if (itemSelect.get(item).get(user)) {
                    count++;
                }
            }
            HashMap<String, Double> r = new HashMap<>();
            for (String user: itemSelect.get(item).keySet()) {
                if (itemSelect.get(item).get(user)) {
                    r.put(user, 1./count);
                } else {
                    r.put(user, 0.);
                }
            }
            setRatios(r);
            setTotal(items.get(item));
            owed.put(item, splitTotal(item));
        }
        owed.put(n, tallyTotals(owed));
        return owed;
    }

    private static HashMap<String, Double> tallyTotals(HashMap<String, HashMap<String, Double>> owed) {
        HashMap<String, Double> totals = new HashMap<>();
        for (HashMap<String, Double> item: owed.values()) {
            for (String u: item.keySet()) {
                if (!totals.containsKey(u)) {
                    totals.put(u, item.get(u));
                } else {
                    totals.put(u, totals.get(u)+item.get(u));
                }
            }
        }
        return totals;
    }
}
