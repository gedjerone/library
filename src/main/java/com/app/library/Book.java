package com.app.library;

public class Book {
    private int ID;
    private String Title;
    private int Date;
    private String FirstName;
    private String LastName;
    private String ThirdName;

    public Book(int ID, String Title, int Date, String FirstName, String LastName, String ThirdName) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ThirdName = ThirdName;
        this.Title = Title;
        this.Date = Date;
    }

    public Book() { /**/ }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getThirdName() {
        return ThirdName;
    }

    public void setThirdName(String thirdName) {
        this.ThirdName = thirdName;
    }

    public String getTitle() {
        return  Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }

    public Integer getDate() {
        return Date;
    }
    public void setDate(Integer date) {
        this.Date = date;
    }
}
