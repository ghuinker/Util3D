package com.se319s18a9.util3d.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.ConnectedPoint;
import com.se319s18a9.util3d.backend.Line;
import com.se319s18a9.util3d.backend.Map;
import com.se319s18a9.util3d.backend.User;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapFragment extends Fragment implements View.OnClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    Map graph;
    Task<byte []> download;
    Task upload;
    LoadingDialogFragment loadingDialogFragment;
    String filename;

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

    ArrayList<MarkerOptions> markers = new ArrayList<>();

    public MapFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        graph = new Map();
        //graph.setSavedPoint(graph.addNewLine("Electric").getNullHeadPoint());
                        /*
                String test = User.getInstance().getStringFromFirebase("testJSON1");
                try {
                    graph.readFromJSON(test);
                }
                catch(Exception e)
                {

                }
                */
        //byte[] testByte = User.getInstance().getFileFromFirebaseStorage("testJSON1.txt");
        //String test = new String(testByte);

        byte[] testByte2 = {123, 34, 108, 105, 110, 101, 48, 34, 58, 123, 34, 116, 121, 112, 101, 34, 58, 34, 69, 108, 101, 99, 116, 114, 105, 99, 34, 44, 34, 105, 110, 105, 116, 105, 97, 108, 80, 111, 105, 110, 116, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 48, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 48, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 45, 49, 56, 46, 56, 54, 56, 49, 55, 57, 57, 54, 48, 54, 55, 56, 51, 53, 56, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 53, 46, 56, 53, 57, 51, 54, 48, 51, 53, 57, 54, 48, 57, 49, 50, 55, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 49, 51, 46, 48, 49, 49, 54, 56, 53, 48, 56, 50, 49, 55, 56, 56, 55, 57, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 50, 54, 46, 51, 54, 55, 50, 48, 48, 57, 49, 49, 48, 52, 53, 48, 55, 56, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 49, 57, 46, 53, 51, 50, 49, 55, 52, 48, 54, 54, 54, 49, 49, 52, 52, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 49, 53, 46, 57, 51, 55, 52, 56, 52, 56, 48, 48, 56, 49, 53, 53, 56, 50, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 52, 57, 46, 51, 48, 54, 48, 49, 53, 51, 52, 57, 51, 51, 56, 50, 50, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 57, 46, 50, 53, 55, 56, 50, 56, 52, 56, 49, 52, 57, 53, 51, 56, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 53, 49, 46, 51, 57, 57, 50, 48, 49, 56, 56, 56, 51, 57, 51, 52, 49, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 49, 57, 46, 49, 48, 49, 53, 52, 52, 57, 53, 51, 56, 56, 50, 54, 57, 52, 125, 125, 44, 34, 99, 104, 105, 108, 100, 49, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 49, 57, 46, 55, 53, 50, 57, 48, 51, 48, 50, 49, 48, 57, 54, 51, 56, 55, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 52, 56, 46, 49, 54, 52, 48, 51, 49, 57, 56, 57, 56, 55, 50, 52, 53, 54, 44, 34, 99, 104, 105, 108, 100, 48, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 57, 46, 50, 49, 55, 55, 54, 49, 57, 48, 57, 56, 49, 48, 52, 57, 49, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 51, 55, 46, 55, 51, 52, 51, 50, 54, 54, 48, 56, 52, 55, 57, 48, 50, 125, 125, 44, 34, 99, 104, 105, 108, 100, 50, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 52, 56, 46, 50, 50, 52, 54, 54, 48, 53, 56, 55, 56, 54, 53, 57, 51, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 52, 50, 46, 52, 50, 49, 56, 52, 52, 57, 51, 54, 57, 48, 55, 50, 57, 125, 125, 44, 34, 99, 104, 105, 108, 100, 49, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 51, 51, 46, 54, 50, 54, 56, 48, 48, 49, 51, 57, 51, 54, 55, 55, 55, 52, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 55, 56, 46, 48, 52, 54, 57, 48, 50, 56, 50, 55, 57, 49, 56, 53, 51, 125, 125, 44, 34, 99, 104, 105, 108, 100, 49, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 45, 52, 54, 46, 56, 56, 48, 50, 51, 52, 55, 52, 50, 52, 53, 52, 51, 54, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 56, 46, 50, 48, 51, 49, 53, 52, 55, 50, 55, 56, 49, 54, 53, 56, 50, 125, 44, 34, 99, 104, 105, 108, 100, 50, 34, 58, 123, 34, 108, 97, 116, 105, 116, 117, 100, 101, 34, 58, 45, 50, 55, 46, 57, 57, 52, 52, 50, 48, 48, 54, 49, 57, 56, 50, 49, 49, 53, 44, 34, 108, 111, 110, 103, 105, 116, 117, 100, 101, 34, 58, 45, 51, 55, 46, 57, 54, 56, 55, 50, 48, 49, 54, 48, 52, 50, 52, 55, 49, 125, 125, 125, 125, 125};
        String test2 = new String(testByte2);

        //renderFromScratch();
        //User.getInstance().writeFileToFirebaseStorage("test7531");

        boolean loadFromFile;
        filename = null;
        if(getArguments()!=null){
            loadFromFile=getArguments().getBoolean("LoadMap");
            filename = getArguments().getString("ProjectName");
        }
        else{
            loadFromFile = false;
        }
        if(filename==null)
        {
            //TODO: generate non-conflicting name
            filename = "newfile";
        }
        if(loadFromFile) {
            download = User.getInstance().getFileFromFirebaseStorage(filename, this::loadDownloadedMap);
            loadingDialogFragment = new LoadingDialogFragment();
            loadingDialogFragment.setCancelable(false);
            Bundle messageArgument = new Bundle();
            messageArgument.putString("message", "Loading Map");
            loadingDialogFragment.setArguments(messageArgument);
            loadingDialogFragment.show(getActivity().getFragmentManager(), null);
        }
        else{
            //setup blank map
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Set toolbar title
        final Bundle bundle = getArguments();
        getActivity().setTitle(bundle.getString("ProjectName"));

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

                renderFromScratch();
            }
        });

        return v;
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
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (MarkerOptions marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50); // padding = 50 px
                googleMap.animateCamera(cu);
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

    private class PointAndPolyline{
        private ConnectedPoint savedPoint;
        private Polyline savedPoly;

        PointAndPolyline(ConnectedPoint point, Polyline poly){
            savedPoint = point;
            savedPoly = poly;
        }

        public ConnectedPoint getConnectedPoint()
        {
            return savedPoint;
        }

        public Polyline getSavedPoly()
        {
            return savedPoly;
        }
    }

    private void renderPoint(ConnectedPoint point){
        graph.setSavedPoint(point);
        if(!point.isRoot()) {
            MarkerOptions markerOptions = new MarkerOptions().position(point.getLatLng());
            Marker marker = googleMap.addMarker(markerOptions);
            Polyline polyline = null;
            if(!point.getParentPoint().isRoot()) {
                PolylineOptions polylineOptions = new PolylineOptions().add(point.getParentPoint().getLatLng(), point.getLatLng());
                polyline = googleMap.addPolyline(polylineOptions);
            }
            marker.setTag(new PointAndPolyline(point, polyline));
        }
        if(point.getChildren()!=null){
            int childPointIndex = 0;
            while(childPointIndex<point.getChildren().size())
            {
                renderPoint(point.getChildren().get(childPointIndex));
                childPointIndex++;
            }
        }
    }

    private void renderFromScratch(){
        googleMap.clear();
        for (Line line:graph.getLines()) {
            renderPoint(line.getNullHeadPoint());
        }
    }

    public void loadDownloadedMap(){
        try {
            if(download.getException()!=null){
                throw download.getException();
            }
            graph.readFromJSON(new String(download.getResult()));
            LoadingDialogFragment temp = loadingDialogFragment;
            loadingDialogFragment = null;
            temp.dismiss();
            //TODO: remove?
            if(googleMap!=null) {
                renderFromScratch();
            }
        }
        catch(Exception e){
            LoadingDialogFragment temp = loadingDialogFragment;
            loadingDialogFragment = null;
            temp.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public void saveCallback() {
        saveOrExit(false);
    }

    public void saveAndExitCallback(){
        saveOrExit(true);
    }

    private void saveOrExit(boolean saveAndExit) {
        if(!upload.isSuccessful()){
            Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_LONG).show();
        }
        LoadingDialogFragment temp = loadingDialogFragment;
        loadingDialogFragment = null;
        temp.dismiss();
        if(saveAndExit){
            getActivity().getSupportFragmentManager().popBackStackImmediate("dashboardIdentifier", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void dialogSaveHelper(AlertDialog dialog, String newFilename, boolean exitAfterSave)
    {
        filename = newFilename;
        dialog.dismiss();
        try{
            loadingDialogFragment = new LoadingDialogFragment();
            loadingDialogFragment.setCancelable(false);
            Bundle messageArgument = new Bundle();
            if(exitAfterSave) {
                messageArgument.putString("message", "Saving and exiting");
                upload = User.getInstance().writeFileToFirebaseStorage(filename, graph.writeToJSON().getBytes(),this::saveAndExitCallback);
            }else{
                messageArgument.putString("message", "Saving map");
                upload = User.getInstance().writeFileToFirebaseStorage(filename, graph.writeToJSON().getBytes(),this::saveCallback);
            }
            loadingDialogFragment.setArguments(messageArgument);
            loadingDialogFragment.show(getActivity().getFragmentManager(), null);
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void saveWithDialog(boolean exitAfterSave){
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        final View dialogView = layoutInflater.inflate(R.layout.dialog_savemap, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
        //TODO: fill with current filename
        ((EditText) dialogView.findViewById(R.id.dialog_saveMap_editText_filename)).setText(filename);
        if(exitAfterSave){
            alertDialogBuilder.setTitle("Save and Exit");
            ((TextView) dialogView.findViewById(R.id.dialog_saveMap_textView_prompt)).setText("Would you like to save before exiting?");
        }
        else
        {
            alertDialogBuilder.setTitle("Save");
            ((TextView) dialogView.findViewById(R.id.dialog_saveMap_textView_prompt)).setText("Would you like to save?");
        }
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialogSaveHelper((AlertDialog) dialog, ((EditText) dialogView.findViewById(R.id.dialog_saveMap_editText_filename)).getText().toString(), exitAfterSave);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().getSupportFragmentManager().popBackStackImmediate("dashboardIdentifier", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initializeMapOnClickListener() {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if(trackingEnabled) {
                    ConnectedPoint currentPoint = graph.getSavedPoint().addAChild(point.latitude, point.longitude);
                    MarkerOptions markerOptions = new MarkerOptions().position(currentPoint.getLatLng());

                    //TODO: set polyline color and make markers look better
                    switch(selectedUtility) {
                        case WATER:
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            break;
                        case GAS:
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            break;
                        case ELECTRIC:
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                            break;
                        case SEWAGE:
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            break;
                    }
                    //TODO: Determine why this was added and then delete. Seems unnecessary.
                    markers.add(markerOptions);

                    Marker marker = googleMap.addMarker(markerOptions);
                    Polyline polyline = null;
                    if(!currentPoint.getParentPoint().isRoot())
                    {
                        PolylineOptions polylineOptions = new PolylineOptions().add(currentPoint.getParentPoint().getLatLng(),currentPoint.getLatLng());
                        polyline = googleMap.addPolyline(polylineOptions);
                    }
                    marker.setTag(new PointAndPolyline(currentPoint,polyline));
                    graph.setSavedPoint(currentPoint);

                    //String logMessage;
                    //try {
                    //    logMessage = graph.writeToJSON();
                    //} catch(JSONException e)
                    //{
                    //    logMessage = "Failed to write to JSON";
                    //}
                    //Log.d("MapJSON",Arrays.toString(logMessage.getBytes()));
                }
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                graph.setSavedPoint(((PointAndPolyline) marker.getTag()).getConnectedPoint());
                return false;
            }
        });

    //TODO: Add marker drag listener
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