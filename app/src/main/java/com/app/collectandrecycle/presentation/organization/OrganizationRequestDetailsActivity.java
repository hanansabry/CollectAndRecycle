package com.app.collectandrecycle.presentation.organization;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.databinding.ActivityOrganizationRequestDetailsBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.requesets.RequestsViewModel;
import com.app.collectandrecycle.utils.Constants;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;

public class OrganizationRequestDetailsActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityOrganizationRequestDetailsBinding binding;
    private RequestsViewModel requestsViewModel;
    private Request request;

    @Override
    public View getDataBindingView() {
        binding = ActivityOrganizationRequestDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String requestId = getIntent().getStringExtra(Constants.REQUEST);

        requestsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RequestsViewModel.class);
        requestsViewModel.retrieveRequestDetails(requestId);

        requestsViewModel.getRequestDetailsLiveData().observe(this, request -> {
            if (request != null) {
                this.request = request;
                binding.setRequest(request);
                OrganizationRequestItemsAdapter adapter = new OrganizationRequestItemsAdapter(request.getRequestItemList());
                binding.reuqestItemsRecyclerview.setAdapter(adapter);

                if (request.getStatus().equals(Request.RequestStatus.New.name())) {
                    binding.deliverButton.setVisibility(View.VISIBLE);
                    binding.confirmButton.setVisibility(View.VISIBLE);
                } else if (request.getStatus().equals(Request.RequestStatus.Delivered.name())) {
                    binding.deliverButton.setVisibility(View.GONE);
                    binding.confirmButton.setVisibility(View.GONE);
                } else {
                    //status is confirmed
                    binding.deliverButton.setVisibility(View.VISIBLE);
                    binding.confirmButton.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.deliverButton.getLayoutParams();
                    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    binding.deliverButton.setLayoutParams(layoutParams);
                }
            }
        });

        requestsViewModel.getClientDetailsLiveData().observe(this, client -> {
            if (client != null) {
                binding.setClient(client);
            }
        });

        requestsViewModel.getRequestStatusLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Request status is changed successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Something wrong is happened, please try again", Toast.LENGTH_SHORT).show();
            }
        });

        requestsViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void onConfirmClicked(View view) {
        requestsViewModel.setRequestStatus(request, Request.RequestStatus.Confirmed.name());
    }

    public void onDeliverClicked(View view) {
        requestsViewModel.setRequestStatus(request, Request.RequestStatus.Delivered.name());
    }
}