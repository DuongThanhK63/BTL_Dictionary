/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class is used to create a 'Dictionary' data type consisting of multiple 'Word'.
 * @author Trung và Thành
 */


import java.util.*;

public class Dictionary {

    LinkedList[] wordList = new LinkedList[100];

    Dictionary() {
        for (int i = 0; i < 26; i++) {
            wordList[i] = new LinkedList<>();
        }
    }

}
