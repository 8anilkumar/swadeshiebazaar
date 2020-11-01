package work.newproject.asus.as.swadeshiebazaar;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.LocationModel;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.adapter.LocationAdapter;
import work.newproject.asus.as.swadeshiebazaar.auth.AuthActivity;
import work.newproject.asus.as.swadeshiebazaar.network.ChildApi;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildApiClints;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class GetLocation extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    TextView txt_Skip,txtCurrentLocation,txtManualLocation;
    LinearLayout currentLocationLinear,manualLocationLinear;
    RecyclerView locationRecyclerView;
    private static LocationManager locationManager;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    float calculateDistanse;
    ChildApi apiChild = ChildApiClints.getClientChild().create(ChildApi.class);
    ProgressBar progress_circular;
    LocationAdapter offersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt_Skip = findViewById(R.id.txt_Skip);
        txtCurrentLocation = findViewById(R.id.txtCurrentLocation);
        txtManualLocation = findViewById(R.id.txtManualLocation);
        progress_circular = findViewById(R.id.progress_circular);
        currentLocationLinear = findViewById(R.id.currentLocationLinear);
        manualLocationLinear = findViewById(R.id.manualLocationLinear);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);

        txt_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedpreferences.getInstance().get(GetLocation.this, AppStrings.userID) != null) {
                    Intent intent = new Intent(GetLocation.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(GetLocation.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        txtCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        txtManualLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualLocationLinear.setVisibility(View.VISIBLE);
                currentLocationLinear.setVisibility(View.GONE);
                showProgress();
                apiChild.getlocation().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<LocationModel>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull LocationModel productSubCarModel) {
                                Log.d("TAG", "onSuccess: " + productSubCarModel.getStatus());
                                if (productSubCarModel.getStatus().equals("success")) {
                                    locationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                                    offersAdapter = new LocationAdapter(productSubCarModel.getData(), GetLocation.this);
                                    locationRecyclerView.setAdapter(offersAdapter);
                                }
                                hideProgress();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hideProgress();
                                Log.d("TAG", "onError: " + e.getMessage());
                            }
                        });
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = getLastKnownLocation();
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lat, longi, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Address> addresses2 = null;
                try {
                    addresses2 = geocoder.getFromLocation(26.753668, 83.375276, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses.size() > 0) {
                    String countryName = addresses.get(0).getSubLocality();
                    place1 = new MarkerOptions().position(new LatLng(lat, longi)).title(countryName);
                } else {
                    place1 = new MarkerOptions().position(new LatLng(lat, longi)).title("Unable to find country");
                }

                if (addresses2.size() > 0) {
                    String countryName2 = addresses2.get(0).getSubLocality();
                    place2 = new MarkerOptions().position(new LatLng(26.753668, 83.375276)).title(countryName2);
                    float results[] = new float[1];
                    Location.distanceBetween(locationGPS.getLatitude(), locationGPS.getLongitude(), 26.753668, 83.375276, results);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.snippet("Distance = " + results[0]);
                    calculateDistanse = (results[0] / 1000);
                    if (calculateDistanse < 8) {
                        String latitute = Double.toString(lat);
                        String longitute = Double.toString(longi);
                        SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("LATITUDE", latitute);
                        editor.putString("LONGITUDE", longitute);
                        editor.commit();
                        if (MySharedpreferences.getInstance().get(GetLocation.this, AppStrings.userID) != null) {
                                Intent intent = new Intent(GetLocation.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                        } else {
                                Intent intent = new Intent(GetLocation.this, AuthActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(this, "Sorry we not abale to deliver product at this location ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.main);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Location here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length()>0){

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                offersAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return  true;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.attention))
                .setMessage(getString(R.string.do_you_want_to_exit))
                .setNeutralButton(Html.fromHtml(getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(Html.fromHtml(getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}