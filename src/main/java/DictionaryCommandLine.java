/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BTD
 */
import java.io.*;
import java.util.*;
import com.sun.speech.freetts.VoiceManager; // cái này phải tải thư viện freetts về và import vào //project.

public class DictionaryCommandLine {

    public static void main(String[] args) throws IOException {
        DictionaryManagement dict = new DictionaryManagement();
        Scanner sc = new Scanner(System.in);
        dict.insertFromFile();
//      dict.insertFromCommandLine(sc.nextInt());
//        dict.dictionaryLookup(sc.nextLine());
        dict.dictionarySearch(sc.nextLine());
//        dict.removeWord(sc.nextLine());
 //       dict.showAllWord();
//       dict.saveFile();
 //       dict.searchNearbyWord(sc.nextLine());
    }
}
