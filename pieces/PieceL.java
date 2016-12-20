/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 19, 2010
 */

package pieces;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The T piece.
 * 
 * @author Logan Feiler
 * @version November 19, 2010
 */
public class PieceL extends GeneralPiece {
  
  /**
   * Much better orange than the API provides.
   */
  private static final Color TRUE_ORANGE = new Color(255, 165, 0);
  
  /**
   * Constructor that initializes the orientation.
   * 
   * @param the_location The starting location of the piece.
   */
  public PieceL(final Point the_location) {
    super(setOrientations(), the_location, TRUE_ORANGE);
  }

  /**
   * Returns the orientations for the L piece.
   * 
   * @return A list of point arrays that store the orientations.
   */
  private static List<Point[]> setOrientations() {
    final List<Point[]> orientations = new ArrayList<Point[]>(4);
    orientations.add(new Point[] {new Point(0, 1), new Point(1, 1), new Point(2, 1),
      new Point(2, 2)});
    orientations.add(new Point[] {new Point(1, 0), new Point(1, 1), new Point(0, 2),
      new Point(1, 2)});
    orientations.add(new Point[] {new Point(0, 0), new Point(0, 1), new Point(1, 1),
      new Point(2, 1)});
    orientations.add(new Point[] {new Point(1, 0), new Point(2, 0), new Point(1, 1),
      new Point(1, 2)});
    return orientations;
  }
}
