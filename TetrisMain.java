import gui.TetrisGui;

/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 15, 2010
 */

/**
 * Starts the program.
 * 
 * @author Logan Feiler
 * @version November 15, 2010; 8:43 AM
 */
public final class TetrisMain {

  /**
   * A private constructor that prevents an instance of TetrisMain from being
   * created.
   */
  private TetrisMain() {
    // Do nothing.
  }

  /**
   * Launches the Tetris game.
   * 
   * @param the_args A string array of arguments that are required to start the
   *          program.
   */
  public static void main(final String[] the_args) {
    final TetrisGui tetris_game = new TetrisGui();
    tetris_game.guiCreator();
  }
}
