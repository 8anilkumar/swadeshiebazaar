package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import work.newproject.asus.as.swadeshiebazaar.adapter.SearcProductAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.KeyWordModel;

public class SearchProductListActivity extends AppCompatActivity implements SearcProductAdapter.GetData,SearcProductAdapter.Remove{

    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    String keyword;
    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.txtTotalCart)
    TextView txtTotalCart;
    RoomDataBase db;
    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    List<CartTable> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_list);
        ButterKnife.bind(this);
        keyword = getIntent().getStringExtra("keyword");
        db = RoomDataBase.getInstance(this);
        imgCart.setOnClickListener(v -> goToCart());
        txtTotalCart.setOnClickListener(v -> goToCart());
        imgBack.setOnClickListener(v -> onBackPressed());
        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();
        searchList();
    }

    //

    private void searchList() {
        showProgress();
        api.getProductList(keyword, "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<KeyWordModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull KeyWordModel home_data_model) {
                        Log.d("TAG", "onSuccess: " + home_data_model.getStatus());
                        if (home_data_model.getStatus().equals("success")) {
                            rvCartList.setLayoutManager(new GridLayoutManager(SearchProductListActivity.this, 2));
                            SearcProductAdapter offersAdapter = new SearcProductAdapter(home_data_model.getData(), SearchProductListActivity.this,SearchProductListActivity.this);
                            rvCartList.setAdapter(offersAdapter);
                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
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

    @Override
    public void remove(long productId) {
        db.mainDuo().deleteByProductIDWishList(productId);
    }

    @Override
    public void getData(String name, long productID, String actualPrice, String image) {
        wishListTable dataBase = new wishListTable();
        dataBase.setName(name);
        String q = String.valueOf("0");
        dataBase.setQty(q);
        dataBase.setImage(image);
        dataBase.setType("0");
        float value = Float.parseFloat(actualPrice);

        dataBase.setPrice("0");
        dataBase.setProductID(productID);

        dataBase.setActualPrice(actualPrice);
        db.mainDuo().insertWishTable(dataBase);
    }

    private void goToCart() {
        Intent intent = new Intent(SearchProductListActivity.this, CartActivity.class);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (cartList.isEmpty()) {
            txtTotalCart.setText("0");
        } else {
            txtTotalCart.setText(cartList.size() + "");
        }
    }


    public void refresh(){
        cartList=new ArrayList<>();
        cartList = db.mainDuo().getCartList();
        if (cartList.isEmpty()) {
            txtTotalCart.setText("0");
        } else {
            txtTotalCart.setText(cartList.size() + "");
        }
    }
}