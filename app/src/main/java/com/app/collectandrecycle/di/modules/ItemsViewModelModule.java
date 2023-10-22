package com.app.collectandrecycle.di.modules;

import com.app.collectandrecycle.di.ViewModelKey;
import com.app.collectandrecycle.presentation.categories.CategoriesViewModel;
import com.app.collectandrecycle.presentation.items.ItemsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ItemsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ItemsViewModel.class)
    public abstract ViewModel bindViewModel(ItemsViewModel viewModel);
}
