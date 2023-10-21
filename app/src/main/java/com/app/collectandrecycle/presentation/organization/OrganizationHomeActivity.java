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
import com.app.collectandrecycle.presentation.authentication.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizationHomeActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private OrganizationViewModel organizationViewModel;
    private ActivityOrganizationHomeBinding binding;

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
        organizationViewModel.retrieveRegions(sessionManager.getFirebaseId());
        organizationViewModel.retrieveCategories(sessionManager.getFirebaseId());
        organizationViewModel.retrieveItems(sessionManager.getFirebaseId());

        organizationViewModel.getRegionsLiveData().observe(this, regions -> {
            OrganizationMainItemsAdapter adapter = new OrganizationMainItemsAdapter(regions, null, null, Region.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.regionsRecyclerview.setLayoutManager(layout);
            binding.regionsRecyclerview.setAdapter(adapter);
        });

        organizationViewModel.getCategoriesLiveData().observe(this, categories -> {
            OrganizationMainItemsAdapter adapter = new OrganizationMainItemsAdapter(null, categories, null, Category.class.getName());
            LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.categoriesRecyclerview.setLayoutManager(layout);
            binding.categoriesRecyclerview.setAdapter(adapter);
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

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        sessionManager.logoutUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onAddRegionClicked(View view) {
        Toast.makeText(this, "Region", Toast.LENGTH_SHORT).show();
    }

    public void onAddCategoryClicked(View view) {
        Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
    }

    public void onAddItemClicked(View view) {
        Toast.makeText(this, "Item", Toast.LENGTH_SHORT).show();
    }

    public void onShowRequestsClicked(View view) {
    }
}