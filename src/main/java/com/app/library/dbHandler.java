package com.app.library;

import javafx.collections.ObservableList;

import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
import java.util.Properties;

public class dbHandler {

    private String user;
    private String pass;

    private String connectionURL;

    dbHandler() {/**/}

    dbHandler(String $user, String $pass) {
        user = $user;
        pass = $pass;
    }

    private Connection dbConnection;

    private String getUser() {
        return this.user;
    }

    private String getPass() {
        return this.pass;
    }

    public Boolean checkConnection(){
        Properties properties = new Properties();

        try(InputStream in = Files.newInputStream(Paths.get("/Users/gedjer/IntelliJ Projects/library/src/main/java/com/app/library/sql.properties"))) {
            properties.load(in);
        } catch (IOException exception) {
            System.out.println("[EXEPTION][LOG|" + exception + "]");
        }

        String dbServer = properties.getProperty("SERVER");
        String dbHost = properties.getProperty("HOST");
        String dbName = properties.getProperty("NAME");
        String dbPort = properties.getProperty("PORT");

        this.connectionURL = "jdbc:" + dbServer + "://" + dbHost + ":" + dbPort + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception){
            System.out.println("[EXEPTION][LOG|" + exception + "]");
        }

        try {
            dbConnection = DriverManager.getConnection(connectionURL, getUser(), getPass());
            System.out.println("[INFO][LOG|SUCCESSFULLY CONNECTED TO DB]");
            return true;
        } catch (SQLException exception){
            System.out.println("[EXEPTION][LOG|" + exception + "]");
            return false;
        }
    }

    public Connection getDbConnection() {
        Properties properties = new Properties();

        try(InputStream in = Files.newInputStream(Paths.get("/Users/gedjer/IntelliJ Projects/library/src/main/java/com/app/library/sql.properties"))) {
            properties.load(in);
        } catch (IOException exception) {
            System.out.println("[EXEPTION][LOG|" + exception + "]");
        }

        String dbServer = properties.getProperty("SERVER");
        String dbHost = properties.getProperty("HOST");
        String dbName = properties.getProperty("NAME");
        String dbPort = properties.getProperty("PORT");

        this.connectionURL = "jdbc:" + dbServer + "://" + dbHost + ":" + dbPort + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception){
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }

        try {
            dbConnection = DriverManager.getConnection(connectionURL, getUser(), getPass());
            System.out.println("[INFO][LOG|SUCCESSFULLY CONNECTED TO DB]");
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
        return dbConnection;
    }

    public void InsertAuthor(String FirstName, String LastName, String ThirdName){
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate("INSERT author(first_name, last_name, third_name) VALUES ('" + FirstName + "','" + LastName + "','" + ThirdName + "')");
            System.out.println("[INFO][LOG|SUCCESSFULLY INSERTED TO DB]");
            System.out.println("[INFO][SQL|INSERT author(first_name, last_name, third_name) VALUES (" + FirstName + ", " + LastName + ", " + ThirdName + ")");
        } catch (SQLException exception) {
            System.out.println("[INFO][SQL|INSERT author(first_name, last_name, third_name) VALUES ('" + FirstName + "', '" + LastName + "', '" + ThirdName + "')");
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void InsertBook(String title, Integer date, String author_name){
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate("INSERT book(title, date, author_name) VALUES ('" + title + "','" + date + "','" + author_name + "')");
            System.out.println("[INFO][LOG|SUCCESSFULLY INSERTED TO DB]");
            System.out.println("[INFO][SQL|INSERT book(title, date, author_name) VALUES (" + title + ", " + date + ", " + author_name + ")");
        } catch (SQLException exception) {
            System.out.println("[INFO][SQL|INSERT book(title, date, author_name) VALUES ('" + title + "', " + date + ", '" + author_name + "')");
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void getBook(ObservableList<Book> dbBook) {
        try {
            Statement statement = dbConnection.createStatement();

            dbBook.clear();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM author, book WHERE last_name = author_name");
            System.out.println("[INFO][LOG|DATA SUCCESSFULLY SELECTED FROM DB]");
            while(resultSet.next()) {
                int ID = resultSet.getInt(5);
                String Title = resultSet.getString(6);
                int Date = resultSet.getInt(8);
                String FirstName = resultSet.getString(2);
                String LastName = resultSet.getString(3);
                String ThirdName = resultSet.getString(4);
                dbBook.add(new Book(ID, Title, Date, FirstName, LastName, ThirdName));
                System.out.println("[INFO][SQL|" + ID + "|" + Title + "|" + Date + "|" + FirstName + "|" + LastName + "|" + ThirdName + "]");
            }
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void getBookID(ObservableList<String> dbBookID) {
        try {
            Statement statement = dbConnection.createStatement();

            dbBookID.clear();

            ResultSet resultSet = statement.executeQuery("SELECT id FROM book");
            System.out.println("[INFO][LOG|DATA SUCCESSFULLY SELECTED FROM DB]");
            while(resultSet.next()) {
                String ID = String.valueOf(resultSet.getInt(1));
                dbBookID.add(ID);
                System.out.println("[INFO][SQL|" + ID + "]");
            }
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void getBookWhereID(Book dbBook, String id) {
        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM author, book WHERE last_name = author_name AND book.id = " + id);
            System.out.println("[INFO][LOG|DATA SUCCESSFULLY SELECTED FROM DB]");
            while(resultSet.next()) {
                int ID = resultSet.getInt(5);
                String Title = resultSet.getString(6);
                int Date = resultSet.getInt(8);
                String FirstName = resultSet.getString(2);
                String LastName = resultSet.getString(3);
                String ThirdName = resultSet.getString(4);
                dbBook.setID(ID);
                dbBook.setTitle(Title);
                dbBook.setDate(Date);
                dbBook.setFirstName(FirstName);
                dbBook.setLastName(LastName);
                dbBook.setThirdName(ThirdName);
                System.out.println("[INFO][SQL|" + ID + "|" + Title + "|" + Date + "|" + FirstName + "|" + LastName + "|" + ThirdName + "]");
            }
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void editDB(Book dbBook, Book dbBookNew) {
        try {
            Statement statement = dbConnection.createStatement();

            statement.executeUpdate("UPDATE author SET first_name = '" + dbBookNew.getFirstName() +
                    "', last_name = '" + dbBookNew.getLastName() + "', third_name = '" +
                    dbBookNew.getThirdName() + "' WHERE last_name = '" + dbBook.getLastName() + "'");

            System.out.println("[INFO][SQL|author UPDATED]");

            statement.executeUpdate("UPDATE book SET title = '" + dbBookNew.getTitle() +
                    "', author_name = '" + dbBookNew.getLastName() + "', date = " + dbBookNew.getDate() +
                    " WHERE id = " + dbBook.getID());

            System.out.println("[INFO][SQL|book UPDATED]");
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }

    public void removeBook(Book dbBook) {
        try {
            Statement statement = dbConnection.createStatement();

            statement.executeUpdate("DELETE FROM book WHERE id = " + dbBook.getID());

            System.out.println("[INFO][SQL|book DELETED]");
        } catch (SQLException exception) {
            System.out.println("[EXEPTION][SQL|" + exception + "]");
        }
    }
}