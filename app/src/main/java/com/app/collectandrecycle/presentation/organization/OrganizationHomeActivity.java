package com.app.collectandrecycle.presentation.organization;

import android.os.Bundle;
import android.view.View;

import com.app.collectandrecycle.databinding.ActivityOrganizationHomeBinding;
import com.app.collectandrecycle.presentation.BaseActivity;

public class OrganizationHomeActivity extends BaseActivity {

    private ActivityOrganizationHomeBinding binding;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrganizationHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}