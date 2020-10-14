
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class DictController implements Initializable{
    @FXML
    public TextArea displayMeaning;
    DictionaryManagement dict = new DictionaryManagement();
    Scanner sc = new Scanner(System.in);
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

    public void whenClickSearch(ActionEvent actionEvent) {
        String word1 = insertDict.getText();
        String a = dict.dictionaryLookup(word1);
        displayMeaning.setText(a);

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
        TextField meaning = new TextField();
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
        if(dict.dictionaryLookup(wordbf) == null) {
            displayMeaning.setText("Not found");
        }
        else{
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("EDIT");
                ButtonType addButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

                GridPane grip = new GridPane();
                grip.setHgap(10);
                grip.setVgap(10);
                grip.setPadding(new Insets(20, 200, 10, 10));

                TextField meaning = new TextField();
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

    public void whenClickDelete(ActionEvent actionEvent) {
        String word1 = insertDict.getText();
        dict.removeWord(word1);
        displayMeaning.setText("DONE");

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
}
