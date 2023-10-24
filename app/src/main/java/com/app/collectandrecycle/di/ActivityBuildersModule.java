package com.app.collectandrecycle.di;


import com.app.collectandrecycle.di.modules.AuthenticationViewModelModule;
import com.app.collectandrecycle.di.modules.BaseViewModelModule;
import com.app.collectandrecycle.di.modules.CategoriesViewModelModule;
import com.app.collectandrecycle.di.modules.ItemsViewModelModule;
import com.app.collectandrecycle.di.modules.OrganizationViewModelModule;
import com.app.collectandrecycle.di.modules.RegionsViewModelModule;
import com.app.collectandrecycle.di.modules.RequestsViewModelModule;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.MainActivity;
import com.app.collectandrecycle.presentation.SplashActivity;
import com.app.collectandrecycle.presentation.authentication.LoginActivity;
import com.app.collectandrecycle.presentation.authentication.RegisterActivity;
import com.app.collectandrecycle.presentation.categories.AddCategoryActivity;
import com.app.collectandrecycle.presentation.client.AddRequestActivity;
import com.app.collectandrecycle.presentation.client.ClientHomeActivity;
import com.app.collectandrecycle.presentation.items.AddItemActivity;
import com.app.collectandrecycle.presentation.organization.OrganizationHomeActivity;
import com.app.collectandrecycle.presentation.regions.RegionListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {BaseViewModelModule.class})
    abstract BaseActivity contributeBaseActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector(modules = AuthenticationViewModelModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector
    abstract ClientHomeActivity contributeClientHomeActivity();

    @ContributesAndroidInjector(modules = {ItemsViewModelModule.class, CategoriesViewModelModule.class, RegionsViewModelModule.class})
    abstract OrganizationHomeActivity contributeOrganizationHomeActivity();

    @ContributesAndroidInjector(modules = RegionsViewModelModule.class)
    abstract RegionListActivity contributeRegionListActivity();

    @ContributesAndroidInjector(modules = CategoriesViewModelModule.class)
    abstract AddCategoryActivity contributeAddCategoryActivity();

    @ContributesAndroidInjector(modules = {ItemsViewModelModule.class, CategoriesViewModelModule.class})
    abstract AddItemActivity contributeAddItemActivity();

    @ContributesAndroidInjector(modules = {RegionsViewModelModule.class, RequestsViewModelModule.class})
    abstract AddRequestActivity contributeAddRequestActivity();

}
