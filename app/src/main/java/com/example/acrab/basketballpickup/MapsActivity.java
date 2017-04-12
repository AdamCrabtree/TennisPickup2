package com.example.acrab.basketballpickup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.acrab.basketballpickup.R.id.map;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks {

    private GoogleMap mMap;
    public GamesDatabase dataSource;
    private MarkerOptions myMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION=1;
    private boolean createMatch = false;
    private ListView currentMatchesList;
    private List<String> current_matches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dataSource = new GamesDatabase(this);
        dataSource.open();
        List<Comment> values = dataSource.getAllComments();
        String[] array = new String[values.size()];
        int index = 0;
        for (Object value : values) {
            array[index] = value.toString();
            index++;
        }
        current_matches = Arrays.asList(array);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

      mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        currentMatchesList = (ListView) findViewById(R.id.currentMatches);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, current_matches);
        currentMatchesList.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.match_create_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createMatch = true;
            }
        });
        currentMatchesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String nameAndTimeString = adapter.getItem(position);
                Intent intent = new Intent(MapsActivity.this, SignUpActivity.class);
                intent.putExtra("NAME_AND_TIME_STRING", nameAndTimeString);
                startActivity(intent);

            }
        });
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
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
        mMap = googleMap;

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(41.655405, -83.614947);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Hello world"));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            public boolean onMarkerClick(Marker arg0) {
                if(createMatch==true) {
                    if (arg0.getTitle().equals("Hello world")) {
                        launchCreateMatch();
                        createMatch = false;
                        return true;
                    }
                }
                if(createMatch==false){
                    Intent expandedMatchViewIntent = new Intent(MapsActivity.this, ExpandedMatchView.class);
                    expandedMatchViewIntent.putExtra("current_matches", (Serializable) current_matches);
                    startActivity(expandedMatchViewIntent);
                    return true;
                }
                return true;
            }
        });

    }



    public void onConnected(Bundle myBundle){

    }
    public void onConnectionSuspended(int j){

    }


    public void launchCreateMatch() {

            Intent intent = new Intent(this, CreateMatchActivity.class);
            startActivity(intent);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }
        public String getString(int position){
            String item = getItem(position);
            return item;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
