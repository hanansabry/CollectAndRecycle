package com.app.collectandrecycle.presentation.client;

import com.app.collectandrecycle.data.DatabaseRepository;
import com.app.collectandrecycle.presentation.BaseViewModel;

import javax.inject.Inject;

public class RequestsViewModel extends BaseViewModel {

    @Inject
    public RequestsViewModel(DatabaseRepository databaseRepository) {
        super(databaseRepository);
    }
}
