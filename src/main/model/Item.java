package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a merchandise item having a name, price,
// stock and List of String representing the customers who have purchased this item.
// code for the toJson() methods
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Item implements Writable {
    private String name;
    private double price;
    private int stock;
    private List<String> customers;

    // REQUIRES: price > 0.00
    // EFFECTS: Constructs an Item with the given name, price
    //          and stock. The item initially have an empty list
    //          of Customers who has purchased this item.
    public Item(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        customers = new ArrayList<>();
    }

    // MODIFIES: this, c
    // EFFECTS: Adds c.getName() in the customers list if it is not already there and add this item
    //          to the purchases list of Customer c, else do nothing. The order added in
    //          is not guaranteed.
    public void addCustomer(Customer c) {
        if (!customers.contains(c.getName())) {
            customers.add(c.getName());
            c.addItem(this);
        }
    }

    // MODIFIES: this, c
    // EFFECTS: Removes c.getName() in the customers list if it is there and remove this item
    //          from the purchases list of Customer c, else do nothing.
    public void removeCustomer(Customer c) {
        if (customers.contains(c.getName())) {
            customers.remove(c.getName());
            c.removeItem(this);
        }
    }

    // EFFECTS: Given the customer's name, returns true if the customers List contains
    //          that particular name, else return false.
    public Boolean containsCustomer(String customer) {
        for (String c : customers) {
            if (c.equals(customer)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getStock() {
        return stock;
    }

    public List<String> getCustomers() {
        return customers;
    }

    @Override
    // EFFECTS: return this item as a JSONObject.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("stock", stock);
        json.put("Customers", customersToJson());
        return json;
    }

    // EFFECTS: returns customers list of this item as a JSON array
    private JSONArray customersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s : customers) {
            jsonArray.put(s);
        }

        return jsonArray;
    }
}
