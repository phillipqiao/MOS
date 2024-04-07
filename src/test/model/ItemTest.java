package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item testItem;
    private Customer c1;
    private Customer c2;
    private Customer c3;

    @BeforeEach
    public void runBefore() {
        testItem = new Item("Cheese Burger", 1.39, 3);
        c1 = new Customer ("Cat");
        c2 = new Customer ("Nick");
        c3 = new Customer ("David");
    }

    @Test
    public void testConstructor() {
        assertEquals("Cheese Burger", testItem.getName());
        assertEquals(1.39, testItem.getPrice());
        assertEquals(3, testItem.getStock());
        assertEquals(0, testItem.getCustomers().size());
    }

    @Test
    public void testAddCustomerOnce() {
        testItem.addCustomer(c1);
        List<String> customers = testItem.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(c1.getName(), customers.get(0));
        List<String> orders = c1.getPurchases();
        assertEquals(1, orders.size());
        assertEquals(testItem.getName(), orders.get(0));
    }

    @Test
    public void testAddCustomerMultiple() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        List<String> customers = testItem.getCustomers();
        assertEquals(2, customers.size());
        assertEquals(c1.getName(), customers.get(0));
        assertEquals(c2.getName(), customers.get(1));
        List<String> orders = c1.getPurchases();
        assertEquals(1, orders.size());
        assertEquals(testItem.getName(), orders.get(0));
        List<String> orders2 = c2.getPurchases();
        assertEquals(1, orders2.size());
        assertEquals(testItem.getName(), orders2.get(0));
    }

    @Test
    public void testAddCustomerMultipleItem() {
        Item item2 = new Item("Ice Cream", 0.50, 4);
        testItem.addCustomer(c1);
        item2.addCustomer(c1);
        assertEquals(1, testItem.getCustomers().size());
        assertEquals(c1.getName(), testItem.getCustomers().get(0));
        assertEquals(1, item2.getCustomers().size());
        assertEquals(c1.getName(), item2.getCustomers().get(0));
        List<String> orders = c1.getPurchases();
        assertEquals(2, orders.size());
        assertEquals(testItem.getName(), orders.get(0));
        assertEquals(item2.getName(), orders.get(1));
    }

    @Test
    public void testRemoveCustomerOnce() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        testItem.removeCustomer(c1);
        List<String> customers = testItem.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(c2.getName(), customers.get(0));
    }

    @Test
    public void testRemoveCustomerMultiple() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        testItem.removeCustomer(c1);
        testItem.removeCustomer(c2);
        List<String> customers = testItem.getCustomers();
        assertEquals(0, customers.size());
    }

    @Test
    public void testContainsCustomerTrue() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        assertTrue(testItem.containsCustomer(c1.getName()));
        assertTrue(testItem.containsCustomer(c2.getName()));
    }

    @Test
    public void testContainsCustomerFalse() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        assertTrue(testItem.containsCustomer(c1.getName()));
        assertTrue(testItem.containsCustomer(c2.getName()));
        assertFalse(testItem.containsCustomer(c3.getName()));
    }

    @Test
    public void testToJsonBase() {
        JSONObject json = testItem.toJson();
        String itemName =  json.getString("name");
        double itemPrice =  json.getDouble("price");
        int itemStock =  json.getInt("stock");

        JSONArray iJsonArray = json.getJSONArray("Customers");
        assertEquals("Cheese Burger", itemName);
        assertEquals(1.39, itemPrice);
        assertEquals(3, itemStock);
        assertEquals(0, iJsonArray.length());
    }

    @Test
    public void testToJson() {
        testItem.addCustomer(c1);
        testItem.addCustomer(c2);
        JSONObject json = testItem.toJson();
        String itemName =  json.getString("name");
        double itemPrice =  json.getDouble("price");
        int itemStock =  json.getInt("stock");

        JSONArray iJsonArray = json.getJSONArray("Customers");
        assertEquals("Cheese Burger", itemName);
        assertEquals(1.39, itemPrice);
        assertEquals(3, itemStock);
        assertEquals(2, iJsonArray.length());
    }
}