package com.tmind.ocean.model;

/**
 * Created by lijunying on 15/12/17.
 */
public class UserVistorMapModelTo {

    private String name;
    private Integer value;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UserVistorMapModelTo(String name, Integer value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
