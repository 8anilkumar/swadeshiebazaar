package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import work.newproject.asus.as.swadeshiebazaar.CartActivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.adapter.ChilCatAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.SeeAllAdapter;
import work.newproject.asus.as.swadeshiebazaar.adapter.ServicesAdapter;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.PojoModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;

public class ServicesFragment extends Fragment {


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_services, container, false);
        ButterKnife.bind(this,view);
        db = RoomDataBase.getInstance(getContext());

        assert getArguments() != null;
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
        imgCart.setOnClickListener(v -> goToCart());
        txtTotalCart.setOnClickListener(v -> goToCart());

        cartList = new ArrayList<>();
        cartList = db.mainDuo().getCartList();

        getProductList();
        return view;
    }


    private void goToCart() {
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

    private void getProductList(){
        rvCartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ServicesAdapter offersAdapter = new ServicesAdapter(getContext(), getPojoList(),ServicesFragment.this);
        rvCartList.setAdapter(offersAdapter);
    }

    private List<PojoModel> getPojoList(){
        List<PojoModel> list=new ArrayList<>();
        list.add(new PojoModel("AC Repair",R.drawable.acrepair));
        list.add(new PojoModel("Plumber services",R.drawable.plumbing_repair_service));
        list.add(new PojoModel("Car wash",R.drawable.carwash));
        list.add(new PojoModel("Painters",R.drawable.house_painting));
        list.add(new PojoModel("Pest Control",R.drawable.pest_control));
        list.add(new PojoModel("Electrician",R.drawable.electrician));
        list.add(new PojoModel("Carpenters",R.drawable.carpenters));

        return list;
    }


    public void getEnq(String sub){

     sendMail(sub);

    }

    private void sendMail(String subject) {
       /* Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info.m1logix@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "The message");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Mail account not configured", Toast.LENGTH_LONG).show();
        }
    }*/


        String[] TO = {"swadeshi.e.bazaar@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:swadeshi.e.bazaar@gmail.com"));

        emailIntent.setData(Uri.parse("mailto:swadeshi.e.bazaar@gmail.com"));

        emailIntent.setType("text/html");
        emailIntent.setPackage("com.google.android.gm");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(emailIntent);
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));


        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }}





