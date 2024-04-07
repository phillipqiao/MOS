package persistence;

import model.Menu;
import model.Item;
import model.Customer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Menu from JSON data stored in file
// code for the JsonReader Class
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs a JsonReader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Menu from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Menu read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Menu from JSON object and returns it
    private Menu parseMenu(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Menu m = new Menu(name);
        addItems(m, jsonObject);
        addCustomers(m, jsonObject);
        return m;
    }

    // MODIFIES: m
    // EFFECTS: parses forSales (items) from JSON object and adds them to menu.
    private void addItems(Menu m, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(m, nextItem);
        }
    }

    // MODIFIES: m
    // EFFECTS: parses customers from JSON object and adds them to menu.
    private void addCustomers(Menu m, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Customers");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomer(m, nextCustomer);
        }
    }

    // MODIFIES: m
    // EFFECTS: parses an Item from JSON object and adds it to Menu
    private void addItem(Menu m, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        int stock = jsonObject.getInt("stock");
        Item item = new Item(name, price, stock);

        JSONArray jsonArray = jsonObject.getJSONArray("Customers");
        for (Object json : jsonArray) {
            String nextCustomer = (String) json;
            addCustomerToItem(item,nextCustomer);
        }

        m.addItem(item);
    }

    // MODIFIES: m
    // EFFECTS: parses a Customer from JSON object and adds it to Menu
    private void addCustomer(Menu m, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Customer customer = new Customer(name);

        JSONArray jsonArray = jsonObject.getJSONArray("purchases");
        for (Object json : jsonArray) {
            String nextItem = (String) json;
            addItemToCustomer(customer,nextItem);
        }

        m.addCustomer(customer);
    }

    // MODIFIES: item
    // EFFECTS: add customer to the item's customers list.
    private void addCustomerToItem(Item item, String customer) {
        Customer c = new Customer(customer);
        item.addCustomer(c);
    }

    // MODIFIES: customer
    // EFFECTS: add item to the customer's purchases list.
    private void addItemToCustomer(Customer customer, String item) {
        customer.addItem(item);
    }
}
