package com.example.notee.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.ShoppingList;
import com.example.notee.model.ShoppingListItem;
import com.example.notee.viewmodel.ShoppingListViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
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
            Toast.makeText(v.getContext(), "Shopping list deleted.", Toast.LENGTH_SHORT).show();
        });

        holder.addShoppingListItemButton.setOnClickListener(v -> showBottomSheet(v, shoppingList.getId()));

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

    private void showBottomSheet(View anchorView, int shoppingListId) {
        View bottomSheetView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.add_item, (ViewGroup) anchorView.getParent(), false);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(anchorView.getContext());
        bottomSheetDialog.setContentView(bottomSheetView);

        TextInputEditText itemNameField = bottomSheetView.findViewById(R.id.item_name_field);
        MaterialButton addToShoppingListButton = bottomSheetView.findViewById(R.id.add_to_shopping_list_button);
        MaterialButton cancelButton = bottomSheetView.findViewById(R.id.cancel_button);

        addToShoppingListButton.setOnClickListener(v -> {
            String itemName = itemNameField.getText() != null ? itemNameField.getText().toString() : "";
            if (!itemName.isEmpty()) {
                viewModel.addItemToShoppingList(shoppingListId, itemName);
                Toast.makeText(anchorView.getContext(), "Item added.", Toast.LENGTH_SHORT).show();
            }
            bottomSheetDialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
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