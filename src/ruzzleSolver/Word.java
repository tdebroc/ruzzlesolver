package ruzzleSolver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;


public class Word  implements Comparable<Word>{
  static HashMap<Character, Integer> characterCost;
  private LinkedHashSet<Point> pointVisited;
  private String wordValue;
  private int score;
  private char[][] gridWord =
      new char[RuzzleHelper.grid.length][RuzzleHelper.grid.length];
  
  
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }
  public LinkedHashSet<Point> getPointVisited() {
    return pointVisited;
  }
  public void setPointVisited(LinkedHashSet<Point> pointVisited) {
    this.pointVisited = pointVisited;
  }
  public String getWordValue() {
    return wordValue;
  }
  public void setWordValue(String wordValue) {
    this.wordValue = wordValue;
  }
  public Word(LinkedHashSet<Point> pointVisited2, String wordValue) {
    super();
    this.pointVisited = pointVisited2;
    this.wordValue = wordValue;
    calculateScore();
    generateGridWord();
  }
  
  public boolean equals(Word w) {
    return (w.getWordValue().equals(wordValue));
  }
  
  public String toString() {
    return score + " " + wordValue;
  }
  
  private void calculateScore() {
    score = 0;
    
    Iterator<Point> itPointSet = pointVisited.iterator();
    for (int i = 0; i < wordValue.length(); i++) {
      int letterValue = characterCost.get(wordValue.charAt(i));
      Point p = itPointSet.next();
      int bonusLetter = RuzzleHelper.gridBonus[p.getY()][p.getX()];
      if (bonusLetter == 1) {
        letterValue *= 2;
      } else if (bonusLetter == 2) {
        letterValue *= 3;
      }
      score += letterValue;
    }
    // Double (3) or Triple Word (4)
    itPointSet = pointVisited.iterator();
    while (itPointSet.hasNext()) {
      Point point = itPointSet.next();
      int bonusWord = RuzzleHelper.gridBonus[point.getY()][point.getX()];
      if (bonusWord == 3) {
        score *= 2;
      } else if (bonusWord == 4) {
        score *= 3;
      }
    }
    
  }
  
  private void generateGridWord() {
    Iterator<Point> itPointSet = pointVisited.iterator();
    int index = 0;
    while (itPointSet.hasNext()) {
      Point point = itPointSet.next();
      gridWord[point.getY()][point.getX()] = wordValue.charAt(index++);
    }
    
  }
  
  public String gridWordToString() {
    StringBuilder gridWordToString = new StringBuilder();  
    for (int i = 0; i < gridWord.length; i++) {
      gridWordToString.append("|");
      for (int j = 0; j < gridWord[i].length; j++) {
        gridWordToString.append(gridWord[i][j] == '\u0000'  ? ' ' :
            gridWord[i][j]);
      }
      gridWordToString.append("|\n");
    }
    return gridWordToString.toString();
  }
  
  
  public void printGridWord() {
    System.out.println(gridWordToString());
  }
  
  public char[][] getGridWord() {
    return gridWord;
  }
  public void setGridWord(char[][] gridWord) {
    this.gridWord = gridWord;
  }
  
  public int compareTo(Word word) {
    if (word.getWordValue().equals(wordValue)) {
      return 0;
    }
    return (word.score - score > 0) ? 1 : -1;
  }
}
