package persistence;

import model.Menu;
import model.Item;
import model.Customer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Menu m = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            Menu m = reader.read();
            assertEquals("My Menu", m.getStoreName());
            assertEquals(0, m.getForSales().size());
            assertEquals(0, m.getCustomers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMenu() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Menu m = reader.read();
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
            fail("Couldn't read from file");
        }
    }
}
