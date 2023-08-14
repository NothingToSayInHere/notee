package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.notee.R;
import com.example.notee.model.ShoppingList;
import com.example.notee.viewmodel.ShoppingListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    FloatingActionButton floatingAddShoppingList;
    private Animation fabDisappearAnimation;
    private Animation fabReappearAnimation;
    List<ShoppingList> shoppingListItems;
    private ShoppingListViewModel viewModel;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        floatingAddShoppingList = findViewById(R.id.floating_add_shopping_list);
        fabDisappearAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_disappear);
        fabReappearAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_reappear);
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        RecyclerView rv = findViewById(R.id.shopping_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(ShoppingListActivity.this));
        rv.hasFixedSize();

        shoppingListItems = new ArrayList<>();

        shoppingListAdapter = new ShoppingListAdapter(new ArrayList<>(), viewModel);

        rv.setAdapter(shoppingListAdapter);

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

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userUid = user.getUid();
            viewModel.getFullShoppingList(userUid).observe(this, shoppingList -> {
                shoppingListAdapter.setShoppingList(shoppingList);

                if (shoppingList.isEmpty()) {
                    floatingAddShoppingList.show();
                    floatingAddShoppingList.startAnimation(fabReappearAnimation);
                } else {
                    floatingAddShoppingList.startAnimation(fabDisappearAnimation);
                    floatingAddShoppingList.hide();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NotesActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

}