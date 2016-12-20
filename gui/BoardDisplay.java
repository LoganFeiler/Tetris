/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 30, 2010
 */

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import board.Board;

/**
 * Displays the current state of the board to the user, as visual output.
 * 
 * @author Logan Feiler
 * @version November 30, 2010
 */
@SuppressWarnings("serial")
public class BoardDisplay extends JPanel implements Observer {

  /**
   * The initial delay (in milliseconds) for the move timer.
   */
  private static final int INITIAL_UPDATE_DELAY = 1000;
  
  /**
   * The number of milliseconds by which the update timer delay changes when the player
   * progresses a level.
   */
  private static final int DELAY_CHANGE = 50;
  
  /**
   * Modifies the update timer when the player gets a Tetris.
   */
  private static final double TIMER_MODIFIER = 1.3;
  
  /**
   * The length of time the timer slows down after a Tetris is achieved.
   */
  private static final int SLOW_DOWN_LENGTH = 15000;
  
  /**
   * Helps get the appropriate font size for when the game displays text on top of the board.
   */
  private static final int FONT_SIZE_MODIFIER = 6;
  
  /**
   * The translucent black used to darken the board during a pause or when the game is over.
   */
  private static final Color TRANSLUCENT_BLACK = new Color(0, 0, 0, 115);
  
  /**
   * The color of the ghost piece.
   */
  private static final Color GHOSTLY_COLOR = new Color(211, 211, 211, 150);
  
  /**
   * The font type for when the game displays text on top of the board.
   */
  private static final String FONT_TYPE = "Serif";
  
  /**
   * The message displayed on the board when the game is paused.
   */
  private static final String PAUSED_MESSAGE = "Paused";
  
  /**
   * The message displayed on the board when the game is over.
   */
  private static final String GAME_OVER_MESSAGE = "Game Over";
  
  /**
   * The board being used to keep track of the game in progress.
   */
  private final Board my_board;
  
  /**
   * The height of the board.
   */
  private final int my_board_height;
  
  /**
   * The width of the board.
   */
  private final int my_board_width;
  
  /**
   * The dimensions of the board.
   */
  private final Dimension my_preferred_size = new Dimension(200, 400);
  
  /**
   * The timer that controls how often the current piece moves down one space.
   */
  private final Timer my_update_timer;
  
  /**
   * The timer that controls how often the current piece moves down one space.
   */
  private final Timer my_slow_down_timer;
  
  /**
   * The current level.
   */
  private int my_level = 1;
  
  /**
   * Whether the game is over.
   */
  private boolean my_game_is_over;
  
  /**
   * Whether the game is paused.
   */
  private boolean my_game_is_paused;
  
  /**
   * Whether the game is paused.
   */
  private boolean my_game_is_in_progress;

  /**
   * A constructor that sets up the board display.
   * 
   * @param the_board The board being displayed.
   */
  public BoardDisplay(final Board the_board) {
    super();
    setBackground(Color.BLACK);
    my_board = the_board;
    my_board_height = my_board.getBoardDimensions()[0];
    my_board_width = my_board.getBoardDimensions()[1];
    setPreferredSize(my_preferred_size);
    my_update_timer = new Timer(INITIAL_UPDATE_DELAY, new UpdateListener());
    my_slow_down_timer = new Timer(SLOW_DOWN_LENGTH, new TetrisListener());
    addKeyListener(new TetrisKeyListener());
    my_board.addObserver(this);
  }
  
  /**
   * Tells the panel what to paint when repaint is called.
   * 
   * @param the_graphics The Graphics2D object that paint component will be using.
   */
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    
    // Draw the board.
    final float block_width = (float) getWidth() / my_board_width;
    final float block_height = (float) getHeight() / my_board_height;
    final Color[][] board_state = my_board.getBoardState();
    for (int row = 0; row < my_board_height; row++) {
      for (int column = 0; column < my_board_width; column++) {
        if (board_state[row][column] != Color.BLACK) {
          g2d.setColor(board_state[row][column]);
          g2d.fillRoundRect((int) (column * block_width + block_width / (my_board_width * 2)),
                            (int) (row * block_height + block_height / my_board_height),
                            (int) (block_width - block_width / my_board_width),
                            (int) (block_height - block_height /
                                ((float) my_board_height / 2)),
                            (int) (block_width / 2), (int) (block_height / 2));
        }
      }
    }
    if (my_game_is_in_progress && !my_game_is_paused) {
      final Point[] ghost_piece = my_board.getGhostPiece();
      g2d.setColor(GHOSTLY_COLOR);
      for (int block = 0; block < ghost_piece.length; block++) {
        g2d.fillRoundRect((int) (ghost_piece[block].getX() * block_width +
                                 block_width / (my_board_width * 2)),
                          (int) (ghost_piece[block].getY() * block_height +
                                 block_height / my_board_height),
                          (int) (block_width - block_width / my_board_width),
                          (int) (block_height - block_height /
                              ((float) my_board_height / 2)),
                          (int) (block_width / 2), (int) (block_height / 2));
      }
    }

