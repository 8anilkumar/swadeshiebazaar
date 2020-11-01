package work.newproject.asus.as.swadeshiebazaar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.auth.AuthActivity;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class SplashScreen extends AppCompatActivity {

    int time = 3;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        imgLogo = findViewById(R.id.imgLogo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        imgLogo.startAnimation(animation);
        Glide.with(this).load(R.drawable.transprentlogo).into(imgLogo);
        Completable.timer(time, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(SplashScreen.this::intentServiceFire);
        Log.d("TAG", "onCreate: "+MySharedpreferences.getInstance().get(this,AppStrings.userID));
    }

//    private void intentServiceFire() {
//        if (MySharedpreferences.getInstance().get(this, AppStrings.userID) != null) {
//            Intent intent = new Intent(SplashScreen.this, GetLocation.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(SplashScreen.this, GetLocation.class);
//            startActivity(intent);
//            finish();
//        }

     private void intentServiceFire() {
        if (MySharedpreferences.getInstance().get(this, AppStrings.userID) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}





