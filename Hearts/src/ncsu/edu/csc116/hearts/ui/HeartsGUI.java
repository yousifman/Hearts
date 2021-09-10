package ncsu.edu.csc116.hearts.ui;
import java.awt.*;

import javax.swing.*;

import ncsu.edu.csc116.hearts.game.Hearts;
import ncsu.edu.csc116.hearts.user.Player;

import java.awt.event.*;

/**
 * HeartsGUI.java
 * 
 * This class creates a graphical representation of a game of Hearts.
 * 
 * @author Dan Longo
 * @author Suzanne Balik
 */
public class HeartsGUI extends JFrame implements ActionListener {
    
    /** default id for serialization */
    private static final long serialVersionUID = 1L;

    /** Width of GUI */
    public static final int WIDTH = 1000;
    
    /** Height of GUI */
    public static final int HEIGHT = 650;
    
    /** X position of upperleft hand corner of GUI */
    public static final int X = 100;
    
    /** Y position of upperleft hand corner of GUI */
    public static final int Y = 100;
    
    /**
     * This represents how many rows should be in the grid displayed at the
     * center of the GUI
     */
    public static final int CENTER_ROWS = 5;
    
    /**
     * This represents how many columns should be in the grid displayed at the
     * center of the GUI
     */
    public static final int CENTER_COLS = 5;
    
    /** This is the image to be used for place holders in the absence of cards */
    public static final ImageIcon BLANK_CARD = new ImageIcon("cards/blank.gif");
    
    /** This font is used to boldface the label of the player whose turn it is */
    public static final Font BOLD = new Font("Courier", 1, 16);
    
    /**
     * This font is used to display the name of players who are not taking their
     * turn in a normal text
     */
    public static final Font NORMAL = new Font("Courier", 0, 14);
    
    /** Directory containing card image files */
    public static final String PATH_NAME = "cards/";
    
    /** File extension for image files */
    public static final String FILE_EXTENSION = ".gif";
    
    /**
     * This is used to determine if the user wants to exit the program when the
     * game is over
     */
    public static final Integer EXIT_OPTION = 1;
    
    /** Long sleep time in ms */
    public static final int LONG_PAUSE = 1000;
    
    /** Short sleep time in ms */
    public static final int SHORT_PAUSE = 600;
    
    /** This label displays the status of the game */
    private JLabel status;
    
    /** An array of labels to display the status of each player in the game */
    private JLabel[] playerLabels;
    
    /**
     * These buttons represent the cards in the human player's hand that can be
     * played
     */
    private JButton[] humanPlayerCards;
    
    /**
     * This is a grid of labels that make up the center of the GUI, which shows
     * cards and player statuses
     */
    private JLabel[] trickCards;
    
    /** This is the model for the game of Hearts */
    private Hearts model;
    
    /**
     * This tells whether or not the buttons should respond to user clicks based
     * on it being the human player's turn
     */
    private boolean isHumanPlayerTurn;
    
    /** Name of player */
    private String humanPlayerName;
    
    /** Whether in testing mode */
    private boolean testing;
    

