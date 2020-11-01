package work.newproject.asus.as.swadeshiebazaar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.adapter.CartAdapter;
import work.newproject.asus.as.swadeshiebazaar.auth.AuthActivity;
import work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment.LoginFragment;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.fragment.ProductShopByCatFragment;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.AddressLIstModel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class CartActivity extends AppCompatActivity implements CartAdapter.RefreshList, OnMapReadyCallback {
    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    RoomDataBase roomDataBase;
    List<CartTable> list;

    @BindView(R.id.txtAddress)
    TextView txtAddress;

    @BindView(R.id.addressCard)
    CardView addressCard;


    @BindView(R.id.txtTotalAmount)
    TextView txtTotalAmount;
    ArrayList<Double> sumTotalAmount;


    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    String addressID = "0";


    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    private int valueAddress = 1;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtAddres)
    TextView txtAddres;

    @BindView(R.id.txtNumber)
    TextView txtNumber;


    @BindView(R.id.btProceedPay)
    Button btProceedPay;

    private static LocationManager locationManager;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    float calculateDistanse;

    SharedPreferences sharedPreferences ;
    private static SharedPreferences.Editor editor;
    String userAddres,userlatitude,userlongitude;
    String deliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        sharedPreferences =  getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userAddres = sharedPreferences.getString("USER_SELECT_ADDRESS", "");
        userlatitude = sharedPreferences.getString("LATITUDE", "");
        userlongitude = sharedPreferences.getString("LONGITUDE", "");
        deliveryAddress = txtAddres.getText().toString();

        list = new ArrayList<>();
        imgBack.setOnClickListener(v -> onBackPressed());
        roomDataBase = RoomDataBase.getInstance(this);
        list = roomDataBase.mainDuo().getCartList();
        sumTotalAmount = new ArrayList<>();
        txtAddress.setOnClickListener(v -> addNewAddress());
        btProceedPay.setOnClickListener(v -> placeOrder());
      //  btProceedPay.setOnClickListener(v -> sendCart());
        getCartList();

    }

    private void placeOrder() {
        placeManualLocation();
//        if(userAddres != null){
//            placeManualLocation();
//        }else {
//            getLocation();
//        }
    }

    private void placeManualLocation() {
        if (addressID.equals("0")) {
            Intent i = new Intent(CartActivity.this, AuthActivity.class);
            startActivity(i);
        } else {
            Intent intent = new Intent(CartActivity.this, PlaceAxtivity.class);
            intent.putExtra("addressID", addressID);
            startActivity(intent);
        }
    }

    private void addNewAddress() {
        Intent intent = new Intent(CartActivity.this, AddressListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MySharedpreferences.getInstance().get(this, AppStrings.userID) != null) {
            getAddressList();
//            Intent intent = getIntent();
//            String flag = intent.getStringExtra("flag");
//            if (flag.equals("1")){
//                getAddressList();
//            }else {
//                String selectLocation = intent.getStringExtra("location");
//                String useralterName = intent.getStringExtra("name");
//                String useralterMobile = intent.getStringExtra("mobile");
//               txtAddres.setText(selectLocation);
//                txtName.setText(useralterName);
//                txtNumber.setText(useralterMobile);
//                }
            btProceedPay.setVisibility(View.VISIBLE);
            addressCard.setVisibility(View.VISIBLE);
            if (list.isEmpty()) {
                btProceedPay.setVisibility(View.GONE);
                txtTotalAmount.setVisibility(View.GONE);
            } else {
                btProceedPay.setVisibility(View.VISIBLE);
                txtTotalAmount.setVisibility(View.VISIBLE);
            }
        } else {
            btProceedPay.setVisibility(View.VISIBLE);
            addressCard.setVisibility(View.GONE);
        }
    }

    private void getCartList() {
        list = new ArrayList<>();
        list = roomDataBase.mainDuo().getCartList();

        if (list.isEmpty()) {
            btProceedPay.setVisibility(View.GONE);
            txtTotalAmount.setVisibility(View.GONE);
        } else {
            btProceedPay.setVisibility(View.VISIBLE);
            txtTotalAmount.setVisibility(View.VISIBLE);
        }

        sumTotalAmount = new ArrayList<>();
        adapter = new CartAdapter(this, list);
        rvCartList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCartList.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++) {
            try {
                sumTotalAmount.add(Double.valueOf(list.get(i).getPrice()));
                int sumddata = 0;
                for (double ii : sumTotalAmount)
                    sumddata += ii;
                String a = String.valueOf(sumddata);
                txtTotalAmount.setText("₹ " + a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        getCartList();
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }

    private void getAddressList() {
        showProgress();
        api.getAddressList(MySharedpreferences.getInstance().get(this, AppStrings.userID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddressLIstModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(@NonNull AddressLIstModel addressListModel) {
                        if (addressListModel.getStatus().equalsIgnoreCase("success")) {
                            for (int i = 0; i < addressListModel.getData().size(); i++) {
                                if (addressListModel.getData().get(i).getStatus().equalsIgnoreCase("1")) {
                                    String pinCode = addressListModel.getData().get(i).getPincode();
                                    String state = addressListModel.getData().get(i).getState();
                                    final String mobile = addressListModel.getData().get(i).getAltMobile();
                                    String city = addressListModel.getData().get(i).getCity();
                                    String houseNumber = addressListModel.getData().get(i).getHouseNo();
                                    String area = addressListModel.getData().get(i).getAreaColony();
                                    String landmark = addressListModel.getData().get(i).getLandmark();
                                    addressID = addressListModel.getData().get(i).getAddressId();
                                    txtAddres.setText(houseNumber + " " + landmark + " " + area + " " + pinCode + " " + city + " " + state);
                                    txtName.setText(addressListModel.getData().get(i).getAlternateName());
                                    txtNumber.setText(mobile);
                                    valueAddress = 2;
                                    break;
                                }
                                valueAddress = 1;
                            }
                        }
                        hideProgress();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
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
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses2 = null;
                try {
                    addresses2 = geocoder.getFromLocation(26.753668, 83.375276, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses2.size() > 0) {
                    String countryName2 = addresses2.get(0).getSubLocality();
                    place2 = new MarkerOptions().position(new LatLng(26.753668, 83.375276)).title(countryName2);
                    float results[] = new float[1];
                    Location.distanceBetween(locationGPS.getLatitude(),locationGPS.getLongitude(),26.753668,83.375276,results);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.snippet("Distance = "+ results[0]);
                    calculateDistanse = (results[0]/1000);
                    if(calculateDistanse < 8) {
                        if (deliveryAddress != "") {
                            if (addressID.equals("0")) {
                                Intent i = new Intent(CartActivity.this, AuthActivity.class);
                                startActivity(i);
                            } else {
                                Intent intent = new Intent(CartActivity.this, PlaceAxtivity.class);
                                intent.putExtra("addressID", addressID);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(this, "Please fill delivery address", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Sorry we not abale to deliver product at this location ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    place2 = new MarkerOptions().position(new LatLng(26.753668, 83.375276)).title("Service Not Available");
                }
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
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
}