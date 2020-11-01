package work.newproject.asus.as.swadeshiebazaar.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MyOrderHistoryActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.PlaceAxtivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.PlaceOrderMOdel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {

    private AlertDialog alertDialog;
    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    Double amount, fAmount;
    String chekbox, walletAmount, date, time, addressID;
    List<CartTable> list = new ArrayList<>();
    RoomDataBase roomDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        amount = Double.valueOf(getIntent().getStringExtra("amount"));
        chekbox = getIntent().getStringExtra("chekbox");
        walletAmount = getIntent().getStringExtra("walletAmount");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        addressID = getIntent().getStringExtra("addressID");
        fAmount = amount * 100;
        roomDataBase = RoomDataBase.getInstance(RazorpayActivity.this);
        list = roomDataBase.mainDuo().getCartList();
        startPayment(String.valueOf(fAmount), getString(R.string.app_name), "", "");

    }

    public void startPayment(final String amount, final String name, final String userNUmber, String mail) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("image","http://anandimagicworld.com/Anandi%20Magic%20World%20Logo.png");
            options.put("currency", "INR");
            options.put("amount", amount);
            JSONObject preFill = new JSONObject();
            preFill.put("email", mail);
            preFill.put("contact", userNUmber);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
            onBackIntent();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            System.out.println(razorpayPaymentID);


            if (chekbox.equals("1")){
                sendData("1",razorpayPaymentID);
            }else {
                sendData("2",razorpayPaymentID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + response, Toast.LENGTH_SHORT).show();
            //  senduserDetails(mysharedpreferences.getSaveUSERID(), "654654754", String.valueOf(amt), "false");
            onBackIntent();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendData(String id,String razoID) {
        if (chekbox.equals("1")) {
            Gson gson = new Gson();
            String jsonCartList = gson.toJson(list);
            api.placeOrder(MySharedpreferences.getInstance().get(RazorpayActivity.this, AppStrings.userID), date, time, addressID, "0", "0"
                    , razoID, jsonCartList, "online").observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<PlaceOrderMOdel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull PlaceOrderMOdel placeOrderMOdel) {

                            Log.d("TAG", "onSuccess: " + placeOrderMOdel.getStatus());

                            if (placeOrderMOdel.getStatus().equals("success")) {
                                alert(placeOrderMOdel.getMessage());
                            } else {
                                Toast.makeText(RazorpayActivity.this, placeOrderMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                            Log.d("TAG", "onError: " + e.getMessage());
                        }
                    });

        } else {
            Gson gson = new Gson();
            String jsonCartList = gson.toJson(list);
            api.placeOrder(MySharedpreferences.getInstance().get(RazorpayActivity.this, AppStrings.userID), date, time, addressID, walletAmount, "1"
                    , razoID, jsonCartList, "online").observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<PlaceOrderMOdel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull PlaceOrderMOdel placeOrderMOdel) {

                            Log.d("TAG", "onSuccess: " + placeOrderMOdel.getStatus());

                            if (placeOrderMOdel.getStatus().equals("success")) {
                                alert(placeOrderMOdel.getMessage());
                            } else {
                                Toast.makeText(RazorpayActivity.this, placeOrderMOdel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                            Log.d("TAG", "onError: " + e.getMessage());
                        }
                    });
        }
    }


    private void onBackIntent() {
        Intent intent = new Intent(this, PlaceAxtivity.class);
        startActivity(intent);
        finish();

    }

    private void alert(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RazorpayActivity.this);
        View view = getLayoutInflater().inflate(R.layout.thanku_dilog, null);
        final Button btKeepShop = view.findViewById(R.id.btKeepShop);
        btKeepShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomDataBase.mainDuo().deleteList();
                Toast.makeText(RazorpayActivity.this, message, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                Intent intent = new Intent(RazorpayActivity.this, MyOrderHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });


        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}