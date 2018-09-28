package com.example.sauravrp.listings.views;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.helpers.IntentHelper;
import com.example.sauravrp.listings.views.viewhelpers.LocationListener;
import com.example.sauravrp.listings.views.viewhelpers.PermissionsUtil;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public abstract class BaseLocationActivity extends AppCompatActivity {

    private final static int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private final static int REQUEST_CHECK_SETTINGS = 1;

    private final static String TAG = "BaseLocationActivity";

    private AlertDialog locationPreferredDialog = null;
    private boolean isLocationPreferredDialogVisible = false;

    private LocationListener locationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationListener = new LocationListener(this, getLifecycle());
        checkDeviceLocationSettings();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHECK_SETTINGS) {
            if(resultCode == Activity.RESULT_OK) {
                checkAppPermissions();
            } else {
                // this could be better, maybe we can send user to the settings page
                Toast.makeText(this, R.string.location_not_turned_on, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(locationPreferredDialog != null && !locationPreferredDialog.isShowing()) {
            if (!PermissionsUtil.isLocationPermissionGranted(this)) {
                PermissionsUtil.requestLocationPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                if(!locationListener.isEnabled()) {
                    locationListener.enable();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    locationListener.enable();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (!isLocationPreferredDialogVisible) {
                        showLocationPreferredDialog();
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    protected LocationListener getLocationListener() {
        return locationListener;
    }


    private void checkDeviceLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationListener.getLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            if (isDestroyed() || isFinishing()) {
                return;
            }
            checkAppPermissions();
        });

        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                if (isDestroyed() || isFinishing()) {
                    return;
                }
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(BaseLocationActivity.this,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    Log.e(TAG, sendEx.toString());
                }
            }
        });
    }

    private void checkAppPermissions() {
        if (PermissionsUtil.isLocationPermissionGranted(this)) {
            locationListener.enable();
        }
        else {
            // No explanation needed; request the permission
            PermissionsUtil.requestLocationPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    private void showLocationPreferredDialog() {

        isLocationPreferredDialogVisible = true;

        if (locationPreferredDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            locationPreferredDialog = builder.setMessage(R.string.message_enable_location_services_body_long)
                    .setPositiveButton(R.string.button_label_settings, (dialog, which) -> {
                        isLocationPreferredDialogVisible = false;
                        if (isDestroyed() || isFinishing()) {
                            return;
                        }
                        Intent intent = IntentHelper.getSettingsIntent(getPackageName());
                        startActivity(intent);
                        dialog.cancel();
                    })
                    .setTitle(R.string.message_enable_location_services_title)
                    .create();
        }

        locationPreferredDialog.show();
    }
}


