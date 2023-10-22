package com.app.collectandrecycle.presentation.organization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.Item;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.databinding.ActivityOrganizationHomeBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.MainActivity;
import com.app.collectandrecycle.presentation.categories.AddCategoryActivity;
import com.app.collectandrecycle.presentation.categories.CategoriesViewModel;
import com.app.collectandrecycle.presentation.regions.RegionListActivity;
import com.app.collectandrecycle.presentation.regions.RegionsViewModel;
import com.app.collectandrecycle.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizationHomeActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private OrganizationViewModel organizationViewModel;
    private CategoriesViewModel categoriesViewModel;
    private RegionsViewModel regionsViewModel;
    private ActivityOrganizationHomeBinding binding;
    private OrganizationMainItemsAdapter regionsAdapter;
    private OrganizationMainItemsAdapter categoriesAdapter;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrganizationHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setActivity(this);
        binding.setName("ABC");

        organizationViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(OrganizationViewModel.class);
        categoriesViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(CategoriesViewModel.class);
        regionsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RegionsViewModel.class);

        regionsViewModel.retrieveRegions(sessionManager.getFirebaseId());
        categoriesViewModel.retrieveCategories(sessionManager.getFirebaseId());
        organizationViewModel.retrieveItems(sessionManager.getFirebaseId());

        regionsViewModel.getRegionsLiveData().observe(this, regions -> {
            regionsAdapter = new OrganizationMainItemsAdapter(regions, null, null, Region.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.regionsRecyclerview.setLayoutManager(layout);
            binding.regionsRecyclerview.setAdapter(regionsAdapter);
        });

        categoriesViewModel.getCategoriesLiveData().observe(this, categories -> {
            categoriesAdapter = new OrganizationMainItemsAdapter(null, categories, null, Category.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.categoriesRecyclerview.setLayoutManager(layout);
            binding.categoriesRecyclerview.setAdapter(categoriesAdapter);
        });

        organizationViewModel.getItemsLiveData().observe(this, items -> {
            OrganizationMainItemsAdapter adapter = new OrganizationMainItemsAdapter(null, null, items, Item.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.itemsRecyclerview.setLayoutManager(layout);
            binding.itemsRecyclerview.setAdapter(adapter);
        });

        organizationViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ADD_CATEGORY_REQUEST_CODE) {
                if (data != null) {
                    Category category = data.getParcelableExtra(Constants.CATEGORY);
                    categoriesAdapter.addCategory(category);
                }
            }
        }
    }

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        sessionManager.logoutUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onAddRegionClicked(View view) {
        startActivity(new Intent(this, RegionListActivity.class));
    }

    public void onAddCategoryClicked(View view) {
        startActivityForResult(new Intent(this, AddCategoryActivity.class), Constants.ADD_CATEGORY_REQUEST_CODE);
    }

    public void onAddItemClicked(View view) {
        Toast.makeText(this, "Item", Toast.LENGTH_SHORT).show();
    }

    public void onShowRequestsClicked(View view) {
    }
}