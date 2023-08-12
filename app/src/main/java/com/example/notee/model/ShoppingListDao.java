package com.example.notee.model;

import androidx.lifecycle.LiveData;
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

    @Delete
    void delete(ShoppingList shoppingList);

    @Query("SELECT * FROM shopping_list WHERE userUid = :userUid")
    LiveData<List<ShoppingList>> getFullShoppingList(String userUid);


    @Insert
    void insertItem(ShoppingListItem item);

    @Update
    void updateItem(ShoppingListItem item);

    @Delete
    void deleteItem(ShoppingListItem item);

    @Query("SELECT * FROM shopping_list_item WHERE listId = :listId")
    LiveData<List<ShoppingListItem>> getItemsForShoppingList(int listId);

}