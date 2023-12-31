package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.di.ViewModelKey;
import com.app.collectandrecycle.presentation.requesets.RequestsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RequestsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RequestsViewModel.class)
    public abstract ViewModel bindViewModel(RequestsViewModel viewModel);
}
