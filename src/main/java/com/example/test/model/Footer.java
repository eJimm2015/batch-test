package com.example.test.model;

public class Footer extends Input {
    private String state;

    public Footer() {
    }

    public Footer(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Footer = {" + super.toString() +
                ", state=" + state + "}";
    }
}
