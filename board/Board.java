/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 22, 2010
 */

package board;

import gui.TetrisEvent;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import pieces.GeneralPiece;
import pieces.PieceCorner;
import pieces.PieceI;
import pieces.PieceJ;
import pieces.PieceL;
import pieces.PieceLine;
import pieces.PieceO;
import pieces.PieceS;
import pieces.PieceT;
import pieces.PieceZ;

/**
 * The game board.
 * 
 * @author Logan Feiler
 * @version November 22, 2010
 */
public class Board extends Observable {
  
  /**
   * Width of the board.
   */
  private static final int BOARD_WIDTH = 10;
  
  /**
   * Height of the board.
   */
  private static final int BOARD_HEIGHT = 20;
  
  /**
   * The initial height of all the pieces.
   */
  private static final int PIECE_HEIGHT = 2;
  
  /**
   * The number of different pieces.
   */
  private static final int NUMBER_OF_PIECES = 9;
  
  /**
   * The number of different red, green, or blue values.
   */
  private static final int RGB_MAX = 256;
  
  /**
   * The number of lines needed to be cleared to advance a level.
   */
  private static final int LEVEL_REQUIREMENT = 10;
  
  /**
   * The starting location of all the pieces.
   */
  private static final Point STARTING_POINT =
    new Point((BOARD_WIDTH / 2) - 2, BOARD_HEIGHT - 1);
  
  /**
   * The color of the background of the board.
   */
  private static final Color BOARD_COLOR = Color.BLACK;
  
  /**
   * A predetermined sequence of pieces, stored as integers.
   */
  private static final int[] PIECE_SEQUENCE = {0, 0, 3, 2, 1, 0, 0, 5, 5, 0, 1, 4, 4, 6, 4, 0,
    0, 6, 5, 5, 3, 4, 4, 2, 1, 5, 5, 3, 2, 4};
  
  /**
   * Randomly generates pieces. 
   */
  private final Random my_random_number_generator = new Random();
  
  /**
   * The spaces of the board.
   */
  private final List<Color[]> my_color_grid =
    new ArrayList<Color[]>(BOARD_HEIGHT + PIECE_HEIGHT + 1);
  
  /**
   * Whether or not to use a predetermined sequence of pieces.
   */
  private final boolean my_generate_random_piece_boolean;
  
  /**
   * The player's current score.
   */
  private int my_score;

  /**
   * The number of lines cleared.
   */
  private int my_cleared_lines;
  
  /**
   * The index of the piece to use for the predetermined sequence of pieces.
   */
  private int my_piece_index;
  
  /**
   * The current piece-in-progress.
   */
  private GeneralPiece my_current_piece;
  
  /**
   * The next piece.
   */
  private GeneralPiece my_next_piece;

  /**
   * Initializes the instance fields and tells the board whether or not to use random pieces.
   * 
   * @param the_boolean True for randomly generated pieces. False for a specific sequence of 
   * pieces. 
   */
  public Board(final boolean the_boolean) {
    super();
    my_generate_random_piece_boolean = the_boolean;
    createBoard();
    setChanged();
    notifyObservers(TetrisEvent.BOARD_UPDATED);
  }
  
  /**
   * Creates, fills, and adds a new color array to the end of the list.
   */
  private void createBoard() {
    for (int list_index = 0; list_index < BOARD_HEIGHT + PIECE_HEIGHT + 1; list_index++) {
      createNewRow();
    }
  }
  
  /**
   * Creates a new row and adds it to the board.
   */
  private void createNewRow() {
    final Color[] new_row = new Color[BOARD_WIDTH];
    for (int array_index = 0; array_index < BOARD_WIDTH; array_index++) {
      new_row[array_index] = BOARD_COLOR;
    }
    my_color_grid.add(new_row);
  }
  
  /**
   * Empties the board and refills it with the background color.
   */
  public void emptyBoard() {
    my_color_grid.clear();
    my_score = 0;
    my_cleared_lines = 0;
    createBoard();
    assignNewPiece();
    assignNewPiece();
    setChanged();
    notifyObservers(TetrisEvent.NEW_GAME);
  }
  
