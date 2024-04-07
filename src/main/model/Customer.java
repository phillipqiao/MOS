package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Customer with his/her name and a list of all the merchandise items
// this Customer has purchased.
// code for the toJson() methods
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Customer implements Writable {
    private String name;
    private List<String> purchases;

    // EFFECTS: Constructs a Customer with the given name
    //          and an empty list of purchases (items bought).
    public Customer(String name) {
        this.name = name;
        purchases = new ArrayList<>();
    }

    // REQUIRES: purchases does not contain item
    // MODIFIES: this
    // EFFECTS: Add the name of the item to the purchases list.
    public void addItem(Item item) {
        purchases.add(item.getName());
    }

    // REQUIRES: purchases does not contain item
    // MODIFIES: this
    // EFFECTS: Adds item to the purchases list.
    public void addItem(String item) {
        purchases.add(item);
    }

    // REQUIRES: purchases contains item
    // MODIFIES: this
    // REQUIRES: remove name of item from the purchases list.
    public void removeItem(Item item) {
        purchases.remove(item.getName());
    }

    public String getName() {
        return name;
    }

    public List<String> getPurchases() {
        return purchases;
    }

    @Override
    // EFFECTS: return this Customer as a JSONObject.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("purchases", purchasesToJson());
        return json;
    }

    // EFFECTS: returns purchases list of this Customer as a JSON array
    private JSONArray purchasesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s : purchases) {
            jsonArray.put(s);
        }

        return jsonArray;
    }
}
