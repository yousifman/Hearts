package ncsu.edu.csc116.hearts.user;
import java.util.*;

import ncsu.edu.csc116.hearts.game.Card;

/**
 * Represents a player in the card game 'Hearts'
 * @author Yousif Mansour 200360315
 * July 24, 2021
 */
public class Player {
    
    //CLASS CONSTANTS
    /** The number of cards in a player's hand */
    public static final int CARDS_IN_HAND = 13;
    
    //INSTANCE FIELDS
    /** name of player */
    private String name;
    
    /** total points of a player */
    private int overallPoints;
    
    /** points for a player's current hand */
    private int handPoints;
    
    /** an araay of all card objects in the player's hand */
    private Card[] hand;
    
    /** holds the index of in hand array for where next dealt card is to be placed */
    private int next;
    
    //METHODS
    /**
     * Constuctor method for the player that sets their name and 
     * initializes their hand array. all other fields set to zero.
     * @param name name to be set for player
     */
    public Player(String name) {
        this.name = name;
        
        hand = new Card[CARDS_IN_HAND];
        
        overallPoints = 0;
        handPoints = 0;
        next = 0;
    }
    
    /**
     * getter method to access name instance field
     * @return name name of player
     */
    public String getName() {
        return name;
    }
    
    /**
     * adds a dealt card to a player's hand and
     * sorts hand when all cards are dealt
     * @param card the card to be added to a player's hand
     * @throws IllegalStateException if trying to add cards to a full hand
     */
    public void addCard(Card card) {
        if (next >= CARDS_IN_HAND) {
            throw new IllegalStateException ("Full hand");
        }
        hand[next] = card;
        next++;
        if (next == CARDS_IN_HAND) {
            Arrays.sort(hand);
        }
    }
    
    /**
     * getter method to access handPoints instance field
     * @return handPoints points in player's hand
     */
    public int getHandPoints() {
        return handPoints;
    }
    
    /**
     * getter method to access overallPoints instance field
     * @return overallPoints total points player has
     */
    public int getOverallPoints() {
        return overallPoints;
    }
    
    /**
     * updates player's points in hand and overall points
     * @param points points to be added 
     */
    public void addToHandPoints(int points) {
        handPoints += points;
        overallPoints += points;
    }
    
    /**
     * getter method to access card in player's hand at specified index
     * @param index index of card to be returned
     * @return hand[index] card object in hand to be retunred
     * @throws IllegalArgumentException if index invalid
     */
    public Card getCard(int index) {
        if (index < 0 || index >= CARDS_IN_HAND) {
            throw new IllegalArgumentException ("Invalid index");
        }
        return hand[index];
    }
    
    /**
     * determines if a player has a card of a specified suit that they have not played
     * @param suit the specified suit represented as a char
     * @return hasActiveCardOfSuit boolean value
     * @throws IllegalStateException if player does not have a full hand of cards
     */
    public boolean hasActiveCardOfSuit(char suit) {
        if (next < CARDS_IN_HAND) {
            throw new IllegalStateException ("Hand not full");
        }
        boolean hasActiveCardOfSuit = false;
        
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            if (hand[i].getSuit() == suit && !hand[i].hasBeenPlayed()) {
                hasActiveCardOfSuit = true;
            }
        }
        
