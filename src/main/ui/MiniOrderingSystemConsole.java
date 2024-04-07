package ui;

import model.Customer;
import model.Item;
import model.Menu;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the Mini-Ordering System application (MOS) (Console Version).
// code for MiniOrderingSystemConsole Class was based off:
// The TellerApp class in the AccountNotRobust(TellerApp) example.
// link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
// The JsonSerializationDemo example.
// link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class MiniOrderingSystemConsole {
    private static final String JSON_MOS = "./data/MOS.json";
    private Menu menu;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the MOS application (Console).
    public static void main(String[] args) {
        new MiniOrderingSystemConsole();
    }

    // EFFECTS: runs the MOS application.
    public MiniOrderingSystemConsole() {
        runMos();
    }

    // MODIFIES: this
    // EFFECTS: sets up the MOS application (Console), interact with the user to take in the user's response/input
    //          process user(the owner)'s input (option selection) for MOS's main Menu.
    //          process the closing of the application.
    private void runMos() {
        boolean keepOrdering = true;
        String command = null;

        startUser();

        while (keepOrdering) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepOrdering =  false;
                System.out.println("Thank you for using MOS!");
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: Initiates an input Scanner for user's input.
    //          Instantiates a Menu object for the run time of the application.
    //          Instantiates a JsonWriter and JsonReader for saving and loading menu from the MOS.json file.
    private void startUser() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Welcome to MOS! Please enter the name of the shop here:");
        String username = input.next();
        menu = new Menu(username);
        jsonWriter = new JsonWriter(JSON_MOS);
        jsonReader = new JsonReader(JSON_MOS);
    }

    // EFFECTS: Displays the main menu showing the possible options and their corresponding letter.
    private void displayMenu() {
        System.out.println("\n Hello! Welcome to " + menu.getStoreName() + "! Please select one of the following:");
        System.out.println("\t c -> Customize Menu");
        System.out.println("\t o -> Ordering from Menu");
        System.out.println("\t s -> save Menu to file");
        System.out.println("\t l -> load Menu to file");
        System.out.println("\t q -> quit");
    }

    // MODIFIES: this, item, customer, MOS.json
    // EFFECTS: Process user's main menu commands according to their selection from the main menu.
    private void processCommand(String command) {
        if (command.equals("c")) {
            displayCustomizeMenu();
            command = input.next();
            processCustomizeCommand(command);
        } else if (command.equals("o")) {
            displayOrderingMenu();
            command = input.next();
            processOrderingCommand(command);
        } else if (command.equals("s")) {
            saveMenu();
        } else if (command.equals("l")) {
            loadMenu();
        } else {
            System.out.println("Invalid selection .... please try again");
        }
    }

    // EFFECTS: Displays the "Customize" menu showing the possible options and their corresponding letter.
    private void displayCustomizeMenu() {
        System.out.println("\n Great! What would you like to do with your Menu?");
        System.out.println("\t a -> Add an Item to Menu");
        System.out.println("\t r -> Remove an Item from Menu");
        System.out.println("\t v -> View all Items on Menu");
    }

    // MODIFIES: this, item, MOS.json
    // EFFECTS: Process user's "Customize" menu commands according to their selection from the main menu.
    private void processCustomizeCommand(String command) {
        if (command.equals("a")) {
            doAddItem();
        } else if (command.equals("r")) {
            doRemoveItem();
        } else if (command.equals("v")) {
            doViewItems();
        } else {
            System.out.println("Invalid selection .... please try again");
        }
    }

    // EFFECTS: Displays the "Ordering" menu showing the possible options and their corresponding letter.
    private void displayOrderingMenu() {
        System.out.println("\n Nice to meet you! What would you like to do?");
        System.out.println("\t o -> Order Items from Menu");
        System.out.println("\t v -> View my order");
        System.out.println("\t c -> Checkout");
    }

    // MODIFIES: this, item, customer
    // EFFECTS: Process user's "Ordering" menu commands according to their selection from the main menu.
    private void processOrderingCommand(String command) {
        if (command.equals("o")) {
            doOrderItem();
        } else if (command.equals("v")) {
            doViewCustomerOrder();
        } else if (command.equals("c")) {
            doCheckout();
        } else {
            System.out.println("Invalid selection .... please try again");
        }
    }

    // MODIFIES: this, item, customer
    // EFFECTS: Add an item to menu promoting the user to enter the item's price,
    //          and stock if the price user entered > 0.00,
    //          else prompts the user back to the main menu and notify Invalid entry.
    private void doAddItem() {
        System.out.println("Enter the name of your item: ");
        String name = input.next();

        if (menu.containsItem(name)) {
            System.out.println("Invalid entry, this item is already in your menu");
        } else {
            System.out.println("Enter the price of your item in $: "
                    + "\n(Please enter the price in a decimals format (e.g. #.##))");
            double price = input.nextDouble();
            if (price <= 0.00) {
                System.out.println("Invalid entry, price of the item can not be <= 0.00");
            } else {
                System.out.println("Enter the stock of your item: "
                        + "\n(Please enter a whole number > 0 (e.g. #)");
                int stock = input.nextInt();

                menu.addItem(new Item(name,price,stock));
                System.out.println("Success! Your item has been added!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Promote the user to enter the item's name, and remove the item from the menu if it is there
    //          so that it is no longer offered, else notify user menu does not contain item.
    private void doRemoveItem() {
        System.out.println("Enter the name of your item you would like to remove: ");
        String name = input.next();

        if (menu.containsItem(name)) {
            menu.removeItem(menu.selectItem(name));
            System.out.println("Done! That item has been removed from your menu");
        } else {
            System.out.println("Sorry, your menu does not contain this item...");
        }
    }

    // EFFECTS: Displays all items in the menu.
    private void doViewItems() {
        System.out.println("Here are all the Items in the menu:");

        if (menu.getForSales().size() > 0) {
            int number = 1;
            for (Item i : menu.getForSales()) {
                System.out.println("\t" + number++ + ": " + i.getName());
            }
        } else {
            System.out.println("Menu is currently empty...");
        }
    }

    // MODIFIES: this, item, customer
    // EFFECTS: Promote the user to enter their name and the item they want to order,
    //          If input is d, end ordering.
    //          Else if customer has already ordered this item,
    //          then notify customer the item can not be ordered again.
    //          Else doOrderForCustomer(counter, item, customerName)
    private void doOrderItem() {
        System.out.println("Welcome!");
        doViewItems();
        System.out.println("Please enter your name below:");
        String customerName = input.next();
        System.out.println("Hello! " + customerName
                + "\nPlease enter the name of the item you would like"
                + " to order (Note: all items are limit 1 per customer) or enter d when you finish.");
        boolean moreOrdering = true;
        int counter = purchasesCalculator(customerName);

        while (moreOrdering) {

            System.out.println("For your item " + counter + " you would like to order: (or enter d when you finish)");
            String item = input.next();
            if (item.equals("d")) {
                moreOrdering = false;
            } else if (menu.selectItem(item).containsCustomer(customerName)) {
                System.out.println("Sorry, you can only order each item once.");
            } else {
                counter = doOrderForCustomer(counter, item, customerName);
            }
        }
        System.out.println("Thank you for ordering with us!");
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

    // MODIFIES: this, item, customer
    // EFFECTS: Take orders for the customer if item is in forSales list,
    //          else notify the customer to select an Item from the Menu.
    //          When taking orders with a customer previously ordered from the menu,
    //          add item to its purchases list, else add item to a newly created customer (profile).
    private int doOrderForCustomer(int counter, String item, String customerName) {
        int count = counter;
        if (menu.containsItem(item) && menu.containsCustomer(customerName)) {
            menu.takingOrders(menu.selectItem(item),menu.selectCustomer(customerName));
            System.out.println(item + " has been added your order!");
            count++;
        } else if (menu.containsItem(item)) {
            menu.takingOrders(menu.selectItem(item), new Customer(customerName));
            System.out.println(item + " has been added your order!");
            count++;
        } else {
            System.out.println("Invalid selection.. Please select something from the menu.");
        }
        return count;
    }

    // EFFECTS: Displays all items the customer has ordered.
    private void  doViewCustomerOrder() {
        System.out.println("Please enter the the customer's name below:");
        String customerName = input.next();

        if (menu.containsCustomer(customerName)) {
            Customer c = menu.selectCustomer(customerName);
            System.out.println(customerName + "'s Order:");
            int number = 1;
            for (String i : c.getPurchases()) {
                System.out.println("\t" + number++ + ": " + i);
            }
        } else {
            System.out.println("Sorry, this customer has not yet ordered.");
        }
    }

    // MODIFIES: this, customer
    // EFFECTS: if (menu.containsCustomer(customerName)), remove customer from the customers
    //          list of this menu and remove all items in the purchases list of the customer,
    //          else notify user the customer has not yet ordered.
    private void doCheckout() {
        System.out.println("Dear Customer, please enter your name below to be checked out:");
        String customerName = input.next();

        if (menu.containsCustomer(customerName)) {
            Customer c = menu.selectCustomer(customerName);
            menu.clearCustomer(c);
            System.out.println("Thank you! You are checked out. We hope to see you again!");
        } else {
            System.out.println("Sorry, this customer has not yet ordered.");
        }
    }

    // EFFECTS: saves the menu to MOS.json file
    private void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.writeMenu(menu);
            jsonWriter.close();
            System.out.println("Saved " + menu.getStoreName() + " to " + JSON_MOS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_MOS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads menu from MOS.json file
    private void loadMenu() {
        try {
            menu = jsonReader.read();
            System.out.println("Loaded " + menu.getStoreName() + " from " + JSON_MOS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MOS);
        }
    }
}
