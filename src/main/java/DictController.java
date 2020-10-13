
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


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

    public void whenClickTextField(ActionEvent actionEvent) {
    }

    public void whenClickSearch(ActionEvent actionEvent) {
        String word1 = insertDict.getText();
        String a = dict.dictionaryLookup(word1);
        displayMeaning.setText(a);

    }

    public void whenClickAdd(ActionEvent actionEvent) {
        DIctFX ab = new DIctFX();
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        TextField textField = new TextField();
        TextArea textArea = new TextArea();
        textArea.setPromptText("nhap");
        textField.setPromptText("nhap");
        Button button = new Button();
        button.setText("add");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(textField, 0, 0);
        grid.add(button, 10 ,0);
        grid.add(textArea, 0, 1);
        stage.setScene(new Scene(grid, 400, 400));
        stage.show();

    }

    public void whenClickEdit(ActionEvent actionEvent) {
    }

    public void whenClickDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vp moi");
        alert.show();
        TextField textField = new TextField();

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
