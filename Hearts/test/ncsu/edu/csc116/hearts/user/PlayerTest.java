package ncsu.edu.csc116.hearts.user;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ncsu.edu.csc116.hearts.game.Card;

/**
 * Tests Player class
 * 
 * @author Jessica Young Schmidt
 * @author Yousif Mansour
 */
public class PlayerTest {

    /** Test player */
    private Player testPlayer;

    /** Three of clubs */
    private Card c3;

    /**
     * Set up fields for tests
     */
    @BeforeEach
    public void setUp() {
        testPlayer = new Player("Human");
        c3 = new Card('c', 3);
    }

    /**
     * test Constructor initalizes with correct initial values
     */
    @Test
    public void testConstructor() {
        assertEquals("Human", testPlayer.getName(), "Test constructor: getName");
        for (int i = 0; i < 13; i++) {
            assertNull(testPlayer.getCard(i), "Test that card " + i + " is null");
        }
        assertEquals(0, testPlayer.getHandPoints(), "Test constructor: getHandPoints");
        assertEquals(0, testPlayer.getOverallPoints(), "Test constructor: getOverallPoints");
    }
    
    /**
     * test addCard with one card
     */
    @Test
    public void testAddCardA() {
        // Test that there are no cards in hand
        String id = "test that first card of hand is null";
        assertNull(testPlayer.getCard(0), id);

        // Add a card to the hand
        testPlayer.addCard(c3);
        id = "test that card is added as first card";
        assertEquals(new Card('c', 3), testPlayer.getCard(0), id);
        for (int i = 1; i < 13; i++) {
            assertNull(testPlayer.getCard(i), "Test that card " + i + " is null");
        }
    }
    
    /**
     * test addCard with multiple cards
     */
    @Test
    public void testAddCardB() {
        // Test that there are no cards in hand
        String id = "test that first card of hand is null";
        assertNull(testPlayer.getCard(0), id);
        
        //create two more cards
        Card c4 = new Card('c', 4);
        Card c5 = new Card('c', 5);
        
        // Add three cards to the hand
        testPlayer.addCard(c3);
        testPlayer.addCard(c4);
        testPlayer.addCard(c5);
        id = "test that first card is added in first spot";
        assertEquals(c3, testPlayer.getCard(0), id);
        id = "test that second card is adden in second spot";
        assertEquals(c4, testPlayer.getCard(1), id);
        id = "test that third card is added in third spot";
        assertEquals(c5, testPlayer.getCard(2), id);
        for (int i = 3; i < 13; i++) {
            assertNull(testPlayer.getCard(i), "Test that card " + i + " is null");
        }
    }
    
