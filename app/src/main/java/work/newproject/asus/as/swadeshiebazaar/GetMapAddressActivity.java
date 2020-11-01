package work.newproject.asus.as.swadeshiebazaar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ModelData;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.CityListModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.add_address_model;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.state_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class GetMapAddressActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    private Button btn_confirm;
    private ImageView img_back;
    public static TextView txt_cityname;
    private ImageView img_direction;
    private RelativeLayout ll_search;
    private ProgressBar pb;
    public static double latitude, longitude;
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private  final static String API_KEY  = "fsPdJIdk";
    MaterialSpinner spinner, spinnerCity;
    private AlertDialog alertDialog;
    List<String> categories;
    List<String> cityCat;
    List<ModelData> dataList;
    List<ModelData> cityList;
    private String spinnerValue, spinnerCityValue;
    private ImageView imgClose;
    String isFrom = "", tag = "", cityname = "";
    LocationManager locationManager;
    Marker Marker;
    CameraPosition position;
    Api api = ApiClints.getClient().create(Api.class);
    String placeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map_address);
        ll_search = findViewById(R.id.ll_search);
        txt_cityname = findViewById(R.id.txt_cityname);
        img_direction = findViewById(R.id.img_direction);
        btn_confirm = findViewById(R.id.btn_confirm);
        img_back = findViewById(R.id.img_back);
        pb = findViewById(R.id.pb);
        getLocation();
        ll_search.setOnClickListener(v -> startAutocompleteActivity());
        img_direction.setOnClickListener(v -> checkPermissions(true));
        img_back.setOnClickListener(v -> onBackPressed());
        btn_confirm.setOnClickListener(v -> comfermAddress());
    }

    private void startAutocompleteActivity() {
        List<com.google.android.libraries.places.api.model.Place.Field> placeFields = new ArrayList<>(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.values()));
        Intent autocompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields).setCountry("IN").build(GetMapAddressActivity.this);
        startActivityForResult(autocompleteIntent, 1001);
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(GetMapAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (GetMapAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GetMapAddressActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 111);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = latti;
                longitude = longi;
                placeName = location.toString();
                AddMarker();
                return;

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = latti;
                longitude = longi;
                placeName = location1.toString();
                AddMarker();
                return;

            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                latitude = latti;
                longitude = longi;
                placeName = location2.toString();
                AddMarker();
                return;
            } else {
                getMyLocation(false);
            }
        }
    }

    public void AddMarker() {
        if (mMap != null) {
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            position = new CameraPosition.Builder().target(latLng).zoom(18f).build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.animateCamera(update);
            if (Marker != null) {
                Marker.showInfoWindow();
                Marker.setDraggable(true);
            }
          //  Marker = mMap.addMarker(new MarkerOptions().position(latLng).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_location_on_24)));
          mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("mylo", "Added Markers");
        mMap = googleMap;
        checkPermissions(false);
        if (mMap != null) {
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.addMarker(new MarkerOptions().position(latLng).title("Your here...").icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin)));
            position = new CameraPosition.Builder().target(latLng).zoom(18f).build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.animateCamera(update);
            if (Marker != null) {
                Marker.showInfoWindow();
                Marker.setDraggable(true);
            }
          Marker = mMap.addMarker(new MarkerOptions().position(latLng).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin)));
        }
    }

    private void checkPermissions(boolean b) {
        int permissionLocation = ContextCompat.checkSelfPermission(GetMapAddressActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation(b);
        }
    }

    private void getMyLocation(final boolean b) {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(GetMapAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat.checkSelfPermission(GetMapAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                                        if (isFrom.equals("Address")) {
                                            if (!b) {
                                                if (!getIntent().getExtras().getString("type").equals("Add")) {
                                                   // getAddress(getIntent().getExtras().getString("Address_id"));
                                                } else {
                                                    getLocation();
                                                }
                                            } else {
                                                getLocation();
                                            }
                                        } else {
                                            getCityname(mylocation.getLatitude(), mylocation.getLongitude());
                                            getLocation();
                                        }
                                    /* mLatLng = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());
                                    setLocationOnMap(mLatLng);*/
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(GetMapAddressActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        } else {
            setUpGClient();
        }
    }

    private void getCityname(double lattitude, double longitude) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(lattitude, longitude, 1);
            Log.i("+++++", "Place: " + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getSubLocality());
            cityname = String.valueOf(addresses.get(0).getAddressLine(0));
            Log.i("+++++", "Place1: " + addresses.get(0).getAddressLine(0));
            if (cityname.equalsIgnoreCase("null")) {
            } else {
                LatLng latLng = new LatLng(lattitude, longitude);
                mMap.addMarker(new MarkerOptions().position(latLng));
                txt_cityname.setText(cityname);
            }
            Log.i("+++++", "Place: " + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getSubLocality());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(GetMapAddressActivity.this);
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppStrings.isNetworkAvailable(GetMapAddressActivity.this)) {
            initViews();
            setUpGClient();
        } else {
            startActivity(new Intent(GetMapAddressActivity.this, NoInternetActivity.class));
        }
    }

    private void initViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Places.initialize(GetMapAddressActivity.this, API_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation(false);
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
            case 1001:
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    txt_cityname.setText(place.getAddress());
                    LatLng mLatLng = place.getLatLng();
                    LatLng latLng = mLatLng;
                    cityname = txt_cityname.getText().toString();
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //mMap.addMarker(new MarkerOptions().position(latLng).title(cityname).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin)));
                    position = new CameraPosition.Builder().target(latLng).zoom(18f).build();
                    CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
                    mMap.animateCamera(update);
                    if (Marker != null) {
                        Marker.showInfoWindow();
                        Marker.setDraggable(true);
                    }
                    Marker = mMap.addMarker(new MarkerOptions().position(latLng).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin)));
                    Log.i("+++++", "Place: " + place.getName() + ", " + place.getId());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i("+++++", "Auto complete error:" + status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                break;

            case 111:
                if (requestCode == RESULT_OK) {
                    getMyLocation(false);
                } else if (requestCode == RESULT_CANCELED)
                    checkPermissions(false);
                break;
        }
    }
    private void comfermAddress() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GetMapAddressActivity.this);
        View vieww = getLayoutInflater().inflate(R.layout.add_address_layout, null);
        final EditText edName = vieww.findViewById(R.id.edName);
        final EditText edLandMark = vieww.findViewById(R.id.edLandMark);
        final EditText edCity = vieww.findViewById(R.id.edPin);
        final Button btAddAddress = vieww.findViewById(R.id.btAddAddress);
        final EditText edHouseNumber = vieww.findViewById(R.id.edHouseNumber);
        final EditText edAreaColony = vieww.findViewById(R.id.edAreaColony);
        spinner = vieww.findViewById(R.id.spinner);
        spinnerCity = vieww.findViewById(R.id.spinnerCity);
        final EditText edMobile = vieww.findViewById(R.id.edMobile);
        imgClose = vieww.findViewById(R.id.imgClose);
        categories = new ArrayList<>();
        dataList = new ArrayList<>();
        cityCat = new ArrayList<>();
        cityList = new ArrayList<>();
        getStateList();
        spinnerValue = "2";
        spinnerCityValue = "2";
        categories = new ArrayList<String>();
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long idd, String item) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (item.equalsIgnoreCase("Select State")) {
                    Toast.makeText(GetMapAddressActivity.this, "Select State", Toast.LENGTH_LONG).show();
                    spinnerValue = "2";
                } else {
                    spinnerValue = item;
                    for (int i = 0; i < dataList.size(); i++) {
                        if (spinnerValue.equals(dataList.get(i).getName())) {
                            getCityList(dataList.get(i).getId());
                            Log.d("TAG", "onItemSelected: " + dataList.get(i).getId());

                            break;
                        }
                    }
                }
            }
        });

        spinnerCity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long idd, String item) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (item.equalsIgnoreCase("Select City")) {
                    Toast.makeText(GetMapAddressActivity.this, "Select State", Toast.LENGTH_LONG).show();
                    spinnerCityValue = "2";
                } else {
                    spinnerCityValue = item;
                }
            }
        });

        btAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString().trim();
                String landmArl = edLandMark.getText().toString().trim();
                String pinCode = edCity.getText().toString().trim();
                String mob = edMobile.getText().toString().trim();
                String houseNumber = edHouseNumber.getText().toString().trim();
                String colony = edAreaColony.getText().toString().trim();
                String spinner = spinnerValue;
                String spinerCity = spinnerCityValue;
                if (name.isEmpty()) {
                    edName.setError("Empty");
                } else if (mob.isEmpty()) {
                    edMobile.setError("Empty");
                } else if (spinner.equalsIgnoreCase("2")) {
                    Toast.makeText(GetMapAddressActivity.this, "Select State", Toast.LENGTH_LONG).show();
                } else if (spinerCity.equalsIgnoreCase("2")) {
                    Toast.makeText(GetMapAddressActivity.this, "Select City", Toast.LENGTH_LONG).show();
                } else if (pinCode.isEmpty()) {
                    edCity.setError("Empty");
                } else if (houseNumber.isEmpty()) {
                    edHouseNumber.setError("Empty");
                } else if (colony.isEmpty()) {
                    edAreaColony.setError("Empty");
                } else if (landmArl.isEmpty()) {
                    edLandMark.setError("Blank");
                } else {
                    addAddres(pinCode, houseNumber, colony, spinerCity, spinner, landmArl, mob, name);
                }
            }
        });

        imgClose.setOnClickListener(v -> alertDialog.dismiss());
        builder.setView(vieww);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void getStateList() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.show();
        api.getState().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<state_model>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull state_model state_model) {
                        if (state_model.getStatus().equalsIgnoreCase("success")) {
                            categories.add("Select State");
                            for (int i = 0; i < state_model.getData().size(); i++) {
                                categories.add(state_model.getData().get(i).getName());
                                ModelData data = new ModelData();
                                data.setId(state_model.getData().get(i).getId());
                                data.setName(state_model.getData().get(i).getName());
                                dataList.add(data);
                            }
                            spinner.setItems(categories);
                        }
                        pd.dismiss();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        pd.dismiss();
                    }
                });
    }


    private void getCityList(String id) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.show();
        api.getCityList(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CityListModel>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CityListModel cityListModel) {
                        Log.d("TAG", "onSuccess: " + cityListModel.getStatus());
                        if (cityListModel.getStatus().equalsIgnoreCase("success")) {
                            cityCat.add("Select City");
                            for (int i = 0; i < cityListModel.getData().size(); i++) {
                                cityCat.add(cityListModel.getData().get(i).getName());
                                ModelData data = new ModelData();
                                data.setId(cityListModel.getData().get(i).getId());
                                data.setName(cityListModel.getData().get(i).getName());
                                cityList.add(data);
                            }
                            spinnerCity.setItems(cityCat);
                            pd.dismiss();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        pd.dismiss();
                        Log.d("TAG", "onError: " + e.getMessage());
                    }
                });
    }

    private void addAddres(String pinCode, String houseNumber, String areaColony, String city, String State, String landMark, String aNumber, String name) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.show();
        api.addAddress(MySharedpreferences.getInstance().get(this, AppStrings.userID), pinCode, houseNumber, areaColony, city, State, landMark, aNumber, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<add_address_model>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull add_address_model add_address_model) {
                        if (add_address_model.getStatus().equals("success")) {
                            alertDialog.dismiss();
                            Intent intent = new Intent (GetMapAddressActivity.this,AddressListActivity.class);
                            startActivity(intent);

                        }
                        Toast.makeText(GetMapAddressActivity.this, add_address_model.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        pd.dismiss();
                    }
           });
    }
    private void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}