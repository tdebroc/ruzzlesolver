package ruzzleSolver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class Letter {
  
  private char value;
  private HashMap<Character, Letter> childrens = new HashMap<Character, Letter>();
  private Letter father = null;
  private boolean isWord = false;
  private String word;
  
  public Letter (char c) {
    this.value = c; 
  }
  
  
  public static void displayChildrenWords(Letter letter) {
    Iterator<Entry<Character, Letter>> childrensLetters =
        letter.getChildrens().entrySet().iterator();
    while (childrensLetters.hasNext()) {
      displayWords(childrensLetters.next().getValue(), "");
    }
  }
  
  public static void displayWords(Letter letter, String word) {
    String currentWord = new String(word + letter.getValue());
    if (letter.isWord()) {
      System.out.println(word + letter.getValue());
    }
    Iterator<Entry<Character, Letter>> childrensLetters =
        letter.getChildrens().entrySet().iterator();
    while (childrensLetters.hasNext()) {
      displayWords(childrensLetters.next().getValue(), currentWord);
    }
    
  }
  
  
  public HashMap<Character, Letter> getChildrens() {
    return childrens;
  }
  public void setChildrens(HashMap<Character, Letter> childrens) {
    this.childrens = childrens;
  }
  public Letter getFather() {
    return father;
  }
  public void setFather(Letter father) {
    this.father = father;
  }
  public boolean isWord() {
    return isWord;
  }
  public void setWord(boolean isWord) {
    this.isWord = isWord;
  }
  public char getValue() {
    return value;
  }
  public void setValue(char value) {
    this.value = value;
  }


  public String getWord() {
    return word;
  }


  public void setWord(String word) {
    this.word = word;
  }
  
  
}
