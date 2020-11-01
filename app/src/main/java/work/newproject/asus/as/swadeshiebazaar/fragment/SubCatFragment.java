package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

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
import work.newproject.asus.as.swadeshiebazaar.adapter.SubAndCHildData;
import work.newproject.asus.as.swadeshiebazaar.adapter.SubCatAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;


public class SubCatFragment extends Fragment {

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
    String flag,position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_cat, container, false);
        ButterKnife.bind(this, view);
        catID = getArguments().getString("catID");
        flag = getArguments().getString("flag");
        position = getArguments().getString("position");
        Log.d("TAG", "onCreateView: " + catID);
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());

        getSubAndChild();

        //getList();
        return view;
    }

    private void getList() {

        showProgress();
        api.getSub(catID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SubCatModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SubCatModel subCatModel) {

                        if (subCatModel.getStatus().equals("success")) {
                            rvCartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            SubCatAdapter subCatAdapter = new SubCatAdapter(getContext(), subCatModel.getData(), SubCatFragment.this);
                            rvCartList.setAdapter(subCatAdapter);
                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
           });
    }

    public void getChildID(String id) {
        showProgress();
        api.getChild(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ChildCatMOdel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull ChildCatMOdel childCatMOdel) {
                        hideProgress();
                        if (childCatMOdel.getStatus().equals("success")) {
                            Bundle args = new Bundle();
                            args.putString("catID", id);
                            args.putString("catType", "sub");
                            Fragment fragmentt = new getChildFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            fragmentt.setArguments(args);
                            transaction.replace(R.id.fragment, fragmentt);
                            transaction.addToBackStack("social").commit();
                        } else {
                            Log.d("TAG", "onSuccess: " + id);
                            Bundle args = new Bundle();
                            args.putString("catID", id);
                            args.putString("catType", "child");
                            Fragment fragmentt = new ProductSubCatFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            fragmentt.setArguments(args);
                            transaction.replace(R.id.fragment, fragmentt);
                            transaction.addToBackStack("social").commit();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
                        hideProgress();
                        Bundle args = new Bundle();
                        args.putString("catID", id);
                        args.putString("catType", "sub");
                        Fragment fragmentt = new ProductSubCatFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        fragmentt.setArguments(args);
                        transaction.replace(R.id.fragment, fragmentt);
                        transaction.addToBackStack("social").commit();
                    }
           });
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }

    private void getSubAndChild() {
        showProgress();
        api.getSubAndChild(catID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<DropDownCatMOdel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull DropDownCatMOdel dropDownCatMOdel) {
                        if(flag.equals("2")) {
                            List<DropDownCatMOdel.Datum> list = dropDownCatMOdel.getData();
                            Bundle args = new Bundle();
                            args.putString("flag",flag);
                            args.putString("catID", catID);
                            args.putString("catType", "sub");
                            Fragment fragmentt = new ProductSubCatFragment(list,flag);
                            FragmentManager manager = (getActivity()).getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            fragmentt.setArguments(args);
                            transaction.replace(R.id.fragment, fragmentt);
                            transaction.commit();
                            //transaction.addToBackStack("social").commit();
                        }else {
                            if (dropDownCatMOdel.getStatus().equalsIgnoreCase("success")){
                                rvCartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                SubAndCHildData subCatAdapter = new SubAndCHildData(getContext(), dropDownCatMOdel.getData(),flag);
                                ((SimpleItemAnimator) rvCartList.getItemAnimator()).setSupportsChangeAnimations(false);
                                rvCartList.setHasFixedSize(true);
                                rvCartList.setAdapter(subCatAdapter);
                            }
                            hideProgress();
                        }
                     }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }
}