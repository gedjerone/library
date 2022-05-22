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

public class AddAuthorController {
    @FXML
    private Button BackButton;

    @FXML
    private Button AddButton;

    @FXML
    private TextField FirstName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField ThridName;

    @FXML
    void initialize() {

        BackButton.setOnMouseEntered(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #505050");
        });

        BackButton.setOnMouseExited(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #2f2f2f");
        });

        AddButton.setOnMouseEntered(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #677677");
        });

        AddButton.setOnMouseExited(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #373f3f");
        });

        FirstName.setOnKeyTyped(keyEvent -> {
            FirstName.setStyle("-fx-background-color: #ffffff");
            LastName.setStyle("-fx-background-color: #ffffff");
            ThridName.setStyle("-fx-background-color: #ffffff");
        });

        LastName.setOnKeyTyped(keyEvent -> {
            FirstName.setStyle("-fx-background-color: #ffffff");
            LastName.setStyle("-fx-background-color: #ffffff");
            ThridName.setStyle("-fx-background-color: #ffffff");
        });

        ThridName.setOnKeyTyped(keyEvent -> {
            FirstName.setStyle("-fx-background-color: #ffffff");
            LastName.setStyle("-fx-background-color: #ffffff");
            ThridName.setStyle("-fx-background-color: #ffffff");
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

            if(!Objects.equals(FirstName.getText(), "") && !Objects.equals(LastName.getText(), "") && !Objects.equals(ThridName.getText(), "")){
                dbHandler db = new dbHandler(properties.getProperty("USER"), properties.getProperty("PASS"));
                db.getDbConnection();
                db.InsertAuthor(FirstName.getText(), LastName.getText(), ThridName.getText());
                AddButton.getScene().getWindow().hide();
            } else {
                FirstName.setStyle("-fx-background-color: #d20000");
                LastName.setStyle("-fx-background-color: #d20000");
                ThridName.setStyle("-fx-background-color: #d20000");
                FirstName.setText("");
                LastName.setText("");
                ThridName.setText("");
            }
        });
    }
}
