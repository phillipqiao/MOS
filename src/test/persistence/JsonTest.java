package persistence;

import model.Item;
import model.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

// code for the JsonTest class
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkItem(String name, double price, int stock, Item item) {
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(stock, item.getStock());
    }

    protected void checkCustomer(String name, Customer c) {
        assertEquals(name, c.getName());
    }
}
