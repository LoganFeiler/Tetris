/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 30, 2010
 */

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import board.Board;

/**
 * Allows the user to start and play a game, as well as alter certain settings.
 * 
 * @author Logan Feiler
 * @version November 30, 2010
 */
@SuppressWarnings("serial")
public class TetrisGui extends JFrame {
  
  /**
   * The horizontal and vertical gap between layout components for the main layout.
   */
  private static final int COMPONENT_GAP = 5;
  
  /**
   * A string that says "Controls" for setting a button and window title.
   */
  private static final String CONTROLS = "Controls";
  
  /**
   * A string that says "About" for setting a button and window title.
   */
  private static final String ABOUT = "About";
  
  /**
   * Rotate key-binding.
   */
  private static final int ROTATE_KEY = KeyEvent.VK_W;
  
  /**
   * Move left key-binding.
   */
  private static final int MOVE_LEFT_KEY = KeyEvent.VK_A;
  
  /**
   * Move right key-binding.
   */
  private static final int MOVE_RIGHT_KEY = KeyEvent.VK_D;
  
  /**
   * Move down key-binding.
   */
  private static final int MOVE_DOWN_KEY = KeyEvent.VK_S;
  
  /**
   * Drop key-binding.
   */
  private static final int DROP_KEY = KeyEvent.VK_SPACE;
  
  /**
   * Pause key-binding.
   */
  private static final int PAUSE_KEY = KeyEvent.VK_P;
  
  /**
   * The board on which the game is played.
   */
  private Board my_game_board;
  
  /**
   * The game board display.
   */
  private BoardDisplay my_board_display;
  
  /**
   * A constructor that calls the super constructor to create the JFrame which
   * will be the foundation of the main user interface.
   */
  public TetrisGui() {
    super("Tetris");
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }
  
  /**
   * Creates and launches the GUI.
   */
  public void guiCreator() {
    setFocusable(false);
    final BorderLayout main_layout = new BorderLayout();
    main_layout.setHgap(COMPONENT_GAP);
    setLayout(main_layout);
    my_game_board = new Board(true);
    add(createBoardDisplay(), BorderLayout.CENTER);
    add(createSidePanel(), BorderLayout.EAST);
//    add(createInstructionPanel(), BorderLayout.SOUTH);
    pack();
    setVisible(true);
    createInstructionWindow();
  }
  
  /**
   * Creates the game board display.
   * 
   * @return The panel with the display.
   */
  private JPanel createBoardDisplay() {
    my_board_display = new BoardDisplay(my_game_board);
    my_board_display.setFocusable(true);
    return my_board_display;
  }
  
  /**
   * Creates a side panel with info and buttons.
   * 
   * @return A JPanel holding everything on the right side of the board.
   */
  private JPanel createSidePanel() {
    final GridLayout side_layout = new GridLayout(3, 1);
    side_layout.setVgap(COMPONENT_GAP);
    final JPanel side_panel = new JPanel(side_layout);
    side_panel.setFocusable(false);
    
    final JPanel next_piece_layout = new JPanel(new BorderLayout());
    next_piece_layout.setFocusable(false);
    final NextPieceDisplay next_piece_panel = new NextPieceDisplay(my_game_board);
    next_piece_panel.setFocusable(false);
    next_piece_layout.add(next_piece_panel, BorderLayout.CENTER);
    
    final JPanel stats_layout = new JPanel(new BorderLayout());
    stats_layout.setFocusable(false);
    final StatsPanel stats_panel = new StatsPanel(my_game_board);
    stats_panel.setFocusable(false);
    stats_layout.add(stats_panel, BorderLayout.CENTER);
    
    side_panel.add(next_piece_layout);
    side_panel.add(stats_layout);
    side_panel.add(createButtons());
    return side_panel;
  }
  
  /**
   * Creates a side panel with info and buttons.
   * 
   * @return A JPanel holding everything on the right side of the board.
   */
  private JPanel createButtons() {
    final JPanel button_layout = new JPanel(new GridLayout(3, 1));
    button_layout.setFocusable(false);
    
    final JPanel start_layout = new JPanel(new FlowLayout());
    start_layout.setFocusable(false);
    button_layout.add(start_layout);
    final JButton start_button = new JButton(new StartActionListener());
    start_button.setFocusable(false);
    start_layout.add(start_button);

    final JPanel controls_layout = new JPanel(new FlowLayout());
    controls_layout.setFocusable(false);
    button_layout.add(controls_layout);
    final JButton controls_button = new JButton(new ControlsActionListener());
    controls_button.setFocusable(false);
    controls_layout.add(controls_button);
    
    final JPanel about_layout = new JPanel(new FlowLayout());
    about_layout.setFocusable(false);
    button_layout.add(about_layout);
    final JButton about_button = new JButton(new AboutActionListener());
    about_button.setFocusable(false);
    about_layout.add(about_button);
    
    final JPanel quit_layout = new JPanel(new FlowLayout());
    quit_layout.setFocusable(false);
    button_layout.add(quit_layout);
    final JButton quit_button = new JButton(new QuitActionListener());
    quit_button.setFocusable(false);
    quit_layout.add(quit_button);

    return button_layout;
  }
  
