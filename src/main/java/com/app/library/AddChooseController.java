package com.app.library;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddChooseController {
    @FXML
    private Button BackButton;

    @FXML
    private Button AuthorButton;

    @FXML
    private Button BookButton;

    @FXML
    void initialize() {
        BackButton.setOnMouseEntered(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #505050");
        });

        BackButton.setOnMouseExited(mouseDragEvent -> {
            BackButton.setStyle("-fx-background-color: #2f2f2f");
        });

        AuthorButton.setOnMouseEntered(mouseDragEvent -> {
            AuthorButton.setStyle("-fx-background-color: #677677");
        });

        AuthorButton.setOnMouseExited(mouseDragEvent -> {
            AuthorButton.setStyle("-fx-background-color: #373f3f");
        });

        BookButton.setOnMouseEntered(mouseDragEvent -> {
            BookButton.setStyle("-fx-background-color: #677677");
        });

        BookButton.setOnMouseExited(mouseDragEvent -> {
            BookButton.setStyle("-fx-background-color: #373f3f");
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

        AuthorButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|AUTHOR_BUTTON PRESSED]");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addauthor.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                System.out.println("[EXEPTION][LOG|" + exception + "]");
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        BookButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|AUTHOR_BUTTON PRESSED]");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addbook.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                System.out.println("[EXEPTION][LOG|" + exception + "]");
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