    if (my_game_is_over) {
      inactiveState(g2d, GAME_OVER_MESSAGE);
    } else if (my_game_is_paused) {
      inactiveState(g2d, PAUSED_MESSAGE);
    }
  }
  
  /**
   * Does everything that is common for both the game over and pause states.
   * Returns the length of the string that is sent in as a parameter.
   * 
   * @param the_graphics The graphics object with which to draw.
   * @param the_message The string to find the length of.
   */
  public void inactiveState(final Graphics2D the_graphics, final String the_message) {
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setPaint(TRANSLUCENT_BLACK);
    g2d.fillRect(0, 0, getWidth(), getHeight());
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font(FONT_TYPE, Font.BOLD, getWidth() / FONT_SIZE_MODIFIER));
    final int string_length = g2d.getFontMetrics().stringWidth(the_message);
    g2d.drawString(the_message, getWidth() / 2 - string_length / 2,
                   (float) getHeight() / 2);
  }

  /**
   * Starts the game.
   */
  public void startNewGame() {
    my_board.emptyBoard();
    my_game_is_over = false;
    my_game_is_in_progress = true;
    my_update_timer.start();
    repaint();
  }
  
  /**
   * Pauses and resumes the game.
   */
  public void pauseGame() {
    if (my_game_is_in_progress) {
      if (my_game_is_paused) {
        my_update_timer.start();
        my_game_is_paused = false;
      } else {
        my_update_timer.stop();
        my_game_is_paused = true;
      }
      repaint();
    }
  }
  
  /**
   * Ends the game.
   */
  public void gameOver() {
    my_update_timer.stop();
    my_slow_down_timer.stop();
    my_game_is_over = true;
    my_game_is_in_progress = false;
    my_game_is_paused = false;
    my_level = 0;
    my_update_timer.setDelay(my_update_timer.getInitialDelay());
    repaint();
  }
  
  /**
   * Returns whether or not the game is paused.
   * 
   * @return Whether or not the game is paused.
   */
  public boolean isPaused() {
    return my_game_is_paused;
  }

  /**
   * Updates the board display when something changes on the board.
   * 
   * @param the_board The board being observed.
   * @param the_event What changed in the board.
   */
  @Override
  public void update(final Observable the_board, final Object the_event) {
    switch((TetrisEvent) the_event) {
      
      case BOARD_UPDATED:
        if (my_level < my_board.getLevel()) {
          my_level++;
          my_update_timer.setDelay(my_update_timer.getDelay() - DELAY_CHANGE);
        }
        repaint();
        break;
        
      case GAME_OVER:
        gameOver();
        break;
        
      case TETRIS:
//        my_update_timer.stop();
        my_update_timer.setDelay((int) (my_update_timer.getDelay() * TIMER_MODIFIER));
        my_slow_down_timer.start();
        my_board.randomizeColors();
        repaint();
        break;

      default:
        // Do nothing.
    }
  }
  
  /**
   * Listens for timer events and updates the state of the board. 
   * 
   * @author Logan Feiler
   */
  private class UpdateListener implements ActionListener {
    
    /**
     * Updates the state of the board.
     * 
     * @param the_event The event triggering the action.
     */
    public void actionPerformed(final ActionEvent the_event) {
      my_board.update();
    }
  }
  
  /**
   * Listens for timer events from the slow-down timer.
   */
  private class TetrisListener implements ActionListener {
    
    /**
     * Speeds the timer back up to normal for the current level.
     * 
     * @param the_event The event triggering the action.
     */
    public void actionPerformed(final ActionEvent the_event) {
      my_slow_down_timer.stop();
      my_update_timer.setDelay(my_update_timer.getInitialDelay() - (my_level * DELAY_CHANGE));
      my_update_timer.start();
    }
  }
  
  /**
   * Listens for keystrokes for manipulating the piece_in_progress.
   */
  private class TetrisKeyListener extends KeyAdapter {

    /**
     * Handles a key being typed.
     * 
     * @param the_event The KeyEvent generated by the key.
     */
    @Override
    public void keyPressed(final KeyEvent the_event) {
      if (my_game_is_in_progress) {
        if (!my_game_is_paused) {
          switch(the_event.getKeyCode()) {
            
            case KeyEvent.VK_W:
              my_board.rotate();
              break;
              
            case KeyEvent.VK_A:
              my_board.moveLeft();
              break;
              
            case KeyEvent.VK_D:
              my_board.moveRight();
              break;
              
            case KeyEvent.VK_S:
              my_board.moveDown();
              break;
              
            case KeyEvent.VK_SPACE:
              my_board.drop();
              break;
              
            default:
              //Do nothing.
          }
        }
        if (the_event.getKeyCode() == KeyEvent.VK_P) {
          pauseGame();
        }
      }
    }
  }
}
