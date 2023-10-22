package com.app.collectandrecycle.data;

import com.app.collectandrecycle.datasource.FirebaseDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DatabaseRepository {

    private final FirebaseDataSource firebaseDataSource;

    @Inject
    public DatabaseRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    public Single<Client> loginAsClient(String email, String password) {
        return firebaseDataSource.loginAsClient(email, password);
    }

    public Single<Organization> loginAsOrganization(String email, String password) {
        return firebaseDataSource.loginAsOrganization(email, password);
    }

    public Single<Client> registerClient(Client client) {
        return firebaseDataSource.registerClient(client);
    }

    public Single<Organization> registerOrganization(Organization organization) {
        return firebaseDataSource.registerOrganization(organization);
    }

    public Observable<List<Region>> retrieveRegions(String organizationId) {
        return firebaseDataSource.retrieveRegions(organizationId);
    }

    public Single<List<Region>> retrieveAllRegions() {
        return firebaseDataSource.retrieveAllRegions();
    }

    public Single<Boolean> addRegionsToOrganization(String organizationId, List<Region> selectedRegions) {
        return firebaseDataSource.addRegionsToOrganization(organizationId, selectedRegions);
    }
}
