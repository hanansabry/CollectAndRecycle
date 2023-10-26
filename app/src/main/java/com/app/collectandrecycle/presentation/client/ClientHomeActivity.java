package com.app.collectandrecycle.presentation.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.databinding.ActivityClientHomeBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.MainActivity;
import com.app.collectandrecycle.presentation.requesets.RequestsAdapter;
import com.app.collectandrecycle.presentation.requesets.RequestsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;

public class ClientHomeActivity extends BaseActivity implements RequestsAdapter.RequestsCallback {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityClientHomeBinding binding;

    @Override
    public View getDataBindingView() {
        binding = ActivityClientHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestsViewModel requestsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RequestsViewModel.class);
        requestsViewModel.retrieveClientRequests(sessionManager.getFirebaseId());
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

    public void onAddRequestFabClicked(View view) {
        startActivity(new Intent(this, AddRequestActivity.class));
    }

    @Override
    public void onRequestClick(Request request) {
        Toast.makeText(this, request.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        sessionManager.logoutUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}