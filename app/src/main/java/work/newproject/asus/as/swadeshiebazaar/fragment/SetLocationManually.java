package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.GetLocation;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.LocationModel;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.LocationAdapter;
import work.newproject.asus.as.swadeshiebazaar.network.ChildApi;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildApiClints;


public class SetLocationManually extends Fragment {

    public SetLocationManually() {
        // Required empty public constructor
    }

    Toolbar toolbar;
    TextView txt_Skip,txtCurrentLocation,txtManualLocation;
    LinearLayout currentLocationLinear,manualLocationLinear;
    RecyclerView locationRecyclerView;
    ProgressBar progress_circular;
    ChildApi apiChild = ChildApiClints.getClientChild().create(ChildApi.class);
    LocationAdapter offersAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_location_manually, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        txt_Skip = view.findViewById(R.id.txt_Skip);
        txtManualLocation = view.findViewById(R.id.txtManualLocation);
        progress_circular = view.findViewById(R.id.progress_circular);
        manualLocationLinear = view.findViewById(R.id.manualLocationLinear);
        locationRecyclerView = view.findViewById(R.id.locationRecyclerView);
        //setLocation();
        return view;
    }

//    private void setLocation() {
//        showProgress();
//        apiChild.getlocation().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<LocationModel>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull LocationModel productSubCarModel) {
//                        Log.d("TAG", "onSuccess: " + productSubCarModel.getStatus());
//                        if (productSubCarModel.getStatus().equals("success")) {
//                            locationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                            offersAdapter = new LocationAdapter(productSubCarModel.getData(), SetLocationManually.this);
//                            locationRecyclerView.setAdapter(offersAdapter);
//                        }
//                        hideProgress();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        hideProgress();
//                        Log.d("TAG", "onError: " + e.getMessage());
//                    }
//          });
//
//    }
//
//
//    private void showProgress() {
//        progress_circular.setVisibility(View.VISIBLE);
//    }
//
//    private void hideProgress() {
//        progress_circular.setVisibility(View.GONE);
//    }

}