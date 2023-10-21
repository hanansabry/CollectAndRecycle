package com.app.collectandrecycle.presentation.categories;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class CategoriesViewModel extends BaseViewModel {

    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Category> addCategoryStateLiveData = new MutableLiveData<>();

    @Inject
    public CategoriesViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
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

    public void addCategory(Category category) {
        addCategoryStateLiveData.postValue(category);
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<Category> getAddCategoryStateLiveData() {
        return addCategoryStateLiveData;
    }
}
