package com.app.collectandrecycle.presentation.requesets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.collectandrecycle.data.Item;
import com.app.collectandrecycle.databinding.ItemRequestLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestItemsAdapter extends RecyclerView.Adapter<RequestItemsAdapter.RequestItemViewHolder> {

    private final List<Item> itemList;

    public RequestItemsAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RequestItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRequestLayoutBinding binding = ItemRequestLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RequestItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class RequestItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemRequestLayoutBinding binding;

        public RequestItemViewHolder(@NonNull ItemRequestLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
