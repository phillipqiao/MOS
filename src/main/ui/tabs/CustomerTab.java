package ui.tabs;

import model.Customer;
import ui.ButtonNames;
import ui.MiniOrderingSystemGUI;

import javax.swing.*;
import java.awt.*;

// Represents the Customer JTabbedPane tab.
// code for CustomerTab Class was based off:
// The SmartHome example.
// link: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public class CustomerTab extends Tab {
    private String nameI;
    private String nameC;

    private JButton orderItem;
    private JButton viewOrder;
    private JButton checkout;
    private JButton refreshMenu;

    private JTextField customerName;
    private JTextField itemName;

    protected JTextArea customerTextBox;
    protected JScrollPane customerReportPane;
    protected JLabel customerReportMessage;

    // EFFECTS: Constructs a Customer JTabbedPane (JPanel) for GUI with buttons
    public CustomerTab(MiniOrderingSystemGUI mosGUI) {
        super(mosGUI);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets up the layout and components (JTextArea, JScrollPane) of the mainDisplay JPanel, as well
    //          as new components (JTextArea, JScrollPane) for the customers JTabbedPane only
    protected void setUpMainDisplay() {
        super.setUpMainDisplay();

        customerReportMessage = new JLabel("Order Tab : ");
        customerReportMessage.setFont(new Font("Times New Roman", Font.BOLD, 20));
        customerReportMessage.setForeground(Color.GREEN);
        customerReportMessage.setBounds(20, 260, 300, 50);

        customerReportPane = new JScrollPane(new JTextArea(6, 40));
        customerReportPane.setBounds(20, 320, 600, 200);
        customerReportPane.setBackground(Color.WHITE);

        customerTextBox = new JTextArea("",6, 40);
        customerTextBox.setBackground(new Color(0x202020));
        customerTextBox.setFont(new Font("Chalkboard", Font.PLAIN, 16));
        customerTextBox.setForeground(Color.GREEN);

        reportBlock.add(customerReportMessage);
        reportBlock.add(customerReportPane);
        setInitialCusReportPane();
        mainDisplay.add(reportBlock);
    }


    // MODIFIES: this
    // EFFECTS: sets up the initial customerReportPane JScrollPane display.
    public void setInitialCusReportPane() {
        String message = "";
        customerTextBox.setText(message);
        customerReportPane.setViewportView(customerTextBox);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: sets up the userBar JPanel in the mainDisplay and add the appropriate CustomerTab
    //          JLabels and JTextFields for user inputs.
    protected void loadUserBarPanel() {
        super.loadUserBarPanel();
        JLabel customerLabel = new JLabel("  Customer Name : ");
        JLabel itemLabel = new JLabel("  Item Name : ");

        customerName = new JTextField();
        itemName = new JTextField();

        userBar.add(customerLabel);
        userBar.add(customerName);
        userBar.add(itemLabel);
        userBar.add(itemName);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: adds appropriate CustomerBar buttons to the sideBar.
    public void addSideBarButtons() {
        orderItem = new JButton(ButtonNames.OrderItem.getValue());
        viewOrder = new JButton(ButtonNames.ViewOrder.getValue());
        checkout = new JButton(ButtonNames.Checkout.getValue());
        refreshMenu = new JButton(ButtonNames.RefreshMenu.getValue());

        JPanel buttonRow = formatButtonRow(orderItem);
        buttonRow.add(viewOrder);
        buttonRow.add(checkout);
        buttonRow.add(refreshMenu);

        buttonActions();
        sideBar.add(buttonRow);
    }

    // MODIFIES: this, menu
    // EFFECTS: assigns functions to the CustomerTab sideBar "orderItem" and "viewOrder" buttons
    private void buttonActions() {
        orderItem.addActionListener(e -> {
            viewItems();
            assignValues();
            boolean moreOrdering = true;
            int count = purchasesCalculator(nameC);

            while (moreOrdering) {
                if (!menu.selectItem(nameI).containsCustomer(nameC)) {
                    count = doOrderForCustomer(count, nameI, nameC);
                } else {
                    moreOrdering = false;
                }
            }
        });

        viewOrder.addActionListener(e -> {
            assignValues();
            displayOrder(nameC);
        });

        buttonsActionsCont();
    }

    // MODIFIES: this, menu
    // EFFECTS: assigns functions to the CustomerTab sideBar "checkout" and "refreshMenu" buttons
    private void buttonsActionsCont() {
        checkout.addActionListener(e -> {
            assignValues();
            doCheckout(nameC);
        });

        refreshMenu.addActionListener(e -> {
            super.updateMenu();
        });
    }

    // MODIFIES: this
    // EFFECTS: assigns values for user inputs in the corresponding CustomerTab JTextFields.
    private void assignValues() {
        nameI = itemName.getText();
        nameC = customerName.getText();
    }

    // EFFECTS: Return 1 if !menu.containsCustomer(customerName),
    //          else return (size of the customers purchases list + 1).
    private int purchasesCalculator(String customerName) {
        int counter;
        if (menu.containsCustomer(customerName)) {
            counter = (menu.selectCustomer(customerName).getPurchases().size() + 1);
        } else {
            counter = 1;
        }
        return counter;
    }

    // MODIFIES: menu
    // EFFECTS: Take orders for the customer if item is in forSales list,
    //          When taking orders with a customer previously ordered from the menu,
    //          add item to its purchases list, else add item to a newly created customer (profile).
    private int doOrderForCustomer(int counter, String item, String customerName) {
        int count = counter;
        if (menu.containsItem(item) && menu.containsCustomer(customerName)) {
            menu.takingOrders(menu.selectItem(item),menu.selectCustomer(customerName));
            count++;
        } else if (menu.containsItem(item)) {
            menu.takingOrders(menu.selectItem(item), new Customer(customerName));
            count++;
        }
        return count;
    }

    // EFFECTS: return a String with all items on the purchases list of the customer (with the name customerName).
    private String customerItemsToString(String customerName) {
        String returnMsg = "  Here's your order : \n \n";
        Customer c = menu.selectCustomer(customerName);
        int number = 1;

        if ((!menu.containsCustomer(customerName)) || (c.getPurchases().size() == 0)) {
            spongeLabel.setVisible(true);
            menuLabel.setVisible(false);
            return "  Sorry, you haven't ordered yet...";
        }

        for (String i : c.getPurchases()) {
            returnMsg += "\t" + number++ + ": " + i + "\n";
        }

        spongeLabel.setVisible(false);
        menuLabel.setVisible(true);
        return returnMsg;
    }

    // MODIFIES: menu
    // EFFECTS: if (menu.containsCustomer(customerName)), remove customer from the customers
    //          list of this menu and remove all items in the purchases list of the customer,
    //          else do nothing.
    private void doCheckout(String customerName) {
        if (menu.containsCustomer(customerName)) {
            Customer c = menu.selectCustomer(customerName);
            menu.clearCustomer(c);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all the purchased items of the customer in the customerReportPane.
    private void displayOrder(String customerName) {
        String message = customerItemsToString(customerName);
        customerTextBox.setText(message);
        customerReportPane.setViewportView(customerTextBox);
    }
}
