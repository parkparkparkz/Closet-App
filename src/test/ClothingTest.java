import model.Clothing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClothingTest {

    private Clothing testClothing1 = new Clothing("blue", 1, "denim", "pants");

    @BeforeEach
    public void runBefore() {
        testClothing1.wear();
    }


    @Test
    public void testConstructor() {
        assertEquals("blue", testClothing1.getColour());
        assertEquals(1, testClothing1.getFit());
        assertEquals("denim", testClothing1.getFabric());
        assertEquals("pants", testClothing1.getClothingType());
    }

    @Test
    public void testHashCode() {
        Clothing compareClothing1 = new Clothing("blue", 1, "denim", "pants");
        Clothing compareClothing2 = new Clothing("white", 1, "denim", "pants");
        assertTrue(compareClothing1.hashCode() == testClothing1.hashCode());
        assertFalse(compareClothing2.hashCode() == testClothing1.hashCode());
    }

    @Test
    public void testEquals() {
        Clothing compareClothing1 = new Clothing("blue", 1, "denim", "pants");
        Clothing compareClothing2 = new Clothing("white", 1, "denim", "pants");
        assertTrue(testClothing1.equals(compareClothing1));
        assertFalse(testClothing1.equals(compareClothing2));
    }


    @Test
    public void testClean() {
        assertEquals(1, testClothing1.getWears());
        assertFalse(testClothing1.isDirty());
        testClothing1.clean();
        assertEquals(0, testClothing1.getWears());
        assertFalse(testClothing1.isDirty());
    }

    @Test
    public void testClothingToString() {
        assertEquals("blue relaxed denim pants", testClothing1.clothingToString());
    }



}
