/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is used to write the functions of the Dictionary App.
 * The functions are: addDict; editDict; insertFromFile; saveFile; speech; searchWord; removeWord.
 * @author Trung và Thành
 */

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

public class DictionaryManagement {

    Dictionary dict = new Dictionary();
    Voice voice;

    /**
     * The intent of this method is add a Word to the Dict.
     * Using the hash method to add.
     * @param word1: a variable to storage the property 'word'
     * @param meaning: a variable to storage the property 'meaning'
     */
    void addDict(String word1, String meaning) {
        word1 = word1.toLowerCase();
        meaning = meaning.toLowerCase();
        Word word = new Word(word1, meaning);
        int key = hash(word1);
        int a = 0;
        for (int j = 0; j < dict.wordList[key].size(); j++) {
            if (((Word) dict.wordList[key].get(j)).word.equals(word1)) {
                ((Word) dict.wordList[key].get(j)).meaning = String.format("%s, %s, %s", ((Word) dict.wordList[key].get(j)).meaning, "\n", meaning);
                a++;
            }
        }
        if (a == 0) {
            dict.wordList[key].add(word);
        }
    }

    /**
     * This method is used to edit a Word in the Dictionary
     * @param wordbf:  a variable to storage the property 'word'
     * @param meaning:  a variable to storage the property 'meaning'
     */
    void editDict(String wordbf, String meaning) {
        meaning = meaning.toLowerCase();
        int key = hash(wordbf);
        for (int j = 0; j < dict.wordList[key].size(); j++) {
            if (((Word) dict.wordList[key].get(j)).word.equals(wordbf)) {
                ((Word) dict.wordList[key].get(j)).meaning = meaning;
            }
        }
    }

    /**
     * Hash method to hash the Dictionary into 26 LinkedList by the first character, Ex: a, b, c, ...
     * @param x: a variable
     * @return the hash value: by the first char division residual by 97
     */
    int hash(String x) {
        int index = 0;
        for (int i = 0; i < x.length(); i++) {
            if ('a' > x.charAt(i) || 'z' < x.charAt(i)) {
                index++;
            } else {
                break;
            }
        }
        return x.charAt(index) % 97;
    }

    /**
     * This method is used to import data from file: dtb.txt
     * Using bufferedReader and fileReader
     * @throws FileNotFoundException
     */
    public void insertFromFile() throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader("dtb.txt"));
        try {
            String currentLine = reader.readLine();
            String word = null;
            String meaning = null;
            while (currentLine != null) {
                if (currentLine.charAt(0) == '@') {
                    if (word != null) {
                        int i = hash(word);
                        dict.wordList[i].add(new Word(word, meaning));
                    }
                    int index1 = currentLine.indexOf('/');
                    word = currentLine.substring(1, index1 - 2);
                    meaning = currentLine.substring(index1, currentLine.length());
                    currentLine = reader.readLine();
                } else {
                    meaning += "\n";
                    meaning += currentLine;
                    currentLine = reader.readLine();
                }
            }
            if (word != null) {
                int i = hash(word);
                dict.wordList[i].add(new Word(word, meaning));

            }
        } catch (IOException e) {
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
            }
        }
    }

    /**
     * This method is used to save the data of the Dictionary to a file name: saveFile.txt.
     * Using bufferedWriter and fileWriter.
     * @throws IOException
     */
    public void saveFile() throws IOException {
        String url = "saveFile.txt";
        File outFile = new File(url);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        try {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < dict.wordList[i].size(); j++) {
                    if (((Word) dict.wordList[i].get(j)).word != null) {

                        writer.write(dict.wordList[i].get(j).toString() + "\n");
                    }
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * This method is used to find a Word in Dictionary.
     * @param x: a String to storage the property 'word' and is used to find Word
     * @return the Word meaning
     */
    public String dictionaryLookup(String x) {
        String b = x.toLowerCase();
        int key = hash(b);
        String a = "";
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.equals(b)) {
                a = ((Word) dict.wordList[key].get(i)).meaning;
            }
        }
        return a;
    }

    /**
     * Create a suggestion Wordlist.
     * @param x: a String to storage the property 'word' and is used to find suggestionWord
     * @return a list of suggestionWord
     */
    ArrayList listView(String x) {
        ArrayList a = new ArrayList();
        int key = hash(x);
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.contains(x.toLowerCase())) {
                a.add(((Word) dict.wordList[key].get(i)).word);
            }
        }
        return a;
    }

    /**
     * This method is used to delete a Word from Dictionary
     * @param x: a String to storage the property 'word' and is used to find Word
     */
    void removeWord(String x) {
        int key = hash(x.toLowerCase());
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.equals(x.toLowerCase())) {
                dict.wordList[key].remove(i);
            }
        }
    }

    /**
     * This method is used to show the dictionary in commandLine.
     */
    void showAllWord() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < dict.wordList[i].size(); j++) {
                if (((Word) dict.wordList[i].get(j)).word != null) {
                    System.out.println(dict.wordList[i].get(j).toString() + " ");
                }
            }
        }
    }

    /**
     * This method is used to speech a Word
     * @param words
     */
    public void speech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(190);// Setting the rate of the voice
                voice.setPitch(150);// Setting the Pitch of the voice
                voice.setVolume(3);// Setting the volume of the voice
                SpeakText(words);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
    public void SpeakText(String words) {
        voice.speak(words);
    }

    /**
     * This method is used to compare almost right between two Word
     * @param s: a compare Word
     * @param s1: a compare Word
     * @return true if the tolerance of two Word is under 30% of Word 1
     */
    boolean SoSanh(String s, String s1) {
        int saiSo = (int) Math.round(s.length() * 0.3); // Setting the tolerance of two Word: 0,3 Word1's length
        if (s1.length() < (s.length() - saiSo) || s1.length() > (s.length() + saiSo)) {
            return false;
        }
        int i = 0;
        int j = 0;
        int loi = 0;
        //Compare each character of 2 strings, if they are not equal, and variable 'loi' increase one unit and compare the next neighboring words of both strings
        //In the error range if any, then adjust the position i, j to be the index of those two strings
        //When 1 in 2 the string is over, variable ' loi' will be add by the leftover String
        //And if variable ' loi' > variable 'saiSo',return true; else return false
        while (i < s.length() && j < s1.length()) {
            if (s.charAt(i) != s1.charAt(j)) {
                loi++;
                for (int k = 1; k <= saiSo; k++) {
                    if ((i + k < s.length()) && s.charAt(i + k) == s1.charAt(j)) {
                        i += k;
                        break;
                    } else if ((j + k < s1.length()) && s.charAt(j) == s1.charAt(j + k)) {
                        j += k;
                        break;
                    }
                }
            }
            i++;
            j++;
        }
        loi += s.length() - i + s1.length() - j;
        if (loi <= saiSo) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is search the nearby Word of an existing Word
     * @param x: a String to storage the property 'word' and is used to find NearbyWord
     * @return
     */
    ArrayList searchNearbyWord(String x) {
        ArrayList b = new ArrayList();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < dict.wordList[i].size(); j++) {
                if (SoSanh(((Word) dict.wordList[i].get(j)).word, x)) {
                    b.add(((Word) dict.wordList[i].get(j)).word);
                }
            }
        }
        return b;
    }
}


