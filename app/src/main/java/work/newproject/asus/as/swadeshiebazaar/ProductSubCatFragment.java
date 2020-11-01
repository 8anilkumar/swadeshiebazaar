package work.newproject.asus.as.swadeshiebazaar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ProductDataModel;
import work.newproject.asus.as.swadeshiebazaar.adapter.ChildProductAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SubItemApdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SubProductAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.horizontal_cardview;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.ChildApi;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ProductSubCarModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;

public class ProductSubCatFragment extends Fragment implements MVpView{

    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    String keyword;
    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;
    String catID, catType;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    ChildApi apiChild = ChildApiClints.getClientChild().create(ChildApi.class);

    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.txtTotalCart)
    TextView txtTotalCart;

    List<CartTable> cartList;
    RoomDataBase db;

    @BindView(R.id.rvCatType)
    RecyclerView rvCatType;

    String main_id, name, flag, postion;
    List<DropDownCatMOdel.ChildCat> horizontalRVData;
    List<DropDownCatMOdel.Datum> subCate;

    public ProductSubCatFragment(List<DropDownCatMOdel.ChildCat> horizontalRVData) {
        this.horizontalRVData = horizontalRVData;
    }

    public ProductSubCatFragment(List<DropDownCatMOdel.Datum> subCate, String flag) {
        this.subCate = subCate;
        this.flag = flag;
    }

    public ProductSubCatFragment() {

    }
    String userId ="16";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_sub_cat, container, false);
        ButterKnife.bind(this, view);
        db = RoomDataBase.getInstance(getContext());
        assert getArguments() != null;
        catID = getArguments().getString("catID");
        catType = getArguments().getString("catType");
        main_id = getArguments().getString("mainID");
        flag = getArguments().getString("flag");
        postion = getArguments().getString("position");
        Log.d("TAG", "onCreateView: " + main_id);
        name = getArguments().getString("name");
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        imgCart.setOnClickListener(v -> goToCart());
        txtTotalCart.setOnClickListener(v -> goToCart());
        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();
        if (flag.equalsIgnoreCase("1")) {
            getProductList(catID, catType);
            getData(name, catID);
        } else {
            getProductList(catID, "main");
            getSubDataat(name, catID);
            getList();
        }
        return view;
    }

    private void getProductList(String id, String type) {
        showProgress();
        api.getSubCat(id, type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProductSubCarModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull ProductSubCarModel productSubCarModel) {
                        Log.d("TAG", "onSuccess: " + productSubCarModel.getStatus());
                        if (productSubCarModel.getStatus().equals("success")) {
                            rvCartList.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            SubProductAdapter offersAdapter = new SubProductAdapter(productSubCarModel.getData(), getContext(), ProductSubCatFragment.this);
                            rvCartList.setAdapter(offersAdapter);
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

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }


    private void goToCart() {
        Intent intent = new Intent(getContext(), CartActivity.class);
        startActivity(intent);
    }


    public void refresh() {
        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();
        if (cartList.isEmpty()) {
            txtTotalCart.setText("0");
        } else {
            txtTotalCart.setText(cartList.size() + "");
        }
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


    private void getList() {
        DropDownCatMOdel.Datum dataa = new DropDownCatMOdel.Datum();
        dataa.setSubCat("All");
        if (subCate.size() == 0) {

        } else {
            if (subCate.get(0).getSubCat() != "All") {
                subCate.add(0, dataa);
            }
            rvCatType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            SubItemApdapter subCatAdapter = new SubItemApdapter(getContext(), subCate, ProductSubCatFragment.this, name,this);
            rvCatType.setAdapter(subCatAdapter);
            subCatAdapter.notifyDataSetChanged();
        }
    }


    private void getSubDataat(String namee, String catID) {
        DropDownCatMOdel.Datum dataa = new DropDownCatMOdel.Datum();
        dataa.setSubCat("All");
        if (subCate.size() == 0) {
        } else {
            if (subCate.get(0).getSubCat() != "All") {
                subCate.add(0, dataa);
            }
            rvCatType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            SubItemApdapter subCatAdapter = new SubItemApdapter(getContext(), subCate, ProductSubCatFragment.this, name,this);
            rvCatType.setAdapter(subCatAdapter);
            subCatAdapter.notifyDataSetChanged();
        }
    }


    private void getData(String namee, String catID) {
        List<ModelData> list = new ArrayList<>();

        ModelData data = new ModelData();
        data.setId(main_id);
        data.setName("All");
        list.add(data);

        DropDownCatMOdel.ChildCat dataa = new DropDownCatMOdel.ChildCat();
        dataa.setName("All");
        if (horizontalRVData.size() == 0) {

        } else {
            if (horizontalRVData.get(0).getName() != "All") {
                horizontalRVData.add(0, dataa);
            }
        }

        ModelData data1 = new ModelData();
        data1.setId(catID);
        data1.setName(namee);
        list.add(data1);
        rvCatType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontal_cardview subatAdapter = new horizontal_cardview(getContext(), horizontalRVData, ProductSubCatFragment.this,this);
        rvCatType.setAdapter(subatAdapter);
    }

    private void getListcat() {
        DropDownCatMOdel.ChildCat dataa = new DropDownCatMOdel.ChildCat();
        dataa.setName("All");
        if (horizontalRVData.size() == 0) {

        } else {
            if (horizontalRVData.get(0).getName() != "All") {
                horizontalRVData.add(0, dataa);
            }
        }
        rvCatType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontal_cardview subCatAdapter = new horizontal_cardview(getContext(), horizontalRVData, ProductSubCatFragment.this,this);
        rvCatType.setAdapter(subCatAdapter);
    }

    private void getChildProduct(String id, String userId) {
        showProgress();
        apiChild.getChildProduct(id,userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProductDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull ProductDataModel productSubCarModel) {
                        Log.d("TAG", "onSuccess: " + productSubCarModel.getStatus());
                        if (productSubCarModel.getStatus().equals("success")) {
                            rvCartList.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            ChildProductAdapter offersAdapter = new ChildProductAdapter(productSubCarModel.getData(), getContext(), ProductSubCatFragment.this);
                            rvCartList.setAdapter(offersAdapter);
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


    public void getSubData (String name, String id) {
        if (!name.equalsIgnoreCase("All")) {
            getProductList(id, "sub");
        } else {
            getList();
            Log.d("TAG", "getSubData: " + main_id);
            getProductList(catID, "main");
        }
    }

    @Override
    public void getProduct(String name, String id) {
        if (!name.equalsIgnoreCase("All")) {
            getChildProduct(id,userId);
        } else {
            getListcat();
            Log.d("TAG", "getCatData: " + main_id);
            getProductList(catID, "sub");
        }
    }
}
