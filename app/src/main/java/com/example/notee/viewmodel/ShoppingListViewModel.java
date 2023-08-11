package com.example.notee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notee.ShoppingListRepository;
import com.example.notee.model.ShoppingList;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private final ShoppingListRepository repository;
    private final MutableLiveData<Boolean> isShoppingListAdded = new MutableLiveData<>(false);

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingListRepository(application.getApplicationContext());
    }

    public LiveData<Boolean> getIsShoppingListAdded() {
        return isShoppingListAdded;
    }

    public void addShoppingList(ShoppingList shoppingList) {
        if (shoppingList == null) {
            throw new IllegalArgumentException("Shopping list cannot be null");
        }
        if (repository != null) {
            repository.addShoppingList(shoppingList);
            repository.getFullShoppingList();
            isShoppingListAdded.setValue(true);
        }
    }

    public void deleteShoppingList(int id) {
        repository.deleteShoppingList(id);
    }

    public LiveData<List<ShoppingList>> getFullShoppingList() {
        return repository.getFullShoppingList();
    }

    public void setIsShoppingListAdded(boolean value) {
        isShoppingListAdded.setValue(value);
    }
}