package com.app.library;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LibAppController {

    @FXML
    private Button AddButton;

    @FXML
    private Button EditButton;

    @FXML
    private Button RemoveButton;

    @FXML
    private Button RefreshButton;

    private static ObservableList<Book> dbBook = FXCollections.observableArrayList();
    @FXML
    private TableView<Book> Table;

    @FXML
    private TableColumn<Book, Integer> IDColumn;

    @FXML
    private TableColumn<Book, String> TitleColumn;

    @FXML
    private TableColumn<Book, Integer> DateColumn;

    @FXML
    private TableColumn<Book, String> FirstNameColumn;

    @FXML
    private TableColumn<Book, String> LastNameColumn;

    @FXML
    private TableColumn<Book, String> ThirdNameColumn;

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
        db.getBook(dbBook);

        AddButton.setOnMouseEntered(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #505050");
        });

        AddButton.setOnMouseExited(mouseDragEvent -> {
            AddButton.setStyle("-fx-background-color: #2f2f2f");
        });

        EditButton.setOnMouseEntered(mouseDragEvent -> {
            EditButton.setStyle("-fx-background-color: #505050");
        });

        EditButton.setOnMouseExited(mouseDragEvent -> {
            EditButton.setStyle("-fx-background-color: #2f2f2f");
        });

        RemoveButton.setOnMouseEntered(mouseDragEvent -> {
            RemoveButton.setStyle("-fx-background-color: #505050");
        });

        RemoveButton.setOnMouseExited(mouseDragEvent -> {
            RemoveButton.setStyle("-fx-background-color: #2f2f2f");
        });

        RefreshButton.setOnMouseEntered(mouseDragEvent -> {
            RefreshButton.setStyle("-fx-background-color: #505050");
        });

        RefreshButton.setOnMouseExited(mouseDragEvent -> {
            RefreshButton.setStyle("-fx-background-color: #2f2f2f");
        });

        RefreshButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|REFRESH_BUTTON PRESSED]");
            db.getBook(dbBook);
        });

        AddButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|ADD_BUTTON PRESSED]");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("add.fxml"));

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

        EditButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|EDIT_BUTTON PRESSED]");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("edit.fxml"));

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

        RemoveButton.setOnAction(event -> {
            System.out.println("[INFO][LOG|REMOVE_BUTTON PRESSED]");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("remove.fxml"));

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

        IDColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("ID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("Date"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("FirstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("LastName"));
        ThirdNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("ThirdName"));

        Table.setItems(dbBook);
    }
}
