package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    private Customer c1;
    private Item item1;
    private Item item2;

    @BeforeEach
    public void runBefore() {
        c1 = new Customer("Phillip");
        item1 =  new Item("Cheese Burger", 1.39, 3);
        item2 =  new Item("Fries", 0.99, 5);
    }

    @Test
    public void testConstructor(){
        assertEquals("Phillip", c1.getName());
        assertTrue(c1.getPurchases().isEmpty());
    }

    @Test
    public void testAddItemOnce() {
        c1.addItem(item1);
        assertEquals(1,c1.getPurchases().size());
        assertEquals(item1.getName(),c1.getPurchases().get(0));
    }

    @Test
    public void testAddItemMultiple() {
        c1.addItem(item1);
        c1.addItem(item2);
        assertEquals(2,c1.getPurchases().size());
        assertEquals(item1.getName(),c1.getPurchases().get(0));
        assertEquals(item2.getName(),c1.getPurchases().get(1));
    }

    @Test
    public void testAddItemStringOnce() {
        c1.addItem(item1.getName());
        assertEquals(1,c1.getPurchases().size());
        assertEquals(item1.getName(),c1.getPurchases().get(0));
    }

    @Test
    public void testAddItemStringMultiple() {
        c1.addItem(item1.getName());
        c1.addItem(item2.getName());
        assertEquals(2,c1.getPurchases().size());
        assertEquals(item1.getName(),c1.getPurchases().get(0));
        assertEquals(item2.getName(),c1.getPurchases().get(1));
    }

    @Test
    public void testRemoveItemOnce() {
        c1.addItem(item1);
        c1.addItem(item2);
        c1.removeItem(item1);
        assertEquals(1,c1.getPurchases().size());
        assertEquals(item2.getName() ,c1.getPurchases().get(0));
    }

    @Test
    public void testRemoveItemMultiple() {
        c1.addItem(item1);
        c1.addItem(item2);
        c1.removeItem(item1);
        c1.removeItem(item2);
        assertTrue(c1.getPurchases().isEmpty());
    }

    @Test
    public void testToJsonBase() {
        JSONObject json = c1.toJson();
        String cname =  json.getString("name");
        JSONArray cJsonArray = json.getJSONArray("purchases");
        assertEquals(cname, "Phillip");
        assertEquals(0, cJsonArray.length());
    }

    @Test
    public void testToJson() {
        c1.addItem(item1);
        c1.addItem(item2);
        JSONObject json = c1.toJson();
        String cname =  json.getString("name");
        JSONArray cJsonArray = json.getJSONArray("purchases");
        assertEquals(cname, "Phillip");
        assertEquals(2, cJsonArray.length());
    }
}
