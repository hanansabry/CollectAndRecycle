package com.app.collectandrecycle.presentation.organization;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.databinding.ActivityOrganizationRequestsBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.requesets.RequestsAdapter;
import com.app.collectandrecycle.presentation.requesets.RequestsViewModel;
import com.app.collectandrecycle.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class OrganizationRequestsActivity extends BaseActivity implements RequestsAdapter.RequestsCallback {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityOrganizationRequestsBinding binding;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrganizationRequestsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestsViewModel requestsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RequestsViewModel.class);
        requestsViewModel.retrieveOrganizationRequests(sessionManager.getFirebaseId());
        requestsViewModel.getRequestsViewModel().observe(this, this::populateRequestsRecyclerView);

        requestsViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private void populateRequestsRecyclerView(List<Request> requests) {
        if (requests != null && !requests.isEmpty()) {
            RequestsAdapter requestsAdapter = new RequestsAdapter(requests, this);
            binding.requestsRecyclerview.setAdapter(requestsAdapter);
        }
    }

    @Override
    public void onRequestClick(Request request) {
        Intent intent = new Intent(this, OrganizationRequestDetailsActivity.class);
        intent.putExtra(Constants.REQUEST, request.getId());
        intent.putExtra(Constants.CLIENT, request.getClientId());
        startActivity(intent);
    }
}