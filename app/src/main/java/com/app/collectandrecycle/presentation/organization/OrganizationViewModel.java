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
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
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
        List<Region> regions = new ArrayList<>();
        regions.add(r1);
        regions.add(r2);

        regionsLiveData.postValue(regions);
    }

    public void retrieveCategories(String organizationId) {
        Category r1 = new Category();
        r1.setName("Category 1");

        Category r2 = new Category();
        r2.setName("Category 2");
        List<Category> categories = new ArrayList<>();
        categories.add(r1);
        categories.add(r2);

        categoriesLiveData.postValue(categories);
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

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }
}
