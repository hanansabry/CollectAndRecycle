package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.di.ViewModelKey;
import com.app.collectandrecycle.presentation.categories.CategoriesViewModel;
import com.app.collectandrecycle.presentation.regions.RegionsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CategoriesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    public abstract ViewModel bindViewModel(CategoriesViewModel viewModel);
}
