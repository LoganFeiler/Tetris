/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * November 16, 2010
 */

package pieces;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The I piece.
 * 
 * @author Logan Feiler
 * @version November 17, 2010
 */
public class PieceI extends GeneralPiece {

  /**
   * Constructor that initializes the orientation.
   * 
   * @param the_location The starting location of the piece.
   */
  public PieceI(final Point the_location) {
    super(setOrientations(), the_location, Color.CYAN);
  }

  /**
   * Returns the orientations for the I piece.
   * 
   * @return A list of point arrays that store the orientations.
   */
  private static List<Point[]> setOrientations() {
    final List<Point[]> orientations = new ArrayList<Point[]>(2);
    orientations.add(new Point[] {new Point(0, 2), new Point(1, 2), new Point(2, 2),
      new Point(3, 2)});
    orientations.add(new Point[] {new Point(2, 0), new Point(2, 1), new Point(2, 2),
      new Point(2, 3)});
    return orientations;
  }
}
