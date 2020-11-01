package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import work.newproject.asus.as.swadeshiebazaar.fragment.MyOrderListFragment;

public class MyOrderHistoryActivity extends AppCompatActivity {

    String pageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_history);
        openDashBoard();

    }

    private void openDashBoard() {
        Bundle args = new Bundle();
        Fragment fragmentt = new MyOrderListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MyOrderHistoryActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}