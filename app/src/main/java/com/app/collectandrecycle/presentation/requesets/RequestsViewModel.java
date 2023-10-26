package com.app.collectandrecycle.presentation.requesets;

import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.Organization;
import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> addRequestStateViewModel = new MutableLiveData<>();
    private final MutableLiveData<List<Request>> requestsViewModel = new MutableLiveData<>();

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

    public void retrieveClientRequests(String clientId) {
        databaseRepository.retrieveClientRequests(clientId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Request>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Request> requests) {
                        requestsViewModel.setValue(requests);
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

    public MutableLiveData<Boolean> getAddRequestStateViewModel() {
        return addRequestStateViewModel;
    }

    public MutableLiveData<List<Request>> getRequestsViewModel() {
        return requestsViewModel;
    }
}
