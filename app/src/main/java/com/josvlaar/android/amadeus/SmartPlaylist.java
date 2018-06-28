package com.josvlaar.android.amadeus;

import java.io.Serializable;

public class SmartPlaylist implements Serializable {
    private String variable1;
    private String value1;
    private String variable2;
    private String value2;
    private String name;

    public SmartPlaylist(String name) {
        this.name = name;
    }

    public SmartPlaylist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariable1() {
        return variable1;
    }

    public void setVariable1(String variable1) {
        this.variable1 = variable1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}
