package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

//describes a list of the Clothing objects
public class Closet {

    private ArrayList<Clothing> listOfClothes;




    //effects: constructs a closet with an empty list of clothes
    public Closet() {

        this.listOfClothes = new ArrayList<>();
        //EventLog eventLog = EventLog.getInstance();
    }

    //effects: constructs a closet with a non-empty list of clothes;
    public Closet(ArrayList<Clothing> listOfClothes) {
        this.listOfClothes = listOfClothes;
        EventLog.getInstance().logEvent(new Event("loaded closet from file"));
    }

    public ArrayList<Clothing> getListOfClothes() {
        return this.listOfClothes;
    }

    //modifies: this
    //effect: adds a clothing item to the closet
    public void addToCloset(Clothing c) {

        this.listOfClothes.add(c);
        EventLog.getInstance().logEvent(new Event("Added to closet."));
    }


    //effects: returns true if this contains c
    public boolean containsClothing(Clothing c) {
        return this.listOfClothes.contains(c);
    }

    //modifies: this
    //effects: returns true and removes item if the item was in the list and false otherwise
    public boolean removeClothing(Clothing c) {
        if (this.containsClothing(c)) {
            this.listOfClothes.remove(c);
            return true;
        } else {
            return false;
        }
    }

    /*
    //effects: creates and returns a new list of clothes that only contain Pants
    public ArrayList<Clothing> allPants() {
        ArrayList<Clothing> pantsList = new ArrayList<Clothing>();
        for (Clothing c : this.listOfClothes) {
            if (c.getClass() == Pants.class) {
                pantsList.add(c);
            }
        }
        return pantsList;

    }

    //effects: creates and returns a new list of clothes that only contain Shirts
    public HashSet<Shirt> allShirts() {
        HashSet<Shirt> shirtsList = new HashSet<Shirt>();
        for (Clothing c : this.listOfClothes) {
            if (c.getClass() == Shirt.class) {
                Shirt newShirt = new Shirt(c.getFabric(), c.getFit(), c.getPattern(), c.getColour(),
                        ((Shirt) c).getIsCropped(), ((Shirt) c).getSleeveLength(), ((Shirt) c).getIsCollared());
                shirtsList.add(newShirt);
            }
        }
        return shirtsList;

    }

    //effects: creates and returns a new list of clothes that only contains Outerwear
    public HashSet<Clothing> allOuterwear() {
        HashSet<Clothing> outerwearList = new HashSet<Clothing>();
        for (Clothing c : this.listOfClothes) {
            if (c.getClass() == Outerwear.class) {
                outerwearList.add(c);
            }
        }
        return outerwearList;

    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Closet)) {
            return false;
        }
        Closet closet = (Closet) o;
        return Objects.equals(getListOfClothes(), closet.getListOfClothes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListOfClothes());
    }

    //effects: creates and returns a list of all the clean clothes in this
    public ArrayList<Clothing> allCleanClothing() {
        ArrayList<Clothing> cleanClothes = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {

            if (!c.isDirty()) {
                cleanClothes.add(c);
            }
        }
        return cleanClothes;
    }

    //effects: creates and returns a list of all dirty clothes in this
    public ArrayList<Clothing> allDirtyClothing() {
        ArrayList<Clothing> dirtyClothes = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.isDirty()) {
                dirtyClothes.add(c);
            }
        }
        return dirtyClothes;
    }

    //modifies: this
    //effects: empties closet
    public void clearCloset() {
        this.listOfClothes = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Closet cleared."));
    }

    //requires: fit must be 0, 1, or 2
    //effects: creates and returns a new list of clothes only with the specified fit
    public ArrayList<Clothing> allOneFit(int fit) {
        ArrayList<Clothing> oneFit = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.getFit() == fit) {
                oneFit.add(c);
            }
        }
        return oneFit;
    }


    //effects: creates and returns a new list of clothes only with the specified fabric
    public ArrayList<Clothing> allOneFabric(String fabric) {
        ArrayList<Clothing> oneFabric = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.getFabric().equals(fabric)) {
                oneFabric.add(c);
            }
        }
        return oneFabric;
    }

    /*
    //requires: input must be a kind of pattern
    //effects: creates and returns a new list of clothes only with the specified pattern
    public ArrayList<Clothing> allOnePattern(String pattern) {
        ArrayList<Clothing> oneFabric = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.getPattern().equals(pattern)) {
                oneFabric.add(c);
            }
        }
        return oneFabric;
    }
*/


    //effects: creates and returns a new list of clothes only with the specified colour
    public ArrayList<Clothing> allOneColour(String colour) {
        ArrayList<Clothing> oneColour = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.getColour().equals(colour)) {
                oneColour.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Closet filtered by colour."));
        return oneColour;

    }


    //effects: creates and returns a new list of clothes only with the specified colour
    public ArrayList<Clothing> allOneClothingType(String clothingType) {
        ArrayList<Clothing> oneType = new ArrayList<>();
        for (Clothing c : this.listOfClothes) {
            if (c.getClothingType().equals(clothingType)) {
                oneType.add(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Closet filtered by type."));
        return oneType;
    }


    //effects: returns the list of Outerwear as readable string instead of a list of objects
  /*  public String showOuterwear(HashSet<Outerwear> listOfOuterwear) {
        String allOuterwear = "";
        for (Outerwear o : listOfOuterwear) {
            allOuterwear = allOuterwear + o.outerwearToString();
        }
        return allOuterwear;
    }

    //effects: returns the list of Shirts as readable string instead of a list of objects
    public String showShirts() {
        HashSet<Shirt> listOfShirts = this.allShirts();
        String allShirts = "";
        for (Shirt s: listOfShirts) {
            allShirts = allShirts + s.shirtToString();
        }
        return allShirts;
    }

    //effects: returns the list of Pants as readable string instead of a list of objects
    public String showPants() {
        HashSet<Shirt> listOfShirts = this.allShirts();
        String allPants = "";
        for (Shirt s: listOfShirts) {
            allPants = allPants + s.shirtToString();
        }
        return allPants;
    }
*/
    //modifies: this
    //effects: sets all clothing wear counts to 0;
    public void cleanAllClothes() {
        for (Clothing c : this.listOfClothes) {
            c.setWears(0);
        }
    }


    //effects: returns String of all the clothing in the closet
    public String showAllClothes() throws EmptyClosetException {
        if (this.listOfClothes.isEmpty()) {
            throw new EmptyClosetException();
        }
        String closet = "";
        for (Clothing c : this.listOfClothes) {
            closet = closet + c.clothingToString() + ", ";
        }
        return closet;
    }


    //effects: creates a JSONArray from a Closet (specifically, the list of clothing)
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : this.listOfClothes) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}

    /*
    // EFFECTS: returns things in this closet as a JSON array
    public JSONArray closetToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Clothing c : this.listOfClothes) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
*/

/*
    //effects: returns a readable String describing all the items in this
    public String showAllClothes() {
        String describeCloset = "";
        for (Clothing c: this.listOfClothes) {
            if (c.getClass() == Shirt.class) {
                String shirt = ((Shirt) c).shirtToString();
                describeCloset = describeCloset + shirt;
            }
            if (c.getClass() == Pants.class) {
                String pants = ((Pants) c).pantsToString();
                describeCloset = describeCloset + pants;
            }
            if (c.getClass() == Outerwear.class) {
                String outer = ((Outerwear) c).outerwearToString();
                describeCloset = describeCloset + outer;
            }
        }
        return describeCloset;
    }
}

*/
