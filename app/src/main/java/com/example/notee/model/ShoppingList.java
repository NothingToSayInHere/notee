package com.example.notee.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_lists")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public ShoppingList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

}