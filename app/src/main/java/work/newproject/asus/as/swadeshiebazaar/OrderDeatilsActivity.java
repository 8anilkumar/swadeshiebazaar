package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.adapter.OrderDetailsAdapter;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OrderDeatilsMOdel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class OrderDeatilsActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;


    @BindView(R.id.imgBack)
    ImageView imgBack;

    String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deatils);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(v -> onBackPressed());
        orderID = getIntent().getStringExtra("id");
        getOrderList();
    }

    private void getOrderList() {
        showProgress();
        api.getOrderDetails(MySharedpreferences.getInstance().get(this, AppStrings.userID), orderID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new SingleObserver<OrderDeatilsMOdel>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull OrderDeatilsMOdel orderDeatilsMOdel) {
                if (orderDeatilsMOdel.getStatus().equals("success")) {
                    OrderDetailsAdapter adapter = new OrderDetailsAdapter(orderDeatilsMOdel.getData(), OrderDeatilsActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OrderDeatilsActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                }
                hideProgress();
            }

            @Override
            public void onError(@NonNull Throwable e) {
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