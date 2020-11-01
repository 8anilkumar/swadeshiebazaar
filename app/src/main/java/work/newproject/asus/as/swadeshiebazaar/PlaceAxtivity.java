package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import work.newproject.asus.as.swadeshiebazaar.fragment.PlaceOrderFragment;

public class PlaceAxtivity extends AppCompatActivity {

    String addressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_axtivity);
        addressID=getIntent().getStringExtra("addressID");
        openDashBoard();
    }

    private void openDashBoard() {
        Bundle args = new Bundle();
        Fragment fragmentt = new PlaceOrderFragment();
        args.putString("addressID",addressID);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.commit();
    }

}