        return hasActiveCardOfSuit;
    }
    
    /**
     * determines if a player's non-played cards are all hearts
     * @return onlyHasHearts boolean value
     * @throws IllegalStateException if player does not have a full hand of cards 
     */
    public boolean onlyHasHearts() {
        if (next < CARDS_IN_HAND) {
            throw new IllegalStateException ("Hand not full");
        }
        boolean onlyHasHearts = true;
        
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            if (!hand[i].hasBeenPlayed() && hand[i].getSuit() != Card.HEARTS) {
                onlyHasHearts = false;
            }
        }
        
        return onlyHasHearts;
    }
    
    /**
     * creates a String array for the name of all cards in a player's hand
     * @return cardNmaes array reference for string array of card names
     * @throws IllegalStateException if player does not have a full hand of cards
     */
    public String[] getCardNames() {
        if (next < CARDS_IN_HAND) {
            throw new IllegalStateException ("Hand not full");
        }
        String[] cardNames = new String[CARDS_IN_HAND];
        
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            cardNames[i] = hand[i].toString();
        }
        return cardNames;
    }
    
    /**
     * creates a string that represents a player's status 
     * "[name]: [handPoints]"
     * @return player's status
     */
    public String toString() {
        return name + ": " + handPoints;
    }
    
    /**
     * reset's a player's hand by setting all cards to null and next index to 0
     */
    public void dumpCards() {
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            hand[i] = null;
        }
        next = 0;
    }
    
    /**
     * reset's players point in hand to zero
     */
    public void resetHandPoints() {
        handPoints = 0;
    }
    
    /**
     * This method determines the card that a computer player will play in the
     * current round (trick). This method assumes the hand has been sorted and
     * is in order by suits - clubs, diamond, spades, hearts - and the values of
     * the cards in each suit are ordered from lowest to highest value.
     * @param startingCard the card that started the round
     * @param isFirstRound whether or not this is the first round of a hand
     * @param heartsStarted whether or not hearts are in play at this point in the hand
     * @return the card that will be played
     */
    public Card getMove(Card startingCard, boolean isFirstRound, boolean heartsStarted) {
        
        //If this is the first round (trick) and the computer player has the 2 of Clubs, 
        //they must play it. If the player has the 2 of Clubs, it should be the first 
        //card in their (sorted) hand
        if (isFirstRound && startingCard == null && 
            hand[0].getSuit() == Card.CLUBS && hand[0].getValue() == 2) {
            hand[0].setPlayed(true);
            return hand[0];
        } 
        
        //If an initial card was played, the computer player must follow suit
        //and will play the lowest card in that suit
        if (startingCard != null) {
            char currentSuit = startingCard.getSuit(); 
            for (int i = 0; i < CARDS_IN_HAND; i++) {
                if (!hand[i].hasBeenPlayed() && hand[i].getSuit() == currentSuit) {
                    hand[i].setPlayed(true);
                    return hand[i];
                }
            }
        }

        //If no card with a matching suit was found and it's not the first round(trick)
        //the computer player will play the Queen of Spades if they have it and it
        //hasn't been played yet
        //If not, they will play their highest valued Heart, if they have one and it
        //hasn't been played yet
        if (startingCard != null && !isFirstRound) {
            //Look for the Queen of Spades
            for (int i = 0; i < CARDS_IN_HAND; i++) {
                if (hand[i].getSuit() == Card.SPADES && hand[i].getValue() == Card.QUEEN_VALUE &&
                    !hand[i].hasBeenPlayed()) {
                    hand[i].setPlayed(true);
                    return hand[i];
                }
            }
            for (int i = CARDS_IN_HAND - 1; i >= 0; i--) {
                if (hand[i].getSuit() == Card.HEARTS && !hand[i].hasBeenPlayed()) {
                    hand[i].setPlayed(true);
                    return hand[i];
                }
            }
        }

        //If no card has been found yet, the first card that hasn't been played
        //in the sorted hand will be played
        for (int i = 0; i < hand.length; i++) {
            if (!hand[i].hasBeenPlayed()) {
                
                //A club or diamond is always valid
                if (hand[i].getSuit() == Card.CLUBS ||
                    hand[i].getSuit() == Card.DIAMONDS) {
                    hand[i].setPlayed(true);
                    return hand[i];
                }
                
                //All spades other than the queen are valid
                //The queen of spades can be played if it's not the
                //first round(trick)
                if (hand[i].getSuit() == Card.SPADES) {
                    if (hand[i].getValue() != Card.QUEEN_VALUE) {
                        hand[i].setPlayed(true);
                        return hand[i];
                    }
                    else if (!isFirstRound) {
                        hand[i].setPlayed(true);
                        return hand[i];
                    }
                }
                
                //A heart is valid if it's not the first round 
                //and either hearts have been played previously or
                //the player only has hearts. NOTE: This case would 
                //occur when the player is playing the initial card
                //in the trick
                if (hand[i].getSuit() == Card.HEARTS && !isFirstRound && 
                    (heartsStarted || onlyHasHearts())) {
                    hand[i].setPlayed(true);
                    return hand[i];
                }    
            }
        }
        //No card found so far - this could happen in the unlikely situation
        //that the player's hand contained only hearts or
        //the Queen of spades with the rest of the cards being hearts
        //Return the first unplayed card        
        for (int i = 0; i < hand.length; i++) {
            if (!hand[i].hasBeenPlayed()) {
                hand[i].setPlayed(true);
                return hand[i];
            }
        }
        //No unplayed card in hand
        throw new IllegalStateException("No unplayed card in hand");

    }
}