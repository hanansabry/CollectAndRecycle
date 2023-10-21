package com.app.collectandrecycle.presentation.regions;

import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class RegionsViewModel extends BaseViewModel {

    private final MutableLiveData<List<Region>> regionsLiveData = new MutableLiveData<>();

    @Inject
    public RegionsViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveAllRegions() {
        Region r1 = new Region();
        r1.setName("Region 55");

        Region r2 = new Region();
        r2.setName("Region 33");

        Region r3 = new Region();
        r3.setName("Region 22");

        Region r4 = new Region();
        r4.setName("Region 40");
        List<Region> regions = new ArrayList<>();
        regions.add(r1);
        regions.add(r2);
        regions.add(r2);
        regions.add(r4);

        regionsLiveData.postValue(regions);
    }

    public MutableLiveData<List<Region>> getRegionsLiveData() {
        return regionsLiveData;
    }
}
