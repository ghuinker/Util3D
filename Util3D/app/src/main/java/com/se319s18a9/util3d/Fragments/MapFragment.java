package com.se319s18a9.util3d.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.se319s18a9.util3d.R;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MapFragment extends Fragment implements View.OnClickListener {

    MapView mMapView;
    private GoogleMap googleMap;

    FloatingActionButton myLocationFab;

    TextView waterUtilityTextView;
    FloatingActionButton waterUtilityTypeFab;
    TextView gasUtilityTextView;
    FloatingActionButton gasUtilityTypeFab;
    TextView electricUtilityTextView;
    FloatingActionButton electricUtilityTypeFab;
    TextView sewageUtilityTextView;
    FloatingActionButton sewageUtilityTypeFab;

    FloatingActionButton utilityTypeFab;
    FloatingActionButton trackingFab;

    public enum UtilityType {
        WATER, GAS, ELECTRIC, SEWAGE
    }

    public UtilityType selectedUtility = UtilityType.WATER;

    boolean trackingEnabled = false;
    boolean utilitiesVisible = false;

    public MapFragment() {
        // Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Set toolbar title

        getActivity().setTitle(R.string.global_fragmentName_map);

        // Initialize local variables

        mMapView = v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        myLocationFab = v.findViewById(R.id.fragment_map_fab_myLocation);
        myLocationFab.setOnClickListener(this);

        waterUtilityTextView = v.findViewById(R.id.fragment_map_textView_waterUtilityType);
        waterUtilityTextView.setVisibility(View.INVISIBLE);

        waterUtilityTypeFab = v.findViewById(R.id.fragment_map_fab_utilityType_water);
        waterUtilityTypeFab.setOnClickListener(this);
        waterUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

        gasUtilityTextView = v.findViewById(R.id.fragment_map_textView_gasUtilityType);
        gasUtilityTextView.setVisibility(View.INVISIBLE);

        gasUtilityTypeFab= v.findViewById(R.id.fragment_map_fab_utilityType_gas);
        gasUtilityTypeFab.setOnClickListener(this);
        gasUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

        electricUtilityTextView = v.findViewById(R.id.fragment_map_textView_electricUtilityType);
        electricUtilityTextView.setVisibility(View.INVISIBLE);

        electricUtilityTypeFab = v.findViewById(R.id.fragment_map_fab_utilityType_electric);
        electricUtilityTypeFab.setOnClickListener(this);
        electricUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

        sewageUtilityTextView = v.findViewById(R.id.fragment_map_textView_sewageUtilityType);
        sewageUtilityTextView.setVisibility(View.INVISIBLE);

        sewageUtilityTypeFab = v.findViewById(R.id.fragment_map_fab_utilityType_sewage);
        sewageUtilityTypeFab.setOnClickListener(this);
        sewageUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

        utilityTypeFab = v.findViewById(R.id.fragment_map_fab_utilityType);
        utilityTypeFab.setOnClickListener(this);

        trackingFab = v.findViewById(R.id.fragment_map_fab_tracking);
        trackingFab.setOnClickListener(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                initializeMapOnClickListener();

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }

//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.fragment_map_menu_togglemaptype:
                if(googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Toast.makeText(getContext(), "Map Type: Satellite", Toast.LENGTH_SHORT).show();
                } else if(googleMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    Toast.makeText(getContext(), "Map Type: Terrain", Toast.LENGTH_SHORT).show();
                } else if(googleMap.getMapType() == GoogleMap.MAP_TYPE_TERRAIN) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    Toast.makeText(getContext(), "Map Type: Hybrid", Toast.LENGTH_SHORT).show();
                } else if(googleMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(getContext(), "Map Type: Normal", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                Toast.makeText(getActivity(), "Something happened", Toast.LENGTH_SHORT).show();
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fragment_map_fab_myLocation:
                // TODO: Move camera to current GPS location
                break;
            case R.id.fragment_map_fab_utilityType_water:
                Toast.makeText(getContext(), "Water", Toast.LENGTH_SHORT).show();
                selectedUtility = UtilityType.WATER;
                break;
            case R.id.fragment_map_fab_utilityType_gas:
                Toast.makeText(getContext(), "Gas", Toast.LENGTH_SHORT).show();
                selectedUtility = UtilityType.GAS;
                break;
            case R.id.fragment_map_fab_utilityType_electric:
                Toast.makeText(getContext(), "Electric", Toast.LENGTH_SHORT).show();
                selectedUtility = UtilityType.ELECTRIC;
                break;
            case R.id.fragment_map_fab_utilityType_sewage:
                Toast.makeText(getContext(), "Sewage", Toast.LENGTH_SHORT).show();
                selectedUtility = UtilityType.SEWAGE;
                break;
            case R.id.fragment_map_fab_utilityType:
                if(!trackingEnabled && !utilitiesVisible) {
                    enableUtilities(true);
                } else if(utilitiesVisible) {
                    enableUtilities(false);
                }
                break;
            case R.id.fragment_map_fab_tracking:
                if(!trackingEnabled) {
                    trackingEnabled = true;
                    utilityTypeFab.setEnabled(false);
                    enableUtilities(false);
                    Toast.makeText(getContext(), "Tracking enabled", Toast.LENGTH_SHORT).show();

                    TextDrawable drawable = null;

                    switch (selectedUtility) {
                        case WATER:
                            drawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(120)  // width in px
                                    .height(120) // height in px
                                    .endConfig().buildRound("W", R.color.colorGenericBlue);
                            break;
                        case GAS:
                            drawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(120)  // width in px
                                    .height(120) // height in px
                                    .endConfig().buildRound("G", R.color.colorGenericOrange);
                            break;
                        case ELECTRIC:
                            drawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(120)  // width in px
                                    .height(120) // height in px
                                    .endConfig().buildRound("E", R.color.colorGenericYellow);
                            break;
                        case SEWAGE:
                            drawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(120)  // width in px
                                    .height(120) // height in px
                                    .endConfig().buildRound("S", R.color.colorGenericGreen);
                            break;
                    }

                    trackingFab.setImageDrawable(drawable);
                } else {
                    trackingEnabled = false;
                    utilityTypeFab.setEnabled(true);
                    trackingFab.setImageResource(android.R.drawable.ic_media_play);
                    Toast.makeText(getContext(), "Tracking disabled", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void initializeMapOnClickListener() {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if(trackingEnabled) {
                    switch(selectedUtility) {
                        case WATER:
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(point.latitude, point.longitude))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            break;
                        case GAS:
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(point.latitude, point.longitude))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            break;
                        case ELECTRIC:
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(point.latitude, point.longitude))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            break;
                        case SEWAGE:
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(point.latitude, point.longitude))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            break;
                    }
                }
            }
        });
    }

    // Helper methods

    private void enableUtilities(boolean enable) {
        if(enable) {
            // set visible

            waterUtilityTypeFab.setVisibility(FloatingActionButton.VISIBLE);
            waterUtilityTextView.setVisibility(View.VISIBLE);

            gasUtilityTypeFab.setVisibility(FloatingActionButton.VISIBLE);
            gasUtilityTextView.setVisibility(View.VISIBLE);

            electricUtilityTypeFab.setVisibility(FloatingActionButton.VISIBLE);
            electricUtilityTextView.setVisibility(View.VISIBLE);

            sewageUtilityTypeFab.setVisibility(FloatingActionButton.VISIBLE);
            sewageUtilityTextView.setVisibility(View.VISIBLE);

            // set listeners

            waterUtilityTypeFab.setOnClickListener(this);
            gasUtilityTypeFab.setOnClickListener(this);
            electricUtilityTypeFab.setOnClickListener(this);
            sewageUtilityTypeFab.setOnClickListener(this);

            utilitiesVisible = true;
        } else {
            // set invisible

            waterUtilityTextView.setVisibility(View.INVISIBLE);
            waterUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

            gasUtilityTextView.setVisibility(View.INVISIBLE);
            gasUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

            electricUtilityTextView.setVisibility(View.INVISIBLE);
            electricUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

            sewageUtilityTextView.setVisibility(View.INVISIBLE);
            sewageUtilityTypeFab.setVisibility(FloatingActionButton.INVISIBLE);

            // disconnect listeners

            waterUtilityTypeFab.setOnClickListener(null);
            gasUtilityTypeFab.setOnClickListener(null);
            electricUtilityTypeFab.setOnClickListener(null);
            sewageUtilityTypeFab.setOnClickListener(null);

            utilitiesVisible = false;
        }
    }
}