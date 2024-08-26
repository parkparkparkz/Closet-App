package persistence;

import model.Closet;
import model.Clothing;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Closet closet = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCloset() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCloset.json");
        try {
            Closet closet = reader.read();
            assertTrue(closet.getListOfClothes().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCloset() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCloset.json");
        try {
            Closet closet = reader.read();
            ArrayList<Clothing> clothings = closet.getListOfClothes();
            assertEquals(2, clothings.size());
            checkClothing("white", 1, "cotton", "shirt", clothings.get(0));
            checkClothing("blue", 2, "denim", "pants", clothings.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
