package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


//user interface to allow user to create and edit a closet
public class ClosetApp {



    private static final String JSON_STORE = "./data/closet.json";

    private Scanner input;

    private Closet closet;



    private JsonWriter jsonWriter;

    private JsonReader jsonReader;

    // EFFECTS: runs the closet application
    public ClosetApp() throws FileNotFoundException {
        runCloset();

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCloset() {
        boolean keepGoing = true;

        init();

        while (keepGoing) {



            displayMenu();
            String firstSelection = input.next().toLowerCase();

            if (firstSelection.equals("q")) {
                keepGoing = false;
            } else {
                try {
                    processCommand(firstSelection);
                } catch (InvalidFitException e) {
                    fail();
                } catch (NotInClosetException e) {
                    failToFind();
                } catch (EmptyClosetException e) {
                    System.out.println("your closet is empty :(");
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes closet
    private void init() {
        closet = new Closet();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add to closet");
        System.out.println("\tw -> wear in closet");
        System.out.println("\tr -> remove from closet");
        System.out.println("\tc -> clean some clothes");
        System.out.println("\ts -> show my clothes");
        System.out.println("\tsave -> save my closet");
        System.out.println("\tload -> load my closet from file");
        System.out.println("\tq -> quit");
    }

    //effects: prints fail message
    private void fail() {
        System.out.println("Sorry, that input is not valid");
    }

    //effects: prints fail message when input is valid but the item is not in the closet
    public void failToFind() {
        System.out.println("Sorry, that item is not here");
    }

    // MODIFIES: this
    // EFFECTS: processes user firstSelection
    private void processCommand(String firstSelection) throws InvalidFitException,
            NotInClosetException, EmptyClosetException {
        if (firstSelection.equals("a")) {
            putInCloset(newClothing());
        } else if (firstSelection.equals("w")) {
            wearInCloset(makeClothing());
        } else if (firstSelection.equals("r")) {
            removeFromCloset(makeClothing());
        } else if (firstSelection.equals("c")) {
            washChoice();
        } else if (firstSelection.equals("s")) {
            try {
                System.out.println("your closet: " + closet.showAllClothes());
            } catch (EmptyClosetException e) {
                System.out.println("your closet is empty :(");
            }
        } else if (firstSelection.equals("save")) {
            saveCloset();
        } else if (firstSelection.equals("load")) {
            loadCloset();
        } else {
            fail();
        }
    }

    //effects: saves the closet to file
    private void saveCloset()  {
        try {
            jsonWriter.open();
            jsonWriter.write(closet);
            jsonWriter.close();
            System.out.println("Saved closet to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, unable to write to file: " + JSON_STORE);
        }
    }

    //modifies:this
    //effects: loads closet from file
    private void loadCloset() {
        try {
            closet = jsonReader.read();
            System.out.println("Loaded closet from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Sorry, unable to load to file: " + JSON_STORE);
        }
    }


    //effects: "creates" Clothing from description given by user
    private Clothing newClothing() throws InvalidFitException {

        clothingTypeMenu();
        String clothingType = input.next().toLowerCase();
        fabricMenu();
        String fabric = input.next().toLowerCase();
        colourMenu();
        String colour = input.next().toLowerCase();
        fitMenu();
        int fit = Integer.parseInt(input.next());
        if (fit < 0 || fit > 2) {
            throw new InvalidFitException();
        }
        return new Clothing(colour, fit, fabric, clothingType);
    }



    //effects: adds c to Closet
    private void putInCloset(Clothing c) throws EmptyClosetException {
        closet.addToCloset(c);
        if (closet.getListOfClothes().isEmpty()) {
            throw new EmptyClosetException();
        }
        System.out.println("you have added to your closet");
        System.out.println("current closet: " + closet.showAllClothes());

    }


    //effects: wears c and adds 1 wear
    private void wearInCloset(Clothing c) {
        c.wear();
        System.out.println(c.clothingToString());
        System.out.println("wears: " + c.getWears());
    }




    //effects: removes c from closet
    private void removeFromCloset(Clothing c) throws EmptyClosetException {
        closet.removeClothing(c);
        if (closet.getListOfClothes().isEmpty()) {
            throw new EmptyClosetException();
        }
        System.out.println("you have removed an item from your closet");
        System.out.println("current closet: " + closet.showAllClothes());
    }




    //effects: if user selects A, proceeds to washOneThing, and if user selects B,
    // proceeds to set all closet item wears to 0
    private void washChoice() {
        System.out.println("would you like to wash 1 item or your whole closet?");
        System.out.println("\ta -> I want to wash one thing");
        System.out.println("\tb -> I want to wash everything");
        String washSelection = input.next().toLowerCase();
        if (washSelection.equals("a")) {
            try {
                washOneThing(makeClothing());
            } catch (InvalidFitException e) {
                fail();
            } catch (NotInClosetException e) {
                failToFind();
            }
        } else if (washSelection.equals("b")) {
            try {
                washAll();
            } catch (EmptyClosetException e) {
                System.out.println("your closet is empty :(");

            }
        }
    }


    //effects: washes 1 selected item
    private void washOneThing(Clothing clothingToBeWashed) throws InvalidFitException {
        clothingToBeWashed.clean();
        System.out.println("clothingToBeWashed.clothingToString()");
        System.out.println("wears: " + clothingToBeWashed.getWears());
    }


    //effects: cleans all items in closet
    private void washAll() throws EmptyClosetException {
        if (closet.getListOfClothes().isEmpty()) {
            throw new EmptyClosetException();
        }
        closet.cleanAllClothes();
        StringBuilder cleanCloset = new StringBuilder();
        for (Clothing c: closet.getListOfClothes()) {
            cleanCloset.append(c.clothingToString()).append("wears: ").append(c.getWears()).append(", ");
        }
        System.out.println(cleanCloset);


    }


    //effects: returns true if clothing specified by user is contained in closet, false otherwise
    private boolean findClothing(String colour, int fit, String fabric, String clothingType) {

        Clothing searchClothing = new Clothing(colour, fit, fabric, clothingType);

        return (closet.containsClothing(searchClothing));
    }


    //effects: collects data from user about specific Clothing
    private Clothing makeClothing() throws InvalidFitException, NotInClosetException {

        clothingTypeMenu();
        String clothingType = input.next().toLowerCase();
        fabricMenu();
        String fabric = input.next().toLowerCase();
        colourMenu();
        String colour = input.next().toLowerCase();
        fitMenu();
        int fit = Integer.parseInt(input.next());
        if (fit < 0 || fit > 2) {
            throw new InvalidFitException();
        } else if (!findClothing(colour, fit, fabric, clothingType)) {
            throw new NotInClosetException();
        }
        return new Clothing(colour, fit, fabric, clothingType);


    }




    //effects: asks user for clothing type
    private void clothingTypeMenu() {
        System.out.println("what kind of clothing? ");
        System.out.println("example: shirt, pants, jacket, hoodie");
    }

    //effects: asks user for colour
    private void colourMenu() {
        System.out.println("what colour is the clothing?");
    }

    //effects: asks user for clothing fabric
    private void fabricMenu() {
        System.out.println("what is the clothing made of?");
        System.out.println("example: cotton, denim, fleece");
    }

    //effects: asks user for clothing fit
    private void fitMenu() {
        System.out.println("What is the fit of the article in question?");
        System.out.println("\t0 -> fitted");
        System.out.println("\t1 -> relaxed");
        System.out.println("\t2 -> baggy");
    }


}



/*
    private String showClothing() {
        String filter = clothesFilteringMenu();
        if (filter.equals("a")) {
            String typeSelection = typeSelectionMenu();
            if (typeSelection.equals("s")) {
                closet.showShirts();
            }
        }
    }

    private String clothesFilteringMenu() {
        System.out.println("Would you like to see your clothes specified by:");
        System.out.println("a -> Type (Pants, Shirts, or Outerwear");
        System.out.println("b -> Colour");
        System.out.println("c -> Fabric");
        System.out.println("d -> Fit");
        System.out.println("e -> Pattern");
        return input.next().toLowerCase();
    }

    private String typeSelectionMenu() {
        System.out.println("Would you like to see your:");
        System.out.println("s -> Shirts");
        System.out.println("o -> Outerwear");
        System.out.println("p -> Pants");
        return input.next().toLowerCase();
    }


    //effects: prints "nothing here is dirty!" if all the clothes in the closet are clean
    //         and "something is dirty" if not
    private void areAllClothesClean() {

        if (closet.getListOfClothes().isEmpty()) {
            System.out.println("there is nothing in this closet");
        }
        boolean cleanSoFar = true;

        for (Clothing c: closet.getListOfClothes()) {
            if (c.isDirty()) {
                cleanSoFar = false;
            }
        }
        if (cleanSoFar) {
            System.out.println("nothing here is dirty!");
        } else {
            System.out.println("something is dirty");
        }
    }

    //effects: determines if 1 or all clothing is being washed
    private void cleaningOneOrMore() {
        String oneOrMore = oneOrMoreMenu();

        if (!oneOrMore.equals("one") && !oneOrMore.equals("all")) {
            fail();
        } else if (oneOrMore.equals("all")) {
            washAllClothes();
        }

    }


    //effects: asks user if they want to wash 1 or all clothing
    public String oneOrMoreMenu() {
        System.out.println("Would you like to clean just 1 specific piece of clothing in your closet?");
        System.out.println("\tone -> yes, just one please");
        System.out.println("\tall -> no, I want to clean everything!");
        return input.next();
    }

    //effects: asks user to choose between the three types of clothing
    private String pickWhatKindOfClothing() {
        System.out.println("What kind of clothing?:");
        System.out.println("\tp -> pants");
        System.out.println("\to -> outerwear");
        System.out.println("\ts -> shirt");
        return input.next().toLowerCase();
    }

    //effects: asks user to input the type of fabric of the clothing they want
    private String getSelectFabric() {
        System.out.print("What is the fabric of the article in question?");
        return input.next().toLowerCase();
    }

    //effects: asks user to input the type of fit of the clothing they are talking about
    private String getSelectFit() {
        System.out.println("What is the fit of the article in question?");
        System.out.println("\t0 -> fitted");
        System.out.println("\t1 -> relaxed");
        System.out.println("\t2 -> baggy");
        return input.next().toLowerCase();
    }

    //effects: asks user to input the type of pattern of the clothing they are talking about
    private String getSelectPattern() {
        System.out.print("What is the pattern of the article in question?");
        return input.next().toLowerCase();
    }

    //effects: asks user to input the colour of the clothing they are talking about
    private String getSelectColour() {
        System.out.print("What is the main colour of the article in question?");
        return input.next().toLowerCase();
    }

    //modifies: this
    //effects: puts a new item into the closet
    private void putInCloset(String clothingType, String fabric, int fit, String pattern, String colour) {
        if (clothingType.equals("p")) {
            closet.addToCloset(makePants(fabric, fit, pattern, colour));
            System.out.println("pants have been added :)");

        } else if (clothingType.equals("o")) {
            closet.addToCloset(makeOuterwear(fabric, fit, pattern, colour));
            System.out.println("outerwear has been added :)");

        } else if (clothingType.equals("s")) {
            closet.addToCloset(makeShirt(fabric, fit, pattern, colour));
            System.out.println("shirt has been added :)");

        } else {
            fail();
        }

    }

    //modifies: this
    //effects: removes a specified item from the closet
    private void removeFromCloset(String clothingType, String fabric, int fit, String pattern, String colour) {
        if (clothingType.equals("p")) {
            if (closet.removeClothing(makePants(fabric, fit, pattern, colour))) {
                System.out.println("pants have been removed :)");
            } else {
                failToFind();
            }

        } else if (clothingType.equals("o")) {
            if (closet.removeClothing(makeOuterwear(fabric, fit, pattern, colour))) {
                System.out.println("outerwear has been removed :)");
            } else {
                failToFind();
            }
        } else if (clothingType.equals("s")) {
            if (closet.removeClothing(makeShirt(fabric, fit, pattern, colour))) {
                System.out.println("shirt has been removed :)");
            } else {
                failToFind();
            }
        } else {
            fail();
        }
    }

    //effects: creates a Pants item
    private Pants makePants(String fabric, int fit, String pattern, String colour) {
        Pants pants = null;
        int pantLength = pantsLengthMenuSelection();

        if (pantLength < 0 || pantLength > 2) {
            fail();
        } else {
            pants = new Pants(fabric, fit, pattern, colour, pantLength);

        }
        return pants;
    }

    //effects: displays a menu of differing pant lengths and returns user input
    private int pantsLengthMenuSelection() {
        System.out.println("What is the length of your pants?");
        System.out.println("\t0 -> short");
        System.out.println("\t1 -> knee length");
        System.out.println("\t2 -> ankle length");
        return Integer.parseInt(input.next());
    }

    //effects: creates an Outerwear item
    private Outerwear makeOuterwear(String fabric, int fit, String pattern, String colour) {
        String isCropped = isCroppedMenu();
        if (!isCropped.equals("true") && !isCropped.equals("false")) {
            fail();
        }

        String isHooded = isHoodedMenu();
        if (!isHooded.equals("true") && !isHooded.equals("false")) {
            fail();
        }

        int sleeveLength = sleeveLengthMenu();
        if (sleeveLength < 0 || sleeveLength > 2) {
            fail();
        }

        return new Outerwear(fabric, fit, pattern, colour,
                Boolean.parseBoolean(isHooded), sleeveLength, Boolean.parseBoolean(isCropped));
    }

    //effects: displays a menu and returns user input on the length of clothing
    private String isCroppedMenu() {
        System.out.println("Is your piece cropped?");
        System.out.println("\true -> cropped");
        System.out.println("\false -> not cropped, it is full length");
        return input.next().toLowerCase();
    }

    //effects: displays a menu and returns user input on the hood of clothing (or lack thereof)
    private String isHoodedMenu() {
        System.out.println("Does your outerwear have a hood?");
        System.out.println("\true -> it has a hood");
        System.out.println("\false -> no hood");
        return input.next().toLowerCase();
    }

    //effects: displays a menu and returns user input on the length of sleeves (or lack thereof)
    private int sleeveLengthMenu() {
        System.out.println("What is the sleeve length?");
        System.out.println("\t0 -> sleeveless");
        System.out.println("\t1 -> short sleeved");
        System.out.println("\t2 -> long sleeved");
        return Integer.parseInt(input.next());
    }

    //effects: displays a menu and returns user input on the collar of clothing (or lack thereof)
    private String isCollaredMenu() {
        System.out.println("Is your shirt collared?");
        System.out.println("\true -> collared");
        System.out.println("\false -> not collared, it has some other neck");
        return input.next().toLowerCase();
    }

    //effects: creates a new shirt item
    private Shirt makeShirt(String fabric, int fit, String pattern, String colour) {
        String isCropped = isCroppedMenu();
        if (!isCropped.equals("true") && !isCropped.equals("false")) {
            fail();
        }

        int sleeveLength = sleeveLengthMenu();
        if (sleeveLength < 0 || sleeveLength > 2) {
            fail();
        }

        String isCollared = isCollaredMenu();
        if (!isCollared.equals("true") && !isCollared.equals("false")) {
            fail();
        }

        return new Shirt(fabric, fit, pattern, colour, Boolean.parseBoolean(isCropped),
                sleeveLength, Boolean.parseBoolean(isCollared));
    }

    //effects: sends user off to different functions depending on if they want to wear shirt, pants, or outerwear
    private void wearInCloset(String clothingType, String fabric, int fit, String pattern, String colour) {

        if (clothingType.equals("p")) {
            searchForPants(fabric, fit, pattern, colour);
        } else if (clothingType.equals("o")) {
            searchForOuterwear(fabric, fit, pattern, colour);
        } else if (clothingType.equals("s")) {
            searchForShirt(fabric, fit, pattern, colour);
        } else {
            fail();
        }
    }

    //effects: searches for pants that the user wants to wear and wears them if found, fails if not
    public void searchForPants(String fabric, int fit, String pattern, String colour) {
        Pants searchPants = makePants(fabric, fit, pattern, colour);
        if (closet.containsClothing(searchPants)) {
            wearPants(searchPants);
        } else {
            failToFind();
        }
    }

    //effects: searches for outerwear that the user wants to wear and wears them if found, fails if not
    public void searchForOuterwear(String fabric, int fit, String pattern, String colour) {
        Outerwear searchOuter = makeOuterwear(fabric, fit, pattern, colour);
        if (closet.containsClothing(searchOuter)) {
            wearOuter(searchOuter);
        } else {
            failToFind();
        }
    }

    //effects: searches for a shirt that the user wants to wear and wears it if found, fails if not
    public void searchForShirt(String fabric, int fit, String pattern, String colour) {
        Shirt searchShirt = makeShirt(fabric, fit, pattern, colour);
        if (closet.containsClothing(searchShirt)) {
            wearShirt(searchShirt);
        } else {
            failToFind();
        }
    }


    //effects: washes a specific clothing item user dictates
    public void washOneThing(String clothingType, String fabric, int fit, String pattern, String colour) {
        if (clothingType.equals("p")) {
            Pants searchPants = makePants(fabric, fit, pattern, colour);
            if (closet.containsClothing(searchPants)) {
                washPants(searchPants);
            }
        } else if (clothingType.equals("o")) {
            Outerwear searchOuter = makeOuterwear(fabric, fit, pattern, colour);
            if (closet.containsClothing(searchOuter)) {
                washOuter(searchOuter);
            }
        } else if (clothingType.equals("s")) {
            Shirt searchShirt = makeShirt(fabric, fit, pattern, colour);
            if (closet.containsClothing(searchShirt)) {
                washShirt(searchShirt);
            }
        } else {
            fail();
        }
    }



    //modifies: p
    //effects: wears pants and bring up wear count 1 time
    public void wearPants(Pants c) {
        Pants replacement;
        closet.removeClothing(c);
        replacement = new Pants(c.getFabric(), c.getFit(), c.getPattern(), c.getColour(),c.getLength());
        int i = c.getWears() + 1;
        replacement.setWears(i);
        closet.addToCloset(replacement);

        System.out.println("you have worn your pants :)");
    }

    //modifies: p
    //effects: washes pants, aka bringing wear count to 0
    public void washPants(Pants p) {
        Pants replacement;
        closet.removeClothing(p);
        replacement = new Pants(p.getFabric(), p.getFit(), p.getPattern(), p.getColour(),p.getLength());
        closet.addToCloset(replacement);

        System.out.println("you have washed your pants :)");
    }

    //modifies: o
    //effects: wears outerwear and bring up wear count 1 time
    public void wearOuter(Outerwear o) {
        Outerwear replacement;
        closet.removeClothing(o);
        replacement = new Outerwear(o.getFabric(), o.getFit(), o.getPattern(), o.getColour(), o.getIsHooded(),
                o.getSleeveLength(), o.getIsCropped());
        int i = o.getWears() + 1;
        replacement.setWears(i);
        closet.addToCloset(replacement);

        System.out.println("you have worn your outerwear :)");
    }

    //modifies: o
    //effects: washes outerwear, aka bringing wear count to 0
    public void washOuter(Outerwear o) {
        Outerwear replacement;
        closet.removeClothing(o);
        replacement = new Outerwear(o.getFabric(), o.getFit(), o.getPattern(), o.getColour(), o.getIsHooded(),
                o.getSleeveLength(), o.getIsCropped());
        closet.addToCloset(replacement);

        System.out.println("you have washed your outerwear :)");
    }

    //modifies: s
    //effects: wears shirt and bring up wear count 1 time
    public void wearShirt(Shirt s) {
        Shirt replacement;
        closet.removeClothing(s);
        replacement = new Shirt(s.getFabric(), s.getFit(), s.getPattern(), s.getColour(), s.getIsCropped(),
                s.getSleeveLength(), s.getIsCollared());
        int i = s.getWears() + 1;
        replacement.setWears(i);
        closet.addToCloset(replacement);

        System.out.println("you have worn your shirt :)");
    }

    //modifies: s
    //effects: washes shirt, aka bringing wear count to 0
    public void washShirt(Shirt s) {
        Shirt replacement;
        closet.removeClothing(s);
        replacement = new Shirt(s.getFabric(), s.getFit(), s.getPattern(), s.getColour(), s.getIsCropped(),
                s.getSleeveLength(), s.getIsCollared());
        closet.addToCloset(replacement);

        System.out.println("you have washed your shirt :)");
    }







}
*/