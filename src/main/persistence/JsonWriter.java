package persistence;

import model.Menu;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of Menu to file
// code for the JsonWriter Class
// was based off the code in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a JsonWriter to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //          be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Menu to file
    public void writeMenu(Menu m) {
        JSONObject json = m.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this, destination json file
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}