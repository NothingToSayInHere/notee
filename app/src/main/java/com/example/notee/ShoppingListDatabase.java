package com.example.notee;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListDao;

import android.content.Context;

import androidx.room.Room;

@Database(entities = {ShoppingList.class}, version = 1)
public abstract class ShoppingListDatabase extends RoomDatabase {
    private static ShoppingListDatabase instance;

    public abstract ShoppingListDao shoppingListDao();

    public static synchronized ShoppingListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ShoppingListDatabase.class, "shopping-list-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}