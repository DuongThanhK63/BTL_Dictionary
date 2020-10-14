/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.*;
import java.util.*;

public class DictionaryManagement {

    Dictionary dict = new Dictionary();
    Voice voice;

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

    void editDict(String wordbf, String meaning) {
        meaning = meaning.toLowerCase();
        int key = hash(wordbf);
        for (int j = 0; j < dict.wordList[key].size(); j++) {
            if (((Word) dict.wordList[key].get(j)).word.equals(wordbf)) {
                ((Word) dict.wordList[key].get(j)).meaning = meaning;
            }
        }
    }


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
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }

    public void saveFile() throws IOException {
        String url = "C:\\Users\\Admin\\Desktop\\DictionaryApp\\DictionaryApp\\src\\main\\resources\\a.txt";
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

    public String dictionaryLookup(String x) {
        String b = x.toLowerCase();
        int key = hash(b);
        String a = "";
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.equals(b)) {
//                System.out.println(dict.wordList[key].get(i).toString());
//                speech(x);
                a = ((Word) dict.wordList[key].get(i)).meaning;
            }
        }
        return a;
    }

    void dictionarySearch(String x) {
        int key = hash(x.toLowerCase());
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.contains(x.toLowerCase())) {
                System.out.println(dict.wordList[key].get(i).toString() + " ");
            }
        }
    }

    void removeWord(String x) {
        int key = hash(x.toLowerCase());
        for (int i = 0; i < dict.wordList[key].size(); i++) {
            if (((Word) dict.wordList[key].get(i)).word.equals(x.toLowerCase())) {
                dict.wordList[key].remove(i);
            }
        }
    }

    void showAllWord() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < dict.wordList[i].size(); j++) {
                if (((Word) dict.wordList[i].get(j)).word != null) {
                    System.out.println(dict.wordList[i].get(j).toString() + " ");
                }
            }
        }
    }

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


    boolean SoSanh(String s, String s1) {
        int saiSo = (int) Math.round(s.length() * 0.3);
        if (s1.length() < (s.length() - saiSo) || s1.length() > (s.length() + saiSo)) {
            return false;
        }
        int i = 0;
        int j = 0;
        int loi = 0;
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

    void searchNearbyWord(String x) {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < dict.wordList[i].size(); j++) {
                if (SoSanh(((Word) dict.wordList[i].get(j)).word, x)) {
                    System.out.println(dict.wordList[i].get(j).toString() + " ");
                }
            }
        }
    }
}


