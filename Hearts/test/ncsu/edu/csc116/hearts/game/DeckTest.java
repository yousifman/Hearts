package ncsu.edu.csc116.hearts.game;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Deck class
 * 
 * @author Jessica Young Schmidt
 * @author Yousif Mansour 200360315
 */
public class DeckTest {

    /** Deck for testing */
    private Deck deck;
    
    /** prebuilt array of cards */
    private Card[] testDeck = {new Card('c', 2), new Card('c', 3), new Card('c', 4),
                               new Card('c', 4), new Card('c', 5), new Card('c', 6),
                               new Card('c', 7), new Card('c', 8), new Card('c', 9),
                               new Card('c', 10), new Card('c', 11), new Card('c', 12),
                               new Card('c', 13), new Card('c', 14), new Card('d', 2),
                               new Card('d', 3), new Card('d', 4), new Card('d', 5),
                               new Card('d', 6), new Card('d', 7), new Card('d', 8),
                               new Card('d', 9), new Card('d', 10), new Card('d', 11),
                               new Card('d', 12), new Card('d', 13), new Card('d', 14),
                               new Card('s', 2), new Card('s', 3), new Card('s', 4), 
                               new Card('s', 5), new Card('s', 6), new Card('s', 7), 
                               new Card('s', 8), new Card('s', 9), new Card('s', 10),
                               new Card('s', 11), new Card('s', 12), new Card('s', 13),
                               new Card('s', 14), new Card('h', 2), new Card('h', 3), 
                               new Card('h', 4), new Card('h', 5), new Card('h', 6), 
                               new Card('h', 7), new Card('h', 8), new Card('h', 9),
                               new Card('h', 10), new Card('h', 11), new Card('h', 12),
                               new Card('h', 13), new Card('h', 14)};

    /**
     * Sets up fields for testing
     */
    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }
    
    /**
     * Test deck constructor
     * must be ordered by value and by the following suit order: 
     * Clubs, Diamonds, Spades, Hearts
     */
    @Test
    public void testConstructor() {
        for (int i = 0; i < 52; i++) {
            testDeck[i].equals(deck.nextCard());
        }
    }

    /**
     * Test nextCard for the first card
     */
    @Test
    public void testNextCardA() {
        assertEquals(new Card('c', 2), deck.nextCard(), "Test first card");
    }
    
    /**
     * Test nextCard for the second card
     */
    @Test
    public void testNextCardB() {
        deck.nextCard();
        assertEquals(new Card('c', 3), deck.nextCard(), "Test second card");
    }

    /**
     * Tests that exception is thrown for next card when no cards are left
     */
    @Test
    public void testNextCardException() {
        for (int i = 0; i < 52; i++) {
            deck.nextCard();
        }

        Exception exception = assertThrows(IllegalStateException.class, () -> deck.nextCard(),
                "no cards left");
        assertEquals("No more cards", exception.getMessage(), "No more cards - exception message");
    }
    
    /**
     * test getNext with the first and second cards
     */
    @Test
    public void testGetNext() {
        assertEquals(0, deck.getNext(), "Test getNext with first card");
        deck.nextCard();
        assertEquals(1, deck.getNext(), "Test getNext with second card");
    }
    
    /**
     * test getCards by copying cards array reference and comparing to deck
     */
    @Test
    public void testGetCards() {
        Card[] copy = deck.getCards();
        for (int i = 0; i < 52; i++) {
            copy[i].equals(deck.nextCard());
        }
    }

    /**
     * Test toString
     */
    @Test
    public void testToString() {
        String expected = "card 0: c2\n" + "card 1: c3\n" + "card 2: c4\n" + "card 3: c5\n"
                + "card 4: c6\n" + "card 5: c7\n" + "card 6: c8\n" + "card 7: c9\n"
                + "card 8: c10\n" + "card 9: c11\n" + "card 10: c12\n" + "card 11: c13\n"
                + "card 12: c14\n" + "card 13: d2\n" + "card 14: d3\n" + "card 15: d4\n"
                + "card 16: d5\n" + "card 17: d6\n" + "card 18: d7\n" + "card 19: d8\n"
                + "card 20: d9\n" + "card 21: d10\n" + "card 22: d11\n" + "card 23: d12\n"
                + "card 24: d13\n" + "card 25: d14\n" + "card 26: s2\n" + "card 27: s3\n"
                + "card 28: s4\n" + "card 29: s5\n" + "card 30: s6\n" + "card 31: s7\n"
                + "card 32: s8\n" + "card 33: s9\n" + "card 34: s10\n" + "card 35: s11\n"
                + "card 36: s12\n" + "card 37: s13\n" + "card 38: s14\n" + "card 39: h2\n"
                + "card 40: h3\n" + "card 41: h4\n" + "card 42: h5\n" + "card 43: h6\n"
                + "card 44: h7\n" + "card 45: h8\n" + "card 46: h9\n" + "card 47: h10\n"
                + "card 48: h11\n" + "card 49: h12\n" + "card 50: h13\n" + "card 51: h14\n";
        assertEquals(expected, deck.toString(), "Test toString after constructing deck");
    }

    /**
     * Test initialize method
     */
    @Test
    public void testInitialize() {
        Deck deck2 = new Deck();
        assertTrue(deck.equals(deck2), "Test that the decks are equal after constructor");
        deck2.nextCard();
        assertFalse(deck.equals(deck2), "Test that not equal if call nextCard on one");
        deck2.initialize();
        assertTrue(deck.equals(deck2), "Test that equal after initialize called");

    }

    /**
     * Test that shuffle results in different deck
     */
    @Test
    public void testShuffle() {
        Deck deck2 = new Deck();
        deck2.shuffle();
        assertFalse(deck.equals(deck2), "Test that shuffle results in different deck");
    }

    /**
     * Test equal decks
     */
    @Test
    public void testEqualsA() {
        Deck deck2 = new Deck();
        assertTrue(deck.equals(deck2), "Test equal decks");
    }
    
    /**
     * Test equal and shuffled deck
     */
    @Test
    public void testEqualsB() {
        Deck deck2 = new Deck();
        deck2.shuffle();
        assertFalse(deck.equals(deck2), "Test shuffled and standard deck");
        // NOTE: There is a (1/52!) chance that the shuffled deck is equal to the standard deck
    }

    /**
     * Tests values for public constant
     */
    @Test
    public void testClassConstants() {
        assertEquals(52, Deck.CARDS_IN_DECK, "Test CARDS_IN_DECK constant");
    }
}
