package com.app.collectandrecycle.presentation.client;

import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.presentation.BaseViewModel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> addRequestStateViewModel = new MutableLiveData<>();

    @Inject
    public RequestsViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }

    public void addNewRequest(Request request) {
        databaseRepository.addNewRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean success) {
                        addRequestStateViewModel.setValue(success);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorState.setValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<Boolean> getAddRequestStateViewModel() {
        return addRequestStateViewModel;
    }
}
