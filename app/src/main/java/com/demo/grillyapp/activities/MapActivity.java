package com.demo.grillyapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.api.models.response.Restaurant;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;

    private int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        state = AppData.state;

        ImageView backButton = findViewById(R.id.backButton);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        backButton.setOnClickListener(view -> {
            finish();
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                map.setMyLocationEnabled(true);
            }
        }

        if(state == 0){
            animateCamera(44.0128, 20.9114, 7F);
            for(Restaurant restaurant : AppData.restaurants){
                String title = restaurant.getName();
                double latitude = restaurant.getLatitude();
                double longitude = restaurant.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
            }
        }else {
            animateCamera(AppData.restaurant.getLatitude(), AppData.restaurant.getLongitude(), 13F);
            for (Restaurant restaurant : AppData.restaurants){
                String title = restaurant.getName();
                double latitude = restaurant.getLatitude();
                double longitude = restaurant.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);

                if(restaurant.getId().equals(AppData.restaurant.getId())){
                    map.addMarker(new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_choosen_marker)));
                }else {
                    map.addMarker(new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
                }
            }
        }

    }

    private void animateCamera(double latitude, double longitude, float zoom){
        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, zoom);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("MapActivity", "Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
        double phoneLatitude = location.getLatitude();
        double phoneLongitude = location.getLongitude();

        animateCamera(phoneLatitude, phoneLongitude, 13F);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
