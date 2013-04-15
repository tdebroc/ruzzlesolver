package ruzzleSolver;
import java.util.Iterator;
import java.util.TreeSet;


public class Words {
  
  TreeSet<Word> wordSet = new TreeSet<Word>();
  
  public void addWord(Word word) {
  if (word.getScore() < 4) return;
    Word wordFound = wordSetContains(word);
    if (wordFound != null) {
      if (wordFound.getScore() < word.getScore()) {
        wordSet.remove(wordFound);
        wordSet.add(word);
      }
    } else {
      wordSet.add(word);
    }
  }
  
  private Word wordSetContains(Word word) {
    Iterator<Word> itWordSet = wordSet.iterator();
    while (itWordSet.hasNext()) {
      Word w = itWordSet.next();
      if (word.equals(w)) {
        return w;
      }
    }
    return null;
  }
  
  public String toString() {
    StringBuilder wordsToString = new StringBuilder();
    wordsToString.append("== Print results ==\n");
    Iterator<Word> itWordSet = wordSet.iterator();
    while (itWordSet.hasNext()) {
      Word wordToPrint = itWordSet.next();
      wordsToString.append(wordToPrint + "\n");
      wordsToString.append(wordToPrint.gridWordToString());
    }
    return wordsToString.toString();
  }
  
  public void print() {
  }
  
}
