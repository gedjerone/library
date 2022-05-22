package com.app.library;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LoginController {

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button LoginButton;

    @FXML
    private CheckBox Remember;

    @FXML
    private void NoAuth() {
        LoginField.setStyle("-fx-background-color: #d20000");
        PasswordField.setStyle("-fx-background-color: #d20000");
        PasswordField.setText("");
    }

    private void Login(File file, Path path) {
        dbHandler db = new dbHandler(LoginField.getText(), PasswordField.getText());
        if(db.checkConnection()) {
            if(!file.exists() && Remember.isSelected()) {
                try {
                    file.createNewFile();
                    System.out.println("[INFO][LOG|TEMP_FILE CREATED]");
                    String temp = "USER = " + LoginField.getText() + "\nPASS = " + PasswordField.getText();
                    try {
                        Files.write(path, temp.getBytes());
                        System.out.println("[INFO][LOG|DATA_WRITTEN CREATED]");
                    } catch (IOException exception) {
                        System.out.println("[EXEPTION][LOG|" + exception + "]");
                    }
                } catch (IOException exception) {
                    System.out.println("[EXEPTION][LOG|" + exception + "]");
                }
            }

            LoginButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("libapp.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                System.out.println("[EXEPTION][LOG|" + exception + "]");
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
        else {
            NoAuth();
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

        if(file.exists()) {
            String dbUser = properties.getProperty("USER");
            String dbPass = properties.getProperty("PASS");
            System.out.println(dbUser + dbPass);
            LoginField.setText(dbUser);
            PasswordField.setText(dbPass);
        }

        LoginButton.setOnMouseEntered(mouseDragEvent -> {
            LoginButton.setStyle("-fx-background-color: #505050");
        });

        LoginButton.setOnMouseExited(mouseDragEvent -> {
            LoginButton.setStyle("-fx-background-color: #2f2f2f");
        });

        LoginField.setOnKeyTyped(keyEvent -> {
            LoginField.setStyle("-fx-background-color: #ffffff");
            PasswordField.setStyle("-fx-background-color: #ffffff");
        });

        PasswordField.setOnKeyTyped(keyEvent -> {
            LoginField.setStyle("-fx-background-color: #ffffff");
            PasswordField.setStyle("-fx-background-color: #ffffff");
        });

        LoginButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("[INFO][LOG|ENTER PRESSED]");
                    Login(file, path);
                }
            }
        });

        LoginButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|LOGIN_BUTTON PRESSED]");
            Login(file, path);
        });
    }
}