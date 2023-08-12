package com.example.notee.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.ShoppingList;
import com.example.notee.viewmodel.ShoppingListViewModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingListList;
    private final ShoppingListViewModel viewModel;

    public ShoppingListAdapter(List<ShoppingList> shoppingListList, ShoppingListViewModel viewModel) {
        this.shoppingListList = shoppingListList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingListList.get(position);
        holder.shoppingListName.setText(shoppingListList.get(holder.getAdapterPosition()).getName());

        holder.deleteButton.setOnClickListener(v -> {
            int deleteListId = shoppingList.getId();
            viewModel.deleteShoppingList(deleteListId);
            Toast.makeText(v.getContext(), "Shopping list deleted", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView shoppingListName;
        ImageButton deleteButton;
        ImageButton addItemButton;

        ViewHolder(View itemView) {
            super(itemView);

            shoppingListName = itemView.findViewById(R.id.shopping_list_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
            addItemButton = itemView.findViewById(R.id.add_item_button);
        }

    }

    public void setShoppingList(List<ShoppingList> shoppingList) {
        shoppingListList = shoppingList;
        notifyDataSetChanged();
    }
}