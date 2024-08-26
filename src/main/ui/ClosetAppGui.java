package ui;

import model.Closet;
import model.Clothing;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Graphical user interface for the ClosetApp
public class ClosetAppGui implements ActionListener {

    private static final String JSON_STORE = "./data/closet.json";
    JFrame frame;
    JFrame addFrame;
    JFrame fitFrame;
    JPanel titlePanel;
    JPanel buttonPanel;

    JPanel closetPanel;
    JLabel titleLabel = new JLabel();

    JButton saveButton;
    JButton loadButton;
    JButton clearButton;
    JButton addButton;
    JButton filterTypeButton;

    JButton filterColourButton;


    Closet closet;
    private JsonWriter jsonWriter;

    private JsonReader jsonReader;

    JFrame oneColourFrame;
    JTextField filterColour;
    JButton submitFilterColour;
    JPanel filterColourPanel;
    JFrame oneTypeFrame;
    JTextField filterType;
    JButton submitFilterType;
    JPanel filterTypePanel;
    JTextField colourTextField;
    JTextField fabricTextField;
    JTextField clothingTypeTextField;
    JPanel stringAnswerPanel;
    JPanel intAnswerPanel;
    JButton submit;

    Clothing clothing;

    String newClothingColour;
    String newClothingClothingType;
    String newClothingFabric;
    JRadioButton fitted;
    JRadioButton relaxed;
    JRadioButton baggy;


    //runs a closet frame;
    public ClosetAppGui() {
        runClosetFrame();
    }


