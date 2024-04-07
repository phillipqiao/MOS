package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Shop's menu having a name of the store,
// a list of all the items currently for sale, and list of the customers who has made a purchase
// in the store.
// code for the toJson() methods
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Menu implements Writable {

    private String storeName;
    private List<Item> forSales;
    private List<Customer> customers;

    // EFFECTS: Construct a Menu with the given name, an empty lists of Item,
    //          and an empty lists of Customer.
    public Menu(String name) {
        this.storeName = name;
        forSales = new ArrayList<>();
        customers = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds an Item in the forSales list if not already there,
    //          else do nothing.
    public void addItem(Item item) {
        if (!forSales.contains(item)) {
            forSales.add(item);
            EventLog.getInstance().logEvent(new Event("Item : " + item.getName() +  " added to the menu!"));
        }
    }

    // REQUIRES: forSales.contains(item);
    // MODIFIES: this
    // EFFECTS: Remove item from the forSales list.
    public void removeItem(Item item) {
        forSales.remove(item);
        EventLog.getInstance().logEvent(new Event("Item : " + item.getName() +  " removed from the menu!"));
    }

    // MODIFIES: this
    // EFFECTS: Adds a customer in the customers list if not already there,
    //          else do nothing.
    public void addCustomer(Customer c) {
        if (!customers.contains(c)) {
            customers.add(c);
        }
    }

    // REQUIRES: customers.contains(c);
    // MODIFIES: this
    // EFFECTS: Remove Customer c from the customers list.
    public void removeCustomer(Customer c) {
        customers.remove(c);
    }

    // EFFECTS: Given the item's name, returns true if the forSales List contains
    //          that particular item, else return false.
    public Boolean containsItem(String itemName) {
        for (Item i : forSales) {
            if (i.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Given the customer's name, returns true if the customers List contains
    //          that particular customer, else return false.
    public Boolean containsCustomer(String customerName) {
        for (Customer c : customers) {
            if (c.getName().equals(customerName)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: the item selecting must be in the forSales list.
    // EFFECTS: returns the item in the forSales list with name == itemName
    public Item selectItem(String itemName) {
        for (Item i : forSales) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: the customer selecting must be in the customers list.
    // EFFECTS: returns the customer in the customers list with name == customerName
    public Customer selectCustomer(String customerName) {
        for (Customer c : customers) {
            if (c.getName().equals(customerName)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: forSales.contains(item)
    // MODIFIES: this, item, c
    // EFFECTS: Add a Customer c to the customers list of this menu and to the customers list
    //          of the item. Finally, add that item  to the purchases list of Customer c.
    public void takingOrders(Item item, Customer c) {
        item.addCustomer(c);
        this.addCustomer(c);
        EventLog.getInstance().logEvent(new Event("Customer " + c.getName()
                +  " has ordered " + item.getName() + "!"));
    }

    // MODIFIES: this, item, c
    // EFFECTS: removes Customer c from the customers list of this menu and from the
    //          customers list for all items the customers has purchased. Finally, remove
    //          these item(s) from the purchases list of the Customer c.
    public void clearCustomer(Customer c) {
        customers.remove(c);
        for (Item item : forSales) {
            item.removeCustomer(c);
        }
        EventLog.getInstance().logEvent(new Event("Customer : " + c.getName()
                +  " has been checked out! "));
    }

    public String getStoreName() {
        return storeName;
    }

    public List<Item> getForSales() {
        return forSales;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    // EFFECTS: return this menu as a JSONObject.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", storeName);
        json.put("Items", forSalesToJson());
        json.put("Customers", customersToJson());
        return json;
    }

    // EFFECTS: returns forSales list in this menu as a JSON array
    private JSONArray forSalesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : forSales) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns customers list in this menu as a JSON array
    private JSONArray customersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : customers) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
