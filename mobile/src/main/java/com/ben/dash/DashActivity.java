package com.ben.dash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class DashActivity extends AppCompatActivity {

    LocationManager map;
    Location carLocation;
    ImageButton markLocation, goToCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        setupAd();

        if (map == null) {
            map = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1972);
                return;
            }
            map.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        markLocation = (ImageButton) findViewById(R.id.mark_location);
        goToCar = (ImageButton) findViewById(R.id.guide_location);

        markLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markLocation();
            }
        });

        goToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCar();
            }
        });
    }


    public void markLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        carLocation = map.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (carLocation != null) {
            Toast.makeText(this, "Car location marked", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Could not mark location",Toast.LENGTH_LONG).show();
        }
    }


    public void goToCar() {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+carLocation.getLatitude()+","+carLocation.getLongitude()+"&mode=w");

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);

    }

    public void setupAd(){
        AdView ad = (AdView)findViewById(R.id.adView);
        ad.setAdSize(AdSize.FULL_BANNER);
        ad.setEnabled(true);
    }

}
