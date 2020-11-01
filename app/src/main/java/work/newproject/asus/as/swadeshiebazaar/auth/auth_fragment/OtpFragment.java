package work.newproject.asus.as.swadeshiebazaar.auth.auth_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.MainActivity;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OtpVerifyModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SignUpModel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;


public class OtpFragment extends Fragment {


    CompositeDisposable disposable = new CompositeDisposable();
    Api api = ApiClints.getClient().create(Api.class);
    @BindView(R.id.etOtp)
    EditText etOtp;

    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;

    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    @BindView(R.id.tvResendCode)
    TextView tvResendCode;

    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    String number, name, mail, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        ButterKnife.bind(this, view);

        number = getArguments().getString("number");
        name = getArguments().getString("name");
        mail = getArguments().getString("mail");
        password = getArguments().getString("password");

        Glide.with(getActivity()).load(R.drawable.s_logo).into(imgLogo);
        fabNext.setOnClickListener(v -> sendOtp());
        tvResendCode.setOnClickListener(v -> resendOtp());
        return view;
    }


    private void resendOtp() {
        showProgress();
        api.userSignUp(name, mail, number, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SignUpModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull SignUpModel signUpModel) {
                        hideProgress();
                        Toast.makeText(getContext(), signUpModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideProgress();
                    }
                });
    }

    private void sendOtp() {
        if (etOtp.getText().toString().trim().isEmpty()) {
            etOtp.setError("Empty");
        } else {

            showProgress();
            api.otpVerfy(number, etOtp.getText().toString().trim())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<OtpVerifyModel>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull OtpVerifyModel otpVerifyModel) {
                            Toast.makeText(getContext(), otpVerifyModel.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            if (otpVerifyModel.getStatus().equals("success")) {
                                MySharedpreferences.getInstance().save(getContext(), AppStrings.userID, otpVerifyModel.getData().get(0).getUserId());
                                goDashBoard();
                            }


                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            hideProgress();
                            Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void showProgress() {
        progress_circular.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progress_circular.setVisibility(View.GONE);
    }

    private void goDashBoard() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}