package ui.tabs;

import model.Item;
import model.Menu;
import ui.MiniOrderingSystemGUI;

import javax.swing.*;
import java.awt.*;


// Represents the abstract JTabbedPane(JPanel) tab setup.
// code for Tab Class was based off:
// The SmartHome example.
// link: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
public abstract class Tab extends JPanel {
    private final MiniOrderingSystemGUI mos;
    protected Menu menu;

    protected JPanel sideBar;
    protected JPanel mainDisplay;
    protected JPanel reportBlock;

    protected JTextArea textBox;
    protected JPanel userBar;
    protected JScrollPane reportPane;
    protected JLabel reportMessage;

    protected JLabel menuLabel;
    protected ImageIcon menuImage;
    protected JLabel spongeLabel;
    protected ImageIcon emptyOrder;
    protected JLabel welcomeLabel;
    protected ImageIcon welcome;

    // EFFECTS: sets up the abstract layout and JPanels of the JTabbedPane tabs.
    public Tab(MiniOrderingSystemGUI mos) {
        this.mos = mos;
        menu = this.mos.getMenu();
        setLayout(null);
        setUpPanels();
    }

    // MODIFIES: this
    // EFFECTS: instantiates and sets up the sideBar and mainDisplay JPanels.
    public void setUpPanels() {
        sideBar = new JPanel();
        sideBar.setBackground(new Color(123,50,250));
        sideBar.setBounds(20, 20, MiniOrderingSystemGUI.WIDTH - 85,50);

        mainDisplay = new JPanel();
        mainDisplay.setBackground(new Color(0x1a0f29));
        mainDisplay.setBounds(20, 51, MiniOrderingSystemGUI.WIDTH - 85, 680);

        addSideBarButtons();
        this.add(sideBar);
        this.add(mainDisplay);
        setUpMainDisplay();
        loadUserBarPanel();
    }

    // MODIFIES: this
    // EFFECTS: adds appropriate buttons to the sideBar.
    public abstract void addSideBarButtons();

    // MODIFIES: this
    // EFFECTS: sets up the layout and components (JTextArea, JScrollPane) of the mainDisplay JPanel
    protected void setUpMainDisplay() {
        mainDisplay.setLayout(null);

        reportBlock = new JPanel(null);
        reportBlock.setBackground(new Color(0x1a0f29));
        reportBlock.setBounds(30, 50, 800, 600);

        reportMessage = new JLabel("Menu Tab : ");
        reportMessage.setFont(new Font("Times New Roman", Font.BOLD, 20));
        reportMessage.setForeground(Color.GREEN);
        reportMessage.setBounds(20, 0, 300, 50);

        reportPane = new JScrollPane(new JTextArea(6, 40));
        reportPane.setBounds(20, 51, 600, 200);
        reportPane.setBackground(Color.WHITE);

        textBox = new JTextArea("",6, 40);
        textBox.setBackground(new Color(0x202020));
        textBox.setFont(new Font("Chalkboard", Font.PLAIN, 16));
        textBox.setForeground(Color.GREEN);

        reportBlock.add(reportMessage);
        reportBlock.add(reportPane);
        setInitialReportPane();
        mainDisplay.add(reportBlock);
        addMenuLabel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the initial reportPane JScrollPane display.
    public void setInitialReportPane() {
        String message = "";
        textBox.setText(message);
        reportPane.setViewportView(textBox);
    }

    // MODIFIES: this
    // EFFECTS: sets up the userBar JPanel in the mainDisplay.
    protected void loadUserBarPanel() {
        userBar = new JPanel();
        userBar.setLayout(new GridLayout(14,1));
        userBar.setBounds(850, 50, 240, 600);
        mainDisplay.add(userBar);
    }

    // EFFECTS: return a String with all items on the menu.
    public String viewItems() {
        String returnMsg = "  Here are all the Items in the menu : \n \n";
        int number = 1;

        if (menu.getForSales().size() == 0) {
            return "  Menu is currently empty...";
        }

        for (Item i : menu.getForSales()) {
            returnMsg += "\t" + number++ + ": " + i.getName() + "\n";
        }
        return returnMsg;
    }

    // EFFECTS: creates and returns JPanel row with b included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setBackground(new Color(123,50,250));
        p.setLayout(new FlowLayout());
        p.add(b);
        return p;
    }

    // MODIFIES: this
    // EFFECTS: displays all the menu items in the reportPane
    public void updateMenu() {
        String message = viewItems();
        textBox.setText(message);
        reportPane.setViewportView(textBox);
    }

    // MODIFIES: this
    // EFFECTS: adds all visual components (ImageIcon) in mainDisplay
    public void addMenuLabel() {
        menuImage = new ImageIcon("./data/iconsImages/minion.gif");
        menuImage.setImage(menuImage.getImage().getScaledInstance((30 * 8),
                (18 * 8),
                Image.SCALE_DEFAULT));
        emptyOrder = new ImageIcon("./data/iconsImages/emptyOrder.gif");
        emptyOrder.setImage(emptyOrder.getImage().getScaledInstance(180,
                180,
                Image.SCALE_DEFAULT));

        menuLabel = new JLabel();
        menuLabel.setIcon(menuImage);
        menuLabel.setBounds(370, 170, 500, 600);

        spongeLabel = new JLabel();
        spongeLabel.setIcon(emptyOrder);
        spongeLabel.setBounds(380, 170, 500, 600);

        mainDisplay.add(menuLabel);
        mainDisplay.add(spongeLabel);

        menuLabel.setVisible(false);
        spongeLabel.setVisible(false);
        addWelcomeLabel();
    }

    // MODIFIES: this
    // EFFECTS: adds the welcome ImageIcon in mainDisplay
    public void addWelcomeLabel() {
        welcome = new ImageIcon("./data/iconsImages/welcome.gif");
        welcome.setImage(welcome.getImage().getScaledInstance(285,
                194,
                Image.SCALE_DEFAULT));

        welcomeLabel = new JLabel();
        welcomeLabel.setIcon(welcome);
        welcomeLabel.setBounds(360, 105, 285, 194);

        mainDisplay.add(welcomeLabel);

        welcomeLabel.setVisible(true);
    }
}
