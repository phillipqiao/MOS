package persistence;

import model.Menu;
import model.Item;
import model.Customer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Menu m = new Menu("My Menu");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMenu() {
        try {
            Menu m = new Menu("My Menu");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.writeMenu(m);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            m = reader.read();
            assertEquals("My Menu", m.getStoreName());
            assertEquals(0, m.getForSales().size());
            assertEquals(0, m.getCustomers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMenu() {
        try {
            Menu m = new Menu("My Menu");
            Item burger = new Item("burger", 4.99, 5);
            Item fries = new Item("fries", 1.99, 3);

            Customer david = new Customer("David");
            Customer phil = new Customer("Phil");
            m.addItem(burger);
            m.addItem(fries);
            m.addCustomer(david);
            m.addCustomer(phil);
            m.takingOrders(burger, phil);
            m.takingOrders(fries, phil);
            m.takingOrders(burger, david);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.writeMenu(m);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            m = reader.read();
            assertEquals("My Menu", m.getStoreName());
            List<Item> forSales = m.getForSales();
            assertEquals(2, forSales.size());
            checkItem("burger", 4.99, 5, forSales.get(0));
            checkItem("fries", 1.99, 3, forSales.get(1));

            List<Customer> customers = m.getCustomers();
            assertEquals(2, customers.size());
            checkCustomer("David", customers.get(0));
            checkCustomer("Phil", customers.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
