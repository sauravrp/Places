package com.example.sauravrp.listings.views.viewhelpers;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationListener implements LifecycleObserver {

    private boolean enabled = false;

    private static final String TAG = "LocationListener";
    private final Context context;
    private final Lifecycle lifecycle;

    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private final MutableLiveData<Location> location = new MutableLiveData<>();

    public LocationListener(Context aContext, Lifecycle aLifecycle) {
        context = aContext;
        lifecycle = aLifecycle;
        lifecycle.addObserver(this);

        //  If you pass an Activity, users will be prompted to upgrade or install Google Play services when necessary.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                // got my location, stop the updates
                stopLocationUpdate();

                if (locationResult == null) {
                    Log.d(TAG, "location result on callback is null");
                }

                if(locationResult.getLastLocation() != null) {
                    location.setValue(locationResult.getLastLocation());
                }

            }
        };
    }

    public LiveData<Location> getLocation() {
        return location;
    }


    public LocationRequest getLocationRequest() {
        if(locationRequest == null) {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(20000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        return locationRequest;
    }

    public void enable() {
        enabled = true;
        if(lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            retrieveLocation();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        if(enabled) {
            stopLocationUpdate();
        }
    }

    @SuppressLint("MissingPermission")
    private void retrieveLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {

            if (location != null) {

                // Logic to handle location object
                Log.d(TAG, "last known location found");
                this.location.setValue(location);
            } else {

                /**
                 * The location object may be null in the following situations:
                 *
                 * Location is turned off in the device settings. The result could be null even if the
                 * last location was previously retrieved because disabling location also clears the cache.
                 *
                 * The device never recorded its location, which could be the case of a
                 * new device or a device that has been restored to factory settings.
                 *
                 * Google Play services on the device has restarted, and there is no
                 * active Fused Location Provider client that has requested location after
                 * the services restarted. To avoid this situation you can create a new client
                 * and request location updates yourself. For more information, see Receiving Location Updates.
                 *
                 */
                // let's just request location
                getLocationUpdate();

            }
        }).addOnFailureListener(e -> Log.d(TAG, e.toString()));
    }



    @SuppressLint("MissingPermission")
    private void getLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null);
    }

    private void stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
