package ui.tabs;

import model.Item;
import ui.ButtonNames;
import ui.MiniOrderingSystemGUI;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

// Represents the Owner JTabbedPane tab.
// code for OwnerTab Class was based off:
// The SmartHome example.
// link: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public class OwnerTab extends Tab {
    private static final String INIT_GREET = "Great! What would you like to do with your Menu?";
    private JLabel ownerGreet;

    private String name;
    private double price;
    private int stock;

    private JButton addItem;
    private JButton removeItem;
    private JButton displayMenu;

    private JTextField itemName;
    private JFormattedTextField itemPrice;
    private JFormattedTextField itemStock;
    private Item item;

    // EFFECTS: Constructs an owner JTabbedPane (JPanel) for GUI with buttons
    public OwnerTab(MiniOrderingSystemGUI mosGUI) {
        super(mosGUI);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets up the userBar JPanel in the mainDisplay and add the appropriate
    //          JLabels and JTextFields for user inputs.
    protected void loadUserBarPanel() {
        super.loadUserBarPanel();
        JLabel nameLabel = new JLabel("  Item Name : ");
        JLabel priceLabel = new JLabel("  Item Price : ");
        JLabel stockLabel = new JLabel("  Item Stock : ");

        itemName = new JTextField();

        itemPrice = new JFormattedTextField(NumberFormat.getNumberInstance());
        itemPrice.addPropertyChangeListener(new FormatTextListener(item));
        itemStock = new JFormattedTextField(NumberFormat.getNumberInstance());
        itemStock.addPropertyChangeListener(new FormatTextListener(item));

        userBar.add(nameLabel);
        userBar.add(itemName);
        userBar.add(priceLabel);
        userBar.add(itemPrice);
        userBar.add(stockLabel);
        userBar.add(itemStock);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds appropriate OwnerBar buttons to the sideBar.
    public void addSideBarButtons() {
        addItem = new JButton(ButtonNames.AddItem.getValue());
        removeItem = new JButton(ButtonNames.RemoveItem.getValue());
        displayMenu = new JButton(ButtonNames.DisplayMenu.getValue());

        JPanel buttonRow = formatButtonRow(addItem);
        buttonRow.add(removeItem);
        buttonRow.add(displayMenu);

        buttonActions();
        sideBar.add(buttonRow);
    }

    // MODIFIES: this, menu
    // EFFECTS: assigns functions to the OwnerTab sideBar buttons
    private void buttonActions() {
        addItem.addActionListener(e -> {
            assignValues();
            if (!menu.containsItem(name)) {
                menu.addItem(new Item(name, price, stock));
            }
        });

        removeItem.addActionListener(e -> {
            name = itemName.getText();
            if (menu.containsItem(name)) {
                menu.removeItem(menu.selectItem(name));
            }
        });

        displayMenu.addActionListener(e -> {
            super.updateMenu();
        });
    }

    // MODIFIES: this
    // EFFECTS: assigns values for user inputs in the corresponding OwnerTab JTextFields
    private void assignValues() {
        name = itemName.getText();
        price = ((Number) itemPrice.getValue()).doubleValue();
        stock = ((Number) itemPrice.getValue()).intValue();
    }

    // MODIFIES: this
    // EFFECTS: creates and places greeting JLabel at top of sideBar panel
    private void placeGreeting() {
        ownerGreet = new JLabel(INIT_GREET, JLabel.CENTER);
        ownerGreet.setSize(WIDTH, HEIGHT / 3);
        this.add(ownerGreet);
    }

    // EFFECTS: overrides the FormatTextListener class to force input format of the JTextFields
    private class FormatTextListener implements PropertyChangeListener {
        Item item;

        public FormatTextListener(Item i) {
            item = i;
        }

        // MODIFIES: this
        // EFFECTS: reformat the value accepted in the JTextFields.
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            //JFormattedTextField textField = (JFormattedTextField) evt.getSource();
        }
    }

}
