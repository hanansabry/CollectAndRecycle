package com.app.collectandrecycle.presentation.requesets;

import com.app.collectandrecycle.data.Client;
import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.data.models.Request;
import com.app.collectandrecycle.presentation.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.core.util.Pair;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestsViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> addRequestStateViewModel = new MutableLiveData<>();
    private final MutableLiveData<List<Request>> requestsViewModel = new MutableLiveData<>();
    private final MutableLiveData<Request> requestDetailsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Client> clientDetailsLiveData = new MutableLiveData<>();
    private final MediatorLiveData<Pair<Request, Client>> requestClientLiveData = new MediatorLiveData<>();

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

    public void retrieveOrganizationRequests(String organizationId) {
        databaseRepository.retrieveOrganizationRequests(organizationId)
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

    public void retrieveRequestDetails(String requestId) {
        databaseRepository.retrieveRequestDetails(requestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Pair<Request, Client>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Pair<Request, Client> requestClientPair) {
                        requestDetailsLiveData.setValue(requestClientPair.first);
                        clientDetailsLiveData.setValue(requestClientPair.second);
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

    public MutableLiveData<List<Request>> getRequestsViewModel() {
        return requestsViewModel;
    }

    public MutableLiveData<Request> getRequestDetailsLiveData() {
        return requestDetailsLiveData;
    }

    public MutableLiveData<Client> getClientDetailsLiveData() {
        return clientDetailsLiveData;
    }
}
