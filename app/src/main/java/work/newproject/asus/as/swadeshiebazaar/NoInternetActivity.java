package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static work.newproject.asus.as.swadeshiebazaar.utils.AppStrings.isNetworkAvailable;

public class NoInternetActivity extends AppCompatActivity {

    TextView tv_tryagain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        tv_tryagain = findViewById(R.id.tv_tryagain);

        tv_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(NoInternetActivity.this)) {
                    finish();
                } else {

                }
            }
        });

    }
}