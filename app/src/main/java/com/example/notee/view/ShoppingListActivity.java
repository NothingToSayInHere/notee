package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.notee.R;
import com.example.notee.ShoppingListDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoppingListActivity extends AppCompatActivity {
    private ShoppingListAdapter adapter;
    private ShoppingListDatabase shoppingListDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingListDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ShoppingListDatabase.class,
                "shopping-list-db"
        ).build();

        RecyclerView recyclerView = findViewById(R.id.shopping_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingListAdapter(shoppingListDatabase);

        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingAddShoppingList = findViewById(R.id.floating_add_shopping_list);
        floatingAddShoppingList.setOnClickListener(v -> startActivity(new Intent(ShoppingListActivity.this, AddShoppingListActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_shopping_list);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_notes) {
                startActivity(new Intent(this, NotesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_shopping_list) {
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

}