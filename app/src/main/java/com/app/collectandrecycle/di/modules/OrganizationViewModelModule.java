package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.di.ViewModelKey;
import com.app.collectandrecycle.presentation.authentication.AuthenticationViewModel;
import com.app.collectandrecycle.presentation.organization.OrganizationViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class OrganizationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OrganizationViewModel.class)
    public abstract ViewModel bindViewModel(OrganizationViewModel viewModel);
}