    /**
     * test getCard with invalid index
     */
    @Test
    public void testGetCardException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> testPlayer.getCard(-1), "Invalid index");
        assertEquals("Invalid index", exception.getMessage(), "Invalid index - exception message");
    }
    
    /**
     * test hasActiveCardOfSuit with a hand that's not full
     */
    @Test
    public void testHasActiveCardOfSuitException() {
        Exception exception = assertThrows(IllegalStateException.class,
            () -> testPlayer.hasActiveCardOfSuit('c'), "Hand not full");
        assertEquals("Hand not full", exception.getMessage(), "Hand not full - exception message");
    }
    
    /**
     * test onlyHasHearts with a hand that's not full
     */
    @Test
    public void testOnlyHasHeartsException() {
        Exception exception = assertThrows(IllegalStateException.class,
            () -> testPlayer.onlyHasHearts(), "Hand not full");
        assertEquals("Hand not full", exception.getMessage(), "Hand not full - exception message");
    }
    
    /**
     * test getCardNames with a hand that's not full
     */
    @Test
    public void testGetCardNamesException() {
        Exception exception = assertThrows(IllegalStateException.class,
            () -> testPlayer.getCardNames(), "Hand not full");
        assertEquals("Hand not full", exception.getMessage(), "Hand not full - exception message");
    }

    /**
     * test hasActiveCardOfSuit for clubs with hand full of clubs
     */
    @Test
    public void testHasActiveCardOfSuitA() {
        //deal player full hand of clubs
        testPlayer.addCard(new Card('c', 2));
        testPlayer.addCard(new Card('c', 3));
        testPlayer.addCard(new Card('c', 4));
        testPlayer.addCard(new Card('c', 5));
        testPlayer.addCard(new Card('c', 6));
        testPlayer.addCard(new Card('c', 7));
        testPlayer.addCard(new Card('c', 8));
        testPlayer.addCard(new Card('c', 9));
        testPlayer.addCard(new Card('c', 10));
        testPlayer.addCard(new Card('c', 11));
        testPlayer.addCard(new Card('c', 12));
        testPlayer.addCard(new Card('c', 13));
        testPlayer.addCard(new Card('c', 14));
        
        assertTrue(testPlayer.hasActiveCardOfSuit('c'), "Test hasActiveCardOfSuit for clubs" +
                   " with hand full of clubs");  
    }
    
    /**
     * test hasActiveCardOfSuit for diamonds with hand full of clubs
     */
    @Test
    public void testHasActiveCardOfSuitB() {
        //deal player full hand of clubs
        testPlayer.addCard(new Card('c', 2));
        testPlayer.addCard(new Card('c', 3));
        testPlayer.addCard(new Card('c', 4));
        testPlayer.addCard(new Card('c', 5));
        testPlayer.addCard(new Card('c', 6));
        testPlayer.addCard(new Card('c', 7));
        testPlayer.addCard(new Card('c', 8));
        testPlayer.addCard(new Card('c', 9));
        testPlayer.addCard(new Card('c', 10));
        testPlayer.addCard(new Card('c', 11));
        testPlayer.addCard(new Card('c', 12));
        testPlayer.addCard(new Card('c', 13));
        testPlayer.addCard(new Card('c', 14));
        
        assertFalse(testPlayer.hasActiveCardOfSuit('d'), "Test hasActiveCardOfSuit for " +
                   "diamonds with hand full of clubs");  
    }
    
    /**
     * test onlyHasHearts with hand full of hearts
     */
    @Test
    public void testOnlyHasHeartsA() {
        //deal player full hand of hearts
        testPlayer.addCard(new Card('h', 2));
        testPlayer.addCard(new Card('h', 3));
        testPlayer.addCard(new Card('h', 4));
        testPlayer.addCard(new Card('h', 5));
        testPlayer.addCard(new Card('h', 6));
        testPlayer.addCard(new Card('h', 7));
        testPlayer.addCard(new Card('h', 8));
        testPlayer.addCard(new Card('h', 9));
        testPlayer.addCard(new Card('h', 10));
        testPlayer.addCard(new Card('h', 11));
        testPlayer.addCard(new Card('h', 12));
        testPlayer.addCard(new Card('h', 13));
        testPlayer.addCard(new Card('h', 14));
        
        assertTrue(testPlayer.onlyHasHearts(), "Test onlyHasHearts with hand full of hearts");
    }
    
    /**
     * test onlyHasHearts with hand of hearts and one club
     */
    @Test
    public void testOnlyHasHeartsB() {
        //deal player full hand of hearts
        testPlayer.addCard(new Card('h', 2));
        testPlayer.addCard(new Card('c', 3));
        testPlayer.addCard(new Card('h', 4));
        testPlayer.addCard(new Card('h', 5));
        testPlayer.addCard(new Card('h', 6));
        testPlayer.addCard(new Card('h', 7));
        testPlayer.addCard(new Card('h', 8));
        testPlayer.addCard(new Card('h', 9));
        testPlayer.addCard(new Card('h', 10));
        testPlayer.addCard(new Card('h', 11));
        testPlayer.addCard(new Card('h', 12));
        testPlayer.addCard(new Card('h', 13));
        testPlayer.addCard(new Card('h', 14));
        
        assertFalse(testPlayer.onlyHasHearts(), "Test onlyHasHearts with hand full of " +
                    "hearts and one club"); 
    }

    /**
     * test getCardNames with a hand full clubs in order
     */
    @Test
    public void testGetCardNames() {
        String[] expected = {"c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10",
                             "c11", "c12", "c13", "c14"};
                            
        testPlayer.addCard(new Card('c', 2));
        testPlayer.addCard(new Card('c', 3));
        testPlayer.addCard(new Card('c', 4));
        testPlayer.addCard(new Card('c', 5));
        testPlayer.addCard(new Card('c', 6));
        testPlayer.addCard(new Card('c', 7));
        testPlayer.addCard(new Card('c', 8));
        testPlayer.addCard(new Card('c', 9));
        testPlayer.addCard(new Card('c', 10));
        testPlayer.addCard(new Card('c', 11));
        testPlayer.addCard(new Card('c', 12));
        testPlayer.addCard(new Card('c', 13));
        testPlayer.addCard(new Card('c', 14));
                            
        String[] test = testPlayer.getCardNames();
        
        for (int i = 0; i < 13; i++) {
            assertEquals(expected[i], test[i], "Test that card " + i + " is " + expected[i]);
        }
    }

    /**
     * Tests toString
     */
    @Test
    public void testToString() {
        assertEquals("Human: 0", testPlayer.toString(), "Test toString");
    }
    
    /**
     * Tests toString with non-zero points
     */
    @Test
    public void testToStringNonZeroPoints() {
        testPlayer.addToHandPoints(12);
        assertEquals("Human: 12", testPlayer.toString(), "Test toString with non-zero points");
    }
    
    /**
     * Tests addToHandPoints
     */
    @Test
    public void testAddToHandPoints() {
        testPlayer.addToHandPoints(6);
        assertEquals(testPlayer.getHandPoints(), 6, "Test addToHandPoints");
    }
    
    /**
     * test resetHandPoints
     */
    @Test
    public void testResetHandPoints() {
        testPlayer.addToHandPoints(8);
        testPlayer.resetHandPoints();
        assertEquals(testPlayer.getHandPoints(), 0, "Test resetHandPoints");
    }

    /**
     * test dumpCards
     */
    @Test
    public void testDumpCards() {
        // deal player full hand then dump cards
        testPlayer.addCard(new Card('c', 2));
        testPlayer.addCard(new Card('c', 3));
        testPlayer.addCard(new Card('c', 4));
        testPlayer.addCard(new Card('c', 5));
        testPlayer.addCard(new Card('c', 6));
        testPlayer.addCard(new Card('c', 7));
        testPlayer.addCard(new Card('c', 8));
        testPlayer.addCard(new Card('c', 9));
        testPlayer.addCard(new Card('c', 10));
        testPlayer.addCard(new Card('c', 11));
        testPlayer.addCard(new Card('c', 12));
        testPlayer.addCard(new Card('c', 13));
        testPlayer.addCard(new Card('c', 14));
        testPlayer.dumpCards();
        
        for (int i = 0; i < 13; i++) {
            assertNull(testPlayer.getCard(i), "Test that card " + i + " is dumped(null)");
        }
    }

    /**
     * Test getMove method
     */
    @Test
    public void testGetMove() {

        Card c2 = new Card('c', 2);
        Card c12 = new Card('c', 12);
        Card d4 = new Card('d', 4);
        Card d5 = new Card('d', 5);
        Card d7 = new Card('d', 7);
        Card d12 = new Card('d', 12);
        Card s5 = new Card('s', 5);
        Card s11 = new Card('s', 11);
        Card s12 = new Card('s', 12);
        Card h2 = new Card('h', 2);
        Card h3 = new Card('h', 3);
        Card h5 = new Card('h', 5);

        testPlayer.addCard(c3);
        testPlayer.addCard(h5);
        testPlayer.addCard(d7);
        testPlayer.addCard(s12);
        testPlayer.addCard(c12);
        testPlayer.addCard(c2);
        testPlayer.addCard(d4);
        testPlayer.addCard(h2);
        testPlayer.addCard(s5);
        testPlayer.addCard(s11);
        testPlayer.addCard(d5);
        testPlayer.addCard(h3);
        testPlayer.addCard(d12);

        // Test first round (trick) and the computer player has the 2 of Clubs
        Card c2Copy = new Card('c', 2);
        c2Copy.setPlayed(true);
        assertEquals(c2Copy, testPlayer.getMove(null, true, false), "Has 2 of clubs");
        Card c3copy = new Card('c', 3);
        c3copy.setPlayed(true);
        assertEquals(c3copy, testPlayer.getMove(new Card('c', 4), true, false), "Lowest clubs");
    }
}
