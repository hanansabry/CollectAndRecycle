package com.app.collectandrecycle.presentation.requesets;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.Item;
import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.databinding.ActivityAddRequestDetailsBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.categories.CategoriesViewModel;
import com.app.collectandrecycle.presentation.client.RequestsViewModel;
import com.app.collectandrecycle.presentation.organization.OrganizationMainItemsAdapter;
import com.app.collectandrecycle.utils.Constants;
import com.app.collectandrecycle.utils.RecyclerItemClickListener;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddRequestDetailsActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityAddRequestDetailsBinding binding;
    private CategoriesViewModel categoriesViewModel;
    private Request request;

    @Override
    public View getDataBindingView() {
        binding = ActivityAddRequestDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = getIntent().getParcelableExtra(Constants.REQUEST);
        binding.setRequest(request);

        RequestsViewModel requestsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RequestsViewModel.class);
        categoriesViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(CategoriesViewModel.class);
        categoriesViewModel.retrieveCategories(request.getOrganizationId());
        categoriesViewModel.getCategoriesLiveData().observe(this, this::populateCategoriesRecyclerView);
        categoriesViewModel.getItemsLiveData().observe(this, this::populateItemsRecyclerView);
    }

    private void populateCategoriesRecyclerView(List<Category> categories) {
        if (categories != null && !categories.isEmpty()) {
            OrganizationMainItemsAdapter categoriesAdapter = new OrganizationMainItemsAdapter(null, categories, null, Category.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.categoriesRecyclerview.setLayoutManager(layout);
            binding.categoriesRecyclerview.setAdapter(categoriesAdapter);
            binding.categoriesRecyclerview.addOnItemTouchListener(
                    new RecyclerItemClickListener(this, binding.categoriesRecyclerview,
                            (view, position) -> {
                                categoriesAdapter.setSelectedItem(position);
                                categoriesViewModel.retrieveCategoryItems(request.getOrganizationId(), categories.get(position).getId());
                            }));
            //retrieve items of first category
            categoriesViewModel.retrieveCategoryItems(request.getOrganizationId(), categories.get(0).getId());
        }
    }

    private void populateItemsRecyclerView(List<Item> items) {
        if (items != null && !items.isEmpty()) {
            binding.itemsHeader.setVisibility(View.VISIBLE);
            binding.itemsRecyclerview.setVisibility(View.VISIBLE);

            RequestItemsAdapter itemsAdapter = new RequestItemsAdapter(items);
            binding.itemsRecyclerview.setAdapter(itemsAdapter);
        } else {
            binding.itemsHeader.setVisibility(View.GONE);
            binding.itemsRecyclerview.setVisibility(View.GONE);
        }
    }

    public void onSendRequestClicked(View view) {
    }
}