    /**
     * This is the constructor which initializes and displays the GUI
     * @param humanPlayerName name of human player
     * @param testing whether in testing mode
     */
    public HeartsGUI(String humanPlayerName, boolean testing) {
        // Set up the GUI's fields
        this.humanPlayerName = humanPlayerName;
        this.testing = testing;
        this.model = new Hearts(humanPlayerName, testing);
        this.status = new JLabel(model.getStatus(), JLabel.CENTER);
        this.playerLabels = new JLabel[Hearts.NUM_PLAYERS];
        this.humanPlayerCards = new JButton[Player.CARDS_IN_HAND];
        this.trickCards = new JLabel[Hearts.NUM_PLAYERS];

        // Start setting up the GUI
        setTitle("CSC116 Hearts");
        setSize(WIDTH, HEIGHT);
        setLocation(X, Y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isHumanPlayerTurn = false;

        // Get the GUI's container
        Container c = getContentPane();

        // Create the JPanel that contains the human player's cards
        // during a trick
        JPanel humanPlayer = new JPanel();
        humanPlayer.setLayout(new BorderLayout());

        // Create the JPanel that contains the buttons for the human player's
        // hand
        JPanel humanPlayerCardButtonPanel = new JPanel();
        humanPlayerCardButtonPanel.setLayout(new GridLayout(1,
                Player.CARDS_IN_HAND));

        // For each player, get the player's status (including the name and the
        // number of points the player has for the hand, set the font to normal
        // showing that it's no one's turn, and make the cards for the trick
        // blank since the hand has not started
        for (int i = 0; i < Hearts.NUM_PLAYERS; i++) {
            playerLabels[i] = new JLabel(model.getPlayerStatus(i),
                    JLabel.CENTER);
            playerLabels[i].setFont(NORMAL);
            trickCards[i] = new JLabel(BLANK_CARD);
        }

        // Add everything to the panel
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(CENTER_ROWS, CENTER_COLS));
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(playerLabels[2]);
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(trickCards[2]);
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(playerLabels[1]);
        center.add(trickCards[1]);
        center.add(new JLabel(""));
        center.add(trickCards[3]);
        center.add(playerLabels[3]);
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(trickCards[0]);
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(new JLabel(""));
        center.add(playerLabels[0]);
        center.add(new JLabel(""));
        center.add(new JLabel(""));

        // For each card in the human players hand, create the action
        // listener to handle when the card is clicked
        for (int i = 0; i < Player.CARDS_IN_HAND; i++) {
            humanPlayerCards[i] = new JButton(BLANK_CARD);
            humanPlayerCards[i].addActionListener(this);
            humanPlayerCardButtonPanel.add(humanPlayerCards[i]);
        }

        // Add the humanPlayerCards panel to the humanPlayer panel
        humanPlayer.add(humanPlayerCardButtonPanel, BorderLayout.CENTER);

        // Build rest of GUI
        c.add(status, BorderLayout.NORTH);
        c.add(humanPlayer, BorderLayout.SOUTH);
        c.add(center, BorderLayout.CENTER);

        // Make everything visible
        setVisible(true);

        // Start playing the hand
        startHand();
    }

    /**
     * This method starts a new hand in the game of Hearts by activating the
     * human player's card buttons and starting the process of playing cards
     */
    public void startHand() {
        // Tell the model to start playing the next hand in the heart's game
        model.nextHand();
                
        //Set the displayed scores for players to 0 for this hand
        updateStatusLabels();

        // Set the status text
        status.setText(model.getStatus());

        // Activate the button's for the human player's cards
        activateButtons();

        // Get the names of the cards in the human player's hand. These names
        // are used to create the path to the image file for the card that is
        // displayed in the GUI.
        String[] pc = model.getHumanPlayerCardNames();
        for (int i = 0; i < Player.CARDS_IN_HAND; i++) {
            humanPlayerCards[i].setIcon(new ImageIcon(PATH_NAME + pc[i]
                    + FILE_EXTENSION));
        }

        // Each trick starts by having the computer players (if any) before the
        // human
        // player play their cards
        executeEarlyCompPlayers();
    }

    /**
     * This method handles displaying the cards being played by computer players
     * that take their turn before the human player.
     */
    public void executeEarlyCompPlayers() {
        // The first turn is based on who randomly obtained the 2 of Clubs,
        // so we must ask the model. We also need to ask for subsequent turns
        // who won the last trick
        int turn = model.getTurn();

        // Update the GUI to show whose turn it is
        playerLabels[turn].setFont(BOLD);

        // If it's not the human player's turn, we need to process all player's
        // that go before the human player
        if (turn != Hearts.HUMAN_PLAYER) {
            // for each of those players
            for (int i = 0; i < Hearts.NUM_PLAYERS - turn; i++) {
                // Get the current player, which may have changed from the value in turn
                int currentPlayer = model.getTurn();
                // This gets the current player's move, and updates whose turn it is
                String filename = model.getComputerMoveCardName();
                //Update the GUI appropriately
                trickCards[currentPlayer].setIcon(new ImageIcon(PATH_NAME
                        + filename + FILE_EXTENSION));
                playerLabels[currentPlayer].setFont(NORMAL);
                playerLabels[model.getTurn()].setFont(BOLD);
                status.setText(model.getStatus());
                
                if (i + 1 != Hearts.NUM_PLAYERS - turn) {
                    try {
                        Thread.sleep(LONG_PAUSE);
                    } catch (Exception e) {
                        System.out.println("Thread sleep error");
                    }
                }
            }
        }
        
        // now we wait for the player to make their turn
        isHumanPlayerTurn = true;
    }

