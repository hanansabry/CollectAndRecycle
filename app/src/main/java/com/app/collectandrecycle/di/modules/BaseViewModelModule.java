package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.presentation.BaseViewModel;
import com.app.collectandrecycle.di.ViewModelKey;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class BaseViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel.class)
    public abstract ViewModel bindViewModel(BaseViewModel viewModel);
}
