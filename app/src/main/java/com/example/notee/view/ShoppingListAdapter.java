package com.example.notee.view;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListItem;
import com.example.notee.viewmodel.ShoppingListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
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

        holder.deleteShoppingListButton.setOnClickListener(v -> {
            int deleteListId = shoppingList.getId();
            viewModel.deleteShoppingList(deleteListId);
            Snackbar.make(v, "Shopping list deleted.", Snackbar.LENGTH_SHORT).show();

        });

        holder.addShoppingListItemButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Add Item");

            EditText itemNameEditText = new EditText(v.getContext());
            builder.setView(itemNameEditText);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String itemName = itemNameEditText.getText().toString();
                if (!itemName.isEmpty()) {
                    viewModel.addItemToShoppingList(shoppingList.getId(), itemName);
                    Snackbar.make(v, "Item added.", Snackbar.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());

        LiveData<List<ShoppingListItem>> itemsLiveData = viewModel.getItemsForShoppingList(shoppingList.getId());
        itemsLiveData.observe((LifecycleOwner) holder.itemView.getContext(), items -> {
            ItemAdapter itemAdapter = new ItemAdapter(items, viewModel);
            holder.itemsRv.setLayoutManager(layoutManager);
            holder.itemsRv.setAdapter(itemAdapter);
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView shoppingListName;
        RecyclerView itemsRv;
        MaterialButton deleteShoppingListButton;
        MaterialButton addShoppingListItemButton;

        ViewHolder(View itemView) {
            super(itemView);

            shoppingListName = itemView.findViewById(R.id.shopping_list_name);
            itemsRv = itemView.findViewById(R.id.items_rv);
            deleteShoppingListButton = itemView.findViewById(R.id.delete_shopping_list_button);
            addShoppingListItemButton = itemView.findViewById(R.id.add_shopping_list_item_button);
        }

    }

    public void setShoppingList(List<ShoppingList> shoppingList) {
        shoppingListList = shoppingList;
        notifyDataSetChanged();
    }
}