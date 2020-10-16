/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is used to create a Word object and two properties of Word are word and meaning.
 * @author Trung và Thành
 */

import com.sun.javafx.charts.Legend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class DictController implements Initializable{

    DictionaryManagement dict = new DictionaryManagement();
    Scanner sc = new Scanner(System.in);
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    public TextArea displayMeaning;

    @FXML
    public Button clearButton;

    @FXML
    public ListView<String> listView;

    @FXML
    private TextField insertDict;

    @FXML
    private TextArea showWord;

    @FXML
    private Button searchDict;

    @FXML
    private Button addDict;

    @FXML
    private Button editDict;

    @FXML
    private Button deleteDict;

    @FXML
    private Button speechDict;

    @FXML
    private Button exit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void whenClickAdd(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("ADD");
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grip = new GridPane();
        grip.setHgap(10);
        grip.setVgap(10);
        grip.setPadding(new Insets(20, 200, 10, 10));
        TextField word1 = new TextField();
        word1.setPromptText("Enter word");
        TextArea meaning = new TextArea();
        meaning.setPromptText("Enter meaning");

        grip.add(new Label("Word:"), 0, 0);
        grip.add(word1, 1, 0);

        grip.add(new Label("Meaning"), 0, 1);
        grip.add(meaning, 1, 1);

        dialog.getDialogPane().setContent(grip);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == addButton){
                return  new Pair<>(word1.getText(), meaning.getText());
            }
            return null;
        } );

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(x -> {
            dict.addDict(x.getKey(), x.getValue());

        });

    }

    public void whenClickEdit(ActionEvent actionEvent) {
        String wordbf = insertDict.getText();
        if (wordbf == null || wordbf.isEmpty() == true){
        }
        else {
            if (dict.dictionaryLookup(wordbf) == null) {
                displayMeaning.setText("Not found");
            } else {
                String meaningbf = dict.dictionaryLookup(wordbf);
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("EDIT");
                ButtonType addButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

                GridPane grip = new GridPane();
                grip.setHgap(10);
                grip.setVgap(10);
                grip.setPadding(new Insets(20, 200, 10, 10));
                TextArea meaning = new TextArea(meaningbf);
                meaning.setPromptText("Enter new meaning");

                grip.add(new Label("Meaning:"), 0, 1);
                grip.add(meaning, 1, 1);

                dialog.getDialogPane().setContent(grip);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == addButton) {
                        return meaning.getText();
                    }
                    return null;
                });

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(x -> {
                    dict.editDict(wordbf, meaning.getText());

                });
            }
        }
    }

    public void whenClickDelete(ActionEvent actionEvent) {
        String word1 = insertDict.getText();
        if (word1 == null || word1.isEmpty() == true){
        }
        else{
            dict.removeWord(word1);
            displayMeaning.setText("DONE");
        }
    }

    public void whenClickSpeech(ActionEvent actionEvent) {
        String word1 = insertDict.getText();
        dict.speech(word1);
    }

    public void whenClickingImport(ActionEvent actionEvent) throws FileNotFoundException {
        dict.insertFromFile();
        System.out.println("oke");
    }

    public void whenClickingSave(ActionEvent actionEvent) throws IOException {
        dict.saveFile();
    }

//    public void whenClickSearch(ActionEvent actionEvent) {
//
//        String word1 = insertDict.getText();
//        if (word1 != null & word1.isEmpty() == false) {
//            String a = dict.dictionaryLookup(word1);
//            displayMeaning.setText(a);
//        }
//    }

    public  void suggestionWord() throws IOException{
        String word1 = insertDict.getText();
        listView.getItems().clear();
        list.removeAll(list);

        if(insertDict.getText().trim().isEmpty()){
        }
        else{
            ArrayList arrayList = dict.listView(insertDict.getText().trim());


            if(arrayList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confimation");
                alert.setHeaderText("YOU ENTERED INCORRECTLY");
                alert.setContentText("Would you like to see some approximate words?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    ArrayList list1 = dict.searchNearbyWord(word1.trim());
                    list.addAll(list1);
                    listView.getItems().addAll(list.sorted());
                }

            }
            else {
                list.addAll(arrayList);
                listView.getItems().addAll(list.sorted());
            }
        }
    }

    public void whenClickingItem(MouseEvent mouseEvent) {
        String item = listView.getSelectionModel().getSelectedItem();
        if(item != null && item.isEmpty() == false){
            insertDict.setText(item);
            String a = dict.dictionaryLookup(item);
            displayMeaning.setText(a);
        }

    }

    public void whenClickingClear(ActionEvent actionEvent) {
        insertDict.clear();
        displayMeaning.clear();
        list.removeAll(list);
        listView.getItems().clear();
    }

    public void whenClickingHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HELP");
        alert.setHeaderText("User manual");
        String a = "1. Add: Add word to the database "
                + "\n" + "2. Delete: Delete word from the database"
                + "\n" + "3. Edit: Change the meaning of word in the database"
                + "\n" + "4. Speech: Pronounce the word in the search box"
                + "\n" + "5: Clear: Clear working window";
        TextArea textTur = new TextArea(a);
        alert.getDialogPane().setContent(textTur);
        Optional<ButtonType> result = alert.showAndWait();

    }
}
