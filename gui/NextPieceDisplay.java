/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * December 3, 2010
 */

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pieces.GeneralPiece;
import board.Board;

/**
 * Displays the next piece.
 * 
 * @author Logan Feiler
 * @version December 3, 2010
 */
@SuppressWarnings("serial")
public class NextPieceDisplay extends JPanel implements Observer {

  /**
   * The dimensions of the next piece display.
   */
  private final Dimension my_preferred_size = new Dimension(100, 100);
  
  /**
   * The board on which the game is being played.
   */
  private final Board my_board;
  
  /**
   * The next piece, which the panel should display.
   */
  private GeneralPiece my_next_piece;
  
  /**
   * A constructor that sets up the board display.
   * 
   * @param the_board The board being used for the game.
   */
  public NextPieceDisplay(final Board the_board) {
    super();
    setBackground(Color.BLACK);
    setPreferredSize(my_preferred_size);
    the_board.addObserver(this);
    my_board = the_board;
  }
  
  /**
   * Tells the panel what to paint when repaint is called.
   * 
   * @param the_graphics The Graphics2D object that paint component will be using.
   */
  public void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    if (my_next_piece != null) {
      final Graphics2D g2d = (Graphics2D) the_graphics;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(my_next_piece.getColor());
      
      // Draw the piece.
      final Point[] blocks = my_next_piece.getOrientation();
      final float block_width = (float) getWidth() / blocks.length;
      float block_height = (float) getHeight() / blocks.length;
      if (block_height > block_width) {
        block_height = block_width;
      }
      // For each block...
      for (int index = 0; index < blocks.length; index++) {
        // Get the row and column corresponding to the position of the block.
        final int row = 3 - (int) blocks[index].getY();
        final int column = (int) blocks[index].getX();
        // Draw the block.
        g2d.fillRoundRect((int) (column * block_height +
                                 block_height / Math.pow(blocks.length, 2)),
                          (int) (row * block_height +
                              block_height / Math.pow(blocks.length, 2)),
                          (int) (block_height - block_height / Math.pow(blocks.length, 2)),
                          (int) (block_height - block_height / Math.pow(blocks.length, 2)),
                          (int) (block_width / 2), (int) (block_height / 2));
      }
    }
  }

  /**
   * Updates the next piece display when the next piece changes.
   * 
   * @param the_board The board informing the next piece display.
   * @param the_event What changed in the board.
   */
  @Override
  public void update(final Observable the_board, final Object the_event) {
    if (the_event == TetrisEvent.PIECE_CHANGED) {
      my_next_piece = my_board.getNextPiece();
      repaint();
    }
  }
}
