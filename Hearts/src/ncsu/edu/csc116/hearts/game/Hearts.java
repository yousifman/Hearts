package ncsu.edu.csc116.hearts.game;
import ncsu.edu.csc116.hearts.user.Player;

/**
 * Manages the play for the Hearts card game
 * @author Dan Longo
 * @author Suzanne Balik
 */
public class Hearts {

    /** Number of players */
    public static final int NUM_PLAYERS = 4;
    
    /** Number of cards per trick (round) */
    public static final int CARDS_IN_TRICK = 4;
    
    /** Maximum game score */
    public static final int MAX_SCORE = 100;
    
    /** Index of human player */
    public static final int HUMAN_PLAYER = 0;
    
    /** Number of points awarded for a heart card */
    public static final int HEART_VALUE = 1;
    
    /** Number of points awarded for the Queent of Spades */
    public static final int QUEEN_SPADES_VALUE = 13;
    
    /** Number of points awarded for "shooting the moon" */ 
    public static final int MOON_SCORE = 26;
  
    /** Deck of cards */
    private Deck deck;
    
    /** Players in game */
    private Player[] players;
    
    /** Cards played in trick */
    private Card[] cardsPlayedInTrick;
    
    /** Index of player whose turn it is */
    private int turn;
   
    /** Index of player who started trick */
    private int whoStartedTrick;
    
    /** Game status */
    private String status;
    
    /** Whether this is the first turn of the trick */
    private boolean isFirstTurn;
    
    /** Number of tricks that have been played */
    private int tricksPlayed;
    
    /** Whether a heart card has been played yet */
    private boolean heartsStarted;
    
    /** Error message */
    private String errorMessage;
    
    /** Whether in testing mode */
    private boolean testing;
  
    /**
     * This is the constructor, where the deck and players are created, and the
     * instance variables pertinent to the gameplay are initialized
     * @param playerName human player name
     * @param testing whether in testing mode
     */
    public Hearts(String playerName, boolean testing) {
        this.testing = testing;
        deck = new Deck();
        players = new Player[NUM_PLAYERS];

        players[HUMAN_PLAYER] = new Player(playerName);

        status = "Initializing";
        cardsPlayedInTrick = new Card[NUM_PLAYERS];

        for (int i = HUMAN_PLAYER + 1; i < NUM_PLAYERS; i++) {
            players[i] = new Player("Computer " + i);
        }

        isFirstTurn = true;
        turn = 0;
        whoStartedTrick = 0;
        tricksPlayed = 0;
        heartsStarted = false;
        errorMessage = "";
    }
  
    /** 
     * Returns whether all tricks in hand have been played
     * @return whether hand is finished
     */
    public boolean isHandFinished() {
        return tricksPlayed == Player.CARDS_IN_HAND;
    }
  
