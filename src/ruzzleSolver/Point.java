package ruzzleSolver;
import java.util.LinkedHashSet;


public class Point implements Comparable<Point> {
  private int x;
  private int y;
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  public int getY() {
    return y;
  }
  public void setY(int y) {
    this.y = y;
  }

  public String toString() {
    return "[" + x + "," + y + "]";
  }
  
  @Override
  public int hashCode() {
    int hashCode = x * 100 + y;
    return hashCode ;
  }
  
  
  @Override 
  public boolean equals(Object o) {
    Point p = (Point) o;
    return (this.x == p.x && this.y == p.y);
  }
  
  public int compareTo(Point p) {
    if (this.x == p.x && this.y == p.y) {
      return 0;
    } else {
      return (this.x - p.x + this.y - p.y > 0) ? 1 : -1;
    }
  }
  
  public static boolean isNextValidPoint(Point p, LinkedHashSet<Point> pointVisited) {
    int gridSize = RuzzleHelper.grid.length;
    return !(p.getX() >= gridSize || p.getY() >= gridSize || p.getX() < 0 ||
        p.getY() < 0 || pointVisited.contains(p));
  }
  
  public Character getChar(char[][] grid) {
    return grid[this.y][this.x];
  } 
  
}
