package persistence;

import model.Closet;
import model.Clothing;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkClothing(String colour, int fit, String fabric, String clothingType, Clothing c) {
        assertEquals(colour, c.getColour());
        assertEquals(fit, c.getFit());
        assertEquals(fabric, c.getFabric());
        assertEquals(clothingType, c.getClothingType());
    }

}
