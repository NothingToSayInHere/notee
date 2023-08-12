package com.example.notee.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int listId;
    private String itemName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}