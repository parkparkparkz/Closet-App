package persistence;

import model.Closet;
import model.Clothing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.json.JSONArray;

import org.json.*;

// Represents a reader that reads closet from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads closet from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Closet read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseCloset(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses closet from JSON object and returns it
    private Closet parseCloset(JSONArray jsonArray) {
        Closet closet;
        closet = new Closet(addAllClothing(jsonArray));
        return closet;
    }

    // MODIFIES: closet
    // EFFECTS: parses clothing(plural) from JSON object and returns them in a list
    private ArrayList<Clothing> addAllClothing(JSONArray jsonArray) {
        ArrayList<Clothing> listOfClothing = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextClothing = (JSONObject) json;
            listOfClothing.add(parseClothing(nextClothing));
        }
        return listOfClothing;
    }

    // MODIFIES: closet
    // EFFECTS: parses clothing from JSON object
    private Clothing parseClothing(JSONObject jsonObject) {
        String colour = jsonObject.getString("colour");
        String fabric = jsonObject.getString("fabric");
        int fit = jsonObject.getInt("fit");
        String clothingType = jsonObject.getString("clothingType");
        Clothing clothing = new Clothing(colour, fit, fabric, clothingType);
        return clothing;
    }
}