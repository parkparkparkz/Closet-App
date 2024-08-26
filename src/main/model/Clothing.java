package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

//represents a piece of clothing with a colour, fit, fabric, type, and wears
public class Clothing implements Writable {

    String colour;

    int fit; //0 = tight, 1 = relaxed, 2 = baggy

    String fabric;
    String clothingType;

    int wears; //number of wears on a piece of clothing since last wash

    static final int MAX_WEARS = 3;

    public Clothing(String colour, int fit, String fabric, String clothingType) {
        this.colour = colour;
        this.fit = fit;
        this.fabric = fabric;
        this.clothingType = clothingType;
        this.wears = 0;
    }

    //modifies: this
    //effects: cleaned clothing piece is as clean as it can be and wear count becomes 0
    public void clean() {
        this.wears = 0;
    }


    //modifies: this
    //effects: adds 1 wear to this
    public void wear() {
        this.wears++;
    }

    public void setWears(int i) {
        this.wears = i;
    }


    //effects: returns true if the clothing is dirty and false if it is clean
    public boolean isDirty() {
        return (this.wears >= MAX_WEARS);
    }

    public String getColour() {
        return this.colour;
    }

    public String getClothingType() {
        return this.clothingType;
    }

    public String getFabric() {
        return this.fabric;
    }


    public int getFit() {
        return this.fit;
    }

    public int getWears() {
        return this.wears;

    }


    //effects: retrieves the string interpretation of a clothing object
    public String clothingToString() {
        String fit = "";
        if (this.fit == 0) {
            fit = "fitted";
        } else if (this.fit == 1) {
            fit = "relaxed";
        } else if (this.fit == 2) {
            fit = "baggy";
        }

        return (this.colour + " " + fit + " " + this.fabric + " " + this.clothingType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clothing)) {
            return false;
        }
        Clothing clothing = (Clothing) o;
        return getFit() == clothing.getFit() && Objects.equals(getColour(),
                clothing.getColour()) && Objects.equals(getFabric(), clothing.getFabric())
                && Objects.equals(getClothingType(), clothing.getClothingType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColour(), getFit(), getFabric(), getClothingType());
    }


    //effects: creates a JSONObject from this
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("colour", this.colour);
        json.put("fit", this.fit);
        json.put("fabric", this.fabric);
        json.put("clothingType", this.clothingType);
        return json;

    }
}
