package com.app.collectandrecycle.presentation.organization;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.Item;
import com.app.collectandrecycle.data.Organization;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class OrganizationViewModel extends BaseViewModel {

    private final MutableLiveData<List<Region>> regionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    @Inject
    public OrganizationViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveRegions(String organizationId) {
        Region r1 = new Region();
        r1.setName("Region 1");

        Region r2 = new Region();
        r2.setName("Region 2");

        Region r3 = new Region();
        r3.setName("Region 2");

        Region r4 = new Region();
        r4.setName("Region 2");

        Region r5 = new Region();
        r5.setName("Region 2");

        Region r6 = new Region();
        r6.setName("Region 2");

        Region r7 = new Region();
        r7.setName("Region 2");
        List<Region> regions = new ArrayList<>();
        regions.add(r1);
        regions.add(r2);
//        regions.add(r3);
//        regions.add(r4);
//        regions.add(r5);
//        regions.add(r6);
//        regions.add(r7);

        regionsLiveData.postValue(regions);
    }

    public void retrieveItems(String organizationId) {
        Item r1 = new Item();
        r1.setName("Item 1");

        Item r2 = new Item();
        r2.setName("Item 2");
        List<Item> items = new ArrayList<>();
        items.add(r1);
        items.add(r2);

        itemsLiveData.postValue(items);
    }

    public MutableLiveData<List<Region>> getRegionsLiveData() {
        return regionsLiveData;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }
}
