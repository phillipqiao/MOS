package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu testMenu;
    private Item item1;
    private Item item2;
    private Item item3;
    private Customer c1;
    private Customer c2;
    private Customer c3;

    @BeforeEach
    public void runBefore() {
        testMenu =  new Menu("Tim");
        item1 =  new Item("Cheese Burger", 1.39, 3);
        item2 =  new Item("Fries", 0.99, 5);
        item3 =  new Item("Ice Cream", 0.50, 4);
        c1 = new Customer ("Cat");
        c2 = new Customer ("Nick");
        c3 = new Customer ("Phil");
    }

    @Test
    public void testConstructor() {
        assertEquals("Tim", testMenu.getStoreName());
        assertEquals(0, testMenu.getForSales().size());
        assertEquals(0, testMenu.getCustomers().size());
    }

    @Test
    public void testAddItemOnce() {
        testMenu.addItem(item1);
        List<Item> forSales = testMenu.getForSales();
        assertEquals(1, forSales.size());
        assertEquals(item1, forSales.get(0));
    }

    @Test
    public void testAddItemMultiple() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        List<Item> forSales = testMenu.getForSales();
        assertEquals(2, forSales.size());
        assertEquals(item1, forSales.get(0));
        assertEquals(item2, forSales.get(1));
    }

    @Test
    public void testAddItemDuplicate() {
        testMenu.addItem(item1);
        testMenu.addItem(item1);
        List<Item> forSales = testMenu.getForSales();
        assertEquals(1, forSales.size());
        assertEquals(item1, forSales.get(0));
    }

    @Test
    public void testRemoveOnce() {
        testMenu.addItem(item1);
        testMenu.removeItem(item1);
        List<Item> forSales = testMenu.getForSales();
        assertEquals(0, forSales.size());
    }

    @Test
    public void testRemoveMultiple() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        testMenu.addItem(item3);
        testMenu.removeItem(item2);
        testMenu.removeItem(item1);
        List<Item> forSales = testMenu.getForSales();
        assertEquals(1, forSales.size());
        assertEquals(item3, forSales.get(0));
    }

    @Test
    public void testAddCustomerOnce() {
        testMenu.addCustomer(c1);
        List<Customer> customers = testMenu.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(c1, customers.get(0));
    }

    @Test
    public void testAddCustomerDuplicate() {
        testMenu.addCustomer(c1);
        testMenu.addCustomer(c1);
        List<Customer> customers = testMenu.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(c1, customers.get(0));
    }

    @Test
    public void testAddCustomerMultiple() {
        testMenu.addCustomer(c1);
        testMenu.addCustomer(c2);
        List<Customer> customers = testMenu.getCustomers();
        assertEquals(2, customers.size());
        assertEquals(c1, customers.get(0));
        assertEquals(c2, customers.get(1));
    }

    @Test
    public void testRemoveCustomerOnce() {
        testMenu.addCustomer(c1);
        testMenu.removeCustomer(c1);
        List<Customer> customers = testMenu.getCustomers();
        assertEquals(0, customers.size());
    }

    @Test
    public void testRemoveCustomerMultiple() {
        testMenu.addCustomer(c1);
        testMenu.addCustomer(c2);
        testMenu.addCustomer(c3);
        testMenu.removeCustomer(c2);
        testMenu.removeCustomer(c1);
        List<Customer> customers = testMenu.getCustomers();
        assertEquals(1, customers.size());
        assertEquals(c3, customers.get(0));
    }

    @Test
    public void testContainsItem() {
        testMenu.addItem(item1);
        testMenu.addItem(item3);
        assertTrue(testMenu.containsItem(item1.getName()));
        assertFalse(testMenu.containsItem(item2.getName()));
        assertTrue(testMenu.containsItem(item3.getName()));
    }

    @Test
    public void testContainsCustomer() {
        testMenu.addCustomer(c2);
        testMenu.addCustomer(c3);
        assertFalse(testMenu.containsCustomer(c1.getName()));
        assertTrue(testMenu.containsCustomer(c2.getName()));
        assertTrue(testMenu.containsCustomer(c3.getName()));
    }

    @Test
    public void testSelectItem() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        assertEquals(item1, testMenu.selectItem(item1.getName()));
        assertEquals(item2, testMenu.selectItem(item2.getName()));
        assertNull(testMenu.selectItem(item3.getName()));
    }

    @Test
    public void testSelectCustomer() {
        testMenu.addCustomer(c1);
        testMenu.addCustomer(c2);
        assertEquals(c1, testMenu.selectCustomer(c1.getName()));
        assertEquals(c2, testMenu.selectCustomer(c2.getName()));
        assertNull(testMenu.selectCustomer(c3.getName()));
    }

    @Test
    public void testTakingOrdersOnce() {
        testMenu.addItem(item1);
        testMenu.takingOrders(item1, c1);
        assertEquals(1, testMenu.getCustomers().size());
        assertEquals(c1, testMenu.getCustomers().get(0));
        assertEquals(1, item1.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(1, c1.getPurchases().size());
        assertEquals(item1.getName(), c1.getPurchases().get(0));
    }

    @Test
    public void testTakingOrdersMultipleCustomer() {
        testMenu.addItem(item1);
        testMenu.takingOrders(item1, c1);
        testMenu.takingOrders(item1, c2);
        assertEquals(2, testMenu.getCustomers().size());
        assertEquals(c1, testMenu.getCustomers().get(0));
        assertEquals(c2, testMenu.getCustomers().get(1));
        assertEquals(2, item1.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(c2.getName(), item1.getCustomers().get(1));
        assertEquals(1, c1.getPurchases().size());
        assertEquals(item1.getName(), c1.getPurchases().get(0));
        assertEquals(1, c2.getPurchases().size());
        assertEquals(item1.getName(), c2.getPurchases().get(0));
    }

    @Test
    public void testTakingOrdersMultipleItem() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        testMenu.takingOrders(item1, c1);
        testMenu.takingOrders(item2, c1);
        assertEquals(1, testMenu.getCustomers().size());
        assertEquals(c1, testMenu.getCustomers().get(0));
        assertEquals(1, item1.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(1, item2.getCustomers().size());
        assertEquals(c1.getName(), item2.getCustomers().get(0));
        assertEquals(2, c1.getPurchases().size());
        assertEquals(item1.getName(), c1.getPurchases().get(0));
        assertEquals(item2.getName(), c1.getPurchases().get(1));
    }

    @Test
    public void testTakingOrdersDuplicate() {
        testMenu.addItem(item1);
        testMenu.takingOrders(item1, c1);
        testMenu.takingOrders(item1, c1);
        assertEquals(1, testMenu.getCustomers().size());
        assertEquals(c1, testMenu.getCustomers().get(0));
        assertEquals(1, item1.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(1, c1.getPurchases().size());
        assertEquals(item1.getName(), c1.getPurchases().get(0));
    }

    @Test
    public void testClearCustomer() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        testMenu.addItem(item3);
        testMenu.takingOrders(item1, c1);
        testMenu.takingOrders(item2, c2);
        testMenu.takingOrders(item3, c2);
        testMenu.takingOrders(item3, c1);
        testMenu.clearCustomer(c2);
        assertEquals(1, testMenu.getCustomers().size());
        assertEquals(c1, testMenu.getCustomers().get(0));
        assertEquals(1, item1.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(0, item2.getCustomers().size());
        assertEquals(1, item3.getCustomers().size());
        assertEquals(c1.getName(), item1.getCustomers().get(0));
        assertEquals(2, c1.getPurchases().size());
        assertEquals(item1.getName(), c1.getPurchases().get(0));
        assertEquals(item3.getName(), c1.getPurchases().get(1));
        assertEquals(0, c2.getPurchases().size());
    }

    @Test
    public void testToJsonBase() {
        JSONObject json = testMenu.toJson();
        String menuName =  json.getString("name");
        JSONArray itemsJsonArray = json.getJSONArray("Items");
        JSONArray customersJsonArray = json.getJSONArray("Customers");
        assertEquals("Tim", menuName);
        assertEquals(0, itemsJsonArray.length());
        assertEquals(0, customersJsonArray.length());
    }

    @Test
    public void testToJson() {
        testMenu.addItem(item1);
        testMenu.addItem(item2);
        testMenu.addItem(item3);
        testMenu.takingOrders(item1, c1);
        testMenu.takingOrders(item2, c2);
        testMenu.takingOrders(item3, c2);
        testMenu.takingOrders(item3, c1);
        JSONObject json = testMenu.toJson();
        String menuName =  json.getString("name");
        JSONArray itemsJsonArray = json.getJSONArray("Items");
        JSONArray customersJsonArray = json.getJSONArray("Customers");
        assertEquals("Tim", menuName);
        assertEquals(3, itemsJsonArray.length());
        assertEquals(2, customersJsonArray.length());
    }
}
