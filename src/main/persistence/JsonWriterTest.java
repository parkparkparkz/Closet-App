package persistence;

import model.Closet;
import model.Clothing;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Closet closet = new Closet();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterEmptyCloset() {
        try {
            Closet closet = new Closet();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCloset.json");
            writer.open();
            writer.write(closet);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCloset.json");
            closet = reader.read();
            assertTrue(closet.getListOfClothes().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralCloset() {
        try {
            Closet closet = new Closet();
            closet.addToCloset(new Clothing("white", 1, "cotton", "shirt"));
            closet.addToCloset(new Clothing("blue", 2, "denim", "pants"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCloset.json");
            writer.open();
            writer.write(closet);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCloset.json");
            closet = reader.read();
            ArrayList<Clothing> listOfClothes = closet.getListOfClothes();
            assertEquals(2, listOfClothes.size());
            checkClothing("white", 1, "cotton", "shirt", listOfClothes.get(0));
            checkClothing("blue", 2, "denim", "pants", listOfClothes.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