    /**
     * This method handles displaying the cards being played by computer players
     * that take their turn after the human player and concludes the round
     */
    public void executeLateCompPlayers() {
        // The first turn is based on who randomly obtained the 2 of Clubs,
        // so we must ask the model. We also need to ask for subsequent turns
        // who won the last trick
        int whoStarted = model.getWhoStartedTrick();
        // The current player is always 0, the human player
        int currentPlayer = 0;
        int howmany = whoStarted - 1;
        if (howmany == -1)
            howmany = 3;
        
        // For each computer player that hasn't played a card on a trick
        for (int i = 0; i < howmany; i++) {
            // Get the current player's turn
            currentPlayer = model.getTurn();
            // This gets the current player's move, and updates whose turn it is
            String filename = model.getComputerMoveCardName();
            // Update the GUI appropriately
            trickCards[currentPlayer].setIcon(new ImageIcon(PATH_NAME
                    + filename + FILE_EXTENSION));
            playerLabels[currentPlayer].setFont(NORMAL);
            if (i != (howmany - 1))
                playerLabels[model.getTurn()].setFont(BOLD);
            status.setText(model.getStatus());
            // wait for 1 second
            try {
                Thread.sleep(LONG_PAUSE);
            } catch (Exception e) {
                System.out.println("Thread sleep error");
            }
        }

        // Evaluate who won the trick and earned the points
        int winner = model.evaluateTrickCards();
        status.setText(model.getStatus());
        finishTrick(winner);
    }

    /**
     * This method updates the labels that show the status of each player so
     * that their current score is displayed in a normal font
     */
    public void updateStatusLabels() {
        // For each player, get the player's name and their current points
        // for the hand
        for (int i = 0; i < Hearts.NUM_PLAYERS; i++) {
            playerLabels[i].setText(model.getPlayerStatus(i));
            playerLabels[i].setFont(NORMAL);
        }
    }

    /**
     * This method displays the winner of a round taking all the cards in the
     * GUI and then either displays the results of a hand or who the winner of
     * the game was.
     * 
     * @param winner
     *            The index within the label array of the player who won the
     *            last round
     */
    public void finishTrick(int winner) {
        // Update the player's statuses
        updateStatusLabels();
        // Animate cards being taken
        playerLabels[winner].setFont(BOLD);
        for (int i = 0; i < Hearts.NUM_PLAYERS; i++) {
            if (i != winner) {
                trickCards[winner].setIcon(trickCards[i].getIcon());
                trickCards[i].setIcon(BLANK_CARD);
                try {
                    Thread.currentThread().sleep(SHORT_PAUSE);
                } 
                catch (Exception e) {
                    System.out.println("Thread sleep error");
                }
            }
        }

        try {
            Thread.currentThread().sleep(SHORT_PAUSE);
        } 
        catch (Exception e) {
            System.out.println("Thread sleep error");
        }

        // Reset to the start of a new trick in a hand
        trickCards[winner].setIcon(BLANK_CARD);
        
        // Wait if the winner is someone other than the human player
        if (winner != Hearts.HUMAN_PLAYER) {
            try {
                Thread.currentThread().sleep(LONG_PAUSE);
            } catch (Exception e) {
                System.out.println("Thread sleep error");
            }
        }

        // If the hand is finished
        if (model.isHandFinished()) {
            // Check if someone shot the moon and adjust points accordingly
            model.checkForMoon();
            // Is the game over?  Did a player accumulate over 100 points across 
            // several hands?
            if (model.isGameOver()) {
                // Create a list of buttons for a pop-up window
                Object[] options = { "New Game", "Exit" };
                String results = "Game is over, winner is: "
                        + model.getWinnerName();
                // Create the pop-up window
                Object choice = JOptionPane.showOptionDialog(null, results,
                        "Game Results", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options,
                        options[0]);

                // Handle exit choice in pop-up window
                if (choice.equals(EXIT_OPTION)) {
                    // stop everything!
                    System.exit(0);
                } else {
                    // start new game
                    model = new Hearts(humanPlayerName, testing);
                    updateStatusLabels();
                    startHand();
                }

            } else {
                // Display overall points for the game thus far
                String scores = model.getOverallPoints();

                Object[] options = { "Start Next Hand" };
                
                //Set up pop-up window
                JOptionPane.showOptionDialog(null, scores, "Current Standings",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options,
                        options[0]);
                resetFonts();
                
                // Start a new hand
                startHand();
            }
        } else {
            // Keep processing tricks in the hand
            executeEarlyCompPlayers();
        }

    }