    /**
     * Removes cards played in trick
     */
    public void removePlayedCards() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            cardsPlayedInTrick[i] = null;
        }
    }
  
    /**
     * Returns error message
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
  
    /**
     * Returns index of player whose turn it is
     * @return index of player whose turn it is
     */
    public int getTurn() {
        return turn;
    }
    
    /**
     * Returns index of player who started trick
     * @return index of player who started trick
     */
    public int getWhoStartedTrick() {
        return whoStartedTrick;
    }
  
    /**
     * Returns array of human player card names
     * @return array of human player card names
     */
    public String[] getHumanPlayerCardNames() {
        return players[HUMAN_PLAYER].getCardNames();
    }
  
    /**
     * Returns game status
     * @return game status
     */
    public String getStatus() {
        return status;
    }
  
    /**
     * Returns status of player
     * @param player index of player
     * @return status of player at given index
     */
    public String getPlayerStatus(int player) {
        return players[player].toString();
    }
  
    /**
     * Returns whether the game is over
     * @return whether the game is over
     */
    public boolean isGameOver() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (players[i].getOverallPoints() >= MAX_SCORE) {
                return true;
            }
        }
        return false;
    }
  
    /**
     * Returns name of game winner
     * @return name of game winner
     */
    public String getWinnerName() {
        int minScore = players[0].getOverallPoints();
        int minIndex = 0;
        for (int i = 1; i < NUM_PLAYERS; i++) {
            if (players[i].getOverallPoints() < minScore) {
                minScore = players[i].getOverallPoints();
                minIndex = i;
            }
        }
        return players[minIndex].getName();
    }
  
    /**
     * Returns string with name of each player and their overall points
     * @return string with name of each player and their overall points
     */
    public String getOverallPoints() {
        String s = "";
        for (int i = 0; i < NUM_PLAYERS; i++) {
            s += players[i].getName() + ": " +
            players[i].getOverallPoints() + "\n";
        }
        return s;
    }
        

    /**
     * This method determines if the moon has been hit, and if so, updates the
     * scores of each player appropriately
     */
    public void checkForMoon() {
        boolean hitMoon = false;
        int hitter = -1;

        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (players[i].getHandPoints() == MOON_SCORE) {
                hitMoon = true;
                hitter = i;
                players[i].addToHandPoints(-1 * MOON_SCORE);
                break;
            }
        }

        if (hitMoon) {
            for (int i = 0; i < NUM_PLAYERS; i++) {
                if (i != hitter) {
                    players[i].addToHandPoints(MOON_SCORE);
                }

            }
        }
    }

    /**
     * This method evaluates the four cards that have been played in a trick and
     * determines who had the highest card based on the first card played and
     * returns the index of which player it was
     * 
     * @return The index of which player took the cards from the last trick
     */
    public int evaluateTrickCards() {
        int points = 0;

        Card highest = cardsPlayedInTrick[whoStartedTrick];

        if (highest.isHeart()) {
            points = HEART_VALUE;
        } else if (highest.isQueenOfSpades()) {
            points = QUEEN_SPADES_VALUE;
        }

        int winner = whoStartedTrick;

        for (int i = 1; i < NUM_PLAYERS; i++) {
            int checkNext = (whoStartedTrick + i) % NUM_PLAYERS;

            if (cardsPlayedInTrick[checkNext].getSuit() == Card.HEARTS) {
                points += HEART_VALUE;
            } else if (cardsPlayedInTrick[checkNext].getSuit() == Card.SPADES &&
                       cardsPlayedInTrick[checkNext].getValue() == Card.QUEEN_VALUE) {
                points += QUEEN_SPADES_VALUE;
            }

            if (cardsPlayedInTrick[checkNext].isHigherThan(highest)) {
                highest = cardsPlayedInTrick[checkNext];
                winner = checkNext;
            }
        }

        turn = winner; // setup for the next trick
        whoStartedTrick = turn; // setup for the next trick
        
        status = players[winner].getName() + " takes the cards and receives " +
                 points + " points.";
        players[winner].addToHandPoints(points);

        isFirstTurn = false;
        removePlayedCards();
        tricksPlayed++;
        return winner;

    }

    /**
     * This method determines if a human user's desired move is acceptable or if
     * it breaks a rule.
     * 
     * @param index index in the player's hand of the Card they wish to play
     * @return Whether or not the card desired is an acceptable move
     */
    public boolean isAcceptableMove(int index) {
        Card temp = players[HUMAN_PLAYER].getCard(index);

        // we must determine if the player's card matches the suit of the
        // starting card
        if (whoStartedTrick == HUMAN_PLAYER) {
            if (isFirstTurn &&
                (temp.getSuit() != Card.CLUBS || temp.getValue() != 2)) {
                errorMessage = "You must play the 2 of Clubs to start the trick.";            
                return false;
            } 
            else if (!players[HUMAN_PLAYER].onlyHasHearts() &&
                     temp.getSuit() == Card.HEARTS && !heartsStarted) {
                errorMessage = "You cannot start a trick with a Heart " +
                               "until one has been played during a trick.";             
                return false;
            } 
            else {
                return true;
            }
        } 
        else {
            if (isFirstTurn) {
                if (temp.getSuit() == Card.HEARTS) {
                    errorMessage = "You cannot play a Heart in the first trick.";                   
                    return false;
                } 
                else if (temp.getSuit() != Card.CLUBS &&
                         players[HUMAN_PLAYER].hasActiveCardOfSuit(Card.CLUBS)) {
                    errorMessage = "You must play a card of the same suit that started the trick.";
                    return false;
                } 
                else if (temp.getSuit() == Card.SPADES && temp.getValue() == Card.QUEEN_VALUE) {
                    errorMessage = "You cannot play the Queen of Spades in the first trick.";
                    return false;
                } 
                else {
                    return true;
                }
            } 
            else {
                char startingSuit = cardsPlayedInTrick[whoStartedTrick].getSuit();
                if (temp.getSuit() != startingSuit &&
                    players[HUMAN_PLAYER].hasActiveCardOfSuit(startingSuit)) {
                    errorMessage = "You must play a card of the same suit that started the trick.";
                    return false;
                } 
                else {
                    return true;
                }
            }
        }
    }

    /**
     * This method returns the filename of the image that represents the card
     * that a computer player wants to use next
     * 
     * @return The filename of the image that represents the card a computer
     *         player wants to use next
     */
    public String getComputerMoveCardName() {
        cardsPlayedInTrick[turn] = players[turn].getMove(cardsPlayedInTrick[whoStartedTrick], 
                                                         isFirstTurn, heartsStarted);
        if (cardsPlayedInTrick[turn].getSuit() == Card.HEARTS) {
            heartsStarted = true;
        }
        String filename = cardsPlayedInTrick[turn].toString();
        turn = (turn + 1) % NUM_PLAYERS;
        status = players[turn].getName() + "'s Turn";
        return filename;
    }

    /**
     * This method accepts the index in the player's hand that contains the card
     * they want to use in the current trick. This method returns the filename
     * of that card's image.
     * @param index of card in player's hand
     * @return The filename of the image of the card the player is using.
     */
    public String getPlayerMoveFilename(int index) {
        cardsPlayedInTrick[turn] = players[turn].getCard(index);
        cardsPlayedInTrick[turn].setPlayed(true);
        if (cardsPlayedInTrick[turn].getSuit() == Card.HEARTS) {
            heartsStarted = true;
        }
        turn = (turn + 1) % NUM_PLAYERS;
        status = players[turn].getName() + "'s Turn";
        return cardsPlayedInTrick[HUMAN_PLAYER].toString();
    }

    /**
     * This method sets up the instance variables for the next hand to be
     * played, and then has the deck shuffled, cards dealt, and hands sorted.
     */
    public void nextHand() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players[i].resetHandPoints();
            players[i].dumpCards();
        }

        turn = 0;
        whoStartedTrick = 0;
        heartsStarted = false;
        isFirstTurn = true;
        tricksPlayed = 0;
        deck.initialize();
        if (!testing) {
            deck.shuffle();
        }
        dealCards();
        
        status = players[turn].getName() + "'s Turn";
    }

    /**
     * This method handles dealing cards to the players for the next hand to be
     * played. During the dealing process, the player who randomly receives the
     * 2 of Clubs is set up to take the first turn.
     */
    public void dealCards() {
        for (int i = 0; i < Player.CARDS_IN_HAND; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                Card next = deck.nextCard();
                if (next.getSuit() == Card.CLUBS && next.getValue() == 2) {
                    
                    whoStartedTrick = j;
                    turn = whoStartedTrick;
                }

                players[j].addCard(next);
            }
        }
    }

