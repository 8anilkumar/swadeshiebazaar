package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.ProductShopByCatAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;


public class ProductShopByCatFragment extends Fragment {

    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    String keyword;
    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    String catID;
    RoomDataBase db;
    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_shop_by_cat, container, false);
        ButterKnife.bind(this, view);
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        getData();
        return view;
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
                            rvCartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            ProductShopByCatAdapter subCatDetailsAdapter = new ProductShopByCatAdapter(home_data_model.getData().get(1).getLayoutData(), getContext());
                            rvCartList.setAdapter(subCatDetailsAdapter);
                        }
                        hideProgress();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onSuccess: " + e.getMessage());
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
}