    /**
     * This method resets the fonts of each player label to a normal font
     */
    public void resetFonts() {
        for (int i = 0; i < Hearts.NUM_PLAYERS; i++) {
            playerLabels[i].setFont(NORMAL);
        }
    }

    /**
     * This method responds to the human player clicking the card buttons. If it
     * is their turn, the button that was clicked will be processed by the model
     * to determine if it is a valid move, and the GUI will be updated with the
     * card being played, or an error message.
     * 
     * @param e
     *            The event that caused this method to trigger (button click)
     */
    public void actionPerformed(ActionEvent e) {
        // Only process button clicks for the cards if it's the human player's turn
        if (isHumanPlayerTurn) {
            // Set the boolean to false, so we don't handle any more click events
            isHumanPlayerTurn = false;
            // For each card in the human player's hand
            for (int i = 0; i < Player.CARDS_IN_HAND; i++) {
                // Check to see if the source of the click is a given card
                if (e.getSource() == humanPlayerCards[i]) {
                    // First we must determine if it is a valid move given the
                    // state of the game
                    if (model.isAcceptableMove(i)) {
                        // If it is, show the card as played
                        trickCards[Hearts.HUMAN_PLAYER]
                                .setIcon(new ImageIcon(PATH_NAME
                                        + model.getPlayerMoveFilename(i)
                                        + FILE_EXTENSION));
                        // Disable the played card (grey it out)
                        humanPlayerCards[i].setEnabled(false);
                        // Update the GUI as appropriate
                        playerLabels[Hearts.HUMAN_PLAYER].setFont(NORMAL);
                        playerLabels[Hearts.HUMAN_PLAYER + 1]
                                .setFont(BOLD);
                        status.setText(model.getStatus());
                        new CPUThread().start();
                        break;
                    } else {
                        // The card is not a valid move, so get the error message
                        String errorMessage = model.getErrorMessage();
                        Object[] options = { "OK" };
                        // Display the error message
                        JOptionPane.showOptionDialog(null, errorMessage,
                                "Invalid Move", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE, null, options,
                                options[0]);
                        // Give the player the option to select another card
                        isHumanPlayerTurn = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method activates the player's card buttons at the start of a new
     * hand
     */
    private void activateButtons() {
        for (int i = 0; i < Player.CARDS_IN_HAND; i++) {
            humanPlayerCards[i].setEnabled(true);
        }
    }

    /**
     * This is the main method, which creates the HeartsGUI object the game is
     * played through.
     * 
     * @param args
     *            Command line arguments, not used
     */
    public static void main(String[] args) {

        if (args.length == 1) {
            new HeartsGUI(args[0], false);
        } else if (args.length == 2 && args[1].equals("-t")) {
            new HeartsGUI(args[0], true);
        } else {
            System.out.println("Usage: java -cp bin HeartsGUI player_name [-t]");
        }

    }

    /**
     * This is an internal class that handles making the computer players take
     * their turn so that it does not stop the GUI from being refreshed
     * appropriately
     */
    class CPUThread extends Thread {
        /**
         * This method is executed when this thread is started
         */
        public void run() {
            try {
                Thread.sleep(LONG_PAUSE);
            } catch (Exception e) {
                System.out.println("Thread sleep error");
            }
            executeLateCompPlayers();
        }
    }

}
