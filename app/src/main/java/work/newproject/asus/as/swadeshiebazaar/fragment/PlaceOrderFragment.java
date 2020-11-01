package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MyOrderHistoryActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.MyProfileModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.PlaceOrderMOdel;
import work.newproject.asus.as.swadeshiebazaar.payment.RazorpayActivity;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;


public class PlaceOrderFragment extends Fragment {

    @BindView(R.id.txtSubtotal)
    TextView totalItme;

    @BindView(R.id.txtGrandTotal)
    TextView txtGrandTotal;
    public static final int REQUEST_CODE = 11;
    FragmentManager fm;


    RoomDataBase roomDataBase;
    List<CartTable> list;
    ArrayList<Double> sumTotalAmount;
    ArrayList<Double> sumTotalActualPrice;

    @BindView(R.id.edDate)
    EditText edDate;
    String selectedDate;

    MaterialSpinner spinner;
    private List<String> timeList = new ArrayList<>();
    private String slotTime = "Select Slot Time";

    @BindView(R.id.btProceed)
    Button btProceed;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;
    int walletAmount = 0, payBelAmount = 0;

    @BindView(R.id.txtWalletAmount)
    TextView txtWalletAmount;

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtTotalAmount)
    TextView txtTotalAmount;

    String addressID, checkBoxStatus = "1";

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    private AlertDialog alertDialog;

    @BindView(R.id.rdCard)
    RadioButton rdCard;

    @BindView(R.id.rdCOD)
    RadioButton rdCOD;

    int flagCashMath = 0;

    @BindView(R.id.txtDeliverCharge)
    TextView txtDeliverCharge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();

        addressID = getArguments().getString("addressID");
        roomDataBase = RoomDataBase.getInstance(getContext());
        list = roomDataBase.mainDuo().getCartList();
        sumTotalAmount = new ArrayList<>();
        sumTotalActualPrice = new ArrayList<>();
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        rdCOD.setOnClickListener(v -> flagCashMath = 1);
        rdCard.setOnClickListener(v -> flagCashMath = 2);
        Log.d("TAG", "onCreateView: " + getCurrentDatAndTime());
        edDate.setOnClickListener(v -> dateDilog());
        getWalletAmount();
        btProceed.setOnClickListener(v -> PlaceOrder());

        timeList.add("Select Slot Time");
        spinner.setItems(timeList);
        getToatlAmount();
        getTotalItem();
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long idd, String item) {
                if (item.equals("Select Slot Time")) {
                    Toast.makeText(getContext(), "Select Time", Toast.LENGTH_SHORT).show();
                } else {
                    slotTime = item;
                }
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtWalletAmount.setText("Wallet Amount ₹ " + "0");
                    int value = payBelAmount - walletAmount;
                    txtGrandTotal.setText("₹ " + value);
                    checkBoxStatus = "2";
                } else {
                    checkBoxStatus = "1";
                    txtGrandTotal.setText("₹ " + payBelAmount);
                    txtWalletAmount.setText("Wallet Amount ₹ " + walletAmount);
                }
            }
        });


        return view;
    }

    @SuppressLint("SetTextI18n")
    private void getTotalItem() {
        if (!list.isEmpty())
            totalItme.setText(list.size() + " ( Items )");
    }

    private void getToatlAmount() {
        for (int i = 0; i < list.size(); i++) {
            try {
                sumTotalAmount.add(Double.valueOf(list.get(i).getPrice()));
                sumTotalActualPrice .add(Double.valueOf(list.get(i).getActualPrice()));

                int sumddata = 0;
                for (double ii : sumTotalAmount)
                    sumddata += ii;
                String a = String.valueOf(sumddata);

                int sumtotaldis = 0;
                for (double ii : sumTotalActualPrice)
                    sumtotaldis += ii;

                payBelAmount = Integer.parseInt(a);
                txtDeliverCharge.setText("₹ " + a);
                if (payBelAmount <= 499){
                    int amount = payBelAmount+30;
                    payBelAmount = amount;
                    txtGrandTotal.setText("₹ " + payBelAmount);
                    txtTotalAmount.setText("₹ "+"30");
                } else {
                    txtGrandTotal.setText("₹ " + payBelAmount);
                    txtTotalAmount.setText("₹ "+"0");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dateDilog() {
        AppCompatDialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(PlaceOrderFragment.this, REQUEST_CODE);
        newFragment.show(fm, "datePicker");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedDate = data.getStringExtra("selectedDate");
            edDate.setText(selectedDate);

            String ss = getCurrentDatAndTime();
            String[] strr = ss.split(" ");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"
            String str2 = strr[0];
            String time = strr[1];
            int t = Integer.parseInt(time);

            Log.d("TAG", "onActivityResult: "+selectedDate);
            Log.d("TAG", "onActivityResult: "+str2);

            timeList=new ArrayList<>();
            if (str2.equals(selectedDate)) {
                if (t <= 7){
                    timeList.add("Select Slot Time");
                    timeList.add("9:00 AM - 12:00 PM");
                    //timeList.add("12:00 PM - 03:00 PM");
                    timeList.add("03:00 PM - 06:00 PM");
                    spinner.setItems(timeList);
                }else if (t < 13 ){
                    timeList.add("Select Slot Time");
              //      timeList.add("12:00 PM - 03:00 PM");
                    timeList.add("03:00 PM - 06:00 PM");
                    spinner.setItems(timeList);
                }else if (t <= 15 ){
                    timeList.add("Select Slot Time");
                //    timeList.add("03:00 PM - 06:00 PM");
                    spinner.setItems(timeList);
                }else if (t <= 18){
                    timeList.add("Select Slot Time");
                    spinner.setItems(timeList);
                }else {
                    timeList.add("Select Slot Time");
                    spinner.setItems(timeList);
                }
            } else {
                timeList.add("Select Slot Time");
                timeList.add("9:00 AM - 12:00 PM");
               // timeList.add("12:00 PM - 03:00 PM");
                timeList.add("03:00 PM - 06:00 PM");
                spinner.setItems(timeList);
            }
        }
    }

    private void getWalletAmount() {
        showProgress();
        api.getProfile(MySharedpreferences.getInstance().get(getContext(), AppStrings.userID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MyProfileModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(@NonNull MyProfileModel myProfileModel) {
                        if (myProfileModel.getStatus().equals("success")) {
                            walletAmount = Integer.parseInt(myProfileModel.getData().get(0).getWallet());
                            txtWalletAmount.setText("Wallet Amount ₹ " + myProfileModel.getData().get(0).getWallet());

                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


    private void PlaceOrder() {
        if (edDate.getText().toString().isEmpty()) {
            edDate.setError("Select Date");
        } else if (slotTime.equals("Select Slot Time")) {
            Toast.makeText(getContext(), "Select Slot Time", Toast.LENGTH_SHORT).show();
        } else if (flagCashMath == 0) {
            Toast.makeText(getContext(), "Select Payment Method", Toast.LENGTH_SHORT).show();
        } else {
            if (flagCashMath == 2) {
                String s = txtGrandTotal.getText().toString();
                String[] str = s.split("₹");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"
                String str1 = str[1];
                Intent intent = new Intent(getContext(), RazorpayActivity.class);
                intent.putExtra("amount", str1);
                intent.putExtra("chekbox", checkBoxStatus);
                intent.putExtra("walletAmount", walletAmount);
                intent.putExtra("date", edDate.getText().toString().trim());
                intent.putExtra("time", slotTime);
                intent.putExtra("addressID", addressID);
                startActivity(intent);
                getActivity().finish();

            } else {
                if (checkBoxStatus.equals("1")) {
                    showProgress();
                    Gson gson = new Gson();
                    String jsonCartList = gson.toJson(list);
                    api.placeOrder(MySharedpreferences.getInstance().get(getContext(), AppStrings.userID), selectedDate, slotTime, addressID, "0", "0"
                            , "0", jsonCartList, "cod").observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new SingleObserver<PlaceOrderMOdel>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onSuccess(@NonNull PlaceOrderMOdel placeOrderMOdel) {

                                    Log.d("TAG", "onSuccess: " + placeOrderMOdel.getStatus());
                                    hideProgress();
                                    if (placeOrderMOdel.getStatus().equals("success")) {
                                        alert(placeOrderMOdel.getMessage());
                                    } else {
                                        Toast.makeText(getContext(), placeOrderMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    hideProgress();
                                    Log.d("TAG", "onError: " + e.getMessage());
                                }
                            });

                } else {
                    showProgress();
                    Gson gson = new Gson();
                    String jsonCartList = gson.toJson(list);
                    api.placeOrder(MySharedpreferences.getInstance().get(getContext(), AppStrings.userID), selectedDate, slotTime, addressID, String.valueOf(walletAmount), "1"
                            , "0", jsonCartList, "cod").observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new SingleObserver<PlaceOrderMOdel>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }
                                @Override
                                public void onSuccess(@NonNull PlaceOrderMOdel placeOrderMOdel) {

                                    Log.d("TAG", "onSuccess: " + placeOrderMOdel.getStatus());
                                    hideProgress();
                                    if (placeOrderMOdel.getStatus().equals("success")) {
                                        alert(placeOrderMOdel.getMessage());
                                    } else {
                                        Toast.makeText(getContext(), placeOrderMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    hideProgress();
                                    Log.d("TAG", "onError: " + e.getMessage());
                                }
                      });
                 }
            }

        }
    }

    private void alert(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.thanku_dilog, null);
        final Button btKeepShop = view.findViewById(R.id.btKeepShop);
        btKeepShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomDataBase.mainDuo().deleteList();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                Intent intent = new Intent(getContext(), MyOrderHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private String getCurrentDatAndTime() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String dateToStr = format.format(today);
        return dateToStr;
    }

}