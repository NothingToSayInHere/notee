package com.example.notee.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingList shoppingList);

    @Delete
    void delete(ShoppingList shoppingList);

    @Query("SELECT * FROM shopping_list")
    LiveData<List<ShoppingList>> getFullShoppingList();
}