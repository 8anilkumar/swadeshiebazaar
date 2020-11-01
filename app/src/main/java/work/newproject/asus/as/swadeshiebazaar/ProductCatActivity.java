package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCatActivity extends AppCompatActivity {

    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    RecyclerView.Adapter adapter;


    @BindView(R.id.imgBack)
    ImageView imgBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cat);
        ButterKnife.bind(this);

    }
}