    //effects: initializes a new closet and closet frame with an empty closet and 6 visible buttons
    public void runClosetFrame() {
        init();
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(800,800));
        addTitlePanel();
        addButtonPanel();
        addClosetPanel();
        frame.setTitle("ClosetApp");
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new ClosingWindowAction());
    }

    // MODIFIES: this
    // EFFECTS: initializes closet and jsonWriter and jsonReader
    private void init() {
        closet = new Closet();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }


    //modifies: this and titlePanel
    // effects: adds a title panel to this
    public void addTitlePanel() {
        titlePanel = new JPanel();
        titlePanel.setBackground(new Color(40, 26, 13));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        titleLabel.setText("ClosetApp");
        titleLabel.setFont(new Font(null, Font.PLAIN, 35));
        titleLabel.setForeground(new Color(222,202,176));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.PAGE_START);
    }


    //modifies: frame, buttonPanel
    //effects: adds a panel with 6 buttons to this
    public void addButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(222,202,176));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(buttonPanel, BorderLayout.PAGE_END);
        addButtons();

    }

    //modifies: frame, closetPanel
    //effects: adds a panel to view the clothing added to the closet or the clothing loaded from file
    public void addClosetPanel() {
        closetPanel = new JPanel();
        closetPanel.setLayout(new GridLayout(4,4));
        closetPanel.setBackground(new Color(222,202,176));
        closetPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(closetPanel, BorderLayout.CENTER);
    }

    //modifies: buttonPanel
    //effects: adds buttons to the bottom panel in this
    public void addButtons() {

        buttonPanel.setLayout(new GridLayout(3,2));
        createButtons();
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addButton);
        buttonPanel.add(filterTypeButton);
        buttonPanel.add(filterColourButton);
        buttonPanel.add(clearButton);
    }


    //modifies: saveButton, loadButton, addButton, filterTypeButton, filterColourButton, clearButton, actionListener
    //effects: creates the above buttons and adds action listener to all of them
    public void createButtons() {
        saveButton = new JButton("save closet");
        saveButton.addActionListener(this);
        loadButton = new JButton("load closet from file");
        loadButton.addActionListener(this);
        addButton = new JButton("add to closet");
        addButton.addActionListener(this);
        filterTypeButton = new JButton("filter closet by clothing type");
        filterTypeButton.addActionListener(this);
        filterColourButton = new JButton("filter closet by colour");
        filterColourButton.addActionListener(this);
        clearButton = new JButton("clear closet");
        clearButton.addActionListener(this);
    }


    //effects: takes next action depending on which button was pressed
    @Override
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveCloset();
        } else if (e.getSource() == loadButton) {
            loadCloset();
        } else if (e.getSource() == addButton) {
            addClothingPage();
        } else if (e.getSource() == filterTypeButton) {
            filterByType();
        } else if (e.getSource() == filterColourButton) {
            filterByColour();
        } else if (e.getSource() == clearButton) {
            clearCloset();
        } else if (e.getSource() == submit) {
            secondAddingStep();
        } else if (e.getSource() == fitted) {
            addNewClothingToCloset(0);
        } else if (e.getSource() == relaxed) {
            addNewClothingToCloset(1);
        } else if (e.getSource() == baggy) {
            addNewClothingToCloset(2);
        } else if (e.getSource() == submitFilterColour) {
            createOneColourCloset(filterColour.getText());
        } else if (e.getSource() == submitFilterType) {
            createOneTypeCloset(filterType.getText());
        }
        frame.pack();
    }


    //modifies: filterColourPanel, oneColourFrame
    // effects: creates a new frame showing only one colour from the closet
    public void createOneColourCloset(String colour) {
        ArrayList<Clothing> colourCloset = closet.allOneColour(colour);
        filterColourPanel.setLayout(new GridLayout(4,4));
        for (Clothing c: colourCloset) {
            JLabel newClothing = new JLabel(c.clothingToString());
            newClothing.setBorder(BorderFactory.createLineBorder(Color.black));
            filterColourPanel.add(newClothing);
        }
        oneColourFrame.add(filterColourPanel, BorderLayout.CENTER);
        oneColourFrame.pack();
        //oneColourFrame.setVisible(true);
    }

    //modifies: filterTypePanel, oneTypeFrame
    // effects: creates a new frame showing only one type of clothing from the closet
    public void createOneTypeCloset(String type) {
        ArrayList<Clothing> typeCloset = closet.allOneClothingType(type);
        filterTypePanel.setLayout(new GridLayout(4,4));
        for (Clothing c: typeCloset) {
            JLabel newClothing = new JLabel(c.clothingToString());
            newClothing.setBorder(BorderFactory.createLineBorder(Color.black));
            filterTypePanel.add(newClothing);
        }
        oneTypeFrame.add(filterTypePanel, BorderLayout.CENTER);
        oneTypeFrame.pack();
        //oneTypeFrame.setVisible(true);
    }


    //modifies: oneTypeFrame, filterType, submitFilterType
    //effects: creates frame that asks user what type of clothing they want to see
    public void filterByType() {
        oneTypeFrame = new JFrame();
        oneTypeFrame.setBounds(0,0,700,400);

        JPanel questionPanel = new JPanel(new FlowLayout());
        questionPanel.add(new JLabel("What type of clothing would you like to see from your closet?"));
        filterType = new JTextField();
        filterType.setPreferredSize(new Dimension(100,25));
        questionPanel.add(filterType);
        submitFilterType = new JButton("filter");
        submitFilterType.addActionListener(this);
        questionPanel.add(submitFilterType);
        oneTypeFrame.add(questionPanel, BorderLayout.PAGE_START);
        filterTypePanel = new JPanel();
        oneTypeFrame.add(filterTypePanel, BorderLayout.CENTER);
        oneTypeFrame.setVisible(true);
    }


    //modifies: oneColourFrame, filterColour, submitColourType
    //effects: creates frame that asks user what colour of clothing they want to see
    public void filterByColour() {
        oneColourFrame = new JFrame();
        oneColourFrame.setBounds(0,0,700,400);
        JPanel questionPanel = new JPanel(new FlowLayout());
        questionPanel.add(new JLabel("What colour of clothing would you like to see from your closet?"));
        filterColour = new JTextField();
        filterColour.setPreferredSize(new Dimension(100, 25));
        questionPanel.add(filterColour);
        submitFilterColour = new JButton("filter");
        submitFilterColour.addActionListener(this);
        questionPanel.add(submitFilterColour);
        oneColourFrame.add(questionPanel, BorderLayout.PAGE_START);
        filterColourPanel = new JPanel();
        oneColourFrame.add(filterColourPanel, BorderLayout.CENTER);
        oneColourFrame.setVisible(true);
    }


    //modifies: this, closetPanel
    //effects: clears closet
    public void clearCloset() {
        closetPanel.removeAll();
        closet.clearCloset();
    }

    public void secondAddingStep() {
        newClothingColour = colourTextField.getText();
        newClothingFabric = fabricTextField.getText();
        newClothingClothingType = clothingTypeTextField.getText();
        askClothingFit();
        addFrame.dispose();
    }

    //modifies: clothing, closet
    //effects: adds new clothing to closet and displays tat
    public void addNewClothingToCloset(int fit) {
        clothing = new Clothing(newClothingColour, fit, newClothingFabric, newClothingClothingType);
        addClothing();
        JLabel newClothing = new JLabel(clothing.clothingToString());
        newClothing.setBorder(BorderFactory.createLineBorder(Color.black));
        closetPanel.add(newClothing);
        fitFrame.dispose();
    }

    //effects: saves the closet to file
    private void saveCloset()  {
        try {
            jsonWriter.open();
            jsonWriter.write(closet);
            jsonWriter.close();
            ImageIcon thumbsUp = new ImageIcon("/Users/joannepark/Desktop/project_w6i4x2022/data/thumbsup.png");
            JOptionPane.showMessageDialog(null,"you have saved your closet",
                    "SAVED", JOptionPane.PLAIN_MESSAGE, thumbsUp);


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "sorry, closet was not able to be saved",
                    "uh oh", JOptionPane.ERROR_MESSAGE);

        }
    }

    //modifies:this
    //effects: loads closet from file
    private void loadCloset() {
        try {
            closet = jsonReader.read();
            JOptionPane.showMessageDialog(null,"you have loaded your closet",
                    "LOADED", JOptionPane.PLAIN_MESSAGE);
            for (Clothing c: closet.getListOfClothes()) {
                JLabel loadedClothing = new JLabel(c.clothingToString());
                loadedClothing.setBorder(BorderFactory.createLineBorder(Color.black));
                closetPanel.add(loadedClothing);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"sorry, unable to load file",
                    "UH OH", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //modifies: this.closet
    //effectsL adds clothing to closet and gives message to user saying that
    private void addClothing() {
        closet.addToCloset(clothing);
        //closetPanel.add(new JButton(clothing.clothingToString()));
        JOptionPane.showMessageDialog(null,"you have added to your closet",
                "Added!", JOptionPane.PLAIN_MESSAGE);
    }

    //modifies: addFrame
    //effects:creates a frame to ask the user about the clothes that they are adding
    private void addClothingPage() {
        addFrame = new JFrame();
        addFrame.setLayout(new FlowLayout());
        addFrame.setPreferredSize(new Dimension(500,300));
        addFrame.add(new JLabel("describe the clothing you're adding in the following boxes"),
                BorderLayout.PAGE_START);
        submit = new JButton("submit");
        submit.addActionListener(this);
        addStringAnswerPanel();

        addFrame.setTitle("adding a piece of clothing");
        addFrame.add(submit);
        addFrame.pack();
        addFrame.setVisible(true);
    }

    //modifies: fitFrame
    //effects: adds user fit of clothing
    private void askClothingFit() {
        fitFrame = new JFrame();
        fitFrame.setPreferredSize(new Dimension(500,300));
        fitFrame.add(new JLabel("What is the fit of your clothing?"), BorderLayout.PAGE_START);
        addIntAnswerPanel();
        fitFrame.pack();
        fitFrame.setVisible(true);
    }


    //modifies: stringAnswerPanel, addFrame,
    //effects: creates and adds panel of 3 options of fit
    public void addStringAnswerPanel() {
        stringAnswerPanel = new JPanel(new FlowLayout());
        stringAnswerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        colourTextField = new JTextField("colour?");
        colourTextField.setPreferredSize(new Dimension(100, 25));
        stringAnswerPanel.add(colourTextField);
        fabricTextField = new JTextField("fabric?");
        fabricTextField.setPreferredSize(new Dimension(100, 25));
        stringAnswerPanel.add(fabricTextField);
        clothingTypeTextField = new JTextField("clothing type?: shirt, pants, etc");
        clothingTypeTextField.setPreferredSize(new Dimension(200, 25));
        stringAnswerPanel.add(clothingTypeTextField);
        addFrame.add(stringAnswerPanel, BorderLayout.CENTER);
    }


    //modifies: intAnswerPanel, fitFrame,
    //effects: creates and adds panel of 3 options of fit
    public void addIntAnswerPanel() {

        intAnswerPanel = new JPanel(new FlowLayout());
        fitted = new JRadioButton("fitted");
        fitted.addActionListener(this);
        relaxed = new JRadioButton("relaxed");
        relaxed.addActionListener(this);
        baggy = new JRadioButton("baggy");
        baggy.addActionListener(this);
        ButtonGroup fitGroup = new ButtonGroup();
        fitGroup.add(fitted);
        fitGroup.add(relaxed);
        fitGroup.add(baggy);
        intAnswerPanel.add(fitted);
        intAnswerPanel.add(relaxed);
        intAnswerPanel.add(baggy);
        fitFrame.add(intAnswerPanel, BorderLayout.CENTER);
    }





}
