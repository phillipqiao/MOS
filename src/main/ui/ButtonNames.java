package ui;

// Represents a enum class containing all the button names.
// code for ButtonNames Class was based off:
// The SmartHome example.
// link: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public enum ButtonNames {
    AddItem("Add Item"),
    RemoveItem("Remove Item"),
    DisplayMenu("Display Menu"),

    OrderItem("Order Item"),
    ViewOrder("View Order"),
    Checkout("Checkout"),
    RefreshMenu("Refresh Menu");

    private final String name;

    // EFFECTS: constructs a ButtonNames enum object instance with this.name = name;
    ButtonNames(String name) {
        this.name = name;
    }

    // EFFECTS: returns name in String value of this button
    public String getValue() {
        return name;
    }
}
