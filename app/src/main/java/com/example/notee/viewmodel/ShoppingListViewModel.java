package com.example.notee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notee.ShoppingListRepository;
import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userUid = user.getUid();
            repository.addShoppingList(shoppingList);
            repository.getFullShoppingList(userUid);
            isShoppingListAdded.setValue(true);
        }
    }

    public void deleteShoppingList(int id) {
        repository.deleteShoppingList(id);
    }

    public LiveData<List<ShoppingList>> getFullShoppingList(String userUid) {
        return repository.getFullShoppingList(userUid);
    }

    public void setIsShoppingListAdded(boolean value) {
        isShoppingListAdded.setValue(value);
    }

    public void addItemToShoppingList(int listId, String itemName) {
        ShoppingListItem item = new ShoppingListItem();
        item.setListId(listId);
        item.setItemName(itemName);
        repository.addShoppingListItem(item);
    }

    public LiveData<List<ShoppingListItem>> getItemsForShoppingList(int listId) {
        return repository.getItemsForShoppingList(listId);
    }

}