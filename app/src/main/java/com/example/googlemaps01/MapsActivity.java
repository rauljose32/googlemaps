package com.example.googlemaps01;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    private static final float ZOOM_DELTA = 2f;
    private static final float DEFAULT_MIN_ZOOM = 2.0f;
    private static final float DEFAULT_MAX_ZOOM = 22.0f;

    private static final LatLngBounds ADELAID = new LatLngBounds(
            new LatLng(-35.0, 138.58), new LatLng(-34.9, 138.61));

    private static final LatLngBounds IFTO = new LatLngBounds(
            new LatLng(-10.268828, -48.410502), new LatLng(-10.147188, -48.275920));

    private static final CameraPosition ADELAID_CAMERA = new CameraPosition.Builder().target(
            new LatLng(-34.92873, 138.59995)).zoom(20.0f).bearing(0).tilt(0).build();

    private static final CameraPosition IFTO_CAMERA = new CameraPosition.Builder().target(
            new LatLng(-10.199202218157746, -48.31158433109522)).zoom(20.0f).bearing(0).tilt(0).build();


    private float mMinZoom, mMaxZoom;
    private TextView mCameraTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = null;
        resetMinMaxZoom();
        mCameraTextView = findViewById(R.id.camera_text);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//method

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClampToAdelaid(View v) {
        if (!checkReady()) {
            return;
        }
        mMap.setLatLngBoundsForCameraTarget(ADELAID);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(ADELAID_CAMERA));
    }

    public void onClampToPacific(View v) {
        if (!checkReady()) {
            return;
        }
        mMap.setLatLngBoundsForCameraTarget(IFTO);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(IFTO_CAMERA));
    }

    public void onLatLngClampReset(View v) {
        if (!checkReady()) {
            return;
        }
        mMap.setLatLngBoundsForCameraTarget(null);
        toast("Limites de Lat Lang resetados");
    }

    public void onSetMinZoomClamp(View v) {
        if (!checkReady()) {
            return;
        }
        mMinZoom = mMinZoom + ZOOM_DELTA;
        mMap.setMinZoomPreference(mMinZoom);
        toast("Min Zoom Configurado: " + mMinZoom);
    }

    public void onSetMaxZoomClamp(View v) {
        if (!checkReady()) {
            return;
        }
        mMaxZoom = mMaxZoom - ZOOM_DELTA;
        mMap.setMaxZoomPreference(mMaxZoom);
        toast("Max Zoom Configurado: " + mMaxZoom);
    }

    public void onMinMaxZoomClampReset(View v) {
        if (!checkReady()) {
            return;
        }
        resetMinMaxZoom();
        mMap.resetMinMaxZoomPreference();
        toast("Zoom Min Max resetados");
    }


    private void resetMinMaxZoom() {
        mMaxZoom = DEFAULT_MAX_ZOOM;
        mMinZoom = DEFAULT_MIN_ZOOM;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
       LatLng ifto = new LatLng(-10.199202218157746, -48.31158433109522);
        mMap = googleMap;
        mMap.setOnCameraIdleListener(this);
        mMap.addMarker(new MarkerOptions().position(ifto).title("IFTO Campus Palmas"));
    }

    @Override
    public void onCameraIdle() {
        mCameraTextView.setText(mMap.getCameraPosition().toString());
    }//method

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(getApplicationContext(), "Mapa ainda não disponivel", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }//method

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }//method


}//class
