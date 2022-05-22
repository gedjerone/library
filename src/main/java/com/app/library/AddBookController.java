package com.app.library;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class AddBookController {
    @FXML
    private Button BackButton;

    @FXML
    private Button AddButton;

    @FXML
    private TextField Title;

    @FXML
    private TextField Date;

    @FXML
    private TextField Author;

    @FXML
    private void Wrong() {
        Title.setStyle("-fx-background-color: #d20000");
        Date.setStyle("-fx-background-color: #d20000");
        Author.setStyle("-fx-background-color: #d20000");
        Title.setText("");
        Date.setText("");
        Author.setText("");
    }

    @FXML
    void initialize(){
        BackButton.setOnMouseEntered(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #505050");
        });

        BackButton.setOnMouseExited(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #2f2f2f");
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

        AddButton.setOnMouseEntered(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #677677");
        });

        AddButton.setOnMouseExited(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #373f3f");
        });

        Title.setOnKeyTyped(keyEvent -> {
            Title.setStyle("-fx-background-color: #ffffff");
            Date.setStyle("-fx-background-color: #ffffff");
            Author.setStyle("-fx-background-color: #ffffff");
        });

        Date.setOnKeyTyped(keyEvent -> {
            Title.setStyle("-fx-background-color: #ffffff");
            Date.setStyle("-fx-background-color: #ffffff");
            Author.setStyle("-fx-background-color: #ffffff");
        });

        Author.setOnKeyTyped(keyEvent -> {
            Title.setStyle("-fx-background-color: #ffffff");
            Date.setStyle("-fx-background-color: #ffffff");
            Author.setStyle("-fx-background-color: #ffffff");
        });

        BackButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|BACK_BUTTON PRESSED]");
            BackButton.getScene().getWindow().hide();
        });

        AddButton.setOnAction(event -> {
            Properties properties = new Properties();

            Path path = Paths.get("/Users/gedjer/IntelliJ Projects/library/src/main/resources/com/app/library/temp.properties");
            File file = new File(String.valueOf(path));

            try(InputStream in = Files.newInputStream(Paths.get(String.valueOf(path)))) {
                properties.load(in);
            } catch (IOException exception) {
                System.out.println("[EXEPTION][LOG|" + exception + "]");
            }

            System.out.println("[INFO][LOG|ADD_BUTTON PRESSED]");

            if(!Objects.equals(Title.getText(), "") && !Objects.equals(Date.getText(), "") && !Objects.equals(Author.getText(), "")){
                try {
                    int Value = Integer.parseInt(Date.getText());
                    dbHandler db = new dbHandler(properties.getProperty("USER"), properties.getProperty("PASS"));
                    db.getDbConnection();
                    db.InsertBook(Title.getText(), Integer.parseInt(Date.getText()), Author.getText());
                    AddButton.getScene().getWindow().hide();
                } catch (Exception exception) {
                    Wrong();
                }
            } else {
                Wrong();
            }
        });
    }
}
