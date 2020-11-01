package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ForgortPasswordModel;


public class ForgetPasswordFragment extends Fragment {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;
    @BindView(R.id.btLogin)
    Button btLogin;

    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);

    @BindView(R.id.edUserName)
    EditText edUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this, view);
        btLogin.setOnClickListener(v -> forgotPassword());
        Glide.with(getActivity()).load(R.drawable.s_logo).into(imgLogo);
        return view;
    }

    private void forgotPassword() {
        String number = edUserName.getText().toString().trim();
        if (number.isEmpty()) {
            edUserName.setError("Empty");
        }   else if (edUserName.length() < 10) {
            edUserName.setError("Enter a valid mobile number");
        }  else {
            showProgress();
            api.forhroPassword(number).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<ForgortPasswordModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }
                        @Override
                        public void onSuccess(@NonNull ForgortPasswordModel forgortPasswordModel) {
                            Toast.makeText(getContext(), forgortPasswordModel.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            if (forgortPasswordModel.getStatus().equals("success")) {
                                Toast.makeText(getContext(), forgortPasswordModel.getMessage(), Toast.LENGTH_SHORT).show();
                                openOTP(number);
                            }
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            hideProgress();
                        }
                    });
          }
    }

    private void openOTP(String number) {
        Bundle args = new Bundle();
        Fragment fragmentt = new ForgotPasswordOtpFragment();
        args.putString("number", number);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentt.setArguments(args);
        transaction.replace(R.id.fragment, fragmentt);
        transaction.addToBackStack("otp").commit();
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }
}