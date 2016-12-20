/*
 * Logan Feiler
 * 
 * TCSS 305 - Autumn 2010
 * Project - Tetris
 * December 7, 2010
 */

package pieces;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The line piece.
 * 
 * @author Logan Feiler
 * @version December 7, 2010
 */
public class PieceLine extends GeneralPiece {
  
  /**
   * Purple.
   */
//  private static final Color PURPLE = new Color(185, 75, 255);
  
  /**
   * Olive green.
   */
//  private static final Color OLIVE = new Color(107, 142, 35);
  
  /**
   * Pale green.
   */
//  private static final Color PALE_GREEN = new Color(152, 251, 152);
  
  /**
   * Light brown.
   */
//  private static final Color LIGHT_BROWN = new Color(222, 184, 135);

  /**
   * Constructor that initializes the orientation.
   * 
   * @param the_location The starting location of the piece.
   */
  public PieceLine(final Point the_location) {
    super(setOrientations(), the_location, Color.WHITE);
  }
  
  /**
   * Returns the orientations for the I piece.
   * 
   * @return A list of point arrays that store the orientations.
   */
  private static List<Point[]> setOrientations() {
    final List<Point[]> orientations = new ArrayList<Point[]>(2);
    orientations.add(new Point[] {new Point(0, 1), new Point(1, 1), new Point(2, 1)});
    orientations.add(new Point[] {new Point(1, 0), new Point(1, 1), new Point(1, 2)});
    return orientations;
  }
}
