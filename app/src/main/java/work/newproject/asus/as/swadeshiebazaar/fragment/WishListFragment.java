package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.WishListAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;


public class WishListFragment extends Fragment implements WishListAdapter.RefreshList {

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;


    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);


    @BindView(R.id.rvCartList)
    RecyclerView rvWishList;

    RecyclerView.Adapter adapter;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    RoomDataBase roomDataBase;
    List<wishListTable> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        roomDataBase = RoomDataBase.getInstance(getContext());
        getWishList();
        return view;
    }


    private void getWishList() {
        list = new ArrayList<>();
        list = roomDataBase.mainDuo().getWishList();
        adapter = new WishListAdapter(getContext(), list, WishListFragment.this);
        rvWishList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvWishList.setAdapter(adapter);


    }

    @Override
    public void update() {
        getWishList();
    }
}