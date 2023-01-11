package com.example.test.model;

public class Input {
    private int lineNumber;

    public Input() {
    }

    public Input(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "lineNumber=" + lineNumber;
    }
}
