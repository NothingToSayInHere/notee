package com.example.notee.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.notee.R;
import com.example.notee.ShoppingListDatabase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddShoppingListActivity extends AppCompatActivity {
    private TextInputEditText addShoppingListName;
    private MaterialButton addShoppingListButton;
    private ShoppingListDatabase shoppingListDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        addShoppingListName = findViewById(R.id.add_shopping_list_name);
        addShoppingListButton = findViewById(R.id.add_shopping_list_button);

        shoppingListDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ShoppingListDatabase.class,
                "shopping-list-db"
        ).build();

    }

}