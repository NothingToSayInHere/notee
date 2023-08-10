package com.example.notee.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingList shoppingList);

    @Query("SELECT * FROM shopping_lists")
    List<ShoppingList> getAllShoppingLists();

    @Delete
    void delete(ShoppingList shoppingList);

    @Update
    void update(ShoppingList shoppingList);

}