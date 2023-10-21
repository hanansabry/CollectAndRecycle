package com.app.collectandrecycle.di;


import com.app.collectandrecycle.di.modules.AuthenticationViewModelModule;
import com.app.collectandrecycle.di.modules.BaseViewModelModule;
import com.app.collectandrecycle.di.modules.OrganizationViewModelModule;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.presentation.MainActivity;
import com.app.collectandrecycle.presentation.SplashActivity;
import com.app.collectandrecycle.presentation.authentication.LoginActivity;
import com.app.collectandrecycle.presentation.authentication.RegisterActivity;
import com.app.collectandrecycle.presentation.client.ClientHomeActivity;
import com.app.collectandrecycle.presentation.organization.OrganizationHomeActivity;

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

    @ContributesAndroidInjector(modules = OrganizationViewModelModule.class)
    abstract OrganizationHomeActivity contributeOrganizationHomeActivity();

}
