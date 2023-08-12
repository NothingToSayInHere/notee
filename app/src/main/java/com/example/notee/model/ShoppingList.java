package com.example.notee.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @ColumnInfo(name = "userUid")
    private final String userUid;

    public ShoppingList(String name, String userUid) {
        this.name = name;
        this.userUid = userUid;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getUserUid() {
        return userUid;
    }

}