  /**
   * Creates a window that tells the user the controls for the game.
   */
  private void createInstructionWindow() {
    final StringBuilder instructions = new StringBuilder();
    instructions.append("Rotate:  " + KeyEvent.getKeyText(ROTATE_KEY));
    instructions.append("\nMove Left:  " + KeyEvent.getKeyText(MOVE_LEFT_KEY));
    instructions.append("\nMove Right:  " + KeyEvent.getKeyText(MOVE_RIGHT_KEY));
    instructions.append("\nMove Down:  " + KeyEvent.getKeyText(MOVE_DOWN_KEY));
    instructions.append("\nDrop Piece:  " + KeyEvent.getKeyText(DROP_KEY));
    instructions.append("\nPause Game:  " + KeyEvent.getKeyText(PAUSE_KEY));
//    instructions.append("\nExit Tetris:  Ctrl + Q");
    javax.swing.JOptionPane.showMessageDialog(null, instructions.toString(), CONTROLS, 0);
  }
  
  /**
   * Creates a window that tells the user about the creation of the game.
   */
  private void createAboutWindow() {
	final String information = "Created by Logan Feiler in 2010";
    javax.swing.JOptionPane.showMessageDialog(null, information, ABOUT, 0);
  }
  
  /**
   * Creates a panel that tells the user the controls for the game.
   * 
   * @return A JPanel with the information of what the controls are.
   */
/*  private JPanel createInstructionPanel() {
    final JPanel upper_panel = new JPanel(new BorderLayout());
    upper_panel.add(new JLabel("Rotate: W"));
    upper_panel.add(new JLabel("\nMove Left: A"));
    upper_panel.add(new JLabel("\nMove Right: D"));
    upper_panel.add(new JLabel("\nMove Down: S"));
    upper_panel.add(new JLabel("\nDrop Piece: Spacebar"));
    upper_panel.add(new JLabel("\nPause Game: P"));
    upper_panel.add(new JLabel("\nExit Tetris: Ctrl + Q"));
    upper_panel.setFocusable(false);

    return upper_panel;
  }*/

  /**
   * Is an action listener that listens for the command to close the program.
   * 
   * @author Logan Feiler
   */
  class QuitActionListener extends AbstractAction {
    
    /**
     * Constructor for the action listener of the "Quit Game" button.
     */
    public QuitActionListener() {
      super("Quit Tetris");
      putValue(Action.ACCELERATOR_KEY,
               KeyStroke.getKeyStroke('Q', java.awt.event.InputEvent.CTRL_MASK));
    }
    
    /**
     * If the action listener detects the "Quit Game" button being pressed, it tells
     * Tetris to close.
     * 
     * @param the_event The event that triggered this method call.
     */
    public void actionPerformed(final ActionEvent the_event) {
      my_board_display.pauseGame();
      dispose();
    }
  }
  
  /**
   * Is an action listener that listens for the command to start the game.
   * 
   * @author Logan Feiler
   */
  class StartActionListener extends AbstractAction {
    
    /**
     * What the corresponding button should say if no game is in progress.
     */
    private static final String NEW_GAME = "New Game";
    
    /**
     * Whether the game is stopped.
     */
    private boolean my_game_is_playing;
    
    /**
     * Constructor for the action listener of the "Start Game" button.
     */
    public StartActionListener() {
      super(NEW_GAME);
    }
    
    /**
     * If the action listener detects the "Start Game" button being pressed, it tells
     * Tetris to start a game.
     * 
     * @param the_event The event that triggered this method call.
     */
    public void actionPerformed(final ActionEvent the_event) {
      final Object button = the_event.getSource();
      if (button.getClass() == JButton.class) {
        if (my_game_is_playing) {
          my_board_display.gameOver();
          ((JButton) button).setText(NEW_GAME);
          my_game_is_playing = false;
        } else {
          my_board_display.startNewGame();
          ((JButton) button).setText("End Game");
          my_game_is_playing = true;
        }
      }
    }
  }

  /**
   * Is an action listener that listens for the command to display the controls.
   * 
   * @author Logan Feiler
   */
    class ControlsActionListener extends AbstractAction {
    
    /**
     * Constructor for the action listener of the "Controls" button.
     */
    public ControlsActionListener() {
      super(CONTROLS);
    }
    
    /**
     * If the action listener detects the "Controls" button being pressed, it tells
     * Tetris to pause the game and display the controls.
     * 
     * @param the_event The event that triggered this method call.
     */
    public void actionPerformed(final ActionEvent the_event) {
      if (!my_board_display.isPaused()) {
        my_board_display.pauseGame();
      }
      createInstructionWindow();
      my_board_display.pauseGame();
    }
  }
    
  /**
   * Is an action listener that listens for the command to display info about the game.
   * 
   * @author Logan Feiler
   */
  class AboutActionListener extends AbstractAction {
    /**
     * Constructor for the action listener of the "About" button.
     */
    public AboutActionListener() {
      super(ABOUT);
    }
 
    /**
     * If the action listener detects the "About" button being pressed, it tells
     * Tetris to pause the game and display info about the creation of the game.
     * 
     * @param the_event The event that triggered this method call.
     */
    public void actionPerformed(final ActionEvent the_event) {
      if (!my_board_display.isPaused()) {
        my_board_display.pauseGame();
      }
      createAboutWindow();
      my_board_display.pauseGame();
    }
  }
  
}
