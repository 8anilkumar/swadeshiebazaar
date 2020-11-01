package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

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
import work.newproject.asus.as.swadeshiebazaar.CartActivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ProductDiscriptionModel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class ProductDiscriptionFragment extends Fragment {


    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @BindView(R.id.productName)
    TextView productName;

    @BindView(R.id.discription)
    TextView discription;



    @BindView(R.id.frontImg)
    ImageView frontImg;

    @BindView(R.id.proustuctPrice)
    TextView proustuctPrice;


    @BindView(R.id.txtAmount)
    TextView txtAmount;

    @BindView(R.id.btAdd)
    TextView btAdd;

    @BindView(R.id.number_qty)
    ElegantNumberButton number_qty;

    @BindView(R.id.imgAmount)
    ImageView imgAmount;


    private String dec="", name="", productBrand="", price="", img1="", img2, img3, img4, productQty="";

    long productID;
    int qty = 1;


    long rowId;

    int checkFirst = 1;

    String prodiuctID;
    @BindView(R.id.imgGoToCary)
    ImageView imgGoToCary;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;


    RoomDataBase db;
    List<CartTable> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_discription, container, false);
        ButterKnife.bind(this, view);
        prodiuctID = getArguments().getString("productID");
        dec = getArguments().getString("dec");
        name = getArguments().getString("name");
        productBrand = getArguments().getString("productBrand");
        price = getArguments().getString("price");
        img1 = getArguments().getString("img1");
        productID = Long.parseLong(getArguments().getString("productID"));
        productQty = getArguments().getString("qty");
        imgAmount.setEnabled(false);
        //   getData();

        String plain = String.valueOf(Html.fromHtml(dec));
        discription.setText(plain);
        productName.setText(name);
        proustuctPrice.setText(price);

        Log.d("TAG", "onCreateView: "+AppStrings.imag_path + img1);
        Glide.with(getContext()).load(AppStrings.cat_path + img1).into(frontImg);

        db = RoomDataBase.getInstance(getContext());

        list = db.mainDuo().getCartList();
        btAdd.setOnClickListener(v -> addItem());
        number_qty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if (newValue != 0) {
                    qty = newValue;
                    addItem();
                } else {
                    resetAll();
                }
            }
        });


        imgGoToCary.setOnClickListener(v -> goToCart());
        return view;
    }

    private void addItem() {
        list = new ArrayList<>();
        list = db.mainDuo().getCartList();
        number_qty.setVisibility(View.VISIBLE);
        btAdd.setVisibility(View.INVISIBLE);
        if (list.isEmpty()) {
            addNewItem();
        } else {
            if (checkFirst == 1) {
                checkFirst = 2;
                addNewItem();
            } else {
                updateItem(productID);
            }
        }
    }

    private void addNewItem() {
        CartTable dataBase = new CartTable();
        dataBase.setName(name);
        String q = String.valueOf(qty);
        dataBase.setQty(q);
        dataBase.setImage(img1);
        dataBase.setType(productQty);
        float value = Float.parseFloat(price);
        float total = qty * value;
        String finamlValue = String.valueOf(total);
        dataBase.setPrice(finamlValue);
        dataBase.setProductID(productID);
        Log.d("TAG", "addNewItem: " + prodiuctID);
        dataBase.setActualPrice(price);
        rowId = db.mainDuo().insertCartTable(dataBase);
        Log.d("TAG", "addItem: " + rowId);
        txtAmount.setText("₹ " + finamlValue);
        list = new ArrayList<>();
        list = db.mainDuo().getCartList();
        checkFirst = 2;
    }


    private void getData() {
        showProgress();
        api.getProductDeatils(prodiuctID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProductDiscriptionModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ProductDiscriptionModel productDiscriptionModel) {

                        if (productDiscriptionModel.getStatus().equalsIgnoreCase("success")) {
                            String plain = Html.fromHtml(productDiscriptionModel.getData().getProductDetail().get(0).getShrtDescription()).toString();
                            Glide.with(getContext()).load(AppStrings.imag_path + productDiscriptionModel.getData().getProductDetail().get(0).getImage()).into(frontImg);
                            discription.setText(plain);

                            productName.setText(productDiscriptionModel.getData().getProductDetail().get(0).getName());
                            proustuctPrice.setText("₹ " + productDiscriptionModel.getData().getProductDetail().get(0).getPrice());
                            img1 = productDiscriptionModel.getData().getProductDetail().get(0).getImage();
                            name = productDiscriptionModel.getData().getProductDetail().get(0).getName();
                            price = productDiscriptionModel.getData().getProductDetail().get(0).getPrice();
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

    private void resetAll() {

        checkFirst = 1;
        db.mainDuo().deleteByProductID(productID);
        number_qty.setNumber("1");
        qty = 1;
        btAdd.setVisibility(View.VISIBLE);
        number_qty.setVisibility(View.INVISIBLE);
        txtAmount.setText("₹ 00");
    }

    @Override
    public void onResume() {
        super.onResume();
        list = new ArrayList<>();
        list = db.mainDuo().getCartList();

        if (list.size() != 0) {
            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).getProductID() == productID) {
                    number_qty.setNumber(list.get(k).getQty());
                    txtAmount.setText("₹ " + list.get(k).getPrice());
                    qty = Integer.parseInt(list.get(k).getQty());
                    btAdd.setVisibility(View.INVISIBLE);
                    number_qty.setVisibility(View.VISIBLE);
                    checkFirst = 2;
                    break;
                } else {
                    number_qty.setNumber("1");
                }
            }

        } else {
            checkFirst = 1;
            resetAll();
        }

    }

    private void goToCart() {
        if (list.size() == 0) {
            Toast.makeText(getContext(), "Cart List Empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), CartActivity.class);
            startActivity(intent);
        }
    }


    private void updateItem(long pvalue) {
        float value = Float.parseFloat(price);
        float total = qty * value;
        String finamlValue = String.valueOf(total);
        db.mainDuo().update(pvalue, String.valueOf(qty), finamlValue);
        Log.d("TAG", "onValueChange: " + finamlValue);
        txtAmount.setText("₹ " + finamlValue);
    }

}