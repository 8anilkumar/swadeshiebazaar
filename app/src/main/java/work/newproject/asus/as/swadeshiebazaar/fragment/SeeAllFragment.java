package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import work.newproject.asus.as.swadeshiebazaar.CartActivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.SeeAllAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SellProductAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;

public class SeeAllFragment extends Fragment {


    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    String keyword;
    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    String catID,catType;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);


    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.txtTotalCart)
    TextView txtTotalCart;

    List<CartTable> cartList;
    RoomDataBase db;


    List<home_data_model.Product> viewAllFList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_see_all, container, false);
        ButterKnife.bind(this,view);

        db = RoomDataBase.getInstance(getContext());
        viewAllFList=new ArrayList<>();
        assert getArguments() != null;
        catID = getArguments().getString("catID");
        catType=getArguments().getString("catType");
        viewAllFList= (List<home_data_model.Product>) getArguments().getSerializable("list");
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        imgCart.setOnClickListener(v -> goToCart());
        txtTotalCart.setOnClickListener(v -> goToCart());

        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();

        Log.d("TAG", "onCreateView: "+viewAllFList.size());
        getProductList();
        return view;
    }

    private void getProductList(){
        rvCartList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        SeeAllAdapter offersAdapter = new SeeAllAdapter(viewAllFList, getContext(),SeeAllFragment.this);
        rvCartList.setAdapter(offersAdapter);

    }

    @SuppressLint("SetTextI18n")
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

    public void remove(long productId) {
        db.mainDuo().deleteByProductIDWishList(productId);
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


    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


    private void goToCart() {
        getArguments().remove("list");
        Intent intent = new Intent(getContext(), CartActivity.class);
        startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        if (cartList.isEmpty()) {
            txtTotalCart.setText("0");
        } else {
            txtTotalCart.setText(cartList.size() + "");
        }
    }
}