//NOTE: The methods below are used for testing/grading and are not used for the Hearts game play
    /**
     * Returns the player at the given index
     * @param i index of player
     * @return player at given index
     */
    public Player getPlayer(int i) {
        return players[i];
    }
    
    /**
     * Used for testing nextHand() method
     */     
    public void testNextHand() {
        for(int i = 0; i < NUM_PLAYERS; i++)
        {
            players[i].resetHandPoints();
            players[i].dumpCards();
        }

        turn = 0;
        whoStartedTrick = 0;
        heartsStarted = false;
        isFirstTurn = true;
        tricksPlayed = 0;
        
        status = players[turn].getName() + "'s Turn";
    }
    
    /** 
     * Used for testing by setting a given player as the one who started 
     * the trick
     * @param index index of player
     */
    public void setTurn(int index) {
        whoStartedTrick = index;
        turn = whoStartedTrick;
    }
    
    /**
     * Return cards played in trick
     * @return cards played in trick
     */
    public Card[] getTrickCards() {
        return cardsPlayedInTrick;
    }


    /**
     * This method returns a text based representation of the players
     * in the game
     * @return The text based representation of the players in the game
     */
    public String toString() {
        String s = "Here we go\n";

        for (int i = 0; i < NUM_PLAYERS; i++) {
            s += players[i] + "\n";
        }

        return s;
    }
}
