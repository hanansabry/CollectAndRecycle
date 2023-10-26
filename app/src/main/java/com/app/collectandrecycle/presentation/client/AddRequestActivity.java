package com.app.collectandrecycle.presentation.client;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.collectandrecycle.data.Organization;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.databinding.ActivityAddRequestBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.regions.RegionsViewModel;
import com.app.collectandrecycle.presentation.requesets.AddRequestDetailsActivity;
import com.app.collectandrecycle.utils.Constants;
import com.app.collectandrecycle.utils.Utils;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddRequestActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityAddRequestBinding binding;
    private Region selectedRegion;
    private RegionsViewModel regionsViewModel;
    private Organization selectedOrganization;
    private Calendar calendar;

    @Override
    public View getDataBindingView() {
        binding = ActivityAddRequestBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        regionsViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RegionsViewModel.class);

        regionsViewModel.retrieveAllRegions();
        regionsViewModel.getRegionsLiveData().observe(this, this::initiateRegionsSpinner);

        regionsViewModel.getOrganizationOfRegionLiveData().observe(this, this::initiateOrganizationsSpinner);
    }

    private void initiateRegionsSpinner(List<Region> regions) {
        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        regionAdapter.addAll(regions);
        binding.regionsSpinner.setAdapter(regionAdapter);
        binding.regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regions.get(position);
                binding.regionEditText.setText(selectedRegion.getName());
                binding.organizationEditText.setText(null);
                binding.organizationsSpinner.setAdapter(null);
                selectedOrganization = null;
                regionsViewModel.retrieveOrganizationsOfRegion(selectedRegion.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initiateOrganizationsSpinner(List<Organization> organizations) {
        ArrayAdapter<Organization> organizationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        organizationAdapter.addAll(organizations);
        binding.organizationsSpinner.setAdapter(organizationAdapter);
        binding.organizationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrganization = organizations.get(position);
                binding.organizationEditText.setText(selectedOrganization.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onDateClicked(View view) {
        Utils.showDatePicker(this, (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String date = Utils.convertMillisecondsToDate(calendar.getTimeInMillis(), Constants.DATE_FORMAT);
            binding.dateEditText.setText(date);
        });
    }

    public void onTimeClicked(View view) {
        Utils.showTimePicker(this, (view1, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            String selectedTime = Utils.convertMillisecondsToDate(calendar.getTimeInMillis(), "HH:mm");
            binding.timeEditText.setText(selectedTime);
        });
    }

    public void onSelectOrganizationClicked(View view) {
        binding.organizationsSpinner.performClick();
    }

    public void onSelectRegionClicked(View view) {
        binding.regionsSpinner.performClick();
    }

    public void onNextClicked(View view) {
        String requestTitle = binding.titleEditText.getText().toString();
        long dateTime = calendar.getTimeInMillis();

        if (requestTitle.isEmpty() || selectedRegion == null || selectedOrganization == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            Request request = new Request();
            request.setTitle(requestTitle);
            request.setClientId(sessionManager.getFirebaseId());
            request.setClientName(sessionManager.getUserName());
            request.setOrganizationId(selectedOrganization.getId());
            request.setOrganizationName(selectedOrganization.getName());
            request.setDateTime(dateTime);
            request.setStatus(Request.RequestStatus.New.name());

            Intent intent = new Intent(this, AddRequestDetailsActivity.class);
            intent.putExtra(Constants.REQUEST, request);
            startActivityForResult(intent, Constants.REQUEST_DETAILS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_DETAILS_REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }
}