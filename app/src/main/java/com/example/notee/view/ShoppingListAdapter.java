package com.example.notee.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.ShoppingListDatabase;
import com.example.notee.model.ShoppingList;

import java.util.List;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    private ShoppingListDatabase shoppingListDatabase;

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public ShoppingListAdapter(ShoppingListDatabase shoppingListDatabase) {
        this.shoppingListDatabase = shoppingListDatabase;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.textListName.setText(shoppingList.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView textListName;
        ImageButton deleteButton;
        ImageButton addItemButton;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            textListName = itemView.findViewById(R.id.text_list_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
            addItemButton = itemView.findViewById(R.id.add_item_button);
        }
    }
}
