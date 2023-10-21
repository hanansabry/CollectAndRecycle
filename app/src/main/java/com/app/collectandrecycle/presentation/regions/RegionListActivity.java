package com.app.collectandrecycle.presentation.regions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.R;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.databinding.ActivityRegionListBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.organization.OrganizationViewModel;
import com.app.collectandrecycle.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RegionListActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityRegionListBinding binding;
    private RegionsAdapter regionsAdapter;

    @Override
    public View getDataBindingView() {
        binding = ActivityRegionListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RegionsViewModel regionsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RegionsViewModel.class);
        regionsViewModel.retrieveAllRegions();
        regionsViewModel.getRegionsLiveData().observe(this, regions -> {
            if (regions != null && !regions.isEmpty()) {
                regionsAdapter = new RegionsAdapter(regions);
                binding.regionsRecyclerview.setAdapter(regionsAdapter);
            }
        });
    }


    // Method to retrieve selected items
    private ArrayList<Region> getSelectedItems() {
        ArrayList<Region> selectedItems = new ArrayList<>();
        SparseBooleanArray selectedPositions = regionsAdapter.getSelectedItems();

        for (int i = 0; i < selectedPositions.size(); i++) {
            int position = selectedPositions.keyAt(i);
            if (selectedPositions.valueAt(i)) {
                selectedItems.add(regionsAdapter.getItem(position));
            }
        }
        return selectedItems;
    }

    public void onSelectRegionsClicked(View view) {
        if (regionsAdapter != null) {
            ArrayList<Region> selectedRegions = getSelectedItems();
            Intent data = new Intent();
            data.putParcelableArrayListExtra(Constants.SELECTED_REGIONS, selectedRegions);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}