package com.app.collectandrecycle.presentation.organization;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.databinding.ActivityOrganizationRequestDetailsBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.requesets.RequestsViewModel;
import com.app.collectandrecycle.utils.Constants;

import javax.inject.Inject;

public class OrganizationRequestDetailsActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityOrganizationRequestDetailsBinding binding;
    private RequestsViewModel requestsViewModel;
    private String requestId;
    private String clientId;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrganizationRequestDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestId = getIntent().getStringExtra(Constants.REQUEST);
        clientId = getIntent().getStringExtra(Constants.CLIENT);

        requestsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RequestsViewModel.class);
        requestsViewModel.retrieveRequestDetails(requestId);

        requestsViewModel.getRequestDetailsLiveData().observe(this, request -> {
            if (request != null) {
                binding.setRequest(request);
                OrganizationRequestItemsAdapter adapter = new OrganizationRequestItemsAdapter(request.getRequestItemList());
                binding.reuqestItemsRecyclerview.setAdapter(adapter);
            }
        });

        requestsViewModel.getClientDetailsLiveData().observe(this, client -> {
            if (client != null) {
                binding.setClient(client);
            }
        });

        requestsViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void onConfirmClicked(View view) {
        Toast.makeText(this, "Confirm clicked", Toast.LENGTH_SHORT).show();
    }

    public void onDeliverClicked(View view) {
        Toast.makeText(this, "Deliver clicked", Toast.LENGTH_SHORT).show();
    }
}