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
public class PieceJ extends GeneralPiece {

  /**
   * Constructor that initializes the orientation.
   * 
   * @param the_location The starting location of the piece.
   */
  public PieceJ(final Point the_location) {
    super(setOrientations(), the_location, Color.BLUE);
  }

  /**
   * Returns the orientations for the J piece.
   * 
   * @return A list of point arrays that store the orientations.
   */
  private static List<Point[]> setOrientations() {
    final List<Point[]> orientations = new ArrayList<Point[]>(4);
    orientations.add(new Point[] {new Point(0, 1), new Point(1, 1), new Point(2, 1),
      new Point(0, 2)});
    orientations.add(new Point[] {new Point(0, 0), new Point(1, 0), new Point(1, 1),
      new Point(1, 2)});
    orientations.add(new Point[] {new Point(2, 0), new Point(0, 1), new Point(1, 1),
      new Point(2, 1)});
    orientations.add(new Point[] {new Point(1, 0), new Point(1, 1), new Point(1, 2),
      new Point(2, 2)});
    return orientations;
  }
}
