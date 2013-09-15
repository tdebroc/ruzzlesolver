package ruzzleSolver;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class RuzzleHelper {
  // static ArrayList<String> dictionnary = new ArrayList<String>();
  static int gridLength = 4;
  static char[][] grid = new char[gridLength][gridLength];
  static int[][] gridBonus = new int[gridLength][gridLength];
  
  private static Words wordsFound = new Words(); 
  static Letter dictionnaryRoot = new Letter(' ');
   static String language = "French";
 // static String language = "Espanol";
 // static String language = "English";
  private static BufferedReader bufferedReader;
  private static String folder = "";
  
  /**
   * Controller for the application
   * @param args
   */
  public static void main(String[] args) {
    loadLettersCost();
    loadGridBonus();
    buildDictionnaryWithBufferedReader(folder + "dictionnary/" + language + "Dictionnary.txt");
    // Letter.displayChildrenWords(dictionnaryRoot);
    loadGrid(folder + "gameplay/grid.txt");
    analyzeGrid();
    wordsFound.print();
  }
  
  public static void solveGrid() {
    loadLettersCost();
    buildDictionnaryWithBufferedReader(folder + "dictionnary/" + language + "Dictionnary.txt");
    // Letter.displayChildrenWords(dictionnaryRoot);
    // buildGrid(folder + "gameplay/grid.txt");
    analyzeGrid();
  }
  
  /**
   * Analyze the grid to get all words possible
   */
  public static void analyzeGrid() {
    System.out.println("== Let's start the analysis ==");
    Date start = new Date();
    wordsFound = new Words();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        Point startingPoint = new Point(i, j);
        analyzeBox(startingPoint, new LinkedHashSet<Point>(), dictionnaryRoot);
      }
    }
    Date end = new Date();
    int totalTime = (int) (end.getTime() - start.getTime());
    System.out.println(" totalTime analysis " + totalTime);
  }
  
  /**
   * Analyze one box and continue recursively.
   * @param p
   * @param pointVisited
   */
  public static void analyzeBox(Point p, LinkedHashSet<Point> pointVisited,
      Letter precedentLetter) {
    Character currentChar = p.getChar(grid);
    Letter currentLetter = precedentLetter.getChildrens().get(currentChar);
    if (currentLetter == null) {
      return;
    }

    pointVisited.add(p);

    if (currentLetter.isWord()) {
      // System.out.println(currentLetter.getWord());
      Word wordFound = new Word(pointVisited, currentLetter.getWord());
      wordsFound.addWord(wordFound);
    }
    
    Point[] nextPoints = {new Point(p.getX(), p.getY() + 1),
        new Point(p.getX(), p.getY() - 1),
        new Point(p.getX() - 1, p.getY()),
        new Point(p.getX() + 1, p.getY()),
        new Point(p.getX() - 1, p.getY() - 1),
        new Point(p.getX() - 1, p.getY() + 1),
        new Point(p.getX() + 1, p.getY() - 1),
        new Point(p.getX() + 1, p.getY() + 1),
    };  
    for (int i = 0; i < nextPoints.length; i++) {
      if (Point.isNextValidPoint(nextPoints[i], pointVisited)) {
        analyzeBox(nextPoints[i], pointVisited,
            currentLetter);
      }
    }
    pointVisited.remove(p);
  }

  
  /**
   * Read the dictionnary file and file the dictionnary tree.
   * @param fileName
   */
  public static void buildDictionnaryWithBufferedReader(String fileName) {
    InputStream in;
    Date start = new Date();
    Letter currentLetter = dictionnaryRoot;
    StringBuilder currentWord = new StringBuilder();
    
    try {
      in = new FileInputStream(fileName);
      Reader reader = new InputStreamReader(in);
      int r;
      bufferedReader = new BufferedReader(reader);
      
      
      while ((r = bufferedReader.read()) != -1) {
        char c = (char) r;
        if (c == '\n') {
          currentLetter.setWord(true);
          currentLetter.setWord(currentWord.toString());
          currentLetter = dictionnaryRoot;
          currentWord.setLength(0);
        } else {
          Letter nextLetter = currentLetter.getChildrens().get(c);
          if (nextLetter == null) {
            Letter newLetter = new Letter(c);
            currentLetter.getChildrens().put(c, newLetter);
            currentLetter = newLetter;
          } else {
            currentLetter = nextLetter;
          }
          currentWord.append(c);
        }
      }
      Date end = new Date();
      int totalTime = (int) (end.getTime() - start.getTime());
      System.out.println(" totalTime build dictionnary " + totalTime);
    
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  
  /**
   * Load the letters cost from the specific file
   */
  public static void loadLettersCost() {
    StringBuilder currentWord = new StringBuilder();
    try {
      Word.characterCost = new HashMap<Character, Integer>();
      char charFromFile = ' ';
      int value;
      InputStream in = new FileInputStream(folder + "gameplay/lettersValues.txt");
      Reader reader = new InputStreamReader(in);
      int r;
      bufferedReader = new BufferedReader(reader);
      while ((r = bufferedReader.read()) != -1) {
        char c = (char) r;
        if (c == '\n') {
          value = Integer.parseInt(currentWord.toString());
          Word.characterCost.put(charFromFile, value);
          currentWord.setLength(0);
        } else if (c == ' ') {
          charFromFile = new Character(currentWord.toString().charAt(0));
          currentWord.setLength(0);
        } else {
          currentWord.append(c);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Load the grid from the file.
   * @return 
   */
  public static void loadGrid(String gridFileName) {
    try {
      char charFromFile = ' ';
      int line = 0;
      int column = 0;

      InputStream in = new FileInputStream(folder + "gameplay/grid.txt");
      Reader reader = new InputStreamReader(in);
      int r;
      bufferedReader = new BufferedReader(reader);
      while ((r = bufferedReader.read()) != -1) {
        char c = (char) r;
        if (c == '\n') {
          line++;
          column = 0;
        } else {
          charFromFile = new Character(c);
          grid[line][column] = charFromFile;
          column++;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load the grid from the file.
   * @return 
   */
  public static void loadGridBonus() {
    try {
      String charFromFile = "";
      int line = 0;
      int column = 0;
      InputStream in = new FileInputStream(folder + "gameplay/gridBonus.txt");
      Reader reader = new InputStreamReader(in);
      int r;
      bufferedReader = new BufferedReader(reader);
      while ((r = bufferedReader.read()) != -1) {
        char c = (char) r;
        if (c == '\n') {
          line++;
          column = 0;
        } else {
          charFromFile = new String(c + "");
          gridBonus[line][column] = Integer.parseInt(charFromFile);
          column++;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Words getWordsFound() {
    return wordsFound;
  }

  public static void setWordsFound(Words wordsFound) {
    RuzzleHelper.wordsFound = wordsFound;
  }
  
  public static void buildGrid(String gridString) {
    int indexString = 0;
    for (int i = 0; i < RuzzleHelper.grid.length; i++) {
      for (int j = 0; j < RuzzleHelper.grid[0].length; j++) {
        RuzzleHelper.grid[i][j] = gridString.charAt(indexString++);
      }
    }
  }
  
  public static void buildGridBonus(String gridBonusString) {
    int indexString = 0;
    for (int i = 0; i < RuzzleHelper.gridBonus.length; i++) {
      for (int j = 0; j < RuzzleHelper.gridBonus[0].length; j++) {
        RuzzleHelper.gridBonus[i][j] = (int) gridBonusString.charAt(indexString++) - 48;
      }
    }
  }
  
}
