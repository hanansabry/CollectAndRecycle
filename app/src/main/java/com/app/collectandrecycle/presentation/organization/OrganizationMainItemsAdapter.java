package com.app.collectandrecycle.presentation.organization;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.Item;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.databinding.OrganizationMainItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizationMainItemsAdapter extends RecyclerView.Adapter<OrganizationMainItemsAdapter.MainItemHolder> {

    private final List<Region> regions;
    private final List<Category> categories;
    private final List<Item> items;
    private final String className;

    public OrganizationMainItemsAdapter(@Nullable List<Region> regions,
                                        @Nullable List<Category> categories,
                                        @Nullable List<Item> items,
                                        String className) {
        this.regions = regions;
        this.categories = categories;
        this.items = items;
        this.className = className;
    }

    @NonNull
    @Override
    public MainItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrganizationMainItemLayoutBinding binding = OrganizationMainItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new MainItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainItemHolder holder, int position) {
        if (className.equals(Region.class.getName())) {
            Region region = regions.get(position);
            holder.bindRegion(region);
        } else if (className.equals(Category.class.getName())) {
            Category category = categories.get(position);
            holder.bindCategory(category);
        } else if (className.equals(Item.class.getName())) {
            Item item = items.get(position);
            holder.bindItem(item);
        }
    }

    @Override
    public int getItemCount() {
        if (regions != null) {
            return regions.size();
        } else if (categories != null) {
            return categories.size();
        } else if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    public void addRegions(ArrayList<Region> selectedRegions) {
        if (regions != null) {
            int newRegionsStartPosition = regions.size();
            regions.addAll(selectedRegions);
            notifyItemRangeInserted(newRegionsStartPosition, selectedRegions.size());
//            notifyDataSetChanged();
        }
    }

    static class MainItemHolder extends RecyclerView.ViewHolder {

        private final OrganizationMainItemLayoutBinding binding;

        public MainItemHolder(@NonNull OrganizationMainItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindRegion(Region region) {
            binding.setName(region.getName());
        }

        private void bindCategory(Category category) {
            binding.setName(category.getName());
        }

        private void bindItem(Item item) {
            binding.setName(item.getName());
        }
    }
}
