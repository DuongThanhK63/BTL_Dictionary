/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is used to create a Word object and two properties of Word are word and meaning.
 * @author Trung và Thành
 */
import java.io.*;
import java.util.*;


public class DictionaryCommandLine {

    public static void main(String[] args) throws IOException {
        DictionaryManagement dict = new DictionaryManagement();
        Scanner sc = new Scanner(System.in);

        System.out.println("Type:" + "\n" +
                "1, import -- load data from file." + "\n" +
                "2, lookup -- search Word in the Dictionary" + "\n" +
                "3, add -- add Words to the Dictionary" + "\n" +
                "4, edit -- edit Word" + "\n" +
                "5, save -- save Dictionary to file" + "\n" +
                "6, quit" + "\n" +
                "7, speech -- speech the word" + "\n" +
                "8, delete -- delete a Word" + "\n" +
                "9, type any to show the Dictionary data.!");
        Boolean check = true;
        String x;
        while(check == true){
            x = sc.nextLine();
            x = x.toLowerCase();
            switch(x){
                case "import": dict.insertFromFile();
                    break;
                case "lookup": System.out.println("Nhap tu can tim: ");
                    String word = sc.nextLine();
                    String meaning = dict.dictionaryLookup(word);
                    if(meaning != "") {
                        System.out.println(word + ": " + meaning);
                    } else {
                        System.out.println("Not Found!");
                    }
                    break;
                    
                case "add": int num;
                    System.out.println("Nhap so luong tu can them: ");
                    num = Integer.parseInt(sc.nextLine());
                    for(int i = 0; i < num; i++){
                        System.out.println("Word: ");
                        String a = sc.nextLine();
                        System.out.println("Meaning: ");
                        String b = sc.nextLine();
                        dict.addDict(a, b);
                    }
                    break;

                case "quit": check = false;
                    break;

                case "edit": System.out.println("Tu can sua: ");
                    String word1 = sc.nextLine();
                    String meaning1 = sc.nextLine();
                    dict.editDict(word1, meaning1);
                    break;

                case "save": dict.saveFile();
                    System.out.println("Done!");
                    break;

                case "speech": System.out.println("Tu can phat am: ");
                    String a = sc.nextLine();
                    dict.speech(a);
                    break;

                case "delete": System.out.println("Tu can xoa: ");
                    String b = sc.nextLine();
                    dict.removeWord(b);
                    break;

                default: dict.showAllWord();
                    break;
            }
        }
    }

}