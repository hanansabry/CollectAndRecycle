package com.app.collectandrecycle.presentation.regions;

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

public class RegionsViewModel extends BaseViewModel {

    private final MutableLiveData<List<Region>> regionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> addRegionsStateLiveData = new MutableLiveData<>();

    @Inject
    public RegionsViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void retrieveAllRegions() {
        databaseRepository.retrieveAllRegions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Region>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Region> regions) {
                        regionsLiveData.setValue(regions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public void retrieveRegions(String organizationId) {
        databaseRepository.retrieveRegions(organizationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Region>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Region> regions) {
                        regionsLiveData.setValue(regions);
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

    public void addRegionsToOrganization(String organizationId, List<Region> selectedRegion) {
        databaseRepository.addRegionsToOrganization(organizationId, selectedRegion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        addRegionsStateLiveData.setValue(success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<Region>> getRegionsLiveData() {
        return regionsLiveData;
    }

    public MutableLiveData<Boolean> getAddRegionsStateLiveData() {
        return addRegionsStateLiveData;
    }
}
