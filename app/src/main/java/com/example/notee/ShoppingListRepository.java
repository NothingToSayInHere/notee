package com.example.notee;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingListRepository {
    private final ShoppingListDao shoppingListDao;

    private final ExecutorService executor;

    public ShoppingListRepository(Context context) {
        ShoppingListDatabase database = ShoppingListDatabase.getInstance(context);

        shoppingListDao = database.shoppingListDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void addShoppingList(ShoppingList shoppingList) {
        executor.execute(() -> {
            ShoppingList shoppingListEntity = new ShoppingList(shoppingList.getName());
            shoppingListDao.insert(shoppingListEntity);
        });
    }

    public LiveData<List<ShoppingList>> getFullShoppingList() {
        return shoppingListDao.getFullShoppingList();
    }

    public void deleteShoppingList(int id) {
        executor.execute(() -> {
            ShoppingList shoppingListEntity = new ShoppingList("");
            shoppingListEntity.setId(id);
            shoppingListDao.delete(shoppingListEntity);
        });
    }

}