  /**
   * Assigns a new piece and color for the piece-in-progress.
   */
  private void assignNewPiece() {
    int new_piece;
    my_current_piece = my_next_piece;

    if (my_generate_random_piece_boolean) {
      new_piece = my_random_number_generator.nextInt(NUMBER_OF_PIECES);
    } else {
      new_piece = PIECE_SEQUENCE[my_piece_index];
      my_piece_index = (my_piece_index + 1) % PIECE_SEQUENCE.length;
    }

    switch(new_piece) {
      
      case 0:
        my_next_piece = new PieceI((Point) STARTING_POINT.clone());
        break;
        
      case 1: 
        my_next_piece = new PieceJ((Point) STARTING_POINT.clone());
        break;
        
      case 2:
        my_next_piece = new PieceL((Point) STARTING_POINT.clone());
        break;
        
      case 3:
        my_next_piece = new PieceO((Point) STARTING_POINT.clone());
        break;
        
      case 4:
        my_next_piece = new PieceS((Point) STARTING_POINT.clone());
        break;
        
      case 5:
        my_next_piece = new PieceT((Point) STARTING_POINT.clone());
        break;
        
      case 6:
        my_next_piece = new PieceZ((Point) STARTING_POINT.clone());
        break;
        
      case 7:
        my_next_piece = new PieceCorner((Point) STARTING_POINT.clone());
        break;
        
      case 8:
        my_next_piece = new PieceLine((Point) STARTING_POINT.clone());
        break;
        
      default:
        my_next_piece = new PieceO((Point) STARTING_POINT.clone());
        // This shouldn't happen.
        break;
    }
    setChanged();
    notifyObservers(TetrisEvent.PIECE_CHANGED);
  }
  
  /**
   * Returns the board's height and width.
   * 
   * @return An integer array with the board's height at index 0 and width at index 1.
   */
  public int[] getBoardDimensions() {
    return new int[] {BOARD_HEIGHT, BOARD_WIDTH}; 
  }
  
  /**
   * Returns the frozen pieces as a 2-dimensional array of colors.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   * @return The frozen pieces, as a 2-dimensional color array.
   */
  public Color[][] getBoardState() throws NullPointerException {
    // Create a 2-dimensional array of frozen blocks.
    final Color[][] frozen_pieces = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    for (int list_index = 0; list_index < BOARD_HEIGHT; list_index++) {
      for (int array_index = 0; array_index < BOARD_WIDTH; array_index++) {
        frozen_pieces[BOARD_HEIGHT - list_index - 1][array_index] =
          my_color_grid.get(list_index)[array_index];
      }
    }
    
    if (my_current_piece != null) {
      // Add the piece-in-progress to the character array representing the board.
      final Point[] blocks = my_current_piece.getState();
      // For each block...
      for (int index = 0; index < blocks.length; index++) {
        // Get the row and column corresponding to the position of the block.
        final int row = (int) blocks[index].getY();
        final int column = (int) blocks[index].getX();
        if (row < BOARD_HEIGHT) {
          frozen_pieces[BOARD_HEIGHT - row - 1][column] = my_current_piece.getColor();
        }
      }
    }
    return frozen_pieces;
  }
  
  /**
   * Returns the player's current score.
   * 
   * @return The player's current score.
   */
  public int getScore() {
    return my_score;
  }
  
  /**
   * Returns the current level.
   * 
   * @return The current level.
   */
  public int getLevel() {
    return (my_cleared_lines / LEVEL_REQUIREMENT) + 1;
  }
  
  /**
   * Returns the number of lines that have been cleared during this game.
   * 
   * @return The number of lines that have been cleared during this game.
   */
  public int getLinesCleared() {
    return my_cleared_lines;
  }
  
  /**
   * Returns a clone of the current piece-in-progress.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   * @return The current piece-in-progress.
   */
  public GeneralPiece getCurrentPiece() throws NullPointerException {
    GeneralPiece current_piece = null;
    try {
      current_piece = (GeneralPiece) my_current_piece.clone();
    } catch (final CloneNotSupportedException exception) {
      exception.printStackTrace();
    }
    return current_piece;
  }
  
  /**
   * Moves the current piece one space to the left.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   */
  public void moveLeft() throws NullPointerException {
    my_current_piece.moveLeft();
    if (canMove(my_current_piece)) {
      setChanged();
      notifyObservers(TetrisEvent.BOARD_UPDATED);
    } else {
      my_current_piece.moveRight();
    }
  }
  
  /**
   * Moves the current piece one space to the right.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   */
  public void moveRight() throws NullPointerException {
    my_current_piece.moveRight();
    if (canMove(my_current_piece)) {
      setChanged();
      notifyObservers(TetrisEvent.BOARD_UPDATED);
    } else {
      my_current_piece.moveLeft();
    }
  }
  
