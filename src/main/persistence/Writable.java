package persistence;

import org.json.JSONObject;

// code for Writable Class
// was based off the Writable class in the JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

