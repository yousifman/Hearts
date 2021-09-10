package ncsu.edu.csc116.hearts.game;
import java.util.*;

/**
 * Represents an entire deck of 52 playing cards
 * @author Yousif Mansour 200360315   
 * July 24, 2021
 */
public class Deck {
    
    //CLASS CONSTANTS
    /** Number of cards in a playing deck */
    public static final int CARDS_IN_DECK = 52;
    
    /** Number of cards ina full suit */
    public static final int CARDS_IN_SUIT = 13;
    
    //INSTANCE FIELDS
    /** an array of all card objects in the created deck */
    private Card[] cards;
    
    /** holds the index of the next card in the deck array to be dealt */
    private int next;
    
    //METHODS
    /**
     * Constructor method for the deck of cards that creates every card object
     * Initialized in the following order:
     * (Clubs 2-14, Diamonds 2-14, Spades 2-14, Hearts 2-14)
     */
    public Deck() {
        cards = new Card[CARDS_IN_DECK];
         
        int i = 0;
        int cardVal = Card.LOWEST_VALUE;
        while (i < CARDS_IN_SUIT) {
            cards[i] = new Card(Card.CLUBS, cardVal);
            cardVal++;
            i++;
        }
            
        cardVal = Card.LOWEST_VALUE;
        while (i < 2 * CARDS_IN_SUIT) {
            cards[i] = new Card(Card.DIAMONDS, cardVal);
            cardVal++;
            i++;
        }
            
        cardVal = Card.LOWEST_VALUE;
        while (i < CARDS_IN_DECK - CARDS_IN_SUIT) {
            cards[i] = new Card(Card.SPADES, cardVal);
            cardVal++;
            i++;
        }
            
        cardVal = Card.LOWEST_VALUE;
        while (i < CARDS_IN_DECK) {
            cards[i] = new Card(Card.HEARTS, cardVal);
            cardVal++;
            i++;
        }
    }
    
    /**
     * getter method to access next instance field
     * @return next index of next card to be dealt
     */
    public int getNext() {
        return next;
    }
    
    /**
     * getter method to access cards[] instance field
     * @return cards reference for cards array that represents deck
     */ 
    public Card[] getCards() {
        return cards;
    }
    
    /**
     * Shuffles the deck of cards array using a random number generator
     */
    public void shuffle() {
        Card temp;
        Random rand = new Random();
        
        for (int i = 1; i < CARDS_IN_DECK; ++i) {
            int randInt = rand.nextInt(i);
            temp = cards[i];
            cards[i] = cards[randInt];
            cards[randInt] = temp;
        }
    }
    
    /**
     * resets hasBeenPlayed boolean to false for every card
     * also sets the 0th index card as the next card to be played
     */
    public void initialize() {
        for (int i = 0; i < CARDS_IN_DECK; ++i) {
            cards[i].setPlayed(false);
        }
        next = 0;
    }
    
    /**
     * deals next card in deck, and increments next index
     * @return cards[next] next card in cards array 
     * @throws IllegalStateException if index is outside of array range
     */
    public Card nextCard() {
        if (next >= CARDS_IN_DECK) {
            throw new IllegalStateException ("No more cards");
        }
        int temp = next;
        next++;
        return cards[temp];
    }
    
    /**
     * checks if two decks are the exact same deck
     * including if they have the same next index
     * @param o given object to be comapred to
     * @return true if o is an equal deck with all equal cards and equal next index
     *         false if else
     */
    public boolean equals(Object o) {
        if (o instanceof Deck) {
            Deck other = (Deck) o;
            return Arrays.deepEquals(cards, other.cards)
                   && next == other.next;
        } else {
            return false;
        }
    }
    
    /**
     * prints string representation of entire deck
     * @return Card.toString() for every card in deck
     */
    public String toString() {
        String deckString = "";
        for (int i = 0; i < CARDS_IN_DECK; i++) {
            deckString += String.format("card %d: " + cards[i].toString() + "\n", i);
        }
        return deckString;
    }
}
            