  /**
   * Moves the current piece down one space, if it can, and returns
   * whether or not the piece can move down.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   * @return Whether or not the game is over.
   */
  public boolean moveDown() throws NullPointerException {
    boolean can_move_down = true;
    my_current_piece.moveDown();
    if (canMove(my_current_piece)) {
      setChanged();
      notifyObservers(TetrisEvent.BOARD_UPDATED);
    } else {
      my_current_piece.moveUp();
      freezePiece();
      can_move_down = false;
    }
    return can_move_down;
  }
  
  /**
   * Finds the location of the ghost piece and returns it.
   * 
   * @return The ghost piece as a 4 element point array of the location of the blocks.
   */
  public Point[] getGhostPiece() {
    GeneralPiece ghost_piece = null;
    try {
      ghost_piece = (GeneralPiece) my_current_piece.clone();
    } catch (final CloneNotSupportedException exception) {
      exception.printStackTrace();
    }
    boolean can_move_down = true;
    do {
      ghost_piece.moveDown();
      if (!canMove(ghost_piece)) {
        ghost_piece.moveUp();
        can_move_down = false;
      }
    } while (can_move_down);
    final Point[] ghost_blocks = ghost_piece.getState();
    for (int index = 0; index < ghost_blocks.length; index++) {
      ghost_blocks[index].setLocation(ghost_blocks[index].getX(),
                                      (BOARD_HEIGHT - 1) - ghost_blocks[index].getY());
    }
    return ghost_blocks;
  }
  
  
  /**
   * Rotates the current piece once, counter-clockwise.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   */
  public void rotate() throws NullPointerException {
    my_current_piece.rotate();
    if (canMove(my_current_piece)) {
      setChanged();
      notifyObservers(TetrisEvent.BOARD_UPDATED);
    } else {
      my_current_piece.reverseRotate();
    }
  }
  
  /**
   * Drops the current piece to the lowest point it can occupy.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   */
  public void drop() throws NullPointerException {
    boolean can_move_down = true;
    do {
      can_move_down = moveDown();
    } while (can_move_down);
  }
  
  /**
   * Returns the next piece.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   * @return The next piece.
   */
  public GeneralPiece getNextPiece() throws NullPointerException {
    GeneralPiece next_piece = null;
    try {
      next_piece = (GeneralPiece) my_next_piece.clone();
    } catch (final CloneNotSupportedException exception) {
      exception.printStackTrace();
    }
    return next_piece;
  }
  
  /**
   * Returns whether or not the game is over.
   * 
   * @return Whether or not the game is over.
   */
  public boolean isGameOver() {
    // Checks if there are any frozen blocks above the top of the board.
    // If there are, the game is over.
    boolean game_over = false;
    final Color[] board_top = my_color_grid.get(BOARD_HEIGHT);
    for (int index = 0; index < BOARD_WIDTH; index++) {
      if (board_top[index] != BOARD_COLOR) {
        game_over = true;
      }
    }
    return game_over;
  }
  
	/**
	 * Checks and returns whether or not the piece can move where it wants to. 
	 *
	 * @param the_piece The piece being checked.
	 * @return Whether or not the piece can move where it wants to.
	 */
	private boolean canMove(final GeneralPiece the_piece) {
		final Point[] blocks = the_piece.getState();
		boolean can_move = true;
		// For each block of the piece...
		for(int index = 0; index < blocks.length; index++) {
			final int row = (int) blocks[index].getY();
			final int column = (int) blocks[index].getX();
			if(row >= 0) {
				final Color[] color_array = my_color_grid.get(row);
				// Check if the block is off the board.
				if(blocks[index].getX() < 0 || blocks[index].getX() >= BOARD_WIDTH ||
				   blocks[index].getY() < 0 || blocks[index].getY() >= BOARD_HEIGHT + PIECE_HEIGHT) {
					can_move = false;
				} else if(color_array[column] != BOARD_COLOR) {
					// If the block is conflicting with a previously frozen piece...
					can_move = false;
				}
			} else {
				can_move = false;
			}
		}
		return can_move;
	}
  
  /**
   * Updates the state of the board.
   * 
   * @throws NullPointerException if the game hasn't been started yet.
   */
  public void update() throws NullPointerException {
    moveDown();
  }
  
