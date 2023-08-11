package com.example.notee.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notee.R;
import com.example.notee.model.ShoppingList;
import com.example.notee.viewmodel.ShoppingListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddShoppingListActivity extends AppCompatActivity {
    private TextInputEditText addShoppingListName;
    private MaterialButton addShoppingListButton;
    private ShoppingListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        addShoppingListName = findViewById(R.id.add_shopping_list_name);
        addShoppingListButton = findViewById(R.id.add_shopping_list_button);

        addShoppingListButton.setOnClickListener(v -> {
            String nameString = addShoppingListName.getText().toString().trim();

            if (!nameString.isEmpty()) {
                ShoppingList shoppingList = new ShoppingList(nameString);
                viewModel.addShoppingList(shoppingList);
                addShoppingListName.setText("");
            } else {
                Toast.makeText(AddShoppingListActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();
            }

        });

        viewModel.getIsShoppingListAdded().observe(this, isShoppingListAdded -> {
            if (isShoppingListAdded) {
                Toast.makeText(AddShoppingListActivity.this, "Shopping list added.", Toast.LENGTH_LONG).show();
                viewModel.setIsShoppingListAdded(false);
                finish();
            }
        });

    }
}