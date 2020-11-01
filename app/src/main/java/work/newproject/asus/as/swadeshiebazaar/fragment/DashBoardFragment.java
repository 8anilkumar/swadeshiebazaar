package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import work.newproject.asus.as.swadeshiebazaar.MyOrderHistoryActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.SearchProductListActivity;
import work.newproject.asus.as.swadeshiebazaar.TeramsAndCondidionActivity;
import work.newproject.asus.as.swadeshiebazaar.adapter.HomeAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.OffersAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.ProductAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SliderImageAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SubCatLIstAdapter;
import work.newproject.asus.as.swadeshiebazaar.auth.AuthActivity;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.KeyWordModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.MyProfileModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class DashBoardFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    RoomDataBase db;

    @BindView(R.id.rvHomeData)
    RecyclerView rvHomeData;

    @BindView(R.id.rvNewProduct)
    RecyclerView rvNewProduct;

    @BindView(R.id.rvOfferList)
    RecyclerView rvOfferList;

    @BindView(R.id.rvTopFeatured)
    RecyclerView rvFeaturedData;

    @BindView(R.id.tvNewProduct)
    TextView tvNewProduct;

    @BindView(R.id.tvOfferList)
    TextView tvOfferList;

    @BindView(R.id.banner_view)
    BannerViewPager banner_view;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.imgMenu)
    ImageView imgMenu;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;


    @BindView(R.id.txtWallet)
    TextView txtWallet;

    @BindView(R.id.txtHelpLineNumber)
    TextView txtHelpLineNumber;

    @BindView(R.id.wallet)
    TextView wallet;

    @BindView(R.id.imgWallet)
    ImageView imgWallet;


    @BindView(R.id.imgCart)
    ImageView imgCart;

    @BindView(R.id.txtTotalCart)
    TextView txtTotalCart;

    List<wishListTable> list;

    @BindView(R.id.contWallet)
    ConstraintLayout contWallet;

    RoomDataBase roomDataBase;
    List<CartTable> cartList;

    @BindView(R.id.imgMywishList)
    ImageView imgMywishList;

    @BindView(R.id.txtTotalWishList)
    TextView txtTotalWishList;

    @BindView(R.id.txtLogout)
    TextView txtLogout;

    @BindView(R.id.contYourCart)
    ConstraintLayout contYourCart;

    @BindView(R.id.imgCall)
    ImageView imgCall;

    @BindView(R.id.imgWps)
    ImageView imgWps;

    @BindView(R.id.contHome)
    ConstraintLayout contHome;

    @BindView(R.id.imgMail)
    ImageView imgMail;

    @BindView(R.id.edsEarch)
    EditText edsEarch;

    @BindView(R.id.imgSearc)
    ImageView imgSearc;

    @BindView(R.id.contMyOrder)
    ConstraintLayout contMyOrder;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtNumber)
    TextView txtNumber;

    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.cardCatagory)
    CardView cardCatagory;

    @BindView(R.id.rvGroceryStaples)
    RecyclerView rvGroceryStaples;

    @BindView(R.id.tvGroceryStaples)
    TextView tvGroceryStaples;

    @BindView(R.id.rvHarbalProduct)
    RecyclerView rvHarbalProduct;

    @BindView(R.id.tvHarbalProduct)
    TextView tvHarbalProduct;

    @BindView(R.id.contTeansAndcONDITIONS)
    ConstraintLayout contTeansAndcONDITIONS;

    @BindView(R.id.btFSell)
    Button btFSell;

    @BindView(R.id.btNewAlll)
    Button btNewAlll;

    List<home_data_model.LayoutDatum> viewAllFList;
    List<home_data_model.LayoutDatum> viewNewList;

    String userMoney;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);
        imgMenu.setOnClickListener(v -> openDrwaer());
        txtWallet.setOnClickListener(v -> openMyWallet());
        // wallet.setOnClickListener(v -> openMyWallet());
        //imgWallet.setOnClickListener(v -> openMyWallet());
        imgCart.setOnClickListener(v -> goToCart());
        txtTotalCart.setOnClickListener(v -> goToCart());
        db = RoomDataBase.getInstance(getContext());
        //contWallet.setOnClickListener(v -> openMyWallet());
        imgMywishList.setOnClickListener(v -> openMyWishList());
        txtLogout.setOnClickListener(v -> signOut());
        contYourCart.setOnClickListener(v -> intentCart());
        imgCall.setOnClickListener(v -> openDiler());
        imgWps.setOnClickListener(v -> sendWps());
        imgMail.setOnClickListener(v -> sendMail());
        contMyOrder.setOnClickListener(v -> getOrder());

        txtHelpLineNumber.setOnClickListener(v -> helpLine());

        contHome.setOnClickListener(v -> drawer_layout.closeDrawers());

        txtTotalWishList.setOnClickListener(v -> openMyWishList());

        imgSearc.setOnClickListener(v -> searchIcon());

        nav_view.setNavigationItemSelectedListener(this);

        cardCatagory.setOnClickListener(v -> openCatShowBy());

        contTeansAndcONDITIONS.setOnClickListener(v -> temnsAndCondiotioin());

        btFSell.setOnClickListener(v -> openViewFall());
        btNewAlll.setOnClickListener(v -> viewNEw());

        if (MySharedpreferences.getInstance().get(getContext(), AppStrings.userID) == null) {
            txtLogout.setText("Login");
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        edsEarch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!edsEarch.getText().toString().trim().isEmpty()) {
                        String searchItem = edsEarch.getText().toString().trim();
                        searchList(searchItem);
                    }
                    return true;
                }
                return false;
            }
        });

        list = new ArrayList<>();
        getData();
        getProfile();
        return view;
    }

    private void helpLine() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8853108383"));
        startActivity(intent);
    }

    private void openCatShowBy() {
        Bundle args = new Bundle();
        Fragment fragmentt = new ProductShopByCatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }

    private void searchIcon() {
        if (!edsEarch.getText().toString().trim().isEmpty()) {
            String searchItem = edsEarch.getText().toString().trim();
            searchList(searchItem);
        }
    }

    private void goToCart() {
        Intent intent = new Intent(getContext(), CartActivity.class);
//        intent.putExtra("flag","1");
        startActivity(intent);
    }

    private void openMyWallet() {
        Bundle args = new Bundle();
        args.putString("userMoney", userMoney);
        Fragment fragmentt = new WalletHistoryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }

    private void openMyWishList() {
        Bundle args = new Bundle();
        Fragment fragmentt = new WishListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }

    private void openDrwaer() {
        String value = "1";
        if (value.equalsIgnoreCase("1")) {
            drawer_layout.openDrawer(GravityCompat.START);
            value = "2";
        } else {
            drawer_layout.openDrawer(GravityCompat.END);
            value = "1";
        }
    }

    private void getData() {
        showProgress();
        api.getHomeData().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<home_data_model>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull home_data_model home_data_model) {
                        if (home_data_model.getStatus().equalsIgnoreCase("success")) {
                            rvHomeData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            HomeAdapter adapter = new HomeAdapter(getContext(), home_data_model.getData());
                            rvHomeData.setAdapter(adapter);
                            viewAllFList = new ArrayList<>();
                            viewAllFList = home_data_model.getData().get(2).getLayoutData();
                            ProductAdapter featuredProduct = new ProductAdapter(home_data_model.getData().get(2).getLayoutData(), getContext(), DashBoardFragment.this);
                            Collections.shuffle(home_data_model.getData().get(2).getLayoutData());
                            rvFeaturedData.setAdapter(featuredProduct);
                            viewNewList = new ArrayList<>();
                            viewNewList = home_data_model.getData().get(3).getLayoutData();
                            if (home_data_model.getData().get(3).getLayoutData().size() > 1) {
                                tvNewProduct.setVisibility(View.VISIBLE);
                                tvNewProduct.setText(home_data_model.getData().get(3).getTitle());
                                ProductAdapter newProducts = new ProductAdapter(home_data_model.getData().get(3).getLayoutData(), getContext(), DashBoardFragment.this);
                                Collections.shuffle(home_data_model.getData().get(3).getLayoutData());
                                rvNewProduct.setAdapter(newProducts);
                            }

                            if (home_data_model.getData().get(4).getLayoutData().size() > 1) {
                                tvOfferList.setVisibility(View.VISIBLE);
                                tvOfferList.setText(home_data_model.getData().get(4).getTitle());
                                OffersAdapter offersAdapter = new OffersAdapter(home_data_model.getData().get(4).getLayoutData(), getContext());
                                rvOfferList.setAdapter(offersAdapter);
                            }

                            if (home_data_model.getData().get(5).getLayoutData().size() > 1) {
                                rvGroceryStaples.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                SubCatLIstAdapter newProducts = new SubCatLIstAdapter(home_data_model.getData().get(5).getLayoutData(), getContext(), DashBoardFragment.this);
                                // Collections.shuffle(home_data_model.getData().get(5).getLayoutData());
                                rvGroceryStaples.setAdapter(newProducts);
                            }
                            btFSell.setVisibility(View.VISIBLE);
                            btNewAlll.setVisibility(View.VISIBLE);
                            setupViewPager(home_data_model.getData().get(0));
                            hideProgress();
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onSuccess: " + e.getMessage());
                        hideProgress();
                  }
          });
    }

    private void setupViewPager(home_data_model.Datum data) {
        banner_view.setAutoPlay(true)
                .setScrollDuration(800)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSliderGap(getActivity().getResources().getDimensionPixelOffset(R.dimen._4sdp))
                .setIndicatorSliderWidth(getActivity().getResources().getDimensionPixelOffset(R.dimen._4sdp), getActivity().getResources().getDimensionPixelOffset(R.dimen._4sdp))
                .setIndicatorSliderColor(getActivity().getColor(R.color.white), getActivity().getColor(R.color.colorPrimary))
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setInterval(2000)
                .setAdapter(new SliderImageAdapter())
                .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                    }
                }).create(data.getLayoutData());

    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
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

        list = new ArrayList<>();
        list = db.mainDuo().getWishList();
        txtTotalWishList.setText(list.size() + "");
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
        list = new ArrayList<>();
        list = db.mainDuo().getWishList();
        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();
        if (cartList.isEmpty()) {
            txtTotalCart.setText("0");
        } else {
            txtTotalCart.setText(cartList.size() + "");
        }
        if (list.isEmpty()) {
            txtTotalWishList.setText("0");
        } else {
            txtTotalWishList.setText(list.size() + "");
        }
    }

    public void remove(long productId) {
        db.mainDuo().deleteByProductIDWishList(productId);
        list = new ArrayList<>();
        list = db.mainDuo().getWishList();
        if (list.isEmpty()) {
            txtTotalWishList.setText("0");
        } else {
            txtTotalWishList.setText(list.size() + "");
        }
    }

    private void signOut() {
        MySharedpreferences.getInstance().removeAll(getContext());
        Intent intent = new Intent(getContext(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void intentCart() {
        drawer_layout.closeDrawers();
        Intent intent = new Intent(getContext(), CartActivity.class);
        //intent.putExtra("flag","1");
        startActivity(intent);
    }

    private void openDiler() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8853108383"));
        startActivity(intent);
    }

    private void sendWps() {
        drawer_layout.closeDrawers();
        String phoneNumberWithCountryCode = "91+8853108383";
        String message = "Hello";
        startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message))));
    }

    private void sendMail() {
        drawer_layout.closeDrawers();
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:swadeshi.e.bazaar@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "The message");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Mail account not configured", Toast.LENGTH_LONG).show();
        }
    }


    private void searchList(String edsEarch) {
        showProgress();
        api.getProductList(edsEarch, "10")
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
                            hideProgress();
                            Intent intent = new Intent(getContext(), SearchProductListActivity.class);
                            intent.putExtra("keyword", DashBoardFragment.this.edsEarch.getText().toString().trim());
                            startActivity(intent);
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

    private void getOrder() {
        if (MySharedpreferences.getInstance().get(getContext(), AppStrings.userID) != null) {
            drawer_layout.closeDrawers();
            Intent intent = new Intent(getContext(), MyOrderHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        } else {
            drawer_layout.closeDrawers();
            Intent intent = new Intent(getContext(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void getProfile() {
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
                            userMoney = myProfileModel.getData().get(0).getWallet();
                            txtWallet.setText(myProfileModel.getData().get(0).getWallet());
                            txtName.setText(myProfileModel.getData().get(0).getUserName());
                            txtNumber.setText(myProfileModel.getData().get(0).getMobile());
                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
           });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_mycontest:
                    openDiler();
                    return true;
                case R.id.navigation_cart:
                    intentCart();
                    return true;
                case R.id.navigation_service:
                    getServices();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
        return false;
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

    private void temnsAndCondiotioin() {
        drawer_layout.closeDrawers();
        Intent intent = new Intent(getContext(), TeramsAndCondidionActivity.class);
        startActivity(intent);
    }

    private void openViewFall() {
        Bundle args = new Bundle();
        Fragment fragmentt = new SellAllProductFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        args.putSerializable("list", (Serializable) viewAllFList);
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }


    private void viewNEw() {
        Bundle args = new Bundle();
        Fragment fragmentt = new SellAllProductFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        args.putSerializable("list", (Serializable) viewNewList);
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }

    public void getSeelAllList(List<home_data_model.Product> dataList) {
        Bundle args = new Bundle();
        Fragment fragmentt = new SeeAllFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        args.putSerializable("list", (Serializable) dataList);
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }

    public void getServices() {
        Bundle args = new Bundle();
        Fragment fragmentt = new ServicesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }
}