  /**
   * Randomizes the colors of the frozen blocks.
   */
  public void randomizeColors() {
    for (int list_index = BOARD_HEIGHT - 1; list_index >= 0; list_index--) {
      final Color[] color_array = my_color_grid.get(list_index);
      for (int array_index = 0; array_index < BOARD_WIDTH; array_index++) {
        if (color_array[array_index] != BOARD_COLOR) {
          final int red = my_random_number_generator.nextInt(RGB_MAX / 2) + (RGB_MAX / 2);
          final int green = my_random_number_generator.nextInt(RGB_MAX / 2) + (RGB_MAX / 2);
          final int blue = my_random_number_generator.nextInt(RGB_MAX / 2) + (RGB_MAX / 2);
          color_array[array_index] = new Color(red, green, blue);
        }
      }
      my_color_grid.set(list_index, color_array);
    }
  }

	/**
	 * Freezes the piece in place, removes full lines, and assigns a new piece.
	 *
	 * @throws NullPointerException if the current piece hasn't been assigned yet.
	 */
	private void freezePiece() throws NullPointerException {
		int lines_cleared = 0;
		final Point[] blocks = my_current_piece.getState();
		// For each block...
		for(int index = 0; index < blocks.length; index++) {
			// Get the row and column corresponding to the location of the block.
			final int row = (int) blocks[index].getY();
			final int column = (int) blocks[index].getX();
			// Add the block to the grid.
			final Color[] color_array = my_color_grid.get(row);
			color_array[column] = my_current_piece.getColor();
			my_color_grid.set(row, color_array);
		}
		my_score++;
		
		// Clears full lines.
		for(int list_index = BOARD_HEIGHT - 1; list_index >= 0; list_index--) {
			boolean line_is_full = true;
			final Color[] color_array = my_color_grid.get(list_index);
			for(int array_index = 0; array_index < BOARD_WIDTH; array_index++) {
				if(color_array[array_index] == BOARD_COLOR) {
					line_is_full = false;
				}
			}
			// If the line is full, remove it from the board.
			if(line_is_full) {
				my_color_grid.remove(list_index);
				createNewRow();
				lines_cleared++;
			}
		}
		my_score += BOARD_WIDTH * Math.pow(lines_cleared, 2);
		my_cleared_lines += lines_cleared;
		// If the user clears 4 lines at once, notify the observers, so they can react accordingly.
		if(lines_cleared == 4) {
			setChanged();
			notifyObservers(TetrisEvent.TETRIS);
		}
		// If the game is over, update the display to indicate it.
		if(isGameOver()) {
			setChanged();
			notifyObservers(TetrisEvent.GAME_OVER);
		} else {
			assignNewPiece();
		}
		setChanged();
		notifyObservers(TetrisEvent.BOARD_UPDATED);
	}
  
  /**
   * Creates and returns a string representation of the current state of the board.
   * 
   * @return A string representation of the current state of the board.
   */
  public String toString() {
    
    // Create a 2-dimensional character array to represent frozen pieces on the board. 
    final char[][] asci_board = new char[BOARD_HEIGHT][BOARD_WIDTH];
    for (int list_index = 0; list_index < BOARD_HEIGHT; list_index++) {
      final Color[] color_array = my_color_grid.get(list_index);
      for (int array_index = 0; array_index < BOARD_WIDTH; array_index++) {
        if (color_array[array_index] == BOARD_COLOR) {
          asci_board[list_index][array_index] = '.';
        } else {
          asci_board[list_index][array_index] = '@';
        }
      }
    }
    
    if (my_current_piece != null) {
      // Add the piece-in-progress to the character array representing the board.
      final Point[] blocks = my_current_piece.getState();
      // For each block...
      for (int index = 0; index < blocks.length; index++) {
        // Get the row and column corresponding to the position of the block.
        final int row = (int) blocks[index].getY();
        final int column = (int) blocks[index].getX();
        if (row < BOARD_HEIGHT) {
          asci_board[row][column] = '#';
        }
      }
    }
    
    //Create a string from the character array representing the board.
    final StringBuilder board_string = new StringBuilder();
    for (int row_index = BOARD_HEIGHT - 1; row_index >= 0; row_index--) {
      for (int column_index = 0; column_index < BOARD_WIDTH; column_index++) {
        board_string.append(asci_board[row_index][column_index]);
      }
      board_string.append('\n');
    }
    return board_string.toString();
  }
}
