package ncsu.edu.csc116.hearts.game;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Card class
 * 
 * @author Jessica Young Schmidt
 * @author Yousif Mansour
 */
public class CardTest {

    /** three of clubs */
    private Card c3;
    
    /** eight of diamonds */
    private Card d8;
    
    /** queen of spades */
    private Card s12;
    
    /** ace of hearts */
    private Card h14;
    
    /** two of diamonds */
    private Card d2;
    
    /** two of diamonds copy */
    private Card d2copy;
    
    /** three of spades */
    private Card s3;
    
    /** four of clubs */
    private Card c4;

    /**
     * Sets up fields for testing
     */
    @BeforeEach
    public void setUp() {
        c3 = new Card('c', 3);
        d8 = new Card('d', 8);
        s12 = new Card('s', 12);
        h14 = new Card('h', 14);
        d2 = new Card('d', 2);
        d2copy = new Card('d', 2);
        d2copy.setPlayed(true);
        s3 = new Card('s', 3);
        c4 = new Card('c', 4);
    }

    /**
     * Test invalid constructor parameters
     */
    @Test
    public void testInvalidConstructorParameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Card('a', 33),
                "invalid suit");
        assertEquals("Invalid suit", exception.getMessage(), "invalid suit - exception message");

        exception = assertThrows(IllegalArgumentException.class, () -> new Card('c', 1),
                "invalid value (1)");
        assertEquals("Invalid value", exception.getMessage(),
                "invalid value (1) - exception message");

        // NOTE: You are not required to add additional invalid parameter test. However,
        // you may add tests if you would like. Not all paths through your Card
        // constructors will be tested by the given tests above.
    }

    /**
     * Test getSuit for three of clubs
     */
    @Test
    public void testGetSuitA() {
        assertEquals('c', c3.getSuit(), "Test getSuit for three of clubs");
    }
    
    /**
     * Test getSuit for eight of diamonds
     */
    @Test
    public void testGetSuitB() {
        assertEquals('d', d8.getSuit(), "Test getSuit for eight of diamonds");
    }
    
    /**
     * Test getSuit for queen of spades
     */
    @Test
    public void testGetSuitC() {
        assertEquals('s', s12.getSuit(), "Test getSuit for queen of spades");
    }
    
    /**
     * Test getSuit for ace of hearts
     */
    @Test
    public void testGetSuitD() {
        assertEquals('h', h14.getSuit(), "Test getSuit for ace of hearts");
    }

    /**
     * Test getValue for three of clubs
     */
    @Test
    public void testGetValueA() {
        assertEquals(3, c3.getValue(), "Test getValue for three of clubs");
    }
    
    /**
     * Test getValue for eight of diamonds
     */
    @Test
    public void testGetValueB() {
        assertEquals(8, d8.getValue(), "Test getValue for eight of diamonds");
    }
    
    /**
     * Test getValue for queen of spades
     */
    @Test
    public void testGetValueC() {
        assertEquals(12, s12.getValue(), "Test getValue for queen of spades");
    }
    
    /**
     * Test getValue for ace of hearts
     */
    @Test
    public void testGetValueD() {
        assertEquals(14, h14.getValue(), "Test getValue for ace of hearts");
    }

    /**
     * Test hasBeenPlayed for three of clubs
     */
    @Test
    public void testHasBeenPlayed() {
        assertFalse(c3.hasBeenPlayed(), "Test hasBeenPlayed for three of clubs");
    }
    
    /**
     * Test hasBeenPlayed for three of clubs
     */
    @Test
    public void testSetPlayed() {
        c3.setPlayed(true);
        assertTrue(c3.hasBeenPlayed(), "Test hasBeenPlayed for three of clubs");
    }

    /**
     * Test isHeart for three of clubs
     */
    @Test
    public void testIsHeartA() {
        assertFalse(c3.isHeart(), "Test isHeart for three of clubs");
    }

    /**
     * Test isHeart for ace of hearts
     */
    @Test
    public void testIsHeartB() {
        assertTrue(h14.isHeart(), "Test isHeart for ace of hearts");
    }

    /**
     * Test toString for three of clubs
     */
    @Test
    public void testToStringA() {
        assertEquals("c3", c3.toString(), "Test toString for three of clubs");
    }
    
    /**
     * Test toString for eight of diamonds
     */
    @Test
    public void testToStringB() {
        assertEquals("d8", d8.toString(), "Test toString for eight of diamonds");
    }
    
    /**
     * Test toString for queen of spades
     */
    @Test
    public void testToStringC() {
        assertEquals("s12", s12.toString(), "Test toString for queen of spades");
    }
    
    /**
     * Test toString for ace of hearts
     */
    @Test
    public void testToStringD() {
        assertEquals("h14", h14.toString(), "Test toString for ace of hearts");
    }

    /**
     * Test equals for three of clubs
     */
    @Test
    public void testEqualsA() {
        assertTrue(c3.equals(c3), "Test equals for three of clubs");
    }
    
    /**
     * Test equals for three of clubs and three of hearts
     */
    @Test
    public void testEqualsB() {
        Card h3 = new Card('h', 3);
        assertFalse(c3.equals(h3), "Test equals for three of clubs and three of hearts");
    }
    
    /**
     * Test equals for three of clubs and eight of clubs
     */
    @Test
    public void testEqualsC() {
        Card c8 = new Card('c', 8);
        assertFalse(c8.equals(c3), "Test equals for three of clubs and eight of clubs");
    }
    
    /**
     * Test equals for two of diamonds
     */
    @Test
    public void testEqualsD() {
        assertFalse(d2.equals(d2copy), "Test equals for two of diamonds");
    }

    /**
     * Test compareTo for three of clubs and three of spades
     */
    @Test
    public void testCompareToA() {
        assertTrue(c3.compareTo(s3) < 0, "Test compareTo for (3) of clubs and (3) of spades");
    }
    
    /**
     * Test compareTo for ace of hearts and eight of diamonds
     */
    @Test
    public void testCompareToB() {
        assertTrue(h14.compareTo(d8) > 0, "Test compareTo for (14) of hearts and (8) of diamonds");
    }
    
    /**
     * Test compareTo for queen of spades and two of diamonds
     */
    @Test
    public void testCompareToC() {
        assertTrue(s12.compareTo(d2) > 0, "Test compareTo for (12) of spades and (2) of diamonds");
    }
    
    /**
     * Test compareTo for four of clubs and eight of diamonds
     */
    @Test
    public void testCompareToD() {
        assertTrue(c4.compareTo(d8) < 0, "Test compareTo for (4) of clubs and (8) of diamonds");
    }
    
    /**
     * Test compareTo for two of diamonds
     */
    @Test
    public void testCompareToE() {
        assertTrue(d2.compareTo(d2copy) == 0, "Test compareTo for (2) of diamonds");
    }

    /**
     * Test isQueenOfSpades for three of clubs
     */
    @Test
    public void testIsQueenOfSpadesA() {
        assertFalse(c3.isQueenOfSpades(), "Test isQueenOfSpades for three of clubs");

    }
    
    /**
     * Test isQueenOfSpades for queen of spades
     */
    @Test
    public void testIsQueenOfSpadesB() {
        assertTrue(s12.isQueenOfSpades(), "Test isQueenOfSpades for queen of spades");
    }
    
    /**
     * Test isQueenOfSpades for three of spades
     */
    @Test
    public void testIsQueenOfSpadesC() {
        assertFalse(s3.isQueenOfSpades(), "Test isQueenOfSpades for three of spades");
    }

    /**
     * Test isHigherThan for four of clubs and three of clubs
     */
    @Test
    public void testIsHigherA() {
        assertTrue(c4.isHigherThan(c3), "Test isHigherThan for four of clubs and three of clubs");
    }
    
    /**
     * Test isHigherThan for ace of spades and two of diamonds
     */
    @Test
    public void testIsHigherB() {
        assertFalse(s12.isHigherThan(d2), "Test isHigherThan for ace of spades and 2 of diamonds");
    }
    
    /**
     * Test isHigherThan for two of diamonds and eight of diamonds
     */
    @Test
    public void testIsHigherC() {
        assertFalse(d2.isHigherThan(d8), "Test isHigherThan for two and eight of diamonds");
    }

    /**
     * Tests values for public constants
     */
    @Test
    public void testClassConstants() {
        assertEquals('c', Card.CLUBS, "Test CLUBS constant");
        assertEquals('d', Card.DIAMONDS, "Test DIAMONDS constant");
        assertEquals('s', Card.SPADES, "Test SPADES constant");
        assertEquals('h', Card.HEARTS, "Test HEARTS constant");
        assertEquals(2, Card.LOWEST_VALUE, "Test LOWEST_VALUE constant");
        assertEquals(14, Card.HIGHEST_VALUE, "Test HIGHEST_VALUE constant");
        assertEquals(12, Card.QUEEN_VALUE, "Test QUEEN_VALUE constant");
    }

}
