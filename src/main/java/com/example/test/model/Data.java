package com.example.test.model;

public class Data implements Input {

    private String id;
    private String date;
    private String state;

    public Data() {}

    public Data(String id, String date, String state) {
        this.id = id;
        this.date = date;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Data = {" +
                "id=" + id + ", " +
                "date=" + date + ", " +
                "state=" + state + '}';
    }
}
