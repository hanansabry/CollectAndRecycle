package com.app.collectandrecycle.presentation.categories;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesViewModel extends BaseViewModel {

    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addCategoryStateLiveData = new MutableLiveData<>();

    @Inject
    public CategoriesViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveCategories(String organizationId) {
        databaseRepository.retrieveCategories(organizationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Category> regions) {
                        categoriesLiveData.setValue(regions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addCategory(String organizationId, Category category) {
        databaseRepository.addCategory(organizationId, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        addCategoryStateLiveData.postValue(success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<Boolean> getAddCategoryStateLiveData() {
        return addCategoryStateLiveData;
    }
}
