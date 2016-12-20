/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * December 6, 2010
 */

package gui;

/**
 * The enmuerations that tell observers what has happened.
 * 
 * @author Logan Feiler
 * @version December 6, 2010
 */
public enum TetrisEvent {
  
  /**
   * A new game is beginning.
   */
  NEW_GAME,

  /**
   * Game over.
   */
  GAME_OVER,
  
  /**
   * Current piece and next piece have changed.
   */
  PIECE_CHANGED,
  
  /**
   * Board has updated.
   */
  BOARD_UPDATED,
  
  /**
   * The player got a Tetris.
   */
  TETRIS;
  
  /**
   * Empty constructor to prevent someone from creating an instance of TetrisEvent.
   */
  private TetrisEvent() {
    // Do nothing.
  }
}
