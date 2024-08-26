import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ClosetTest {
    private Closet testCloset;


    //shirts and pants
    //
    private Clothing testClothing1;
    private Clothing testClothing2;
    private Clothing testClothing3;
    private Clothing testClothing4;
    private Clothing testClothing5;
    private Clothing testClothing6;

    @BeforeEach
    void runBefore() {
        testCloset = new Closet();
        //white fitted linen shirt
        testClothing1 = new Clothing("white", 0, "linen", "shirt");
        //blue baggy cotton shirt
        testClothing2 = new Clothing("blue", 2, "cotton", "shirt");
        //black relaxed linen shirt
        testClothing3 = new Clothing("black", 1, "linen", "shirt");
        //white cotton baggy pants
        testClothing4 = new Clothing("white", 2, "cotton", "pants");
        //blue relaxed linen pants
        testClothing5 = new Clothing("blue", 1, "linen", "pants");
        //black cotton baggy pants
        testClothing6 = new Clothing("black", 2, "cotton", "pants");

        testCloset.addToCloset(testClothing1);
        testCloset.addToCloset(testClothing2);
        testCloset.addToCloset(testClothing3);
        testCloset.addToCloset(testClothing4);
        testCloset.addToCloset(testClothing5);
        testCloset.addToCloset(testClothing6);

        testClothing1.setWears(5);
        testClothing2.setWears(4);
        testClothing3.setWears(3);
    }

    @Test
    public void testClosetConstructor() {
        assertTrue(testCloset.containsClothing(testClothing1));
        assertTrue(testCloset.containsClothing(testClothing2));

    }

    @Test
    public void testRemoveFromCloset() {
        assertTrue(testCloset.removeClothing(testClothing2));
        assertFalse(testCloset.containsClothing(testClothing2));

    }

    @Test
    public void testCleanAllClothes() {
        assertTrue(testClothing1.isDirty());
        assertTrue(testClothing2.isDirty());
        assertTrue(testClothing3.isDirty());
        testCloset.cleanAllClothes();
        assertFalse(testClothing1.isDirty());
        assertFalse(testClothing2.isDirty());
        assertFalse(testClothing3.isDirty());
    }

    @Test
    public void testGetCleanClothes() {
        ArrayList<Clothing> cleanClothes = testCloset.allCleanClothing();
        assertFalse(cleanClothes.contains(testClothing1));
        assertFalse(cleanClothes.contains(testClothing2));
        assertFalse(cleanClothes.contains(testClothing3));
        assertTrue(cleanClothes.contains(testClothing4));
        assertTrue(cleanClothes.contains(testClothing5));
        assertTrue(cleanClothes.contains(testClothing6));
    }

    @Test
    public void testAllDirty() {
        ArrayList<Clothing> dirtyClothes = testCloset.allDirtyClothing();
        assertTrue(dirtyClothes.contains(testClothing1));
        assertTrue(dirtyClothes.contains(testClothing2));
        assertTrue(dirtyClothes.contains(testClothing3));
        assertFalse(dirtyClothes.contains(testClothing4));
        assertFalse(dirtyClothes.contains(testClothing5));
        assertFalse(dirtyClothes.contains(testClothing6));

    }


    @Test
    public void testAllShirts() {
        ArrayList<Clothing> allShirts = testCloset.allOneClothingType("shirt");
        assertTrue(allShirts.contains(testClothing1));
        assertTrue(allShirts.contains(testClothing2));
        assertTrue(allShirts.contains(testClothing3));
        assertFalse(allShirts.contains(testClothing4));
        assertFalse(allShirts.contains(testClothing5));
        assertFalse(allShirts.contains(testClothing6));

    }

    @Test
    public void testAllPants() {
        ArrayList<Clothing> allPants = testCloset.allOneClothingType("pants");
        assertFalse(allPants.contains(testClothing1));
        assertFalse(allPants.contains(testClothing2));
        assertFalse(allPants.contains(testClothing3));
        assertTrue(allPants.contains(testClothing4));
        assertTrue(allPants.contains(testClothing5));
        assertTrue(allPants.contains(testClothing6));


    }

    @Test
    public void testAllOneFit() {
        ArrayList<Clothing> allBaggy = testCloset.allOneFit(2);
        assertFalse(allBaggy.contains(testClothing1));
        assertTrue(allBaggy.contains(testClothing2));
        assertFalse(allBaggy.contains(testClothing3));
        assertTrue(allBaggy.contains(testClothing4));
        assertFalse(allBaggy.contains(testClothing5));
        assertTrue(allBaggy.contains(testClothing6));


    }

    @Test
    public void testAllOneFabric() {
        ArrayList<Clothing> allCotton = testCloset.allOneFabric("cotton");
        assertFalse(allCotton.contains(testClothing1));
        assertTrue(allCotton.contains(testClothing2));
        assertFalse(allCotton.contains(testClothing3));
        assertTrue(allCotton.contains(testClothing4));
        assertFalse(allCotton.contains(testClothing5));
        assertTrue(allCotton.contains(testClothing6));
    }

    @Test
    public void testAllOneColour() {
        ArrayList<Clothing> allBlue = testCloset.allOneColour("blue");
        assertFalse(allBlue.contains(testClothing1));
        assertTrue(allBlue.contains(testClothing2));
        assertFalse(allBlue.contains(testClothing3));
        assertFalse(allBlue.contains(testClothing4));
        assertTrue(allBlue.contains(testClothing5));
        assertFalse(allBlue.contains(testClothing6));
    }

    @Test
    public void testShowAllClothes() throws EmptyClosetException {
        String allClothes = "white fitted linen shirt, blue baggy cotton shirt, black relaxed linen shirt, " +
                "white baggy cotton pants, blue relaxed linen pants, black baggy cotton pants, ";
        assertEquals(allClothes, testCloset.showAllClothes());
        try {
            testCloset.showAllClothes();
        } catch (EmptyClosetException e) {
            fail();
            System.out.println("caught empty closet exception");
        }
    }

    @Test
    public void testShowAllClothesEmpty() throws EmptyClosetException {
        Closet emptyCloset = new Closet();
        try {
            emptyCloset.showAllClothes();
            fail();
            System.out.println("caught empty closet exception");
        } catch (EmptyClosetException e) {

        }
    }

    @Test
    public void testHashCode() {
        Closet compareCloset = new Closet();
        Closet compareCloset2 = new Closet();
        //white fitted linen shirt
        Clothing compareClothing1 = new Clothing("white", 0, "linen", "shirt");
        //blue baggy cotton shirt
        Clothing compareClothing2 = new Clothing("blue", 2, "cotton", "shirt");
        //black relaxed linen shirt
        Clothing compareClothing3 = new Clothing("black", 1, "linen", "shirt");
        //white cotton baggy pants
        Clothing compareClothing4 = new Clothing("white", 2, "cotton", "pants");
        //blue relaxed linen pants
        Clothing compareClothing5 = new Clothing("blue", 1, "linen", "pants");
        //black cotton baggy pants
        Clothing compareClothing6 = new Clothing("black", 2, "cotton", "pants");

        compareCloset.addToCloset(compareClothing1);
        compareCloset.addToCloset(compareClothing2);
        compareCloset.addToCloset(compareClothing3);
        compareCloset.addToCloset(compareClothing4);
        compareCloset.addToCloset(compareClothing5);
        compareCloset.addToCloset(compareClothing6);

        compareCloset2.addToCloset(compareClothing3);
        compareCloset2.addToCloset(compareClothing4);
        compareCloset2.addToCloset(compareClothing5);

        assertTrue(compareCloset.hashCode() == testCloset.hashCode());
        assertFalse(compareCloset2.hashCode() == testCloset.hashCode());
    }

    @Test
    public void testEquals() {
        Closet compareCloset = new Closet();
        Closet compareCloset2 = new Closet();
        //white fitted linen shirt
        Clothing compareClothing1 = new Clothing("white", 0, "linen", "shirt");
        //blue baggy cotton shirt
        Clothing compareClothing2 = new Clothing("blue", 2, "cotton", "shirt");
        //black relaxed linen shirt
        Clothing compareClothing3 = new Clothing("black", 1, "linen", "shirt");
        //white cotton baggy pants
        Clothing compareClothing4 = new Clothing("white", 2, "cotton", "pants");
        //blue relaxed linen pants
        Clothing compareClothing5 = new Clothing("blue", 1, "linen", "pants");
        //black cotton baggy pants
        Clothing compareClothing6 = new Clothing("black", 2, "cotton", "pants");

        compareCloset.addToCloset(compareClothing1);
        compareCloset.addToCloset(compareClothing2);
        compareCloset.addToCloset(compareClothing3);
        compareCloset.addToCloset(compareClothing4);
        compareCloset.addToCloset(compareClothing5);
        compareCloset.addToCloset(compareClothing6);

        compareCloset2.addToCloset(compareClothing3);
        compareCloset2.addToCloset(compareClothing4);
        compareCloset2.addToCloset(compareClothing5);

        assertTrue(testCloset.equals(compareCloset));
        assertFalse(testCloset.equals(compareCloset2));
    }

    /*





*/



   /* //showAllClothesTest
    @Test
    public void testShowAllClothesEmpty() {
        Closet emptyCloset = new Closet();
        assertEquals("", emptyCloset.showAllClothes());

    }

*/

}
