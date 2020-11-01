package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ModelData;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.adapter.AddressListAdapter;
import work.newproject.asus.as.swadeshiebazaar.fragment.WalletHistoryFragment;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.AddressLIstModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.CityListModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SelectAddressMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.add_address_model;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.state_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class AddressListActivity extends AppCompatActivity implements AddressListAdapter.GetChooseAddress {

    @BindView(R.id.btnShipping)
    Button btnShipping;
    @BindView(R.id.btAddNewwAddress)
    Button btAddNewwAddress;
    private AlertDialog alertDialog;
    List<String> categories;
    List<String> cityCat;
    List<ModelData> dataList;
    List<ModelData> cityList;
    private String spinnerValue, spinnerCityValue;
    String userID;
    private ImageView imgClose;

    MaterialSpinner spinner, spinnerCity;

    @BindView(R.id.nestedRec)
    RecyclerView nestedRec;


    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        btAddNewwAddress.setOnClickListener(v -> addNewAddress());
        btnShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             Intent intent = new Intent(AddressListActivity.this,GetMapAddressActivity.class);
//             startActivity(intent);
            }
        });
        Log.d("TAG", "onClick: " + MySharedpreferences.getInstance().get(this, AppStrings.userID));
        getAddressList();
    }

    private void addNewAddress() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddressListActivity.this);
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
                    Toast.makeText(AddressListActivity.this, "Select State", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AddressListActivity.this, "Select State", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AddressListActivity.this, "Select State", Toast.LENGTH_LONG).show();
                } else if (spinerCity.equalsIgnoreCase("2")) {
                    Toast.makeText(AddressListActivity.this, "Select City", Toast.LENGTH_LONG).show();
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
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull state_model state_model) {
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
                    public void onError(@NonNull Throwable e) {
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
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull CityListModel cityListModel) {
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
                    public void onError(@NonNull Throwable e) {
                        pd.dismiss();
                        Log.d("TAG", "onError: " + e.getMessage());
                 }
           });
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
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
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull add_address_model add_address_model) {
                        if (add_address_model.getStatus().equals("success")) {
                            alertDialog.dismiss();
                            getAddressList();
                        }
                        Toast.makeText(AddressListActivity.this, add_address_model.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        pd.dismiss();
                    }
          });
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
                    @Override
                    public void onSuccess(@NonNull AddressLIstModel addressLIstModel) {
                        if (addressLIstModel.getStatus().equalsIgnoreCase("success")) {
                            AddressListAdapter listAdapter = new AddressListAdapter(AddressListActivity.this, addressLIstModel.getData(),AddressListActivity.this);
                            nestedRec.setLayoutManager(new LinearLayoutManager(AddressListActivity.this, LinearLayoutManager.VERTICAL, false));
                            nestedRec.setAdapter(listAdapter);
                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }

    @Override
    public void addres(String addressID) {
        showProgress();
        api.selectAddress(MySharedpreferences.getInstance().get(this, AppStrings.userID), addressID, "1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<SelectAddressMOdel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SelectAddressMOdel selectAddressMOdel) {
                        if (selectAddressMOdel.getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(AddressListActivity.this, selectAddressMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            goCart();
                        } else {
                            Toast.makeText(AddressListActivity.this, selectAddressMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }

    private void goCart() {
        Intent intent = new Intent(AddressListActivity.this, CartActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goCart();
    }

}
