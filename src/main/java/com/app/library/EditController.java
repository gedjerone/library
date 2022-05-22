package com.app.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class EditController {
    @FXML
    private Button BackButton;

    @FXML
    private Button EditButton;

    private static ObservableList<String> dbBookID = FXCollections.observableArrayList();

    private static Book dbBook = new Book();

    @FXML
    private ComboBox<String> IDComboBox;

    @FXML
    private TextField TitleField;

    @FXML
    private TextField DateField;

    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField ThirdNameFiled;

    @FXML
    private void Wrong(TextField field) {
        field.setStyle("-fx-background-color: #d20000");
    }

    private void EditFunc(Book dbBook,dbHandler db){
        try {
            if(!IDComboBox.getValue().matches("") && !TitleField.getText().matches("") && !DateField.getText().matches("") &&
                    !FirstNameField.getText().matches("") && !LastNameField.getText().matches("") && !ThirdNameFiled.getText().matches("")){
                System.out.println("[INFO][LOG|EDITING STARTED]");
                Book dbBookNew = new Book(Integer.parseInt(IDComboBox.getValue()), TitleField.getText(), Integer.parseInt(DateField.getText()),
                        FirstNameField.getText(), LastNameField.getText(), ThirdNameFiled.getText());
                db.editDB(dbBook, dbBookNew);
                EditButton.getScene().getWindow().hide();
            } else {
                System.out.println("[INFO][LOG|SOME FIELDS ARE EMPTY|TRY AGAIN]");
                if (IDComboBox.getValue().matches("")) {IDComboBox.setStyle("-fx-background-color: #d20000");}
                if (TitleField.getText().matches("")) {Wrong(TitleField);}
                if (DateField.getText().matches("")) {Wrong(DateField);}
                if (FirstNameField.getText().matches("")) {Wrong(FirstNameField);}
                if (LastNameField.getText().matches("")) {Wrong(LastNameField);}
                if (ThirdNameFiled.getText().matches("")) {Wrong(ThirdNameFiled);}
            }
        } catch (Exception exception) {
            IDComboBox.setStyle("-fx-background-color: #d20000");
            System.out.println("[INFO][LOG|EMPTY FIELDS|" + exception + "]");
        }
    }

    @FXML
    void initialize() {
        Properties properties = new Properties();

        Path path = Paths.get("/Users/gedjer/IntelliJ Projects/library/src/main/resources/com/app/library/temp.properties");
        File file = new File(String.valueOf(path));

        try(InputStream in = Files.newInputStream(Paths.get(String.valueOf(path)))) {
            properties.load(in);
        } catch (IOException exception) {
            System.out.println("[EXEPTION][LOG|" + exception + "]");
        }

        dbHandler db = new dbHandler(properties.getProperty("USER"), properties.getProperty("PASS"));
        db.getDbConnection();
        db.getBookID(dbBookID);

        IDComboBox.setItems(dbBookID);

        BackButton.setOnMouseEntered(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #505050");
        });

        BackButton.setOnMouseExited(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #2f2f2f");
        });

        EditButton.setOnMouseEntered(mouseDragEvent -> {
                EditButton.setStyle("-fx-background-color: #677677");
        });

        EditButton.setOnMouseExited(mouseDragEvent -> {
            EditButton.setStyle("-fx-background-color: #373f3f");
        });

        TitleField.setOnKeyTyped(keyEvent -> {
            TitleField.setStyle("-fx-background-color: #ffffff");
        });

        DateField.setOnKeyTyped(keyEvent -> {
            DateField.setStyle("-fx-background-color: #ffffff");
        });

        FirstNameField.setOnKeyTyped(keyEvent -> {
            FirstNameField.setStyle("-fx-background-color: #ffffff");
        });

        LastNameField.setOnKeyTyped(keyEvent -> {
            LastNameField.setStyle("-fx-background-color: #ffffff");
        });

        ThirdNameFiled.setOnKeyTyped(keyEvent -> {
            ThirdNameFiled.setStyle("-fx-background-color: #ffffff");
        });

        BackButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|BACK_BUTTON PRESSED]");
            BackButton.getScene().getWindow().hide();
        });

        BackButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ESCAPE)) {
                    System.out.println("[INFO][LOG|ESCAPE PRESSED]");
                    BackButton.getScene().getWindow().hide();
                }
            }
        });

        IDComboBox.setOnAction(event -> {
            IDComboBox.setStyle("-fx-background-color: #ffffff");
            System.out.println("[INFO][LOG|COMBOBOX_EVENT SET|ID = " + IDComboBox.getValue() + "]");
            db.getBookWhereID(dbBook, IDComboBox.getValue());
            TitleField.setText(dbBook.getTitle());
            DateField.setText(String.valueOf(dbBook.getDate()));
            FirstNameField.setText(dbBook.getFirstName());
            LastNameField.setText(dbBook.getLastName());
            ThirdNameFiled.setText(dbBook.getThirdName());
        });

        EditButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|EDIT_BUTTON PRESSED]");
            EditFunc(dbBook, db);
        });

        EditButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("[INFO][LOG|ENTER PRESSED]");
                    EditFunc(dbBook, db);
                }
            }
        });
    }
}
