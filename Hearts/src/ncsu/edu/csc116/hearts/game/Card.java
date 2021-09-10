package ncsu.edu.csc116.hearts.game;
/**
 * Represents a single card out of a deck of cards
 * @author Yousif Mansour 200360315
 * July 24, 2021
 */
public class Card implements Comparable<Card> {
    
    // CLASS CONSTANTS
    /** char that represents the clubs suit */
    public static final char CLUBS = 'c';
    
    /** char that represents the diamonds suit */
    public static final char DIAMONDS = 'd';
    
    /** char that represents the hearts suit */
    public static final char HEARTS = 'h';
    
    /** char that represents the spades suit */
    public static final char SPADES = 's';
    
    /** lowest possible value of a card (2) */
    public static final int LOWEST_VALUE = 2;
    
    /** highest possible value of a card (ace, represented as 14) */
    public static final int HIGHEST_VALUE = 14;
    
    /** value of a queen card */
    public static final int QUEEN_VALUE = 12;
    
    //INSTANCE FIELDS
    /** char that represents suit of a particular card */
    private char suit;
    
    /** int that represents value of a particular card */
    private int value;
    
    /** a boolean that stores whether a card has been played or not */
    private boolean hasBeenPlayed;
    
    //METHODS
    /**
     * Constructor method for any given card
     * initialized with given suit and value
     * @param suit char representing suit of card
     * @param value int representing value of card
     * @throws IllegalArgumentException if suit does not exist
     * @throws IllegalArgumentException if value is outside range
     */
    public Card(char suit, int value) {
        if (suit != CLUBS && suit != DIAMONDS
            && suit != HEARTS && suit != SPADES) {
            throw new IllegalArgumentException ("Invalid suit");
        }
        
        if (value < LOWEST_VALUE || value > HIGHEST_VALUE) {
            throw new IllegalArgumentException ("Invalid value");
        }
        
        this.suit = suit;
        this.value = value;
        hasBeenPlayed = false;
            
    }
    
    /**
     * getter method to access suit instance field
     * @return suit suit of card object
     */
    public char getSuit() {
        return suit;
    }
    
    /**
     * getter method to access value instance field
     * @return value value of card object
     */
    public int getValue() {
        return value;
    }
    
    /**
     * getter method to access whether a card has been played
     * @return hasBeenPlayed boolean that represents if a card has been played
     */
    public boolean hasBeenPlayed() {
        return hasBeenPlayed;
    }
    
    /**
     * sets hasBeenPlayed boolean to the boolean passed as a parameter
     * @param played boolean passed as parameter
     */
    public void setPlayed(boolean played) {
        hasBeenPlayed = played;
    }
    
    /**
     * Checks if a card is of the 'hearts' suit
     * @return true if card is of the 'hearts' suit
     *         false if else
     */
    public boolean isHeart() {
        return suit == HEARTS;
    }
    
    /**
     * Checks if a card is a queen of the 'spades' suit
     * @return true if card is a queen of spades
     *         false if else
     */
    public boolean isQueenOfSpades() {
        return suit == SPADES && value == QUEEN_VALUE;
    }
    
    /**
     * Checks if card is of higher value than parameter card,
     * only if they are of the same suit
     * @param other other card to be compared to
     * @return true if cards are of the same suit and card is of higher value than other card
     *         false if else
     */
    public boolean isHigherThan(Card other) {
        return suit == other.suit && value > other.value;
    }
    
    /**
     * Checks if two cards are the exact same card
     * including if they have/have not been played
     * @param o given object to be compared to
     * @return true if o is a card and has equal instance fields to original card
     *         false if else
     */
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card other = (Card) o;
            return suit == other.suit
                   && value == other.value
                   && hasBeenPlayed == other. hasBeenPlayed;
        } else {
            return false;
        }
    }
    
    /**
     * Creates a string representation of any given suit/value combination
     * @return char representing suit followed by int representing value
     *         example: Queen of Hearts: h12
     */
    public String toString() {
        return "" + suit + value;
    }
    
    
    /**
     * This method is used for sorting cards.
     * It orders the cards by suit (Clubs, Diamonds, Spades, Hearts) and then by
     * value within the suit
     * @param other The Card object to which this Card is being compared.
     * @return  negative value if this Card should be before the other Card,
     *          positive value if this Card should be after the other Card,
     *          0 if this Card has the same suit and value as the other Card.
     */
    public int compareTo(Card other) {
        if (getSuit() == other.getSuit()) {
            if (getValue() < other.getValue()) {
                return -1;
            } else if (getValue() > other.getValue()) {
                return 1;
            }
            else {
                return 0;
            }
        } 
        else {
            switch(getSuit()) {
                case HEARTS:
                    return 1;
                case SPADES:
                    if (other.getSuit() == HEARTS) {
                        return -1;
                    } 
                    else {
                        return 1;
                    }
                case DIAMONDS:
                    if (other.getSuit() == HEARTS || other.getSuit() == SPADES) {
                        return -1;
                    } 
                    else {
                        return 1;
                    }
                //CLUBS
                default:
                    return -1;
            }
        }
    }
}