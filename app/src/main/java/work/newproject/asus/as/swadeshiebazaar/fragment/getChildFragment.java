package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import work.newproject.asus.as.swadeshiebazaar.ProductSubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.ChilCatAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildCatMOdel;


public class getChildFragment extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_child, container, false);

        ButterKnife.bind(this, view);
        catID = getArguments().getString("catID");
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());


        getChild();
        return view;
    }


    private void getChild() {
        showProgress();
        api.getChild(catID).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ChildCatMOdel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ChildCatMOdel childCatMOdel) {
                        if (childCatMOdel.getStatus().equals("success")) {
                       /*     rvCartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            ChilCatAdapter subCatAdapter = new ChilCatAdapter(getContext(), childCatMOdel.getData(), getChildFragment.this);
                            rvCartList.setAdapter(subCatAdapter);*/


                        }
                        hideProgress();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }


    public void getID(String id) {

        Log.d("TAG", "getID: "+id);
        Bundle args = new Bundle();
        args.putString("catID", id);
        args.putString("catType","child");
        Fragment fragmentt = new ProductSubCatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("social").commit();
    }
        private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }
}