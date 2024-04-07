package ui;

import model.EventLog;
import ui.exception.LogException;
import model.Menu;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.CustomerTab;
import ui.tabs.OwnerTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the Mini-Ordering System application (MOS) (GUI version).
// code for MiniOrderingSystemGUI Class was based off:
// The SmartHome example.
// link: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public class MiniOrderingSystemGUI extends JFrame implements WindowListener {
    public static final int TAB_BAR_INDEX = 0;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 820;

    private JTabbedPane tabBar;
    private Menu menu;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;

    private static final String JSON_MOS = "./data/MOS.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the MOS application(GUI).
    public static void main(String[] args) {
        new MiniOrderingSystemGUI();
    }

    // EFFECTS: sets up the MOS application (GUI), interact with the user to take in the user's response/input
    //          processes the user(the owner)'s input (option selection) for MOS's Menu.
    //          also processes the closing of the application.
    private MiniOrderingSystemGUI() {
        super("MiniOrderingSystemConsole - MOS");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(123, 50, 250));
        setResizable(false);

        setUpJson();
        askForLoad();

        tabBar = new JTabbedPane();
        tabBar.setTabPlacement(JTabbedPane.LEFT);
        loadTabs();
        add(tabBar);

        setUpMenuBar();
        menuBarActions();
        setVisible(true);
        this.addWindowListener(this);
    }

    // EFFECTS: returns Menu controlled by this UI
    public Menu getMenu() {
        return menu;
    }

    // MODIFIES: this
    // EFFECTS: add the Owner and the Customer side tabs to the UI application.
    private void loadTabs() {
        OwnerTab ownerTab = new OwnerTab(this);
        CustomerTab customerTab = new CustomerTab(this);

        tabBar.add(ownerTab, TAB_BAR_INDEX);
        tabBar.setTitleAt(TAB_BAR_INDEX, "Owner");
        tabBar.add(customerTab, TAB_BAR_INDEX);
        tabBar.setTitleAt(TAB_BAR_INDEX, "Customer");
    }

    // MODIFIES: this
    // EFFECTS: add the Menu bar for save of this GUI
    private void setUpMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");

        file.add(save);
        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: sets up the JsonWriter and JsonReader
    private void setUpJson() {
        jsonWriter = new JsonWriter(JSON_MOS);
        jsonReader = new JsonReader(JSON_MOS);
    }

    // MODIFIES: this
    // EFFECTS: add function to the "save" in the menu bar when clicked
    private void menuBarActions() {
        save.addActionListener(e -> {
            try {
                jsonWriter.open();
                jsonWriter.writeMenu(menu);
                jsonWriter.close();
            } catch (FileNotFoundException fileNotFoundException) {
                // Not expected
            }
        });
    }

    // MODIFIES: this, menu
    // EFFECTS: prompts the user with a JOptionPane confirm dialog for loading
    //          data from the MOS.json file.
    //          Load the saved menu object if user response is YES,
    //          and instantiate a new menu object if user response is NO.
    public void askForLoad() {
        int userResponse = JOptionPane.showConfirmDialog(null,
                "Would you like to load your old Menu?", "Load or not?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );

        if (userResponse == JOptionPane.YES_OPTION) {
            try {
                menu = jsonReader.read();
                System.out.println("Loaded " + menu.getStoreName() + " from " + JSON_MOS);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_MOS);
            }
        } else if (userResponse == JOptionPane.NO_OPTION) {
            menu = new Menu("Wendy");
        }
    }

    //EFFECTS: returns tabBar of this UI
    public JTabbedPane getTabbedPane() {
        return tabBar;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    // EFFECTS: Prints EventLog in console when GUI window closes.
    public void windowClosing(WindowEvent e) {
        ConsolePrinter printer = new ConsolePrinter();
        try {
            printer.printLog(EventLog.getInstance());
        } catch (LogException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
