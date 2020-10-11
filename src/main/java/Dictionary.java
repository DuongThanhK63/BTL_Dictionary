/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Admin
 */
public class Dictionary {

    LinkedList[] wordList = new LinkedList[100];

    Dictionary() {
        for (int i = 0; i < 26; i++) {
            wordList[i] = new LinkedList<>();
        }
    }
}
