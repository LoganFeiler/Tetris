/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * December 8, 2010
 */

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import board.Board;

/**
 * Displays the player's statistics for the current game. 
 * 
 * @author Logan Feiler
 * @version December 8, 2010
 */
@SuppressWarnings("serial")
public class StatsPanel extends JPanel implements Observer {
  
  /**
   * Helps get the appropriate font size for when the game displays text on top of the board.
   */
  private static final int FONT_SIZE_MODIFIER = 5;
  
  /**
   * Helps place the string when drawing it.
   */
  private static final int STRING_OFFSET = 5;
  
  /**
   * The background color.
   */
  private static final Color BG_COLOR = new Color(255, 255, 245);
  
  /**
   * The font type that will display the statistics for the current game.
   */
  private static final String FONT_TYPE = "Serif";
  
  /**
   * The font color.
   */
  private static final Color FONT_COLOR = Color.BLACK;
  
  /**
   * The dimensions of the board.
   */
  private final Dimension my_preferred_size = new Dimension(100, 100);
  
  /**
   * The board on which the game is being played.
   */
  private final Board my_board;
  
  /**
   * The score.
   */
  private int my_score;
  
  /**
   * The level.
   */
  private int my_level;
  
  /**
   * The number of lines cleared during this game.
   */
//  private int my_cleared_lines;
  
  /**
   * Constructor for the statistics panel.
   * 
   * @param the_board The board on which the game is being played.
   */
  public StatsPanel(final Board the_board) {
    super();
    my_board = the_board;
    setBackground(BG_COLOR);
    setPreferredSize(my_preferred_size);
    my_board.addObserver(this);
  }
  
  /**
   * Tells the panel what to draw when the repaint method is called.
   * 
   * @param the_graphics The Graphics2D object that paint component will be using.
   */
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(FONT_COLOR);
    
    final float panel_width = (float) getWidth();
    float panel_height = (float) getHeight();
    if (panel_height > panel_width) {
      panel_height = panel_width;
    }
    g2d.setFont(new Font(FONT_TYPE, Font.PLAIN,
                         (int) panel_height / FONT_SIZE_MODIFIER));
    g2d.drawString("Score: " + my_score, STRING_OFFSET,
                   STRING_OFFSET + panel_height / 6);
    g2d.drawString("Level: " + my_level, STRING_OFFSET,
                   STRING_OFFSET + panel_height / 2);
//    g2d.drawString("Lines Cleared: " + my_cleared_lines, STRING_OFFSET,
//                   STRING_OFFSET + panel_height * 5 / 6);
  }
  
  /**
   * Updates the panel when the statistics for the game change.
   * 
   * @param the_board The board being observed.
   * @param the_event What changed in the board.
   */
  @Override
  public void update(final Observable the_board, final Object the_event) {
    switch((TetrisEvent) the_event) {
      
      case BOARD_UPDATED:
        my_score = my_board.getScore();
        my_level = my_board.getLevel();
//        my_cleared_lines = my_board.getLinesCleared();
        repaint();
        break;
        
      case NEW_GAME:
        my_score = 0;
        my_level = 0;
        repaint();
        break;
        
      default:
        // Do nothing.
    }
  }
}
