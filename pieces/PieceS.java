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
 * The I piece.
 * 
 * @author Logan Feiler
 * @version November 19, 2010
 */
public class PieceS extends GeneralPiece {

  /**
   * Constructor that initializes the orientation.
   * 
   * @param the_location The starting location of the piece.
   */
  public PieceS(final Point the_location) {
    super(setOrientations(), the_location, Color.GREEN);
  }

  /**
   * Returns the orientations for the S piece.
   * 
   * @return A list of point arrays that store the orientations.
   */
  private static List<Point[]> setOrientations() {
    final List<Point[]> orientations = new ArrayList<Point[]>(2);
    orientations.add(new Point[] {new Point(0, 1), new Point(1, 1), new Point(1, 2),
      new Point(2, 2)});
    orientations.add(new Point[] {new Point(2, 1), new Point(1, 2), new Point(2, 2),
      new Point(1, 3)});
    return orientations;
  }
}
