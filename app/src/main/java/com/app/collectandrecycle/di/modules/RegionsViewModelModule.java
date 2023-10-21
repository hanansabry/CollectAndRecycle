package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.di.ViewModelKey;
import com.app.collectandrecycle.presentation.authentication.AuthenticationViewModel;
import com.app.collectandrecycle.presentation.regions.RegionsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RegionsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegionsViewModel.class)
    public abstract ViewModel bindViewModel(RegionsViewModel viewModel);
}
