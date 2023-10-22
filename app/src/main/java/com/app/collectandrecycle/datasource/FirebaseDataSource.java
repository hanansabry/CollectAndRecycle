package com.app.collectandrecycle.datasource;

import com.app.collectandrecycle.data.Client;
import com.app.collectandrecycle.data.Organization;
import com.app.collectandrecycle.data.Region;
import com.app.collectandrecycle.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;

public class FirebaseDataSource {

    private final FirebaseDatabase firebaseDatabase;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseDataSource(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
    }

    public Single<Client> loginAsClient(String email, String password) {
        return Single.create(emitter -> {
            DatabaseReference clientsRef = firebaseDatabase.getReference(Constants.CLIENTS_NODE);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String clientId = task.getResult().getUser().getUid();
                            clientsRef.child(clientId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Client client = snapshot.getValue(Client.class);
                                            if (client != null) {
                                                client.setId(clientId);
                                                emitter.onSuccess(client);
                                            } else {
                                                emitter.onError(new Throwable("Invalid email or password"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            emitter.onError(error.toException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Organization> loginAsOrganization(String email, String password) {
        return Single.create(emitter -> {
            DatabaseReference organizationsRef = firebaseDatabase.getReference(Constants.ORGANIZATIONS_NODE);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String organizationId = task.getResult().getUser().getUid();
                            organizationsRef.child(organizationId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Organization organization = snapshot.getValue(Organization.class);
                                            if (organization != null) {
                                                organization.setId(organizationId);
                                                emitter.onSuccess(organization);
                                            } else {
                                                emitter.onError(new Throwable("Invalid email or password"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            emitter.onError(error.toException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Client> registerClient(Client client) {
        return Single.create(emitter -> {
            DatabaseReference clientsRef = firebaseDatabase.getReference(Constants.CLIENTS_NODE);
            //create client with email and password
            firebaseAuth.createUserWithEmailAndPassword(client.getEmail(), client.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String clientId = task.getResult().getUser().getUid();
                            client.setId(clientId);
                            clientsRef.child(clientId)
                                    .setValue(client)
                                    .addOnCompleteListener(saveTask -> {
                                        if (saveTask.isSuccessful()) {
                                            emitter.onSuccess(client);
                                        } else {
                                            emitter.onError(saveTask.getException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Single<Organization> registerOrganization(Organization organization) {
        return Single.create(emitter -> {
            DatabaseReference organizationsRef = firebaseDatabase.getReference(Constants.ORGANIZATIONS_NODE);
            //create client with email and password
            firebaseAuth.createUserWithEmailAndPassword(organization.getEmail(), organization.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String organizationId = task.getResult().getUser().getUid();
                            organization.setId(organizationId);
                            organizationsRef.child(organizationId)
                                    .setValue(organization)
                                    .addOnCompleteListener(saveTask -> {
                                        if (saveTask.isSuccessful()) {
                                            emitter.onSuccess(organization);
                                        } else {
                                            emitter.onError(saveTask.getException());
                                        }
                                    });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Observable<List<Region>> retrieveRegions(String organizationId) {
        return Observable.create(emitter -> {
            firebaseDatabase.getReference(Constants.ORGANIZATIONS_NODE)
                    .child(organizationId)
                    .child(Constants.REGIONS_NODE)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Region> regions = new ArrayList<>();
                            for (DataSnapshot regionSnapshot : snapshot.getChildren()) {
                                Region region = regionSnapshot.getValue(Region.class);
                                if (region != null) {
                                    region.setId(regionSnapshot.getKey());
                                }
                                regions.add(region);
                            }
                            emitter.onNext(regions);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    public Single<List<Region>> retrieveAllRegions() {
        return Single.create(emitter -> {
            firebaseDatabase.getReference(Constants.REGIONS_NODE)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Region> regions = new ArrayList<>();
                            for (DataSnapshot regionSnapshot : snapshot.getChildren()) {
                                Region region = regionSnapshot.getValue(Region.class);
                                if (region != null) {
                                    region.setId(regionSnapshot.getKey());
                                }
                                regions.add(region);
                            }
                            emitter.onSuccess(regions);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                        }
                    });
        });
    }

    public Single<Boolean> addRegionsToOrganization(String organizationId, List<Region> selectedRegions) {
        return Single.create(emitter -> {
            HashMap<String, Object> updatedValues = new HashMap<>();
            DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.ORGANIZATIONS_NODE)
                    .child(organizationId)
                    .child(Constants.REGIONS_NODE);
            for (Region region : selectedRegions) {
                updatedValues.put(databaseReference.push().getKey(), region);
            }
            databaseReference.updateChildren(updatedValues)
                    .addOnCompleteListener(task -> emitter.onSuccess(task.isSuccessful()));
        });
    }
}
