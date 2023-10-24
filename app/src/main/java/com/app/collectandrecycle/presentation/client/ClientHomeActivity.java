package com.app.collectandrecycle.presentation.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.collectandrecycle.databinding.ActivityClientHomeBinding;
import com.app.collectandrecycle.presentation.BaseActivity;

public class ClientHomeActivity extends BaseActivity {

    private ActivityClientHomeBinding binding;

    @Override
    public View getDataBindingView() {
        binding = ActivityClientHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onAddRequestFabClicked(View view) {
        startActivity(new Intent(this, AddRequestActivity.class));
    }
}