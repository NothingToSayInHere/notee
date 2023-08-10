package com.example.notee;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListDao;

@Database(entities = {ShoppingList.class}, version = 1)
public abstract class ShoppingListDatabase extends RoomDatabase {
    public abstract ShoppingListDao shoppingListDao();
}