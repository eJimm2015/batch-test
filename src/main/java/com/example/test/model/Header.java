package com.example.test.model;

import java.util.Objects;

public class Header implements Input {
    private String label;
    private String date;

    public Header() {
    }

    public Header(String label, String date) {
        this.label = label;
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Header = {" +
                "label=" + label + ", " +
                "date=" + date + '}';
    }

    public String format() {
        return label + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header = (Header) o;
        return label.equals(header.label) && date.equals(header.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, date);
    }
}
