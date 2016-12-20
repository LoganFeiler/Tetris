/*
s * Logan Feiler
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
 * These are the methods that all pieces use.
 * 
 * @author Logan Feiler
 * @version November 19, 2010
 */
public class GeneralPiece implements Cloneable {

  /**
   * The index of the current phase of the orientation (in essence, the rotation).
   */
  private int my_rotation;

  /**
   * The current x and y coordinates of the piece. Initialized with the x and y coordinates
   * of the starting position.
   */
  private Point my_location;
  
  /**
   * The color of the piece.
   */
  private final Color my_color;

  /**
   * The various orientations of the piece.
   */
  private List<Point[]> my_orientations;

  /**
   * A constructor that initializes the various orientations of the piece.
   * 
   * @param the_orientations The orientations of the piece.
   * @param the_location The starting location of the piece.
   * @param the_color The color of the piece.
   */
  protected GeneralPiece(final List<Point[]> the_orientations, final Point the_location,
                         final Color the_color) {
    my_orientations = the_orientations;
    my_location = the_location;
    my_color = the_color;
  }

  /**
   * Returns the current location.
   * 
   * @return A copy of the point that represents the current location of the piece.
   */
  public Point getLocation() {
    return (Point) my_location.clone();
  }

  /**
   * This is just so I can check the rotations when testing my pieces.
   * 
   * @return The current rotation as an integer.
   */
  public int getRotation() {
    return my_rotation;
  }

  /**
   * Moves the piece to the left one space.
   */
  public void moveLeft() {
    my_location.setLocation(my_location.getX() - 1.0, my_location.getY());
  }

  /**
   * Moves the piece to the right one space.
   */
  public void moveRight() {
    my_location.setLocation(my_location.getX() + 1.0, my_location.getY());
  }

  /**
   * Moves the piece down one space.
   */
  public void moveDown() {
    my_location.setLocation(my_location.getX(), my_location.getY() - 1.0);
  }
  
  /**
   * Moves the piece up one space.
   */
  public void moveUp() {
    my_location.setLocation(my_location.getX(), my_location.getY() + 1.0);
  }

  /**
   * Rotates the piece counter-clockwise by 90 degrees.
   */
  public void rotate() {
    my_rotation = (my_rotation + 1) % my_orientations.size();
  }
  
  /**
   * Rotates the piece clockwise by 90 degrees.
   */
  public void reverseRotate() {
    if (my_rotation <= 0) {
      my_rotation = my_orientations.size() - 1;
    } else {
      my_rotation = my_rotation - 1;
    }
  }
  
  /**
   * Returns the color of the piece.
   * 
   * @return The color of the piece.
   */
  public Color getColor() {
    return my_color;
  }
  
  /**
   * Returns a point array of the location of all the blocks of a piece.
   * 
   * @return A point array of the location of all the blocks of a piece.
   */
  public Point[] getOrientation() {
    final Point[] current_orientation = new Point[my_orientations.get(my_rotation).length];
    // For each point in the array...
    for (int index = 0; index < current_orientation.length; index++) {
      // Put a copy of the point in the new array.
      current_orientation[index] = new Point();
      current_orientation[index].setLocation(my_orientations.get(my_rotation)[index].getX(),
                                             my_orientations.get(my_rotation)[index].getY());
    }
    return current_orientation;
  }

  /**
   * Returns a point array of the location of all the blocks of a piece.
   * 
   * @return A point array of the location of all the blocks of a piece.
   */
  public Point[] getState() {
    // TODO Find out if I should combine getOrientation() with getLocation().
    final Point[] current_state = new Point[my_orientations.get(my_rotation).length];
    // For each point in the array...
    for (int index = 0; index < current_state.length; index++) {
      // Put a copy of the point in the new array.
      current_state[index] = new Point();
      // NOTE: I think this would make getLocation() and getRotation() unnecessary.
      // However, I don't know how to do the JUnit tests without them.
      current_state[index].setLocation(my_orientations.get(my_rotation)[index].getX() +
                                             my_location.getX(), +
                                             my_orientations.get(my_rotation)[index].getY() +
                                             my_location.getY());
    }
    return current_state;
  }

  /**
   * Creates and returns a string representation of the piece.
   * 
   * @return A string that describes the current state of the piece.
   */
  public String toString() {
    final StringBuilder new_string = new StringBuilder();
    final int piece_size = my_orientations.get(my_rotation).length;

    //Append an ASCI code representation of the current orientation of the piece.
    for (int y_coordinate = piece_size - 1; y_coordinate >= 0; y_coordinate--) {
      for (int x_coordinate = 0; x_coordinate < piece_size; x_coordinate++) {
        boolean block_found = false;
        // For each point in the array...
        for (int index = 0; index < piece_size; index++) {
          if (x_coordinate == (int) my_orientations.get(my_rotation)[index].getX() &&
              y_coordinate == (int) my_orientations.get(my_rotation)[index].getY()) {
            block_found = true;
          } 
        }
        if (block_found) {
          new_string.append("#");
        } else {
          new_string.append(".");
        }
      }
      new_string.append("\n");
    }

    // Append the current location, on the board, of the piece.
    new_string.append("Location: (");
    new_string.append((int) my_location.getX());
    new_string.append(", ");
    new_string.append((int) my_location.getY());
    new_string.append(")\n");

    return new_string.toString();
  }

  /**
   * Creates and returns a copy of the piece.
   * 
   * @return A copy of the piece.
   * @throws CloneNotSupportedException If the clone method is not supported for the objects
   * being cloned.
   */
  public Object clone() throws CloneNotSupportedException {
    final GeneralPiece new_piece = (GeneralPiece) super.clone();
    new_piece.my_orientations = new ArrayList<Point[]>(my_orientations.size());
    new_piece.my_location = (Point) my_location.clone();

    // Create a deep copy of my_orientations.
    // For each array in the list...
    for (int list_index = 0; list_index < my_orientations.size(); list_index++) {
      final int array_length = my_orientations.get(list_index).length;
      final Point[] clone_orientations = new Point[array_length];
      final Point[] real_orientations = my_orientations.get(list_index);
      // For each point in the array...
      for (int array_index = 0; array_index < array_length; array_index++) {
        // Put a copy of the point in the new array.
        clone_orientations[array_index] = (Point) real_orientations[array_index].clone();
      }
      new_piece.my_orientations.add(clone_orientations);
    }
    
    return new_piece;
  }
}
